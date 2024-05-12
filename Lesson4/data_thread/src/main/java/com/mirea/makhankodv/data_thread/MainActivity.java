package com.mirea.makhankodv.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import com.mirea.makhankodv.data_thread.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn3");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.postDelayed(runn3, 2000);
                    binding.tvInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        String description = "    runOnUiThread(Runnable): Этот метод используется для выполнения кода в основном потоке пользовательского интерфейса (UI). Он добавляет Runnable в очередь сообщений основного потока и выполнит его, как только основной поток будет доступен.\n" +
                "\n" +
                "    post(Runnable): Метод post() используется для запуска Runnable на виде, который будет выполнен в потоке UI. Он помещает сообщение в очередь, и выполнение начнется в следующем цикле обработки сообщений UI потока. Этот метод можно вызывать из любого места кода.\n" +
                "\n" +
                "    postDelayed(Runnable, long): Этот метод аналогичен post(), но добавляет возможность запланировать выполнение Runnable с задержкой в указанное количество миллисекунд. Runnable будет помещен в очередь и выполнен после указанной задержки.";

        binding.textView.setText(description);

    }
}