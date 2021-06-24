package com.akshansh.stockskhaata.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Constants {
    public static final String TAG_NAME_ASC = "NAME_ASC";
    public static final String TAG_NAME_DESC = "NAME_DESC";
    public static final String TAG_GROWTH_ASC = "GROWTH_ASC";
    public static final String TAG_GROWTH_DESC = "GROWTH_DESC";
    public static final String TAG_GROWTH_PERCENT_ASC = "GROWTH_PERCENT_ASC";
    public static final String TAG_GROWTH_PERCENT_DESC = "GROWTH_PERCENT_DESC";
    public static final String TAG_STOCK_PRICE_ASC = "STOCK_PRICE_ASC";
    public static final String TAG_STOCK_PRICE_DESC = "STOCK_PRICE_DESC";
    public static final String EXPAND = "EXPAND";
    public static final String COLLAPSE = "COLLAPSE";
    public static final String BSE = "BSE";
    public static final String NSE = "NSE";

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
