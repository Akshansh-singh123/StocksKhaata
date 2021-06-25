package com.akshansh.stockskhaata.stocks;

import androidx.annotation.Nullable;

public class StockSearchTerm {
    @Nullable
    private String name;

    public StockSearchTerm(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
