package com.sankalpchauhan.flipkartgridchallenge;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sankalpchauhan.flipkartgridchallenge.config.Constants;
import com.sankalpchauhan.flipkartgridchallenge.databinding.ActivityMainBinding;
import com.sankalpchauhan.flipkartgridchallenge.service.CompleteUploadBrodcast;
import com.sankalpchauhan.flipkartgridchallenge.service.ImageUploadService;
import com.sankalpchauhan.flipkartgridchallenge.service.model.Invoice;
import com.sankalpchauhan.flipkartgridchallenge.service.model.ParsedInvoice;
import com.sankalpchauhan.flipkartgridchallenge.ui.fragment.mainactivity.ConfirmFragment;
import com.sankalpchauhan.flipkartgridchallenge.ui.fragment.mainactivity.InvoicesFragment;
import com.sankalpchauhan.flipkartgridchallenge.viewmodel.MainActivityViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import timber.log.Timber;

import static com.sankalpchauhan.flipkartgridchallenge.util.Utility.setSnackBarNoAction;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    String[] permissionsArray = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public ActivityMainBinding binding;
    private CompleteUploadBrodcast mReciever;
    private IntentFilter mIntentFilter;
    private MainActivityViewModel mainActivityViewModel;
    private ParsedInvoice parsedInvoice;
    private List<Invoice> invoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbarTop);
        getSupportActionBar().setTitle("");
        setContentView(binding.getRoot());
        initViewModel();
        mReciever = new CompleteUploadBrodcast(this);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Constants.BROADCAST_ACTION_SUCCESS);
        mIntentFilter.addAction(Constants.BROADCAST_ACTION_FAIL);
        originalFunctionality();
        mainActivityViewModel.getDataFromDb().observe(this, new Observer<List<Invoice>>() {
            @Override
            public void onChanged(List<Invoice> invoices) {
                if(invoices!=null) {
                    setInvoices(invoices);
                    loadFragment(new InvoicesFragment());
                }
            }
        });
        loadFragment(new InvoicesFragment());
        binding.curvedBottomNavigationView2.inflateMenu(R.menu.menu_bottom_nav);
    }

    private void initViewModel() {
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReciever, mIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReciever);
        super.onPause();
    }

    private void FileSelector() {
        final CharSequence[] options = {"Take Photo", "Select Files", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Invoice");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    File directory = new File(Environment.getExternalStorageDirectory() + "/grid/images");
                    if (! directory.exists()){
                        boolean isCreated = directory.mkdirs();
                        if(!isCreated){
                            Timber.e("Directory Not Created");
                        }
                        // If you require it to make the entire directory path including parents,
                        // use directory.mkdirs(); here instead.
                    }

                    File photoFile = new File(Environment.getExternalStorageDirectory() + "/grid/images/IMG_"+Calendar.getInstance().getTimeInMillis() +".jpg");
                    Uri photoUri = FileProvider.getUriForFile(MainActivity.this,"com.sankalpchauhan.flipkartgridchallenge.provider" , photoFile);
                    ActivityResultContracts.TakePicture contracts = new ActivityResultContracts.TakePicture();
                    ActivityResultLauncher<Uri> takePicture = registerForActivityResult(contracts, new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean success) {
                            if(success){
                                Timber.e("I am here 2");
                                Timber.e(photoFile.getAbsolutePath());
                                // show dialog
                                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                                final View deleteDialogView = factory.inflate(R.layout.processing_dialog, null);
                                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setView(deleteDialogView);
                                deleteDialogView.findViewById(R.id.backgroundBTN).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.show();

                                createPdfAndProcess(photoFile);

                            } else {
                                Toast.makeText(MainActivity.this, "Error!! Image was not captured. Retry", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Timber.e(photoUri.toString());
                    takePicture.launch(photoUri);
                } else if (options[item].equals("Select Files")) {
                    ActivityResultContracts.GetMultipleContents contracts = new ActivityResultContracts.GetMultipleContents();
                    ActivityResultLauncher<String> launcher = registerForActivityResult(contracts, new ActivityResultCallback<List<Uri>>() {
                        @Override
                        public void onActivityResult(List<Uri> result) {
                            //.....do something with the list of urls
                            for(Uri uri: result){
                                Intent i = new Intent(MainActivity.this, ImageUploadService.class);
                                i.putExtra(Constants.IMAGE_URI, uri.toString());
                                ContextCompat.startForegroundService(MainActivity.this, i);
                            }
                            Timber.e(result.toString());
                        }
                    });
                    launcher.launch("application/pdf|image/*");
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    public void uploadSuccess(String imagePath){
        mainActivityViewModel.getParsedInvoice(imagePath).observe(this, new Observer<ParsedInvoice>() {
            @Override
            public void onChanged(ParsedInvoice parsedInvoice) {
                if(parsedInvoice!=null){
                    setParsedInvoice(parsedInvoice);
                    loadFragment(new ConfirmFragment());
                    binding.toolbarTitle.setText("Confirm Invoice");
                    switchFunctionality();
                } else {
                    Timber.e("Something Went Wrong...");
                }
            }
        });
    }

    public boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            String backStateName = fragment.getClass().getName();
            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

            if (!fragmentPopped){ //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.fragment_replace, fragment);
                ft.addToBackStack(backStateName);
                ft.commit();
            }
            return true;
        } else{
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.fragment_replace)).commit();
        }
        return false;
    }

    public void uploadFail(){
        Timber.e("Upload Fail");
    }

    private void createPdfAndProcess(File file){

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Bitmap straightbitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                PdfDocument document = new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(straightbitmap.getHeight(), straightbitmap.getWidth(), 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#ffffff"));
                canvas.drawPaint(paint);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                straightbitmap = Bitmap.createScaledBitmap(straightbitmap, straightbitmap.getWidth(), straightbitmap.getHeight(), true);
                Bitmap bitmap = Bitmap.createBitmap(straightbitmap, 0, 0, straightbitmap.getWidth(), straightbitmap.getHeight(), matrix, true);

                paint.setColor(Color.BLUE);
                canvas.drawBitmap(bitmap, 0, 0 , null);
                document.finishPage(page);


                File directory = new File(Environment.getExternalStorageDirectory() + "/grid/pdf");
                if (! directory.exists()){
                    boolean isCreated = directory.mkdirs();
                    if(!isCreated){
                        Timber.e("Directory Not Created");
                    }
                    // If you require it to make the entire directory path including parents,
                    // use directory.mkdirs(); here instead.
                }
                // write the document content
                File filePath = new File(Environment.getExternalStorageDirectory() + "/grid/pdf/PDF_"+Calendar.getInstance().getTimeInMillis()+".pdf");
                try {
                    document.writeTo(new FileOutputStream(filePath));
                    Uri fileforPdf = Uri.fromFile(filePath);
                    file.delete();
                    Intent i = new Intent(MainActivity.this, ImageUploadService.class);
                    i.putExtra(Constants.IMAGE_URI, fileforPdf.toString());
                    ContextCompat.startForegroundService(MainActivity.this, i);
//            btn_convert.setText("Check PDF");
//            boolean_save=true;
                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
                }

                // close the document
                document.close();
            }
        });

    }

    public void originalFunctionality(){
        binding.fabMain.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        binding.fabMain.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_white_24dp));
        binding.fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beginPermission()) {
                    FileSelector();
                }
            }
        });
    }

    private void switchFunctionality(){
        binding.fabMain.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.success_green)));
        binding.fabMain.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_24dp));
        binding.fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                originalFunctionality();
            }
        });
    }

    public void userAcceptedData(Invoice invoice){
        mainActivityViewModel.InsertWalletToDB(invoice);
    }


    public boolean beginPermission() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            Timber.i("Permission Not Granted");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Timber.i("Requesting Permission");
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Required Permissions");
                alertBuilder.setMessage("This app requires Camera and Storage Permissions to work. The permissions will only be used when you are using this app, Do you consent to these permissions?");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, permissionsArray, Constants.APP_PERMISSIONS);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            } else {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Required Permissions");
                alertBuilder.setMessage("This app requires Camera and Storage Permissions to work. The permissions will only be used when you are using this app, Do you consent to these permissions?");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, permissionsArray, Constants.APP_PERMISSIONS);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        } else {
            Timber.i("Permission was already granted");
            // Do your Work

            return true;
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.APP_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Do your thing

                } else {
                    Snackbar thePermissions = Snackbar.make(findViewById(android.R.id.content), "Please ennable the permissions", Snackbar.LENGTH_INDEFINITE);
                    thePermissions.setAction("ENABLE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                        }
                    });
                    thePermissions.show();
                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        setSnackBarNoAction(binding.parent, "Press back Again to Exit");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);
    }

    public ParsedInvoice getParsedInvoice() {
        return parsedInvoice;
    }

    public void setParsedInvoice(ParsedInvoice parsedInvoice) {
        this.parsedInvoice = parsedInvoice;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
