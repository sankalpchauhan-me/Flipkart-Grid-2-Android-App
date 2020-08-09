package com.sankalpchauhan.flipkartgridchallenge.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ParsedInvoice implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("invoice")
    @Expose
    private ResponseInvoice  invoice;
    private final static long serialVersionUID = 707221871532828434L;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseInvoice  getInvoice() {
        return invoice;
    }

    public void setInvoice(ResponseInvoice invoice) {
        this.invoice = invoice;
    }


}
