package com.devhp.workmanagerjavasample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.devhp.workmanagerjavasample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static final String KEY_INPUT_DATA = "KEY_INPUT";
    public static final String KEY_OUTPUT_DATA = "KEY_OUTPUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Data data = new Data.Builder().putInt(KEY_INPUT_DATA, 1975).build();
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DemoWorker.class).setInputData(data).setConstraints(constraints).build();
        binding.btnRunTask.setOnClickListener(v -> WorkManager.getInstance(getApplicationContext()).enqueue(oneTimeWorkRequest));

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null) {
                binding.tvTaskStatus.setText(workInfo.getState().name());
                if (workInfo.getState().isFinished()) {
                    Data resultData = workInfo.getOutputData();
                    String message = resultData.getString(KEY_OUTPUT_DATA);
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}