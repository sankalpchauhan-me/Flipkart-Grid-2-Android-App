package com.sankalpchauhan.flipkartgridchallenge.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sankalpchauhan.flipkartgridchallenge.databinding.ScannedItemBinding;
import com.sankalpchauhan.flipkartgridchallenge.service.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoicesAdapter extends RecyclerView.Adapter<InvoicesAdapter.InvoiceHolder> {
    final InvoiceAdapterClickListener walletAdapterClickListener;
    List<Invoice> invoiceList = new ArrayList<>();

    public InvoicesAdapter(InvoiceAdapterClickListener walletAdapterClickListener){
        this.walletAdapterClickListener=walletAdapterClickListener;
    }
    private ScannedItemBinding binding;
    @NonNull
    @Override
    public InvoicesAdapter.InvoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ScannedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InvoiceHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoicesAdapter.InvoiceHolder holder, int position) {
        Invoice invoice = invoiceList.get(position);
        holder.scanDate.setText("Scanned on "+invoice.getDateCreated());
        holder.scanName.setText("Innvoice ID: "+invoice.getInvoiceID());

    }

    @Override
    public int getItemCount() {
        if(invoiceList==null) {
            return 0;
        }
        return invoiceList.size();
    }

    public void setInvoiceData(List<Invoice> softwareWalletList){
        invoiceList = softwareWalletList;
        notifyDataSetChanged();
    }

    public interface InvoiceAdapterClickListener{
        void onWalletClick(Invoice invoice, int position);
    }

    public class InvoiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView scanName;
        TextView scanDate;
        public InvoiceHolder(@NonNull ScannedItemBinding itemView) {
            super(itemView.getRoot());
            scanName = itemView.scanName;
            scanDate = itemView.scanDate;
        }

        @Override
        public void onClick(View view) {

        }
    }
}
