package com.mirea.makhankodv.scrollviewapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.makhankodv.scrollviewapp.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout wrapper = findViewById(R.id.wrapper);
        int firstTerm = 1;
        int ratio = 2;
        int terms = 32;

        for (int i = 0; i < terms; i++) {
            View view = getLayoutInflater().inflate(R.layout.item, null, false);
            TextView text = view.findViewById(R.id.textView);
            int term = firstTerm * (int) Math.pow(ratio, i);
            text.setText(String.format("Элемент прогрессии %d: %d", i + 1, term));
            wrapper.addView(view);
        }
    }
} 