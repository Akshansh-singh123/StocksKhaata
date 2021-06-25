package com.akshansh.stockskhaata.common.database.stocks;

import android.util.Log;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

import javax.inject.Inject;

public class StocksRepository {
    private final StocksDao stocksDao;
    private LiveData<List<StockSchema>> stockSchemas;

    public StocksRepository(StocksDao stocksDao) {
        this.stocksDao = stocksDao;
        StocksDatabase.executor.execute(()->{
            stockSchemas = this.stocksDao.getAll();
        });
    }

    @WorkerThread
    public void insert(StockSchema stockSchemas){
        StocksDatabase.executor.execute(()->{
            stocksDao.insert(stockSchemas);
        });
    }

    @WorkerThread
    public void delete(StockSchema stockSchema){
        StocksDatabase.executor.execute(()->{
            stocksDao.delete(stockSchema);
        });
    }

    @WorkerThread
    public void update(StockSchema stockSchema){
        StocksDatabase.executor.execute(()->{
            stocksDao.updateStock(stockSchema);
        });
    }

    @WorkerThread
    public LiveData<List<StockSchema>> getAll(){
        return stockSchemas;
    }

    public LiveData<List<StockSchema>> getStockListBySubName(String name) {
        return stocksDao.getAllBySubName(name);
    }

    public LiveData<List<StockSchema>> getFilteredStockList(SupportSQLiteQuery query){
        return stocksDao.getFiltered(query);
    }
}
