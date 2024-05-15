package com.mirea.makhankodv.notebook;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.makhankodv.notebook.databinding.ActivityMainBinding;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.saveButton.setOnClickListener(v -> writeFileToExternalStorage());
        binding.loadButton.setOnClickListener(v -> readFileFromExternalStorage());
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public void writeFileToExternalStorage() {
        if (isExternalStorageWritable()) {
            String fileName = binding.fileNameInput.getText().toString();
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }
            String quote = binding.quoteInput.getText().toString();

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
                fileOutputStream.write(quote.getBytes());
                fileOutputStream.close();
                showToast("Data saved successfully");
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error writing " + file, e);
                showToast("Error saving data");
            }
        } else {
            showToast("External storage not writable");
        }
    }

    public void readFileFromExternalStorage() {
        if (isExternalStorageReadable()) {
            String fileName = binding.fileNameInput.getText().toString();
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName);

            try {
                FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                reader.close();
                binding.quoteInput.setText(stringBuilder.toString());
                showToast("Data loaded successfully");
            } catch (IOException e) {
                Log.w("ExternalStorage", "Read from file " + file + " failed", e);
                showToast("Error loading data");
            }
        } else {
            showToast("External storage not readable");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
