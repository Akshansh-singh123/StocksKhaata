package com.akshansh.stockskhaata.screens.common.dialogs.addeditstock;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.ViewMvcFactory;
import com.akshansh.stockskhaata.screens.common.dialogs.BaseBottomSheetDialog;
import com.akshansh.stockskhaata.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.stockskhaata.stocks.CUDStockUseCase;

import javax.inject.Inject;


public class AddStockDialogFragment extends BaseBottomSheetDialog implements AddStockDialogViewMvc.Listener {
    private AddStockDialogViewMvc viewMvc;
    @Inject public ScreensNavigator screensNavigator;
    @Inject public CUDStockUseCase cudStockUseCase;
    @Inject public ViewMvcFactory viewMvcFactory;
    private StockSchema editSchema;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);
        editSchema = AddStockDialogFragmentArgs.fromBundle(getArguments()).getStockSchema();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {
        viewMvc = viewMvcFactory.getStockDialogViewMvc(null);
        if(editSchema != null){
            viewMvc.setStock(editSchema);
            viewMvc.setStockName(editSchema.getStockName());
            viewMvc.setBuyPrice(String.valueOf(editSchema.getBuyPrice()));
            viewMvc.setSellPrice(String.valueOf(editSchema.getSellPrice()));
            viewMvc.setStockQuantity(String.valueOf(editSchema.getQuantity()));
            viewMvc.setMarket(editSchema.getMarket());
            viewMvc.setIsShort(editSchema.isShorted());
        }
        return viewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewMvc.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewMvc.clearBinding();
    }

    @Override
    public void onSaveButtonClicked(StockSchema stockSchema, Constants.OperationModes operation) {
        cudStockUseCase.CUDStock(operation,stockSchema);
        screensNavigator.navigateUp();
    }
}
