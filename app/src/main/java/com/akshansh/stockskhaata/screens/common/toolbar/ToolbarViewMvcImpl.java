package com.akshansh.stockskhaata.screens.common.toolbar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.akshansh.stockskhaata.databinding.LayoutToolbarBinding;
import com.akshansh.stockskhaata.screens.common.views.BaseObservableViewMvc;

public class ToolbarViewMvcImpl extends BaseObservableViewMvc<ToolbarViewMvc.Listener>
        implements ToolbarViewMvc {
    private LayoutToolbarBinding binding;
    private TextWatcherListener watcher;
    private static final String TAG = "ToolbarViewMvcImpl";

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
                enableTextSearch();
                showKeyboard(binding.searchEditText);
                for(Listener listener: getListeners()){
                    listener.OnSearchEventStart();
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

    private void enableTextSearch() {
        if(watcher != null){
            binding.searchEditText.removeTextChangedListener(watcher);
        }
        binding.searchTextLayout.setVisibility(View.VISIBLE);
        setSearchClearButton();
        setSearchEditButton();
        watcher = new TextWatcherListener();
        binding.searchEditText.addTextChangedListener(watcher);
        binding.searchEditText.requestFocus();
    }

    private void setSearchClearButton(){
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchEditText.setText("");
            }
        });
    }

    private void setSearchEditButton(){
        binding.editTextBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableTextSearch();
                hideKeyboard(v);
                for(Listener listener: getListeners()){
                    listener.OnSearchEventClosed();
                }
            }
        });
    }

    private void disableTextSearch(){
        binding.searchEditText.setText("");
        binding.searchTextLayout.setVisibility(View.GONE);
        binding.clearButton.setOnClickListener(null);
        binding.editTextBackButton.setOnClickListener(null);
        binding.searchEditText.removeTextChangedListener(watcher);
    }

    private class TextWatcherListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            for(Listener listener: getListeners()){
                listener.OnSearchTextChanged(s.toString());
            }
        }
    }
}
