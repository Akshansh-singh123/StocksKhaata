package com.akshansh.stockskhaata.common.database.stocks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

import javax.inject.Inject;


public class StockViewModel extends ViewModel {
    private final StocksRepository repository;

    @Inject
    public StockViewModel(StocksRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<StockSchema>> getFilteredStockList(SupportSQLiteQuery query){
        return repository.getFilteredStockList(query);
    }

    public void insert(StockSchema stockSchema){
        repository.insert(stockSchema);
    }

    public void delete(StockSchema stockSchema){
        repository.delete(stockSchema);
    }

    public void update(StockSchema stockSchema){
        repository.update(stockSchema);
    }

}
