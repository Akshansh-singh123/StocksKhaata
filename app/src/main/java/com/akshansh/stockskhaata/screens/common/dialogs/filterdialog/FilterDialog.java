package com.akshansh.stockskhaata.screens.common.dialogs.filterdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.screens.common.ViewMvcFactory;
import com.akshansh.stockskhaata.screens.common.dialogs.BaseBottomSheetDialog;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;

import javax.inject.Inject;

public class FilterDialog extends BaseBottomSheetDialog implements FilterDialogViewMvc.Listener {
    @Inject public ViewMvcFactory viewMvcFactory;
    @Inject public FilterDialogEventBus filterDialogEventBus;
    private FilterDialogViewMvc viewMvc;
    private StockFilterTerm term;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);
        term = FilterDialogArgs.fromBundle(getArguments()).getFilterTerms();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewMvc = viewMvcFactory.getFilterDialogViewMvc(container);
        viewMvc.bindView();
        viewMvc.setGrowthPercentRange(-100, 100);
        viewMvc.setStockPriceRange(0, 10000);
        viewMvc.setGrowthPercentSelectedRange(-10,10);
        viewMvc.setStockPriceSelectedRange(0,1000);
        if(term != null) {
            viewMvc.setGrowthPercentSelectedRange(term.getGrowthPercentLow(),
                    term.getGrowthPercentHigh());
            viewMvc.setStockPriceSelectedRange(term.getStockPriceLow(),
                    term.getStockPriceHigh());
            viewMvc.setEnableBSEOption(term.isBSE());
            viewMvc.setEnableNSEOption(term.isNSE());
            viewMvc.setEnableFavoriteOption(term.isFavorite());
            viewMvc.setEnableShortOption(term.isShortSell());
            viewMvc.setEnableSortOption(term.getSortOption());
        }
        return viewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewMvc.registerListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewMvc.unregisterListener(this);
        viewMvc.clearBinding();
    }

    @Override
    public void OnCloseButtonClicked() {
        dismiss();
    }

    @Override
    public void OnClearButtonClicked() {
        filterDialogEventBus.onClearButtonClicked();
        dismiss();
    }

    @Override
    public void OnApplyButtonClicked(StockFilterTerm stockFilterTerm) {
        filterDialogEventBus.onApplyButtonClicked(stockFilterTerm);
        dismiss();
    }
}
