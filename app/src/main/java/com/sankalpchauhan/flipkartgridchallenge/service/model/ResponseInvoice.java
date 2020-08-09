package com.sankalpchauhan.flipkartgridchallenge.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseInvoice implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("cName")
    @Expose
    private String cName;
    @SerializedName("cAddress")
    @Expose
    private String cAddress;
    @SerializedName("cEmail")
    @Expose
    private String cEmail;
    @SerializedName("cGstIn")
    @Expose
    private String cGstIn;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("invoice_number")
    @Expose
    private String invoiceNumber;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("lines")
    @Expose
    private List<Line> lines = null;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("__v")
    @Expose
    private Integer v;
    private final static long serialVersionUID = 5254652386958658950L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCAddress() {
        return cAddress;
    }

    public void setCAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public String getCEmail() {
        return cEmail;
    }

    public void setCEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getCGstIn() {
        return cGstIn;
    }

    public void setCGstIn(String cGstIn) {
        this.cGstIn = cGstIn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
