package com.akshansh.stockskhaata.screens.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.akshansh.stockskhaata.screens.common.dialogs.addeditstock.AddStockDialogViewMvc;
import com.akshansh.stockskhaata.screens.common.dialogs.addeditstock.AddStockDialogViewMvcImpl;
import com.akshansh.stockskhaata.screens.common.dialogs.filterdialog.FilterDialogViewMvc;
import com.akshansh.stockskhaata.screens.common.dialogs.filterdialog.FilterDialogViewMvcImpl;
import com.akshansh.stockskhaata.screens.common.toolbar.ToolbarViewMvc;
import com.akshansh.stockskhaata.screens.common.toolbar.ToolbarViewMvcImpl;
import com.akshansh.stockskhaata.screens.stockslist.StockListViewMvc;
import com.akshansh.stockskhaata.screens.stockslist.StocksListViewMvcImpl;
import com.akshansh.stockskhaata.screens.stockslist.stocklistitem.StockListItemViewMvc;
import com.akshansh.stockskhaata.screens.stockslist.stocklistitem.StockListItemViewMvcImpl;

import javax.inject.Inject;

public class ViewMvcFactory {
    private final LayoutInflater inflater;

    @Inject
    public ViewMvcFactory(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public StockListViewMvc getStockListViewMvc(ViewGroup parent){
        return new StocksListViewMvcImpl(inflater,parent,this);
    }

    public StockListItemViewMvc getStockListItemViewMvc(ViewGroup parent){
        return new StockListItemViewMvcImpl(inflater,parent);
    }

    public AddStockDialogViewMvc getStockDialogViewMvc(ViewGroup parent){
        return new AddStockDialogViewMvcImpl(inflater,parent);
    }

    public ToolbarViewMvc getToolbarViewMvc(ViewGroup parent){
        return new ToolbarViewMvcImpl(inflater, parent);
    }

    public FilterDialogViewMvc getFilterDialogViewMvc(ViewGroup parent){
        return new FilterDialogViewMvcImpl(inflater,parent);
    }
}
