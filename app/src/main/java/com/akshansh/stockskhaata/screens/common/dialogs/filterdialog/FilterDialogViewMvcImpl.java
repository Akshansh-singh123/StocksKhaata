package com.akshansh.stockskhaata.screens.common.dialogs.filterdialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.databinding.FilterDialogBinding;
import com.akshansh.stockskhaata.screens.common.views.BaseObservableViewMvc;
import com.akshansh.stockskhaata.stocks.StockFilterTerm;
import com.akshansh.stockskhaata.stocks.StockSearchTerm;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.LabelFormatter;

import java.util.Locale;

import static com.akshansh.stockskhaata.common.Constants.TAG_GROWTH_ASC;
import static com.akshansh.stockskhaata.common.Constants.TAG_GROWTH_DESC;
import static com.akshansh.stockskhaata.common.Constants.TAG_GROWTH_PERCENT_ASC;
import static com.akshansh.stockskhaata.common.Constants.TAG_GROWTH_PERCENT_DESC;
import static com.akshansh.stockskhaata.common.Constants.TAG_NAME_ASC;
import static com.akshansh.stockskhaata.common.Constants.TAG_NAME_DESC;
import static com.akshansh.stockskhaata.common.Constants.TAG_STOCK_PRICE_ASC;
import static com.akshansh.stockskhaata.common.Constants.TAG_STOCK_PRICE_DESC;

public class FilterDialogViewMvcImpl extends BaseObservableViewMvc<FilterDialogViewMvc.Listener>
        implements FilterDialogViewMvc {
    private FilterDialogBinding binding;

    public FilterDialogViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent) {
        binding = FilterDialogBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
    }

    @Override
    public void bindView() {
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.OnClearButtonClicked();
                }
            }
        });
        binding.applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.OnApplyButtonClicked(getTerms());
                }
            }
        });
        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.OnCloseButtonClicked();
                }
            }
        });
        binding.nameFilterAsc.setTag(TAG_NAME_DESC);
        binding.nameFilterDsc.setTag(TAG_NAME_ASC);
        binding.growthAsc.setTag(TAG_GROWTH_DESC);
        binding.growthDsc.setTag(TAG_GROWTH_ASC);
        binding.growthPercentAsc.setTag(TAG_GROWTH_PERCENT_DESC);
        binding.growthPercentDsc.setTag(TAG_GROWTH_PERCENT_ASC);
        binding.stockPriceAsc.setTag(TAG_STOCK_PRICE_DESC);
        binding.stockPriceDsc.setTag(TAG_STOCK_PRICE_ASC);
    }

    @Override
    public void setEnableNSEOption(boolean enabled) {
        binding.nseFilter.setChecked(enabled);
    }

    @Override
    public void setEnableBSEOption(boolean enabled) {
        binding.bseFilter.setChecked(enabled);
    }

    @Override
    public void setEnableFavoriteOption(boolean enabled) {
        binding.favoritesFilter.setChecked(enabled);
    }

    @Override
    public void setEnableShortOption(boolean enabled) {
        binding.shortSellFilter.setChecked(enabled);
    }

    @Override
    public void setEnableSortOption(@Nullable String tag) {
        if(tag != null) {
            switch (tag) {
                case TAG_NAME_DESC:
                    binding.nameFilterAsc.setChecked(true);
                    return;
                case TAG_NAME_ASC:
                    binding.nameFilterDsc.setChecked(true);
                    return;
                case TAG_GROWTH_DESC:
                    binding.growthAsc.setChecked(true);
                    return;
                case TAG_GROWTH_ASC:
                    binding.growthDsc.setChecked(true);
                    return;
                case TAG_GROWTH_PERCENT_DESC:
                    binding.growthPercentAsc.setChecked(true);
                    return;
                case TAG_GROWTH_PERCENT_ASC:
                    binding.growthPercentDsc.setChecked(true);
                case TAG_STOCK_PRICE_DESC:
                    binding.stockPriceAsc.setChecked(true);
                    return;
                case TAG_STOCK_PRICE_ASC:
                    binding.stockPriceDsc.setChecked(true);
                    return;
                default:
                    throw new IllegalArgumentException("unknown tag passed");
            }
        }
    }

    @Override
    public void setStockPriceRange(float low, float high) {
        binding.stockPriceRangeSlider.setValueFrom(low);
        binding.stockPriceRangeSlider.setValueTo(high);
        binding.stockPriceRangeSlider.setStepSize(250);
        binding.stockPriceRangeSlider.setLabelFormatter(new StockPriceLabelFormatter());
    }

    @Override
    public void setStockPriceSelectedRange(float low, float high) {
        binding.stockPriceRangeSlider.setValues(low,high);
    }

    @Override
    public void setGrowthPercentRange(float low,float high) {
        binding.growthPercentageSlider.setValueFrom(low);
        binding.growthPercentageSlider.setValueTo(high);
        binding.growthPercentageSlider.setStepSize(2);
        binding.growthPercentageSlider.setLabelFormatter(new GrowthPercentLabelFormatter());
    }

    @Override
    public void setGrowthPercentSelectedRange(float low, float high) {
        binding.growthPercentageSlider.setValues(low,high);
    }

    @Override
    public void clearBinding() {
        binding = null;
    }

    private StockFilterTerm getTerms() {
        StockFilterTerm terms = new StockFilterTerm();
        terms.setBSE(binding.bseFilter.isChecked());
        terms.setNSE(binding.nseFilter.isChecked());
        terms.setFavorite(binding.favoritesFilter.isChecked());
        terms.setShortSell(binding.shortSellFilter.isChecked());
        terms.setSortOption(getSortOption());
        terms.setGrowthPercentLow(binding.growthPercentageSlider.getValues().get(0));
        terms.setGrowthPercentHigh(binding.growthPercentageSlider.getValues().get(1));
        terms.setStockPriceLow(binding.stockPriceRangeSlider.getValues().get(0));
        terms.setStockPriceHigh(binding.stockPriceRangeSlider.getValues().get(1));
        return terms;
    }

    private String getSortOption() {
        for (int id : binding.sortChipGroup.getCheckedChipIds()) {
            if (id == binding.sortChipGroup.getCheckedChipId()) {
                return findViewById(id).getTag().toString();
            }
        }
        return null;
    }

    private class StockPriceLabelFormatter implements LabelFormatter{
        @NonNull
        @Override
        public String getFormattedValue(float value) {
            if(value <= 10000)
                return String.format(Locale.ENGLISH,"%s %d",getString(R.string.rupees_symbol),
                         Math.round(value));
            else
                return getString(R.string.rupees_symbol)+" 10000+";
        }
    }

    private static class GrowthPercentLabelFormatter implements LabelFormatter{
        @NonNull
        @Override
        public String getFormattedValue(float value) {
            if(Math.abs(value) <= 100)
                return String.format(Locale.ENGLISH,"%d%%",Math.round(value));
            else if(value > 100)
                return ">100%";
            else
                return "<100%";
        }
    }
}
