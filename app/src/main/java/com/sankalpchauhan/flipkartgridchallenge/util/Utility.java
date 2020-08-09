package com.sankalpchauhan.flipkartgridchallenge.util;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility {

    public static void setSnackBarNoAction(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_LONG);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public static String getSHA(byte[] input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return bytesToHex(md.digest(input));
    }

    public static String bytesToHex(byte[] in) {
        StringBuilder builder = new StringBuilder();
        byte[] arrayOfByte = in;
        int j = in.length;
        for (int i = 0; i < j; i++) {
            byte b = arrayOfByte[i];
            builder.append(String.format("%02x", new Object[]{Byte.valueOf(b)}));
        }
        return builder.toString().toUpperCase();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
