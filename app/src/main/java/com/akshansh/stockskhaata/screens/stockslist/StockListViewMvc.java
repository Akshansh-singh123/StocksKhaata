package com.akshansh.stockskhaata.screens.stockslist;


import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.views.ObservableViewMvc;

import java.util.List;

public interface StockListViewMvc extends ObservableViewMvc<StockListViewMvc.Listener> {
    interface Listener{
        void OnListItemFavoriteButtonClicked(StockSchema stock);
        void OnListItemEditButtonClicked(StockSchema stock);
        void OnListItemDeleteButtonClicked(StockSchema stock);
        void OnAddOptionClicked();
        void OnToolbarFilterOptionClicked();
        void OnSearchEventStart();
        void OnSearchEventClosed();
        void OnSearchTextChanged(String searchText);
    }
    void bindStocks(List<StockSchema> stockSchemas);
    void clearBinding();
}
