package com.sankalpchauhan.flipkartgridchallenge.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sankalpchauhan.flipkartgridchallenge.config.Constants;
import com.sankalpchauhan.flipkartgridchallenge.service.model.Invoice;

import timber.log.Timber;

@Database(entities = {Invoice.class}, version = 1, exportSchema = false)
public abstract class InvoiceDatabase extends RoomDatabase {
    private static final String LOG_TAG = InvoiceDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = Constants.DATABASE_NAME;
    private static InvoiceDatabase sInstance;

    /**
     * Singleton design Pattern
     *
     * @param context
     * @return
     */
    public static InvoiceDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Timber.d(LOG_TAG+ ":Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        InvoiceDatabase.class, InvoiceDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Timber.d(LOG_TAG+ ":Getting the database instance");
        return sInstance;
    }

    public abstract InvoiceDao invoiceDao();
}
