package com.example.workmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button bt_click;
    private TextView tv_status;

    public static final String KEY_TASK_DESC = "key_task_desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder().putString(KEY_TASK_DESC,"Hey I am sending the work data")
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkerClass.class)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        findViewById(R.id.bt_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(getApplicationContext()).enqueue(oneTimeWorkRequest);
            }
        });

        tv_status = findViewById(R.id.tv_status);

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {

                if (workInfo!=null)
                {
                    if (workInfo.getState().isFinished())
                    {
                        Data data1 = workInfo.getOutputData();
                        String output = data1.getString(WorkerClass.KEY_TASK_OUTPUT);
                        tv_status.append(output+"\n");
                    }

                    String status = workInfo.getState().name();
                    tv_status.append(status+"\n");
                }

            }
        });

    }
}