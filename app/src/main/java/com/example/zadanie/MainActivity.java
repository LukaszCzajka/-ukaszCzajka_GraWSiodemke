package com.example.zadanie;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView licznik, komunikat, trafienia;
    Button start, stop;

    Handler handler = new Handler();

    int cyfra = 0;
    int wynik = 0;

    boolean dziala = false;

    Runnable zmianaCyfry = new Runnable() {
        @Override
        public void run() {
            if (dziala) {
                cyfra++;

                if (cyfra > 9) {
                    cyfra = 0;
                }

                licznik.setText(String.valueOf(cyfra));

                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        licznik = findViewById(R.id.textView2);
        komunikat = findViewById(R.id.textView3);
        trafienia = findViewById(R.id.tvHits);

        start = findViewById(R.id.button);
        stop = findViewById(R.id.button2);

        stop.setEnabled(false);

        start.setOnClickListener(v -> {
            cyfra = 0;
            licznik.setText("0");
            komunikat.setText("");

            dziala = true;

            start.setEnabled(false);
            stop.setEnabled(true);

            handler.removeCallbacks(zmianaCyfry);
            handler.postDelayed(zmianaCyfry, 500);
        });

        stop.setOnClickListener(v -> {
            if (!dziala) {
                return;
            }

            dziala = false;
            handler.removeCallbacks(zmianaCyfry);

            start.setEnabled(true);
            stop.setEnabled(false);

            if (cyfra == 7) {
                wynik++;
                komunikat.setText("Trafienie!");
            } else {
                komunikat.setText("Pudło! Zatrzymano na " + cyfra);
            }

            trafienia.setText("Trafienia: " + wynik);
        });
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(zmianaCyfry);
        super.onDestroy();
    }
}