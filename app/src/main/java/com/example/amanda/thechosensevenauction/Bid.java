package com.example.amanda.thechosensevenauction;

import java.io.Serializable;

public class Bid implements Serializable {
    private int id;
    private int auctionId;
    private String customerId;
    private String startTime;
    private double bidPrice;


    public Bid(int id, int auctionId, String customerId, String startTime, double bidPrice) {
        this.id = id;
        this.auctionId = auctionId;
        this.customerId = customerId;
        this.startTime = startTime;
        this.bidPrice = bidPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }
}
