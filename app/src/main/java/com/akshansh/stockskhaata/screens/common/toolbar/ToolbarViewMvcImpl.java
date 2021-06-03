package com.akshansh.stockskhaata.screens.common.toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.akshansh.stockskhaata.databinding.LayoutToolbarBinding;
import com.akshansh.stockskhaata.screens.common.views.BaseObservableViewMvc;

public class ToolbarViewMvcImpl extends BaseObservableViewMvc<ToolbarViewMvc.Listener>
        implements ToolbarViewMvc {
    private LayoutToolbarBinding binding;

    public ToolbarViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        binding = LayoutToolbarBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
    }

    @Override
    public void setTitle(String title) {
        binding.toolbarTitle.setText(title);
    }

    @Override
    public void clearBinding() {
        binding = null;
    }

    @Override
    public void enableAddButton(){
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.OnAddOptionItemSelected();
                }
            }
        });
        binding.addButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableFilterButton(){
        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.OnFilterOptionItemSelected();
                }
            }
        });
        binding.filterButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableSearchButton() {
        binding.searchButton.setVisibility(View.VISIBLE);
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.OnSearchButtonClicked();
                }
            }
        });
    }

    @Override
    public void disableAddButton(){
        binding.addButton.setOnClickListener(null);
        binding.addButton.setVisibility(View.GONE);
    }

    @Override
    public void disableSearchButton() {
        binding.searchButton.setVisibility(View.GONE);
        binding.searchButton.setOnClickListener(null);
    }

    @Override
    public void disableFilterButton(){
        binding.filterButton.setOnClickListener(null);
        binding.filterButton.setVisibility(View.GONE);
    }
}
