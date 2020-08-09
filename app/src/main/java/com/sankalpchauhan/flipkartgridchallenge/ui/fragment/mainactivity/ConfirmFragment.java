package com.sankalpchauhan.flipkartgridchallenge.ui.fragment.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evrencoskun.tableview.TableView;
import com.sankalpchauhan.flipkartgridchallenge.MainActivity;
import com.sankalpchauhan.flipkartgridchallenge.databinding.FragmentConfirmBinding;
import com.sankalpchauhan.flipkartgridchallenge.service.model.Invoice;
import com.sankalpchauhan.flipkartgridchallenge.service.model.ParsedInvoice;
import com.sankalpchauhan.flipkartgridchallenge.service.model.table.CellModel;
import com.sankalpchauhan.flipkartgridchallenge.service.model.table.ColumnHeaderModel;
import com.sankalpchauhan.flipkartgridchallenge.service.model.table.RowHeaderModel;
import com.sankalpchauhan.flipkartgridchallenge.ui.adapter.MyTableAdapter;
import com.sankalpchauhan.flipkartgridchallenge.viewmodel.TableViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class ConfirmFragment extends Fragment {
    private FragmentConfirmBinding binding;

    private List<RowHeaderModel> mRowHeaderList;
    private List<ColumnHeaderModel> mColumnHeaderList;
    private List<List<CellModel>> mCellList;
    private ParsedInvoice parsedInvoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConfirmBinding.inflate(inflater, container, false);
        if(getActivity() instanceof MainActivity) {
            parsedInvoice = ((MainActivity) getActivity()).getParsedInvoice();
            TableViewModel tableViewModel = new TableViewModel();
            Timber.e("CHECK: "+parsedInvoice.getInvoice().getLines().get(0).getPid());
            tableViewModel.generateListForTableView(parsedInvoice.getInvoice().getLines());
            mRowHeaderList = tableViewModel.getRowHeaderModelList();
            mColumnHeaderList = tableViewModel.getColumHeaderModeList();
            mCellList = tableViewModel.getCellModelList();
            MyTableAdapter adapter = new MyTableAdapter();
            binding.contentContainer.setAdapter(adapter);
            binding.contentContainer.setRowHeaderWidth(0);
            adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
            binding.amountET.setText(String.valueOf(parsedInvoice.getInvoice().getAmount()));
            binding.cAddress.setText(parsedInvoice.getInvoice().getCAddress());
            binding.cEmail.setText(parsedInvoice.getInvoice().getCEmail());
            binding.cGSTIN.setText(parsedInvoice.getInvoice().getCGstIn());
            binding.cName.setText(parsedInvoice.getInvoice().getCName());
            binding.date.setText(parsedInvoice.getInvoice().getDate());
            binding.invoiceNumber.setText(String.valueOf(parsedInvoice.getInvoice().getInvoiceNumber()));
            binding.cancelBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).binding.toolbarTitle.setText("Invoices");
                        ((MainActivity) getActivity()).originalFunctionality();
                        ((MainActivity) getActivity()).loadFragment(new InvoicesFragment());
                    }
                }
            });
            ((MainActivity) getActivity()).binding.fabMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).binding.toolbarTitle.setText("Invoices");
                        ((MainActivity) getActivity()).originalFunctionality();
                        Date date= new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        ((MainActivity) getActivity()).userAcceptedData(new Invoice(binding.invoiceNumber.getText().toString(), day+"/"+month+"/"+year));
                    }
                }
            });
        }


        return binding.getRoot();
    }
}
