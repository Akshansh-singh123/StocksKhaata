package com.akshansh.stockskhaata.screens.stockslist;

import android.util.Log;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.dialogs.confirmationdialog.ConfirmationDialogEventBus;
import com.akshansh.stockskhaata.screens.common.dialogs.filterdialog.FilterDialogEventBus;
import com.akshansh.stockskhaata.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.stockskhaata.screens.common.toast.ToastHelper;
import com.akshansh.stockskhaata.stocks.CUDStockUseCase;
import com.akshansh.stockskhaata.stocks.FetchStockListUseCase;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;
import com.akshansh.stockskhaata.stocks.StockSearchTerm;

import java.util.List;

import javax.inject.Inject;

import static com.akshansh.stockskhaata.common.Constants.OperationModes.*;

public class StockListViewController implements StockListViewMvc.Listener,
        FetchStockListUseCase.Listener, ConfirmationDialogEventBus.Listener, FilterDialogEventBus.Listener {
    private StockListViewMvc viewMvc;
    private final ToastHelper toastHelper;
    private final ScreensNavigator screensNavigator;
    private final FetchStockListUseCase fetchStockListUseCase;
    private final CUDStockUseCase cudStockUseCase;
    private final ConfirmationDialogEventBus dialogEventBus;
    private final FilterDialogEventBus filterDialogEventBus;
    private StockSchema deleteStock;
    private StockSearchTerm searchTerm;
    private StockFilterTerm filterTerm;

    @Inject
    public StockListViewController(ToastHelper toastHelper
            , ScreensNavigator screenNavigator
            , FetchStockListUseCase fetchStockListUseCase
            , CUDStockUseCase cudStockUseCase
            , ConfirmationDialogEventBus dialogEventBus
            , FilterDialogEventBus filterDialogEventBus) {
        this.toastHelper = toastHelper;
        this.screensNavigator = screenNavigator;
        this.fetchStockListUseCase = fetchStockListUseCase;
        this.cudStockUseCase = cudStockUseCase;
        this.dialogEventBus = dialogEventBus;
        this.filterDialogEventBus = filterDialogEventBus;
        searchTerm = null;
        filterTerm = null;
    }

    public void bindView(StockListViewMvc viewMvc){
        this.viewMvc = viewMvc;
    }

    public void onStart(){
        viewMvc.registerListener(this);
        fetchStockListUseCase.registerListener(this);
        dialogEventBus.registerListeners(this);
        fetchStockListUseCase.fetchStocksList(searchTerm,filterTerm);
        filterDialogEventBus.registerListeners(this);
    }

    public void onStop(){
        viewMvc.unregisterListener(this);
        fetchStockListUseCase.unregisterListener(this);
        dialogEventBus.unregisterListeners(this);
        filterDialogEventBus.unregisterListeners(this);
    }

    public void onDestroy(){
        viewMvc.clearBinding();
    }

    @Override
    public void OnListItemFavoriteButtonClicked(StockSchema stock) {
        cudStockUseCase.CUDStock(UPDATE,stock);
    }

    @Override
    public void OnListItemEditButtonClicked(StockSchema stock) {
        screensNavigator.toAddStockDialog(stock);
    }

    @Override
    public void OnListItemDeleteButtonClicked(StockSchema stock) {
        deleteStock = stock;
        screensNavigator.toConfirmationDialog();
    }

    @Override
    public void OnAddOptionClicked() {
        screensNavigator.toAddStockDialog(null);
    }

    @Override
    public void OnToolbarFilterOptionClicked() {
        screensNavigator.toFilterDialog(filterTerm);
    }

    @Override
    public void OnSearchEventStart() {

    }

    @Override
    public void OnSearchEventClosed() {
        searchTerm = null;
        fetchStockListUseCase.fetchStocksList(searchTerm,filterTerm);
    }

    @Override
    public void OnSearchTextChanged(String searchText) {
        searchTerm = new StockSearchTerm(searchText);
        fetchStockListUseCase.fetchStocksList(searchTerm,filterTerm);
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

    @Override
    public void OnApplyButtonClicked(StockFilterTerm stockFilterTerm) {
        filterTerm = stockFilterTerm;
        fetchStockListUseCase.fetchStocksList(searchTerm,filterTerm);
    }

    @Override
    public void OnClearFilterButtonClicked() {
        filterTerm = null;
        fetchStockListUseCase.fetchStocksList(searchTerm,filterTerm);
    }
}
