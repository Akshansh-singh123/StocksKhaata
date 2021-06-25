package com.akshansh.stockskhaata.stocks;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class StockFilterTerm implements Serializable {
    private boolean NSE;
    private boolean BSE;
    private boolean favorite;
    private boolean shortSell;
    @Nullable private String sortOption;
    private float stockPriceLow;
    private float stockPriceHigh;
    private float growthPercentLow;
    private float growthPercentHigh;

    public StockFilterTerm() {

    }

    public StockFilterTerm(boolean NSE, boolean BSE, boolean favorite, boolean shortSell,
                           @Nullable String sortOption, float stockPriceLow,
                           float stockPriceHigh, float growthPercentLow, float growthPercentHigh) {
        this.NSE = NSE;
        this.BSE = BSE;
        this.favorite = favorite;
        this.shortSell = shortSell;
        this.sortOption = sortOption;
        this.stockPriceLow = stockPriceLow;
        this.stockPriceHigh = stockPriceHigh;
        this.growthPercentLow = growthPercentLow;
        this.growthPercentHigh = growthPercentHigh;
    }

    public boolean isNSE() {
        return NSE;
    }

    public void setNSE(boolean NSE) {
        this.NSE = NSE;
    }

    public boolean isBSE() {
        return BSE;
    }

    public void setBSE(boolean BSE) {
        this.BSE = BSE;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isShortSell() {
        return shortSell;
    }

    public void setShortSell(boolean shortSell) {
        this.shortSell = shortSell;
    }

    @Nullable
    public String getSortOption() {
        return sortOption;
    }

    public void setSortOption(@Nullable String sortOption) {
        this.sortOption = sortOption;
    }

    public float getStockPriceLow() {
        return stockPriceLow;
    }

    public void setStockPriceLow(float stockPriceLow) {
        this.stockPriceLow = stockPriceLow;
    }

    public float getStockPriceHigh() {
        return stockPriceHigh;
    }

    public void setStockPriceHigh(float stockPriceHigh) {
        this.stockPriceHigh = stockPriceHigh;
    }

    public float getGrowthPercentLow() {
        return growthPercentLow;
    }

    public void setGrowthPercentLow(float growthPercentLow) {
        this.growthPercentLow = growthPercentLow;
    }

    public float getGrowthPercentHigh() {
        return growthPercentHigh;
    }

    public void setGrowthPercentHigh(float growthPercentHigh) {
        this.growthPercentHigh = growthPercentHigh;
    }

    @Override
    public String toString() {
        return "StockFilterTerm{" +
                "NSE=" + NSE +
                ", BSE=" + BSE +
                ", favorite=" + favorite +
                ", shortSell=" + shortSell +
                ", sortOption='" + sortOption + '\'' +
                ", stockPriceLow=" + stockPriceLow +
                ", stockPriceHigh=" + stockPriceHigh +
                ", growthPercentLow=" + growthPercentLow +
                ", growthPercentHigh=" + growthPercentHigh +
                '}';
    }
}
