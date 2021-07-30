package com.akshansh.stockskhaata.common.database.stocks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.akshansh.stockskhaata.common.database.stocks.StockSchema;

import java.util.List;

@Dao
public interface StocksDao {
    @RawQuery(observedEntities = StockSchema.class)
    LiveData<List<StockSchema>> getFiltered(SupportSQLiteQuery query);

    @Update
    void updateStock(StockSchema stockSchema);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StockSchema stockSchemas);

    @Delete
    void delete(StockSchema stockSchema);
}
