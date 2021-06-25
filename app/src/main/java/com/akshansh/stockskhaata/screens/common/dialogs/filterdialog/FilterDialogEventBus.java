package com.akshansh.stockskhaata.screens.common.dialogs.filterdialog;

import com.akshansh.stockskhaata.common.BaseObservable;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;

public class FilterDialogEventBus extends BaseObservable<FilterDialogEventBus.Listener> {
    public interface Listener{
        void OnApplyButtonClicked(StockFilterTerm stockFilterTerm);
        void OnClearFilterButtonClicked();
    }

    public void onApplyButtonClicked(StockFilterTerm stockFilterTerm){
        for(Listener listener: getListeners()){
            listener.OnApplyButtonClicked(stockFilterTerm);
        }
    }

    public void onClearButtonClicked(){
        for(Listener listener: getListeners()){
            listener.OnClearFilterButtonClicked();
        }
    }
}
