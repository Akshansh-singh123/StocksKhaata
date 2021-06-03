package com.akshansh.stockskhaata.common.database.stocks;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stocks")
public class StockSchema implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int ID = 0;
    @ColumnInfo(name = "stock_name")
    private String stockName;
    @ColumnInfo(name = "last_updated_timestamp")
    private String lastUpdatedTimestamp;
    @ColumnInfo(name = "buy_price")
    private double buyPrice;
    @ColumnInfo(name = "sell_price")
    private double sellPrice;
    @ColumnInfo(name = "qty")
    private int quantity;
    @ColumnInfo(name = "market")
    private String market;
    @ColumnInfo(name = "growth")
    private double growth;
    @ColumnInfo(name = "growth_percentage")
    private double growthPercentage;
    @ColumnInfo(name = "favorite")
    private boolean favorite;
    @ColumnInfo(name = "shorted")
    private boolean shorted;

    public StockSchema(String stockName, String lastUpdatedTimestamp, double buyPrice,
                       double sellPrice, int quantity, String market, double growth,
                       double growthPercentage, boolean favorite, boolean shorted) {
        this.stockName = stockName;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.market = market;
        this.growth = growth;
        this.growthPercentage = growthPercentage;
        this.favorite = favorite;
        this.shorted = shorted;
    }

    protected StockSchema(Parcel in) {
        ID = in.readInt();
        stockName = in.readString();
        lastUpdatedTimestamp = in.readString();
        buyPrice = in.readDouble();
        sellPrice = in.readDouble();
        quantity = in.readInt();
        market = in.readString();
        growth = in.readDouble();
        growthPercentage = in.readDouble();
        favorite = in.readByte() != 0;
        shorted = in.readByte() != 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(String lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getGrowth() {
        return growth;
    }

    public void setGrowth(double growth) {
        this.growth = growth;
    }

    public double getGrowthPercentage() {
        return growthPercentage;
    }

    public void setGrowthPercentage(double growthPercentage) {
        this.growthPercentage = growthPercentage;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isShorted() {
        return shorted;
    }

    public void setShorted(boolean shorted) {
        this.shorted = shorted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(stockName);
        dest.writeString(lastUpdatedTimestamp);
        dest.writeDouble(buyPrice);
        dest.writeDouble(sellPrice);
        dest.writeInt(quantity);
        dest.writeString(market);
        dest.writeDouble(growth);
        dest.writeDouble(growthPercentage);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeByte((byte) (shorted ? 1 : 0));
    }

    public static final Creator<StockSchema> CREATOR = new Creator<StockSchema>() {
        @Override
        public StockSchema createFromParcel(Parcel in) {
            return new StockSchema(in);
        }

        @Override
        public StockSchema[] newArray(int size) {
            return new StockSchema[size];
        }
    };
}
