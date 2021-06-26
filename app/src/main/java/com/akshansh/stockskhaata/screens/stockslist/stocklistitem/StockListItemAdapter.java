package com.akshansh.stockskhaata.screens.stockslist.stocklistitem;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.screens.common.ViewMvcFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StockListItemAdapter extends RecyclerView.Adapter<StockListItemAdapter.StockListItemViewHolder>
        implements StockListItemViewMvc.Listener {
    public interface Listener{
        void OnFavoriteButtonClicked(StockSchema stockSchema);
        void OnEditButtonClicked(StockSchema stockSchema);
        void OnDeleteButtonClicked(StockSchema stockSchema);
    }

    private final ViewMvcFactory viewMvcFactory;
    private List<StockSchema> stocks = new ArrayList<>();
    private final Listener listener;
    private final Set<Integer> expandedViewPositions = new HashSet<>();

    public StockListItemAdapter(ViewMvcFactory viewMvcFactory, Listener listener) {
        this.viewMvcFactory = viewMvcFactory;
        this.listener = listener;
    }

    public void bindView(List<StockSchema> stocks){
        this.stocks = new ArrayList<>(stocks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StockListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StockListItemViewMvc viewMvc = viewMvcFactory.getStockListItemViewMvc(parent);
        viewMvc.registerListener(this);
        return new StockListItemViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull  StockListItemAdapter.StockListItemViewHolder holder, int position) {
        StockSchema stock = stocks.get(position);
        if (expandedViewPositions.contains(position)){
            holder.viewMvc.bindViewExpanded(stock,position);
        }else{
            holder.viewMvc.bindViewCollapsed(stock,position);
        }
        holder.viewMvc.setStockName(stock.getStockName());
        holder.viewMvc.setMarket(stock.getMarket());
        holder.viewMvc.setTimeStamp(stock.getLastUpdatedTimestamp());
        holder.viewMvc.setBuyPrice(stock.getBuyPrice());
        holder.viewMvc.setSellPrice(stock.getSellPrice());
        holder.viewMvc.setQuantity(stock.getQuantity());
        holder.viewMvc.isFavorite(stock.isFavorite());
        holder.viewMvc.isShortTrade(stock.isShorted());
        holder.viewMvc.setGrowth(stock.getGrowth(),stock.getGrowthPercentage());
        holder.viewMvc.setTotalInvestment(stock.getQuantity()*stock.getBuyPrice());
        holder.viewMvc.setGrowthTextColor(stock.getGrowth());
        holder.viewMvc.setFavoriteButton(stock);
        holder.viewMvc.setEditButton(stock);
        holder.viewMvc.setDeleteButton(stock);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }


    @Override
    public void onStockListItemExpanded(int position) {
        expandedViewPositions.add(position);
        notifyItemChanged(position);
    }

    @Override
    public void onFavoriteButtonClicked(StockSchema stock) {
        listener.OnFavoriteButtonClicked(stock);
    }

    @Override
    public void onEditButtonClicked(StockSchema stock) {
        listener.OnEditButtonClicked(stock);
    }

    @Override
    public void onDeleteButtonClicked(StockSchema stock) {
        listener.OnDeleteButtonClicked(stock);
    }

    @Override
    public void onStockListItemCollapsed(int position) {
        expandedViewPositions.remove(position);
        notifyItemChanged(position);
    }

    public static class StockListItemViewHolder extends RecyclerView.ViewHolder{
        private final StockListItemViewMvc viewMvc;

        public StockListItemViewHolder(@NonNull StockListItemViewMvc itemView) {
            super(itemView.getRootView());
            viewMvc = itemView;
        }
    }
}
