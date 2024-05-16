package com.mirea.makhankodv.timeservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.mirea.makhankodv.timeservice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final String host = "time.nist.gov";
    private final int port = 13;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTimeTask timeTask = new GetTimeTask();
                timeTask.execute();
            }
        });
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String timeResult = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine();
                timeResult = reader.readLine();
                Log.d("Socket", timeResult);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return timeResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            binding.textView.setText(parseDateTime(result));
        }
    }

    private String parseDateTime(String dateTime) {
        if (dateTime != null && !dateTime.isEmpty()) {
            try {
                String[] strings = dateTime.split(" ");
                String[] dateParts = strings[1].split("-");
                String formattedDate = dateParts[2] + ":" + dateParts[1] + ":" + "20" + dateParts[0];
                String time = strings[2];

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                Date date = sdf.parse(time);
                Calendar calendar = Calendar.getInstance();
                if (date != null) {
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR_OF_DAY, 3);
                }
                String newTime = sdf.format(calendar.getTime());

                return "Дата: " + formattedDate + "\nВремя по МСК: " + newTime;
            } catch (Exception e) {
                Log.e("DateTimeParse", "Error parsing date time: " + dateTime, e);
                return "Ошибка формата даты/времени";
            }
        }
        return "Нет данных";
    }
}
