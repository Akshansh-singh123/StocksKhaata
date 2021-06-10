package com.akshansh.stockskhaata.stocks;

import androidx.annotation.Nullable;

public class StockFilterTerm {
    @Nullable
    private String name;

    public StockFilterTerm(@Nullable String name) {
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
