package com.akshansh.stockskhaata.common.database.stocks;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {StockSchema.class}, version = 1)
public abstract class StocksDatabase extends RoomDatabase {
    private static volatile StocksDatabase INSTANCE;
    public abstract StocksDao stocksDao();
    private static final int NUMBER_OF_THREADS = Runtime.
            getRuntime().availableProcessors();

    public static final ExecutorService executor = Executors
            .newFixedThreadPool(NUMBER_OF_THREADS);

    public static StocksDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (StocksDatabase.class){
                INSTANCE = Room.databaseBuilder(context,
                        StocksDatabase.class,"stocks").build();
            }
        }
        return INSTANCE;
    }
}
