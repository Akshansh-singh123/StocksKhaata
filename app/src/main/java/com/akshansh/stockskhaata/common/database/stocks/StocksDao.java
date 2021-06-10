package com.akshansh.stockskhaata.common.database.stocks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.akshansh.stockskhaata.common.database.stocks.StockSchema;

import java.util.List;

@Dao
public interface StocksDao {
    @Query("SELECT * FROM stocks WHERE stock_name LIKE :name")
    LiveData<List<StockSchema>> getAllBySubName(String name);

    @Query("SELECT * FROM stocks")
    LiveData<List<StockSchema>> getAll();

    @Update
    void updateStock(StockSchema stockSchema);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StockSchema stockSchemas);

    @Delete
    void delete(StockSchema stockSchema);
}
