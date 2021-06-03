package com.akshansh.stockskhaata.common;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static ArrayList<StockSchema> getStockListMocks() {
        ArrayList<StockSchema> stocks = new ArrayList<>();
        return stocks;
    }

    public static double getGrowth(String buyPriceText, String sellPriceText, String quantityText) {
        double buyPrice = Double.parseDouble(buyPriceText);
        double sellPrice = Double.parseDouble(sellPriceText);
        int quantity = Integer.parseInt(quantityText);
        return quantity*(sellPrice - buyPrice);
    }

    public static double getGrowthPercentage(String buyPriceText, String sellPriceText, String quantityText) {
        double buyPrice = Double.parseDouble(buyPriceText);
        int quantity = Integer.parseInt(quantityText);
        double totalInvestment = quantity*buyPrice;
        return (getGrowth(buyPriceText,sellPriceText,quantityText)*100.0)/totalInvestment;
    }

    public static String getTime(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return format.format(new Date(timestamp));
    }

    public static String getFormattedDouble(double total) {
        String out = "";
        if(total<1000.0){
            return String.format(Locale.ENGLISH,"%.1f",total);
        }else if(total>= 1000.0 && total <100000){
            total = total/1000.0;
            return String.format(Locale.ENGLISH,"%.1fk",total);
        }else if(total>= 100000.0 && total <10000000){
            total = total/100000.0;
            return String.format(Locale.ENGLISH,"%.1fL",total);
        }
        total = total/10000000.0;
        return String.format(Locale.ENGLISH,"%.1fC",total);
    }

    public static String getMarketText(String market) {
        if(Constants.markets.contains(market))
            return Constants.getMarketAbbreviations().get(market);
        throw new IllegalArgumentException("illegal market name");
    }

    public static String getGrowthText(double growth, double growthPercentage) {
        String growthText;
        if(growth >= 0) {
            growthText = String.format(Locale.ENGLISH, "\u20b9 %s (%.2f%%) \u2b06",
                    growth, growthPercentage);
        }
        else{
            growthText = String.format(Locale.ENGLISH, "- \u20b9 %s (%.2f%%) \u2b07",
                    Math.abs(growth), Math.abs(growthPercentage));
        }
        if(growthText.length() > 20)
            return getGrowthTextFormatted(growth,growthPercentage);
        return growthText;
    }

    public static String getGrowthTextFormatted(double growth, double growthPercentage) {
        String growthText;
        if(growth >= 0) {
            growthText = String.format(Locale.ENGLISH, "\u20b9 %s (%.2f%%) \u2b06",
                    Utils.getFormattedDouble(growth), growthPercentage);
        }
        else{
            growthText = String.format(Locale.ENGLISH, "- \u20b9 %s (%.2f%%) \u2b07",
                    Utils.getFormattedDouble(Math.abs(growth)), Math.abs(growthPercentage));
        }
        return growthText;
    }

    public static String getInvestmentText(double totalInvestment) {
        String investmentText = String.format(Locale.ENGLISH,
                "\u20b9 %.2f",totalInvestment);
        if(investmentText.length() > 15)
            return "\u20b9".concat(" ")
                    .concat(getInvestmentTextFormatted(totalInvestment));
        return investmentText;
    }

    public static String getInvestmentTextFormatted(double totalInvestment) {
        return String.format(Locale.ENGLISH,
                "%s",Utils.getFormattedDouble(totalInvestment));
    }
}
