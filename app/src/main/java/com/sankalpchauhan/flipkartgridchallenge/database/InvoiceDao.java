package com.sankalpchauhan.flipkartgridchallenge.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sankalpchauhan.flipkartgridchallenge.service.model.Invoice;

import java.util.List;

@Dao
public interface InvoiceDao {

    @Query("SELECT * FROM invoices")
    LiveData<List<Invoice>> getAllInvoices();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertInvoiceToRoom(Invoice invoice);

}
