package com.akshansh.stockskhaata.screens.stockslist;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        implements StockListViewMvc, StockListItemAdapter.Listener, ToolbarViewMvc.Listener,
        View.OnScrollChangeListener {
    private FragmentStocksListBinding binding;
    private final StockListItemAdapter adapter;
    private final ToolbarViewMvc toolbarViewMvc;
    private boolean initialized = false;
    private boolean fabHidden = false;
    private int scrollPositive;
    private int scrollNegative;

    public StocksListViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        binding = FragmentStocksListBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        adapter = new StockListItemAdapter(viewMvcFactory,this);
        toolbarViewMvc = viewMvcFactory.getToolbarViewMvc(parent);
        scrollPositive = 0;
        scrollNegative = 0;
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
    public void bindStocks(List<StockSchema> stocks, boolean liked) {
        binding.floatingActionButton.setOnClickListener(v -> {
            for(Listener listener: getListeners()){
                listener.OnAddOptionClicked();
            }
        });
        if(stocks.size() != 0) {
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setStackFromEnd(true);
            manager.setReverseLayout(true);
            binding.recyclerView.setLayoutManager(manager);
            binding.recyclerView.setAdapter(adapter);
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyTextView.setVisibility(View.GONE);
            binding.statsHud.setVisibility(View.VISIBLE);
            binding.recyclerView.setOnScrollChangeListener(this);
            adapter.bindView(stocks);
            if(!liked)
                binding.recyclerView.scrollToPosition(stocks.size()-1);
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
            listener.OnListItemFavoriteButtonClicked(stockSchema);
        }
    }

    @Override
    public void OnEditButtonClicked(StockSchema stockSchema) {
        for(Listener listener: getListeners()){
            listener.OnListItemEditButtonClicked(stockSchema);
        }
    }

    @Override
    public void OnDeleteButtonClicked(StockSchema stockSchema) {
        for(Listener listener: getListeners()){
            listener.OnListItemDeleteButtonClicked(stockSchema);
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

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(oldScrollY<0){
            scrollNegative = Math.max(-250,oldScrollY+scrollNegative);
        }else{
            scrollPositive = Math.min(250,oldScrollY+scrollPositive);
        }
        if(scrollNegative == -250){
            if(!fabHidden)
                hideFAB();
            scrollNegative = 0;
        }else if(scrollPositive == 250){
            if(fabHidden)
                showFAB();
            scrollPositive = 0;
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

    private void hideFAB() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.floatingActionButton,"translationX",1000f);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        fabHidden = true;
    }

    private void showFAB() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.floatingActionButton,
                "translationX",0f);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        fabHidden = false;
    }
}
