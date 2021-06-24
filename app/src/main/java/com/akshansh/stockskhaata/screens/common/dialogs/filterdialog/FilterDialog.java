package com.akshansh.stockskhaata.screens.common.dialogs.filterdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.databinding.FilterDialogBinding;
import com.akshansh.stockskhaata.screens.common.ViewMvcFactory;
import com.akshansh.stockskhaata.screens.common.dialogs.BaseBottomSheetDialog;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;

import javax.inject.Inject;

public class FilterDialog extends BaseBottomSheetDialog implements FilterDialogViewMvc.Listener {
    @Inject public ViewMvcFactory viewMvcFactory;
    private FilterDialogViewMvc viewMvc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewMvc = viewMvcFactory.getFilterDialogViewMvc(container);
        viewMvc.bindView();
        viewMvc.setGrowthPercentRange(-10,10);
        viewMvc.setGrowthPercentSelectedRange(0,10);
        viewMvc.setStockPriceRange(0,3500);
        viewMvc.setStockPriceSelectedRange(0,1000);
        viewMvc.setEnableBSEOption(true);
        viewMvc.setEnableNSEOption(true);
        viewMvc.setEnableFavoriteOption(true);
        viewMvc.setEnableShortOption(true);
        viewMvc.setEnableSortOption(Constants.TAG_NAME_DESC);
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
        dismiss();
    }

    @Override
    public void OnApplyButtonClicked(StockFilterTerm stockFilterTerm) {
        dismiss();
    }
}
