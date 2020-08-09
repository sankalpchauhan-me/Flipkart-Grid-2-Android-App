package com.sankalpchauhan.flipkartgridchallenge.service.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "invoices")
public class Invoice {
    @PrimaryKey(autoGenerate = true)
    public int id=0;

    @ColumnInfo(name = "InvoiceID")
    @SerializedName("invoiceId")
    @Expose
    private String invoiceID;
    @ColumnInfo(name = "Date")
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;

    @Ignore
    public Invoice(){}

    /**
     * Room Constructor
     */
    public Invoice(String invoiceID, String dateCreated) {
        this.invoiceID = invoiceID;
        this.dateCreated = dateCreated;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
