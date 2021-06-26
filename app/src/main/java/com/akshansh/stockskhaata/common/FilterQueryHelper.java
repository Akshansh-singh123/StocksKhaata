package com.akshansh.stockskhaata.common;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.akshansh.stockskhaata.stocks.StockFilterTerm;
import com.akshansh.stockskhaata.stocks.StockSearchTerm;

import java.util.Locale;

public class FilterQueryHelper {
    public static SimpleSQLiteQuery getFilterQuery(StockFilterTerm term){
        return new SimpleSQLiteQuery(getFilterQueryCommand(term));
    }

    public static SimpleSQLiteQuery getFilterSearchQuery(StockFilterTerm term, StockSearchTerm searchTerm){
        return new SimpleSQLiteQuery(getFilterSearchQueryCommand(term,searchTerm));
    }

    private static String getFilterSearchQueryCommand(StockFilterTerm term, StockSearchTerm searchTerm) {
        return getFilterQueryCommand(term)+" AND stock_name LIKE '%"+searchTerm.getName()+"%'";
    }

    private static String getFilterQueryCommand(StockFilterTerm term){
        String marketQuery = getMarketQuery(term.isNSE(),term.isBSE());
        String booleanQuery = getBooleanQuery(term.isFavorite(),term.isShortSell());
        String rangeQuery = getRangeQuery(term.getStockPriceLow(),
                                                    term.getStockPriceHigh(),
                                                    term.getGrowthPercentLow(),
                                                    term.getGrowthPercentHigh());
        String orderQuery = getOrderQuery(term.getSortOption());
        String query = "SELECT * FROM stocks WHERE "+rangeQuery;
        if(!marketQuery.equals(""))
            query += " AND "+marketQuery;
        if(!booleanQuery.equals(""))
            query += " AND "+booleanQuery;
        query += orderQuery;
        return query;
    }

    private static String getOrderQuery(String sortOption) {
        String out = "";
        if(sortOption != null) {
            out = " ORDER BY "+sortOption;
        }
        return out;
    }

    private static String getRangeQuery(float stockPriceLow, float stockPriceHigh,
                                        float growthPercentLow, float growthPercentHigh) {
        String out = "";
        out = String.format(Locale.ENGLISH,
                "buy_price BETWEEN %f AND %f AND growth_percentage BETWEEN %f AND %f",
                stockPriceLow,stockPriceHigh,growthPercentLow, growthPercentHigh);
        return out;
    }

    private static String getMarketQuery(boolean nse, boolean bse) {
        String out = "";
        if(nse || bse) {
            if(nse && bse)
                out = "market IN ('"+Constants.markets.get(0)+"', '"+Constants.markets.get(1)+"')";
            else if(nse)
                out = "market IN ('"+Constants.markets.get(0)+"')";
            else {
                out = "market IN ('"+ Constants.markets.get(1)+"')";
            }
        }
        return out;
    }

    private static String getBooleanQuery(boolean favorite, boolean shortSell) {
        String out = "";
        if(favorite || shortSell) {
            if(favorite && shortSell)
                out = "favorite == 1 AND shorted == 1";
            else if(favorite)
                out = "favorite == 1";
            else {
                out = "shorted == 1";
            }
        }
        return out;
    }
}
