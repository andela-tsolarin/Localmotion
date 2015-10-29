package com.andela.toni.localmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.andela.toni.localmotion.util.ServiceMonitor;
import com.andela.toni.localmotion.services.LocationTrackingService;

public class MainActivity extends Activity {

    private Button btnTrack;
    private Button btnHistory;
    private boolean trackingStarted;
    private ServiceMonitor serviceMonitor;
    private SeekBar seekBar;
    private TextView tvMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceMonitor = new ServiceMonitor();

        trackingStarted = serviceMonitor.serviceRunning(LocationTrackingService.class.getName(), getApplicationContext());
        btnTrack = (Button) findViewById(R.id.btnTrack);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tvMinutes = (TextView) findViewById(R.id.tvMinutes);

        btnTrack.setText((trackingStarted) ? "STOP TRACKING" : "START TRACKING");
        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trackingStarted) {
                    stopService();
                    btnTrack.setText("START TRACKING");
                } else {
                    startService();
                    btnTrack.setText("STOP TRACKING");
                }

                trackingStarted = !trackingStarted;
            }
        });

        btnTrack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showButtonVisualFeedback(btnTrack, event);
                return false;
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topCurrenciesIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(topCurrenciesIntent);
            }
        });
        btnHistory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showButtonVisualFeedback(btnHistory, event);
                return false;
            }
        });

        final int step = 1;
        int max = 60;
        final int min = 1;
        seekBar.setMax((max - min) / step);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
                tvMinutes.setText(min + (progress * step) + " Minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvMinutes.setText(min + (progressValue * step) + " Minutes");
            }
        });

        tvMinutes.setText(seekBar.getProgress()+" Minutes");
    }

    private void showButtonVisualFeedback(Button btn, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                btn.setBackgroundResource(R.drawable.secondary_button);
                break;
            case MotionEvent.ACTION_UP:
                btn.setBackgroundResource(R.drawable.primary_button);
                break;
            default:
                btn.setBackgroundResource(R.drawable.secondary_button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // No action bar item
        return super.onOptionsItemSelected(item);
    }

    private void startService() {
        startService(new Intent(getBaseContext(), LocationTrackingService.class));
    }

    private void stopService() {
        stopService(new Intent(getBaseContext(), LocationTrackingService.class));
    }
}
