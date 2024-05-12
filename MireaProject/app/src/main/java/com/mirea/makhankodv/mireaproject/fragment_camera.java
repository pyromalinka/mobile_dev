package com.mirea.makhankodv.mireaproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class fragment_camera extends Fragment {
    private Uri imageUri;
    private ImageView imageViewCollage;
    private final ArrayList<Uri> imageUris = new ArrayList<>();
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                imageUris.add(imageUri);
                displayCollage();
            }
        });
        if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        imageViewCollage = view.findViewById(R.id.imageViewCollage);
        Button buttonTakePhoto = view.findViewById(R.id.buttonTakePhoto);

        buttonTakePhoto.setOnClickListener(v -> takePhoto());
        return view;
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
        }

        if (photoFile != null) {
            String authorities = requireContext().getPackageName() + ".fileprovider";
            imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraActivityResultLauncher.launch(intent);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                "JPEG_" + timeStamp + "_",
                ".jpg",
                storageDir
        );
    }

    private void displayCollage() {
        if (imageUris.size() == 0) return;

        try {
            int width = 0, height = 0;

            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (Uri uri : imageUris) {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                bitmaps.add(bitmap);
                width = Math.max(width, bitmap.getWidth());
                height += bitmap.getHeight();
            }

            Bitmap collageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(collageBitmap);
            int currentY = 0;

            for (Bitmap bitmap : bitmaps) {
                canvas.drawBitmap(bitmap, 0, currentY, null);
                currentY += bitmap.getHeight();
                bitmap.recycle();
            }

            imageViewCollage.setImageBitmap(collageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}