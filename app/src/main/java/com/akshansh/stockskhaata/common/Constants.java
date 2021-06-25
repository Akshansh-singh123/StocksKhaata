package com.akshansh.stockskhaata.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Constants {
    public static final String TAG_NAME_ASC = "stock_name ASC";
    public static final String TAG_NAME_DESC = "stock_name DESC";
    public static final String TAG_GROWTH_ASC = "growth ASC";
    public static final String TAG_GROWTH_DESC = "growth DESC";
    public static final String TAG_GROWTH_PERCENT_ASC = "growth_percentage ASC";
    public static final String TAG_GROWTH_PERCENT_DESC = "growth_percentage DESC";
    public static final String TAG_STOCK_PRICE_ASC = "buy_price ASC";
    public static final String TAG_STOCK_PRICE_DESC = "buy_price DESC";
    public static final String TAG_DATE_DESC = "last_updated_timestamp DESC";
    public static final String EXPAND = "EXPAND";
    public static final String COLLAPSE = "COLLAPSE";
    private static final String BSE = "BSE";
    private static final String NSE = "NSE";

    public static final List<String> markets = Arrays.asList(
            "National Stock Exchange (NSE)","Bombay Stock Exchange (BSE)");
    public static final HashMap<String ,String> marketAbbreviations = new HashMap<>();

    public static HashMap<String, String> getMarketAbbreviations() {
        marketAbbreviations.put(markets.get(0),NSE);
        marketAbbreviations.put(markets.get(1),BSE);
        return marketAbbreviations;
    }

    public enum EndpointResultStatus{
        GENERAL_ERROR,SERVER_ERROR,SUCCESS
    }

    public enum UseCaseResult{
        SUCCESS,FAILURE,NETWORK_ERROR
    }

    public enum OperationModes{
        CREATE,UPDATE,DELETE
    }
}
