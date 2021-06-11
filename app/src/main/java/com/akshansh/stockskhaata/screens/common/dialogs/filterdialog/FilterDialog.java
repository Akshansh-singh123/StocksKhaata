package com.akshansh.stockskhaata.screens.common.dialogs.filterdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.databinding.FilterDialogBinding;
import com.akshansh.stockskhaata.screens.common.dialogs.BaseBottomSheetDialog;

public class FilterDialog extends BaseBottomSheetDialog {
    private FilterDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FilterDialogBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

