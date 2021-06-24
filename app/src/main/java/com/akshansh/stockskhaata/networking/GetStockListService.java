package com.akshansh.stockskhaata.networking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.stockskhaata.common.database.stocks.StockViewModel;
import com.akshansh.stockskhaata.stocks.GetStockListEndpoint;
import com.akshansh.stockskhaata.stocks.StockSearchTerm;

import javax.inject.Inject;

public class GetStockListService implements GetStockListEndpoint {
    private final AppCompatActivity lifecycleOwner;
    private final StockViewModel viewModel;

    @Inject
    public GetStockListService(AppCompatActivity lifecycleOwner,StockViewModel viewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = viewModel;
    }

    @Override
    public void getStocksList(@Nullable StockSearchTerm filterTerm,
                              Callback callback) throws NetworkException {
        if (filterTerm != null){
            viewModel.getFilteredStockList(String.format("%%%s%%",filterTerm.getName()))
                    .observe(lifecycleOwner,callback::onFetchStockSuccessful);
        }else{
            viewModel.getAll().observe(lifecycleOwner, callback::onFetchStockSuccessful);
        }
    }
}
