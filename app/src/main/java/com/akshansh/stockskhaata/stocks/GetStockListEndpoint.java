package com.akshansh.stockskhaata.stocks;

import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.Constants.EndpointResultStatus;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.common.database.stocks.StockViewModel;
import com.akshansh.stockskhaata.networking.NetworkException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public interface GetStockListEndpoint {
    interface Callback{
        void onFetchStockSuccessful(List<StockSchema> stocks);
        void onFetchStockFailure(EndpointResultStatus failureReason);
    }
    void getStocksList(@Nullable StockFilterTerm filterTerm,Callback callback)
            throws NetworkException;
}
