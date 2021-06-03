package com.akshansh.stockskhaata.stocks;

import com.akshansh.stockskhaata.common.Constants;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.common.database.stocks.StockViewModel;

import javax.inject.Inject;

public class CUDStockUseCase {
    private final StockViewModel viewModel;

    @Inject
    public CUDStockUseCase(StockViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void CUDStock(Constants.OperationModes operation, StockSchema stockSchema) {
        switch (operation){
            case CREATE:
                viewModel.insert(stockSchema);
                break;
            case UPDATE:
                viewModel.update(stockSchema);
                break;
            case DELETE:
                viewModel.delete(stockSchema);
                break;
            default:
                throw new RuntimeException("invalid operation mode");
        }
    }
}
