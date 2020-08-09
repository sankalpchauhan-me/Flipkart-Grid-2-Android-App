package com.sankalpchauhan.flipkartgridchallenge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sankalpchauhan.flipkartgridchallenge.database.InvoiceDatabase;
import com.sankalpchauhan.flipkartgridchallenge.service.model.Invoice;
import com.sankalpchauhan.flipkartgridchallenge.service.model.ParsedInvoice;
import com.sankalpchauhan.flipkartgridchallenge.service.repository.BackendRepository;
import com.sankalpchauhan.flipkartgridchallenge.util.AppExecutors;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private BackendRepository backendRepository;
    private InvoiceDatabase database;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        backendRepository = BackendRepository.getInstance();
        database = InvoiceDatabase.getInstance(this.getApplication());
    }

    public LiveData<ParsedInvoice> getParsedInvoice(String url){
        return backendRepository.postInvoice(url);
    }

    public void InsertWalletToDB(Invoice invoice){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.invoiceDao().InsertInvoiceToRoom(invoice);
            }
        });
    }

    public LiveData<List<Invoice>> getDataFromDb(){
        return database.invoiceDao().getAllInvoices();
    }

}
