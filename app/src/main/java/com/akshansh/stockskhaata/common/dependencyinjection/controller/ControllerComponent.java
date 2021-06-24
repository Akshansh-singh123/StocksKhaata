package com.akshansh.stockskhaata.common.dependencyinjection.controller;

import com.akshansh.stockskhaata.screens.common.controller.BaseActivity;
import com.akshansh.stockskhaata.screens.common.dialogs.addeditstock.AddStockDialogFragment;
import com.akshansh.stockskhaata.screens.common.dialogs.confirmationdialog.ConfirmationDialogFragment;
import com.akshansh.stockskhaata.screens.common.dialogs.filterdialog.FilterDialog;
import com.akshansh.stockskhaata.screens.stockslist.StockListFragment;

import dagger.Subcomponent;

@ControllerScope
@Subcomponent()
public interface ControllerComponent {
    void inject(FilterDialog filterDialog);
    void inject(StockListFragment stockListFragment);
    void inject(AddStockDialogFragment addStockDialogFragment);
    void inject(ConfirmationDialogFragment confirmationDialogFragment);
}
