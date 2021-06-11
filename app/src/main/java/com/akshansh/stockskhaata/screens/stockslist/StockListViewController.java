package com.akshansh.stockskhaata.screens.stockslist;

import android.os.SystemClock;
import android.util.Log;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.dialogs.confirmationdialog.ConfirmationDialogEventBus;
import com.akshansh.stockskhaata.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.stockskhaata.screens.common.toast.ToastHelper;
import com.akshansh.stockskhaata.stocks.CUDStockUseCase;
import com.akshansh.stockskhaata.stocks.FetchStockListUseCase;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;

import java.util.List;

import javax.inject.Inject;

import static com.akshansh.stockskhaata.common.Constants.OperationModes.*;

public class StockListViewController implements StockListViewMvc.Listener,
        FetchStockListUseCase.Listener, ConfirmationDialogEventBus.Listener {
    private StockListViewMvc viewMvc;
    private final ToastHelper toastHelper;
    private final ScreensNavigator screensNavigator;
    private final FetchStockListUseCase fetchStockListUseCase;
    private final CUDStockUseCase cudStockUseCase;
    private final ConfirmationDialogEventBus dialogEventBus;
    private StockSchema deleteStock;

    @Inject
    public StockListViewController(ToastHelper toastHelper
            , ScreensNavigator screenNavigator
            , FetchStockListUseCase fetchStockListUseCase
            , CUDStockUseCase cudStockUseCase
            , ConfirmationDialogEventBus dialogEventBus) {
        this.toastHelper = toastHelper;
        this.screensNavigator = screenNavigator;
        this.fetchStockListUseCase = fetchStockListUseCase;
        this.cudStockUseCase = cudStockUseCase;
        this.dialogEventBus = dialogEventBus;
    }

    public void bindView(StockListViewMvc viewMvc){
        this.viewMvc = viewMvc;
    }

    public void onStart(){
        viewMvc.registerListener(this);
        fetchStockListUseCase.registerListener(this);
        dialogEventBus.registerListeners(this);
        fetchStockListUseCase.fetchStocksList(null);
    }

    public void onStop(){
        viewMvc.unregisterListener(this);
        fetchStockListUseCase.unregisterListener(this);
        dialogEventBus.unregisterListeners(this);
    }

    public void onDestroy(){
        viewMvc.clearBinding();
    }

    @Override
    public void OnFavoriteButtonClicked(StockSchema stock) {
        cudStockUseCase.CUDStock(UPDATE,stock);
    }

    @Override
    public void OnEditButtonClicked(StockSchema stock) {
        screensNavigator.toAddStockDialog();
    }

    @Override
    public void OnDeleteButtonClicked(StockSchema stock) {
        deleteStock = stock;
        screensNavigator.toConfirmationDialog();
    }

    @Override
    public void OnAddOptionClicked() {
        screensNavigator.toAddStockDialog();
    }

    @Override
    public void OnToolbarFilterOptionClicked() {
        screensNavigator.toFilterDialog();
    }

    @Override
    public void OnSearchEventStart() {

    }

    @Override
    public void OnSearchEventClosed() {
        fetchStockListUseCase.fetchStocksList(null);
    }

    @Override
    public void OnSearchTextChanged(String searchText) {
        fetchStockListUseCase.fetchStocksList(new StockFilterTerm(searchText));
    }

    @Override
    public void onStocksListFetchSuccessful(List<StockSchema> stocks) {
        viewMvc.bindStocks(stocks);
    }

    @Override
    public void onStocksListFetchFailure(Constants.EndpointResultStatus failureReason) {

    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void OnPositiveButtonClicked() {
        if(deleteStock != null)
            cudStockUseCase.CUDStock(DELETE,deleteStock);
    }
}
