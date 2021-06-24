package com.akshansh.stockskhaata.screens.common.dialogs.filterdialog;

import com.akshansh.stockskhaata.screens.common.views.ObservableViewMvc;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;

public interface FilterDialogViewMvc extends ObservableViewMvc<FilterDialogViewMvc.Listener> {
    interface Listener{
        void OnCloseButtonClicked();
        void OnClearButtonClicked();
        void OnApplyButtonClicked(StockFilterTerm stockFilterTerm);
    }
    void bindView();
    void setEnableNSEOption(boolean enabled);
    void setEnableBSEOption(boolean enabled);
    void setEnableFavoriteOption(boolean enabled);
    void setEnableShortOption(boolean enabled);
    void setEnableSortOption(String tag);
    void setStockPriceRange(float low, float high);
    void setStockPriceSelectedRange(float low, float high);
    void setGrowthPercentRange(float low, float high);
    void setGrowthPercentSelectedRange(float low, float high);
    void clearBinding();
}
