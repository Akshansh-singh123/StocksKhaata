package com.akshansh.stockskhaata.screens.common.dialogs.addeditstock;

import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.Constants.OperationModes;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.views.ObservableViewMvc;

public interface AddStockDialogViewMvc extends ObservableViewMvc<AddStockDialogViewMvc.Listener> {
    interface Listener{
        void onSaveButtonClicked(StockSchema stockSchema, OperationModes operation);
    }

    void setStockName(@Nullable String stockName);
    void setStockQuantity(@Nullable String quantity);
    void setBuyPrice(@Nullable String buyPrice);
    void setSellPrice(@Nullable String sellPrice);
    void setMarket(@Nullable String market);
    void setIsShort(boolean isShort);
    void setStock(@Nullable StockSchema stock);
    void clearBinding();
}
