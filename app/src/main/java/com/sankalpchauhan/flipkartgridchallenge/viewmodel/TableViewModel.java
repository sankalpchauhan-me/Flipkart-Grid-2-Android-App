package com.sankalpchauhan.flipkartgridchallenge.viewmodel;

import android.view.Gravity;

import com.sankalpchauhan.flipkartgridchallenge.service.model.Line;
import com.sankalpchauhan.flipkartgridchallenge.service.model.table.CellModel;
import com.sankalpchauhan.flipkartgridchallenge.service.model.table.ColumnHeaderModel;
import com.sankalpchauhan.flipkartgridchallenge.service.model.table.RowHeaderModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class TableViewModel {
    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> mCellModelList;

    /*
       - Each of Column Header -
            "price"
            "rate"
            "desc"
            "gstRate"
            "qty"
            "pid"
     */

    public int getColumnTextAlign(int column) {
      switch (column){
          case 2:
              return Gravity.END;
          case 3:
              return Gravity.END;
          case 4:
              return Gravity.END;
          case 5:
              return Gravity.END;
          default:
              return Gravity.CENTER;
      }
    }
    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Product Id"));
        list.add(new ColumnHeaderModel("Description"));
        list.add(new ColumnHeaderModel("Rate"));
        list.add(new ColumnHeaderModel("GST"));
        list.add(new ColumnHeaderModel("Quantity"));
        list.add(new ColumnHeaderModel("Price"));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<Line> productList) {
        List<List<CellModel>> lists = new ArrayList<>();

        for (int i = 0; i < productList.size(); i++) {
            Line product = productList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, product.getPid()));          // "Id"
            list.add(new CellModel("2-" + i, product.getDesc()));        // "Desc"
            list.add(new CellModel("3-" + i, product.getRate()));    // "Rate"
            list.add(new CellModel("4-" + i, product.getGstRate()));       // "GST Rate"
            list.add(new CellModel("5-" + i, product.getQty()));   // "Qty"
            list.add(new CellModel("6-" + i, product.getPrice()));      // "Price"

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList(int size) {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            // In this example, Row headers just shows the index of the TableView List.
            list.add(new RowHeaderModel(String.valueOf(i + 1)));
        }
        return list;
    }


    public List<ColumnHeaderModel> getColumHeaderModeList() {
        Timber.e("Header Check: "+mColumnHeaderModelList.get(0).getData());
        return mColumnHeaderModelList;
    }

    public List<RowHeaderModel> getRowHeaderModelList() {
        Timber.e("Header Check: "+mRowHeaderModelList.get(0).getData());
        return mRowHeaderModelList;
    }

    public List<List<CellModel>> getCellModelList() {
        Timber.e("Header Check: "+mCellModelList.get(0).get(0).getData());
        return mCellModelList;
    }


    public void generateListForTableView(List<Line> lines) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(lines);
        mRowHeaderModelList = createRowHeaderList(lines.size());
    }
}
