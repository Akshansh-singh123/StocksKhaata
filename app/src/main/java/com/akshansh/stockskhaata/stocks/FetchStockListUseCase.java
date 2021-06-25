package com.akshansh.stockskhaata.stocks;

import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.common.Constants.EndpointResultStatus;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.networking.NetworkException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FetchStockListUseCase {
    public interface Listener{
        void onStocksListFetchSuccessful(List<StockSchema> stocks);
        void onStocksListFetchFailure(EndpointResultStatus failureReason);
        void onNetworkError();
    }

    private final List<Listener> listeners;
    private final GetStockListEndpoint getStockListEndpoint;

    @Inject
    public FetchStockListUseCase(GetStockListEndpoint getStockListEndpoint) {
        this.getStockListEndpoint = getStockListEndpoint;
        listeners = new ArrayList<>();
    }

    public void fetchStocksList(@Nullable StockSearchTerm searchTerm, StockFilterTerm filterTerm) {
        try {
            getStockListEndpoint.getStocksList(searchTerm,filterTerm,new GetStockListEndpoint.Callback() {
                @Override
                public void onFetchStockSuccessful(List<StockSchema> stocks) {
                    for (Listener listener : listeners) {
                        listener.onStocksListFetchSuccessful(stocks);
                    }
                }

                @Override
                public void onFetchStockFailure(EndpointResultStatus failureReason) {
                    if(isInvalidStatus(failureReason))
                        throw new RuntimeException("invalid failure reason");

                    for (Listener listener : listeners) {
                        listener.onStocksListFetchFailure(failureReason);
                    }
                }
            });
        } catch (NetworkException e) {
            for (Listener listener : listeners) {
                listener.onNetworkError();
            }
        }
    }

    private boolean isInvalidStatus(EndpointResultStatus failureReason) {
        return failureReason != EndpointResultStatus.GENERAL_ERROR &&
                failureReason != EndpointResultStatus.SERVER_ERROR;
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }
}
