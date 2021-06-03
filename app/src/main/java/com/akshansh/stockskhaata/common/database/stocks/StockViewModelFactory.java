package com.akshansh.stockskhaata.common.database.stocks;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class StockViewModelFactory implements ViewModelProvider.Factory {
    private final StocksRepository repository;

    public StockViewModelFactory(StocksRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(StockViewModel.class)){
            return (T) new StockViewModel(repository);
        }
        throw new IllegalArgumentException("invalid view model");
    }
}
