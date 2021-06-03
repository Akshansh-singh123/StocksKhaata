package com.akshansh.stockskhaata.screens.stockslist.stocklistitem;

import androidx.annotation.NonNull;

import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.views.ObservableViewMvc;

public interface StockListItemViewMvc extends ObservableViewMvc<StockListItemViewMvc.Listener> {
    interface Listener{
        void onStockListItemCollapsed(int position);
        void onStockListItemExpanded(int position);
        void onFavoriteButtonClicked(StockSchema stock);
        void onEditButtonClicked(StockSchema stock);
        void onDeleteButtonClicked(StockSchema stock);
    }
    void bindViewExpanded(StockSchema stock, int position);
    void bindViewCollapsed(StockSchema stock, int position);
    void clearBinding();
    void setStockName(@NonNull String stockName);
    void setTimeStamp(@NonNull String timeStamp);
    void setBuyPrice(double buyPrice);
    void setSellPrice(double sellPrice);
    void setQuantity(int quantity);
    void setMarket(@NonNull String market);
    void setTotalInvestment(double totalInvestment);
    void setGrowth(double growth,double growthPercentage);
    void isFavorite(boolean favorite);
    void isShortTrade(boolean shortTrade);
    void setGrowthTextColor(double growth);
    void setFavoriteButton(StockSchema stock);
    void setEditButton(StockSchema stock);
    void setDeleteButton(StockSchema stock);
}
