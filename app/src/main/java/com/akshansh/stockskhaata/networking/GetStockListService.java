package com.akshansh.stockskhaata.networking;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.FilterQueryHelper;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.common.database.stocks.StockViewModel;
import com.akshansh.stockskhaata.stocks.GetStockListEndpoint;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;
import com.akshansh.stockskhaata.stocks.StockSearchTerm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class GetStockListService implements GetStockListEndpoint {
    private final AppCompatActivity lifecycleOwner;
    private final StockViewModel viewModel;
    private LiveData<List<StockSchema>> stockListAll;

    @Inject
    public GetStockListService(AppCompatActivity lifecycleOwner,StockViewModel viewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = viewModel;
    }

    @Override
    public void getStocksList(@Nullable StockSearchTerm searchTerm,
                              @Nullable StockFilterTerm filterTerm,
                              Callback callback) throws NetworkException {
        if(searchTerm != null && filterTerm != null){

        }else if(searchTerm != null){
            stockListAll = viewModel
                    .getStockListBySubName(String.format("%%%s%%",searchTerm.getName()));
        }else if(filterTerm != null){
            stockListAll = viewModel
                    .getFilteredStockList(FilterQueryHelper.getFilterQuery(filterTerm));
        }else{
            stockListAll = viewModel.getAll();
        }
        stockListAll.observe(lifecycleOwner,callback::onFetchStockSuccessful);
    }
}
