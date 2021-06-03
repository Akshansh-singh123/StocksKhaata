package com.akshansh.stockskhaata.common.database.stocks;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;


public class StockViewModel extends ViewModel {
    private final StocksRepository repository;
    private final LiveData<List<StockSchema>> listLiveData;

    @Inject
    public StockViewModel(StocksRepository repository) {
        this.repository = repository;
        listLiveData = repository.getAll();
    }

    public LiveData<List<StockSchema>> getAll(){
        return listLiveData;
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
