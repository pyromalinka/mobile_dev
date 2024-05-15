package com.mirea.makhankodv.audiorecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
    private static final int REQUEST_CODE_PERMISSION = 200;
    private ActivityMainBinding binding;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private boolean isStartRecording = true;
    private boolean isStartPlaying = true;
    private String recordFilePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recordFilePath = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "/audiorecordtest.3gp").getAbsolutePath();

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
            // Permissions are granted
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            // Check if permissions were granted
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
