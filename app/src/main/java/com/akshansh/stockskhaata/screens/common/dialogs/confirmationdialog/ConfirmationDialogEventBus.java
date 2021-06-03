package com.akshansh.stockskhaata.screens.common.dialogs.confirmationdialog;

import com.akshansh.stockskhaata.common.BaseObservable;

public class ConfirmationDialogEventBus extends BaseObservable<ConfirmationDialogEventBus.Listener> {
    public interface Listener{
        void OnPositiveButtonClicked();
    }

    public void postEvent(){
        for(Listener listener: getListeners()){
            listener.OnPositiveButtonClicked();
        }
    }
}
