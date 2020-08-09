package com.sankalpchauhan.flipkartgridchallenge.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Line implements Serializable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("gstRate")
    @Expose
    private Integer gstRate;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("pid")
    @Expose
    private String pid;
    private final static long serialVersionUID = -4421633091822704737L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getGstRate() {
        return gstRate;
    }

    public void setGstRate(Integer gstRate) {
        this.gstRate = gstRate;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
