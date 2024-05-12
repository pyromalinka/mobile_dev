package com.mirea.makhankodv.lesson4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.makhankodv.lesson4.databinding.ActivityMusicBinding;

public class MusicActivity extends AppCompatActivity {
    private ActivityMusicBinding binding;

    private final int[] albumImages = {R.drawable.ic_album1, R.drawable.ic_album2, R.drawable.ic_album3};
    private final String[] artists = {"System Of A Down", "Arctic Monkeys", "Linkin Park"};
    private final String[] songs = {"Aerials", "Do I Wanna know?", "Runaway"};

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentIndex = getIntent().getIntExtra("currentIndex", 0);

        binding.imageView2.setImageResource(albumImages[currentIndex]);
        binding.textView.setText(artists[currentIndex]);
        binding.textView2.setText(songs[currentIndex]);

        binding.playButton.setOnClickListener(v -> Log.d("MusicActivity", "Play button clicked"));
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex - 1 + albumImages.length) % albumImages.length;
                updateUI();
            }
        });
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % albumImages.length;
                updateUI();
            }
        });
    }

    private void updateUI() {
        binding.imageView2.setImageResource(albumImages[currentIndex]);
        binding.textView.setText(artists[currentIndex]);
        binding.textView2.setText(songs[currentIndex]);
    }
}
