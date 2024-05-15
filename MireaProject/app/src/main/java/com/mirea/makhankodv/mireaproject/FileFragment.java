package com.mirea.makhankodv.mireaproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.makhankodv.mireaproject.databinding.FragmentFileBinding;
import com.mirea.makhankodv.mireaproject.databinding.FragmentFileDialogBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileFragment extends Fragment {

    private static final int REQUEST_CODE = 1;

    private FragmentFileBinding binding;
    private String fileContent;
    private Uri fileUri;
    private String fileName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnUploadFile.setOnClickListener(v -> openFilePicker());
        binding.fab.setOnClickListener(v -> showProcessingDialog());

        return view;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            fileUri = data.getData();
            readTextFromUri(fileUri);
        }
    }

    private void readTextFromUri(Uri uri) {
        try (InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            fileContent = stringBuilder.toString();
            fileName = getFileName(uri);
            binding.tvFileContent.setText(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = requireActivity().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void showProcessingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        FragmentFileDialogBinding dialogBinding = FragmentFileDialogBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());

        AlertDialog dialog = builder.create();

        dialogBinding.btnProcess.setOnClickListener(v -> {
            String shiftStr = dialogBinding.etShift.getText().toString();
            if (TextUtils.isEmpty(fileContent) || TextUtils.isEmpty(shiftStr)) {
                Toast.makeText(getActivity(), "Пожалуйста введите размер сдвига и выберите тип операции", Toast.LENGTH_SHORT).show();
                return;
            }

            int shift = Integer.parseInt(shiftStr);
            int selectedOption = dialogBinding.rgOptions.getCheckedRadioButtonId();
            boolean isEncrypt = selectedOption == R.id.rbEncrypt;

            String processedContent = caesarCipher(fileContent, shift, isEncrypt);
            binding.tvProcessedFileContent.setText(processedContent);

            if (isExternalStorageWritable()) {
                saveProcessedFile(processedContent, isEncrypt);
            } else {
                Toast.makeText(getActivity(), "Внешнее хранилище недоступно для записи", Toast.LENGTH_LONG).show();
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private String caesarCipher(String input, int shift, boolean isEncrypt) {
        if (!isEncrypt) {
            shift = -shift;
        }

        StringBuilder result = new StringBuilder();
        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                character = (char) ((character - base + shift + 26) % 26 + base);
            }
            result.append(character);
        }
        return result.toString();
    }

    private void saveProcessedFile(String content, boolean isEncrypt) {
        String newFileName = fileName.replace(".txt", isEncrypt ? "_encrypted.txt" : "_decrypted.txt");

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File newFile = new File(path, newFileName);

        try {
            if (!path.exists()) {
                path.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(newFile.getAbsoluteFile());
            OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
            output.write(content);
            output.close();
            Toast.makeText(getActivity(), "Файл сохранён в " + newFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Не удалось сохранить файл", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
