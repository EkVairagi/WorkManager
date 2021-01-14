package com.example.workmanagerexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkerClass extends Worker
{
    public static final String KEY_TASK_OUTPUT = "key_task_output";

    public WorkerClass(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data data = getInputData();
        String desc = data.getString(MainActivity.KEY_TASK_DESC);
        displayNotification("Hey i'm your work",desc);


        Data data1 = new Data.Builder()
                .putString(KEY_TASK_OUTPUT,"Task finished successfully").build();



        return Result.success(data1);
    }

    private void displayNotification(String task,String desc)
    {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel("workerclass","workerclass",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"workerclass")
                .setContentTitle(task).setContentText(desc).setSmallIcon(R.mipmap.ic_launcher);
        notificationManager.notify(1,builder.build());

    }


}
