package com.akshansh.stockskhaata.screens.stockslist.stocklistitem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.Utils;
import com.akshansh.stockskhaata.databinding.LayoutStockListItemBinding;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.views.BaseObservableViewMvc;

import java.util.Locale;

import static com.akshansh.stockskhaata.common.Utils.getGrowthText;
import static com.akshansh.stockskhaata.common.Utils.getGrowthTextFormatted;
import static com.akshansh.stockskhaata.common.Utils.getInvestmentText;
import static com.akshansh.stockskhaata.common.Utils.getInvestmentTextFormatted;

public class StockListItemViewMvcImpl extends BaseObservableViewMvc<StockListItemViewMvc.Listener>
        implements StockListItemViewMvc{
    private LayoutStockListItemBinding binding;

    public StockListItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        binding = LayoutStockListItemBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
    }

    @Override
    public void bindViewCollapsed(StockSchema stock, int position) {
        hideDetails();
        binding.toggleDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.detailsContainer.setVisibility(View.VISIBLE);
                for(Listener listener: getListeners()){
                    listener.onStockListItemExpanded(position);
                }
            }
        });
    }

    @Override
    public void bindViewExpanded(StockSchema stock, int position) {
        showDetails();
        binding.toggleDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.detailsContainer.setVisibility(View.GONE);
                for(Listener listener: getListeners()){
                    listener.onStockListItemCollapsed(position);
                }
            }
        });
    }

    @Override
    public void clearBinding() {
        binding = null;
    }

    @Override
    public void setStockName(@NonNull String stockName) {
        binding.stockNameTextView.setText(stockName);
    }

    @Override
    public void setTimeStamp(@NonNull String timeStamp) {
        binding.lastUpdateTime.setText(Utils.getTime(Long.parseLong(timeStamp)));
    }

    @Override
    public void setBuyPrice(double buyPrice) {
        binding.buyPriceText.setText(String.format(Locale.ENGLISH,"%s %.2f",
                getString(R.string.rupees_symbol),buyPrice));
    }

    @Override
    public void setSellPrice(double sellPrice) {
        binding.sellPriceText.setText(String.format(Locale.ENGLISH,"%s %.2f",
                getString(R.string.rupees_symbol),sellPrice));
    }

    @Override
    public void setQuantity(int quantity) {
        binding.quantityText.setText(String.format(Locale.ENGLISH,"%d",
                quantity));
    }

    @Override
    public void setMarket(@NonNull String market) {
        binding.marketText.setText(Utils.getMarketText(market));
    }

    @Override
    public void setTotalInvestment(double totalInvestment) {
        String totalInvestmentLarge = getInvestmentTextFormatted(totalInvestment);
        String totalInvestmentShort = getInvestmentText(totalInvestment);
        binding.investmentPriceText.setText(totalInvestmentShort);
        binding.investmentLargeText.setText(totalInvestmentLarge);
    }

    @Override
    public void setGrowth(double growth, double growthPercentage) {
        String growthHeader = getGrowthTextFormatted(growth,growthPercentage);
        String growthText = getGrowthText(growth,growthPercentage);
        binding.growthPriceText.setText(growthText);
        binding.growthText.setText(growthHeader);
    }

    @Override
    public void isFavorite(boolean favorite) {
        binding.favoriteButton.setImageResource(favorite ? R.drawable.ic_favorite
                : R.drawable.ic_non_favorite);
    }

    @Override
    public void isShortTrade(boolean shortTrade) {
        binding.shortSellText.setVisibility(shortTrade ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setGrowthTextColor(double growth) {
        if(growth >= 0){
            binding.growthText.setTextColor(getContext().getColor(R.color.trend_up_green));
            binding.growthPriceText.setTextColor(getContext().getColor(R.color.trend_up_green));
        }else{
            binding.growthText.setTextColor(getContext().getColor(R.color.trending_down_red));
            binding.growthPriceText.setTextColor(getContext().getColor(R.color.trending_down_red));
        }
    }

    @Override
    public void setFavoriteButton(StockSchema stock){
        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stock.setFavorite(!stock.isFavorite());
                isFavorite(stock.isFavorite());
                for(Listener listener: getListeners()){
                    listener.onFavoriteButtonClicked(stock);
                }
            }
        });
    }

    @Override
    public void setEditButton(StockSchema stock) {
        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.onEditButtonClicked(stock);
                }
            }
        });
    }

    @Override
    public void setDeleteButton(StockSchema stock) {
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener: getListeners()){
                    listener.onDeleteButtonClicked(stock);
                }
            }
        });
    }

    private void hideDetails() {
        binding.detailsContainer.setVisibility(View.GONE);
        binding.toggleDetailsButton.setText(Constants.EXPAND);
    }

    private void showDetails() {
        binding.detailsContainer.setVisibility(View.VISIBLE);
        binding.toggleDetailsButton.setText(Constants.COLLAPSE);
    }
}
