package com.sankalpchauhan.flipkartgridchallenge.ui.fragment.mainactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sankalpchauhan.flipkartgridchallenge.MainActivity;
import com.sankalpchauhan.flipkartgridchallenge.databinding.FragmentInvoicesBinding;
import com.sankalpchauhan.flipkartgridchallenge.service.model.Invoice;
import com.sankalpchauhan.flipkartgridchallenge.ui.adapter.InvoicesAdapter;

import java.util.List;

public class InvoicesFragment extends Fragment implements InvoicesAdapter.InvoiceAdapterClickListener {
    private FragmentInvoicesBinding binding;
    private InvoicesAdapter invoicesAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInvoicesBinding.inflate(inflater, container, false);
        setUpRecyclerView(setInvoicesData());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(invoicesAdapter.getItemCount()==0){
            binding.emptyText.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.emptyText.setVisibility(View.INVISIBLE);
            binding.emptyView.setVisibility(View.INVISIBLE);
        }
    }

    private void setUpRecyclerView(List<Invoice> invoiceList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        binding.invoicesRv.setLayoutManager(linearLayoutManager);
        binding.invoicesRv.setHasFixedSize(true);
        invoicesAdapter = new InvoicesAdapter(this);
        binding.invoicesRv.setAdapter(invoicesAdapter);
        invoicesAdapter.setInvoiceData(invoiceList);
    }

    public List<Invoice> setInvoicesData(){
        return ((MainActivity) getActivity()).getInvoices();
    }

    @Override
    public void onWalletClick(Invoice invoice, int position) {

    }
}
