package com.mirea.makhankodv.audiorecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.mirea.makhankodv.audiorecord.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private boolean isStartRecording = true;
    private boolean isStartPlaying = true;
    private String recordFilePath = null;

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean audioPermissionGranted = result.getOrDefault(Manifest.permission.RECORD_AUDIO, false);
                Boolean storagePermissionGranted = result.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);
                if (!audioPermissionGranted || !storagePermissionGranted) {
                    Log.e("MainActivity", "Permissions denied");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recordFilePath = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "audiorecordtest.3gp").getAbsolutePath();

        checkPermissions();

        binding.recordButton.setOnClickListener(v -> {
            if (isStartRecording) {
                binding.recordButton.setText("Stop recording");
                binding.playButton.setEnabled(false);
                startRecording();
            } else {
                binding.recordButton.setText("Start recording");
                binding.playButton.setEnabled(true);
                stopRecording();
            }
            isStartRecording = !isStartRecording;
        });

        binding.playButton.setOnClickListener(v -> {
            if (isStartPlaying) {
                binding.playButton.setText("Stop playing");
                binding.recordButton.setEnabled(false);
                startPlaying();
            } else {
                binding.playButton.setText("Start playing");
                binding.recordButton.setEnabled(true);
                stopPlaying();
            }
            isStartPlaying = !isStartPlaying;
        });
    }

    private void checkPermissions() {
        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestPermissionLauncher.launch(new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            });
        }
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e("MainActivity", "prepare() failed");
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e("MainActivity", "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.stop();
        player.release();
        player = null;
    }
}
