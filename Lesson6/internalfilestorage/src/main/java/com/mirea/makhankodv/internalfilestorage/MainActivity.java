package com.mirea.makhankodv.internalfilestorage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.makhankodv.internalfilestorage.databinding.ActivityMainBinding;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String FILE_NAME = "save_text_data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v -> saveData());
        displaySavedData();
    }

    private void saveData() {
        String text = binding.editTextInput.getText().toString();
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            binding.textViewDisplay.setText("Data saved successfully!");
        } catch (IOException e) {
            binding.textViewDisplay.setText("Error saving data!");
            e.printStackTrace();
        }
    }

    private void displaySavedData() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String text = new String(bytes);
            binding.textViewDisplay.setText(text);
        } catch (IOException e) {
            binding.textViewDisplay.setText("No data saved!");
            e.printStackTrace();
        }
    }
}
