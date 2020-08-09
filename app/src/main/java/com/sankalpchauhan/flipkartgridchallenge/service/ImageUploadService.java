package com.sankalpchauhan.flipkartgridchallenge.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sankalpchauhan.flipkartgridchallenge.MainActivity;
import com.sankalpchauhan.flipkartgridchallenge.R;
import com.sankalpchauhan.flipkartgridchallenge.config.Constants;
import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;

import java.io.File;

import timber.log.Timber;

import static com.sankalpchauhan.flipkartgridchallenge.GridApp.CHANNEL_ID;

public class ImageUploadService extends Service {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    int notifyID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String email  = DefaultPrefSettings.getInstance().getUserEmail();
        Uri uri = Uri.parse(intent.getStringExtra(Constants.IMAGE_URI));
//        return super.onStartCommand(intent, flags, startId);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Uploading Invoice")
                .setContentText("Test")
                .setProgress(100, 0, false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_assignment_black_24dp)
                .setContentIntent(pendingIntent);

        startForeground(1, notification.build());
        Intent broadcastIntent = new Intent();
        StorageReference userInvoiceReference = storageRef.child(email+'/'+uri.getLastPathSegment());
        UploadTask uploadTask = userInvoiceReference.putFile(uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if(!task.isSuccessful()){
                throw  task.getException();
            }
            return userInvoiceReference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Uri downloadUri = task.getResult();
                Timber.e(downloadUri.toString());
                broadcastIntent.setAction(Constants.BROADCAST_ACTION_SUCCESS);
                broadcastIntent.putExtra(Constants.IMAGE_URI, downloadUri.toString());
                sendBroadcast(broadcastIntent);
                stopSelf();
            } else {
                broadcastIntent.setAction(Constants.BROADCAST_ACTION_FAIL);
                sendBroadcast(broadcastIntent);
                stopSelf();
            }
        });

        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            notification.setContentText("Progress: "+(int)progress+"%");
            notification.setProgress(100, (int)progress, false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getSystemService(NotificationManager.class).notify(notifyID, notification.build());
            }
        }).addOnPausedListener(taskSnapshot -> {

        });

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
