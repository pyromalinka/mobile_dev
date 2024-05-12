package com.mirea.makhankodv.mireaproject;

import android.content.pm.PackageManager;
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
public class fragment_record extends Fragment {
    private static final int REQUEST_CODE_PERMISSION = 200;
    private final String TAG = fragment_record.class.getSimpleName();
    private MediaRecorder recorder = null;
    private TextView soundLevelTextView;
    private Button toggleRecordingButton;
    private boolean isRecording = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        soundLevelTextView = view.findViewById(R.id.textViewSoundLevel);
        toggleRecordingButton = view.findViewById(R.id.buttonToggleRecording);

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
            updateDecibelLevel();
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

    private void updateDecibelLevel() {
        if (recorder != null) {
            int amplitude = recorder.getMaxAmplitude();
            if (amplitude != 0) {
                double db = 20 * Math.log10((double) Math.abs(amplitude));
                soundLevelTextView.setText(String.format("Уровень шума: %.2f dB", db));
            }

            // Update the decibel level every second
            getView().postDelayed(this::updateDecibelLevel, 1000);
        }
    }

    private void updateButton() {
        if (isRecording) {
            toggleRecordingButton.setText("Остановить измерение");
        } else {
            toggleRecordingButton.setText("Начать измерение");
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
    }
}
