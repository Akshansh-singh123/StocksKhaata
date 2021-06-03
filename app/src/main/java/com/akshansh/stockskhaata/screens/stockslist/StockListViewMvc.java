package com.akshansh.stockskhaata.screens.stockslist;


import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.views.ObservableViewMvc;

import java.util.List;

public interface StockListViewMvc extends ObservableViewMvc<StockListViewMvc.Listener> {
    interface Listener{
        void OnFavoriteButtonClicked(StockSchema stock);
        void OnEditButtonClicked(StockSchema stock);
        void OnDeleteButtonClicked(StockSchema stock);
        void OnAddOptionClicked();
        void OnToolbarFilterOptionClicked();
    }
    void bindStocks(List<StockSchema> stockSchemas);
    void clearBinding();
}
