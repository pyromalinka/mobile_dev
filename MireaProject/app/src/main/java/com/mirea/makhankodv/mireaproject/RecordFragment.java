package com.mirea.makhankodv.mireaproject;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class RecordFragment extends Fragment {
    private static final int REQUEST_CODE_PERMISSION = 200;
    private final String TAG = RecordFragment.class.getSimpleName();
    private MediaRecorder recorder = null;
    private MediaPlayer mediaPlayer = null;
    private TextView soundLevelTextView;
    private Button toggleRecordingButton;
    private Button play05xButton;
    private Button play1xButton;
    private Button play2xButton;
    private boolean isRecording = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        toggleRecordingButton = view.findViewById(R.id.buttonToggleRecording);
        play05xButton = view.findViewById(R.id.buttonPlay05x);
        play1xButton = view.findViewById(R.id.buttonPlay1x);
        play2xButton = view.findViewById(R.id.buttonPlay2x);

        toggleRecordingButton.setOnClickListener(v -> {
            if (isRecording) {
                stopRecording();
            } else {
                if (checkPermissions()) {
                    startRecording();
                } else {
                    requestPermissions(new String[]{
                            android.Manifest.permission.RECORD_AUDIO,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, REQUEST_CODE_PERMISSION);
                }
            }
            isRecording = !isRecording;
            updateButton();
        });

        play05xButton.setOnClickListener(v -> playRecording(0.5f));
        play1xButton.setOnClickListener(v -> playRecording(1.0f));
        play2xButton.setOnClickListener(v -> playRecording(2.0f));

        return view;
    }

    private boolean checkPermissions() {
        int audioPermissionStatus = ContextCompat.checkSelfPermission(requireActivity(),
                android.Manifest.permission.RECORD_AUDIO);
        return audioPermissionStatus == PackageManager.PERMISSION_GRANTED;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "/audiorecordtest.3gp").getAbsolutePath());

        try {
            recorder.prepare();
            recorder.start();
            //updateDecibelLevel(); // No longer needed
        } catch (IOException e) {
            Log.e(TAG, "startRecording: prepare() failed", e);
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    private void playRecording(float speed) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                    "/audiorecordtest.3gp").getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
            mediaPlayer.setOnCompletionListener(mp -> {
                mediaPlayer.release();
                mediaPlayer = null;
            });
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "playRecording: prepare() failed", e);
        }
    }

    private void updateButton() {
        if (isRecording) {
            toggleRecordingButton.setText("Остановить запись");
            play05xButton.setEnabled(false);
            play1xButton.setEnabled(false);
            play2xButton.setEnabled(false);
        } else {
            toggleRecordingButton.setText("Начать запись");
            play05xButton.setEnabled(true);
            play1xButton.setEnabled(true);
            play2xButton.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecording();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRecording();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
