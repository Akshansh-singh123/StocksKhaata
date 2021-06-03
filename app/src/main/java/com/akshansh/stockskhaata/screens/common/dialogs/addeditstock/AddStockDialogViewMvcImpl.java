package com.akshansh.stockskhaata.screens.common.dialogs.addeditstock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.Constants.OperationModes;
import com.akshansh.stockskhaata.common.Utils;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.databinding.DialogAddStockBinding;
import com.akshansh.stockskhaata.screens.common.views.BaseObservableViewMvc;

import static android.text.TextUtils.isEmpty;


public class AddStockDialogViewMvcImpl extends BaseObservableViewMvc<AddStockDialogViewMvc.Listener>
        implements AddStockDialogViewMvc {
    private DialogAddStockBinding binding;
    private StockSchema stock;
    private String stockName;
    private String buyPriceText;
    private String sellPriceText;
    private String marketText;
    private String quantityText;

    public AddStockDialogViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        binding = DialogAddStockBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        stock = null;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,Constants.markets);
        binding.marketSelect.setAdapter(adapter);
        setSaveButton();
    }

    @Override
    public void setStockName(@Nullable String stockName) {
        if (stockName != null) {
            binding.stockNameEditText.setText(stockName);
        }
    }

    @Override
    public void setStockQuantity(@Nullable String quantity) {
        if (quantity != null) {
            binding.quantityEditText.setText(quantity);
        }
    }

    @Override
    public void setBuyPrice(@Nullable String buyPrice) {
        if (buyPrice != null) {
            binding.buyPriceEditText.setText(buyPrice);
        }
    }

    @Override
    public void setSellPrice(@Nullable String sellPrice) {
        if (sellPrice != null) {
            binding.sellPriceEditText.setText(sellPrice);
        }
    }

    @Override
    public void setMarket(@Nullable String market) {
        if (market != null) {
            if(Constants.markets.contains(market)){
                binding.marketSelect.setText(market);
            }
        }
    }

    @Override
    public void setIsShort(boolean isShort) {
        binding.shortSellCheckBox.setChecked(isShort);
    }

    @Override
    public void setStock(@Nullable StockSchema stock) {
        this.stock = stock;
    }

    public void setSaveButton() {
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateGetFields();
                if(stock != null) {
                    for (Listener listener : getListeners()) {
                        listener.onSaveButtonClicked(stock, OperationModes.CREATE);
                    }
                }
            }
        });
    }

    @Override
    public void clearBinding() {
        binding = null;
    }

    private void validateGetFields() {
        if(isValidFieldEntries()) {
            stock = new StockSchema(stockName,
                    String.valueOf(System.currentTimeMillis()),
                    Double.parseDouble(buyPriceText),
                    Double.parseDouble(sellPriceText),
                    Integer.parseInt(quantityText),
                    marketText,
                    Utils.getGrowth(buyPriceText, sellPriceText, quantityText),
                    Utils.getGrowthPercentage(buyPriceText, sellPriceText, quantityText),
                    false,
                    isShortTrade());
        }
    }

    private boolean isValidStockName(){
        stockName = binding.stockNameEditText.getText().toString().trim();
        if(isEmpty(stockName)){
            binding.stockNameLayout.setError("This field cannot be empty");
            return false;
        }
        return true;
    }

    private boolean isValidBuyPrice(){
        buyPriceText = binding.buyPriceEditText.getText().toString().trim();
        if(isEmpty(buyPriceText)){
            binding.buyPriceLayout.setError("This field cannot be empty");
            return false;
        }
        return true;
    }

    private boolean isValidSellPrice(){
        sellPriceText = binding.sellPriceEditText.getText().toString().trim();
        if(isEmpty(sellPriceText)){
            binding.sellPriceLayout.setError("This field cannot be empty");
            return false;
        }
        return true;
    }

    private boolean isValidQuantity(){
        quantityText = binding.quantityEditText.getText().toString().trim();
        if(isEmpty(quantityText)){
            binding.quantityLayout.setError("This field cannot be empty");
            return false;
        }
        return true;
    }

    private boolean isValidMarketText(){
        marketText = binding.marketSelect.getText().toString().trim();
        if(isEmpty(marketText)){
            binding.marketSelectLayout.setError("This field cannot be empty");
            return false;
        }
        return true;
    }

    private boolean isShortTrade(){
        return binding.shortSellCheckBox.isChecked();
    }

    private boolean isValidFieldEntries() {
        return isValidStockName() && isValidBuyPrice() &&
                isValidSellPrice() && isValidQuantity() && isValidMarketText();
    }
}
