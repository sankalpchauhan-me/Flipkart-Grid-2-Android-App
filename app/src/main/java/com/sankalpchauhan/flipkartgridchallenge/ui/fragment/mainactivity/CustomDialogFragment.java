package com.sankalpchauhan.flipkartgridchallenge.ui.fragment.mainactivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.sankalpchauhan.flipkartgridchallenge.R;
import com.sankalpchauhan.flipkartgridchallenge.databinding.ProcessingDialogBinding;

public class CustomDialogFragment extends Dialog {
    private ProcessingDialogBinding binding;
    public CustomDialogFragment(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.processing_dialog);
    }
}
