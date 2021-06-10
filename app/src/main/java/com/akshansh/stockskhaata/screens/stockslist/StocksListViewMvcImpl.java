package com.akshansh.stockskhaata.screens.stockslist;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.databinding.FragmentStocksListBinding;
import com.akshansh.stockskhaata.screens.common.ViewMvcFactory;
import com.akshansh.stockskhaata.screens.common.toolbar.ToolbarViewMvc;
import com.akshansh.stockskhaata.screens.common.views.BaseObservableViewMvc;
import com.akshansh.stockskhaata.screens.stockslist.stocklistitem.StockListItemAdapter;

import java.util.List;
import java.util.Locale;

public class StocksListViewMvcImpl extends BaseObservableViewMvc<StockListViewMvc.Listener>
        implements StockListViewMvc, StockListItemAdapter.Listener, ToolbarViewMvc.Listener {
    private FragmentStocksListBinding binding;
    private final StockListItemAdapter adapter;
    private final ToolbarViewMvc toolbarViewMvc;
    private boolean initialized = false;

    public StocksListViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        binding = FragmentStocksListBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        adapter = new StockListItemAdapter(viewMvcFactory,this);
        toolbarViewMvc = viewMvcFactory.getToolbarViewMvc(parent);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(true);
        manager.setReverseLayout(true);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
        initToolbar();
    }

    private void initToolbar() {
        toolbarViewMvc.enableFilterButton();
        toolbarViewMvc.setTitle(getString(R.string.app_name));
        toolbarViewMvc.enableSearchButton();
        binding.toolbar.addView(toolbarViewMvc.getRootView(),
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        toolbarViewMvc.registerListener(this);
    }

    @Override
    public void bindStocks(List<StockSchema> stocks) {
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.OnAddOptionClicked();
                }
            }
        });
        if(stocks.size() != 0) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyTextView.setVisibility(View.GONE);
            binding.statsHud.setVisibility(View.VISIBLE);
            adapter.bindView(stocks);
            calculateStats(stocks);
            if(!initialized) {
                binding.netGrowthText.setVisibility(View.INVISIBLE);
                binding.tradesText.setVisibility(View.INVISIBLE);
                binding.progressBarGrowth.setVisibility(View.VISIBLE);
                binding.progressBarTrades.setVisibility(View.VISIBLE);
                initialized = true;
            }
        } else{
            binding.recyclerView.setVisibility(View.GONE);
            binding.emptyTextView.setVisibility(View.VISIBLE);
            binding.statsHud.setVisibility(View.GONE);
        }
    }

    @Override
    public void clearBinding() {
        binding = null;
        toolbarViewMvc.clearBinding();
        toolbarViewMvc.unregisterListener(this);
    }

    @Override
    public void OnFavoriteButtonClicked(StockSchema stockSchema) {
        for(Listener listener: getListeners()){
            listener.OnFavoriteButtonClicked(stockSchema);
        }
    }

    @Override
    public void OnEditButtonClicked(StockSchema stockSchema) {
        for(Listener listener: getListeners()){
            listener.OnEditButtonClicked(stockSchema);
        }
    }

    @Override
    public void OnDeleteButtonClicked(StockSchema stockSchema) {
        for(Listener listener: getListeners()){
            listener.OnDeleteButtonClicked(stockSchema);
        }
    }

    @Override
    public void OnAddOptionItemSelected() {

    }

    @Override
    public void OnFilterOptionItemSelected() {
        for(Listener listener: getListeners()){
            listener.OnToolbarFilterOptionClicked();
        }
    }

    @Override
    public void OnSearchEventStart() {
        for(Listener listener: getListeners()){
            listener.OnSearchEventStart();
        }
    }

    @Override
    public void OnSearchEventClosed() {
        for(Listener listener: getListeners()){
            listener.OnSearchEventClosed();
        }
    }

    @Override
    public void OnSearchTextChanged(String s) {
        for(Listener listener: getListeners()){
            listener.OnSearchTextChanged(s);
        }
    }

    private void calculateStats(List<StockSchema> stocks) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int size = stocks.size();
                double netGrowth = 0;
                for (StockSchema stock : stocks) {
                    netGrowth += stock.getGrowth();
                }
                setHUD(netGrowth, size);
            }
        });
    }

    private void setHUD(double netGrowth, int size) {
        String growth = String.format(Locale.ENGLISH,"%s %.2f",
                getString(R.string.rupees_symbol),Math.abs(netGrowth));
        if(netGrowth < 0) {
            binding.statsHud.setBackgroundColor(getContext().getColor(R.color.trending_down_red_light));
            growth = "- "+growth.concat(" ").concat(getString(R.string.trend_down_symbol));
        }else {
            binding.statsHud.setBackgroundColor(getContext().getColor(R.color.trend_up_green_light));
            growth = growth.concat(" ").concat(getString(R.string.trend_up_symbol));
        }
        binding.tradesText.setText(String.format(Locale.ENGLISH,"%d",size));
        binding.netGrowthText.setText(growth);
        binding.netGrowthText.setVisibility(View.VISIBLE);
        binding.tradesText.setVisibility(View.VISIBLE);
        binding.progressBarGrowth.setVisibility(View.GONE);
        binding.progressBarTrades.setVisibility(View.GONE);
    }
}
