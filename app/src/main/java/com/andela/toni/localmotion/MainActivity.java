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

import com.andela.toni.localmotion.services.LocationTrackingService;

public class MainActivity extends Activity {

    private Button btnTrack;
    private Button btnHistory;
    private FloatingActionButton fabPreference;
    private boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        started = false;
        btnTrack = (Button) findViewById(R.id.btnTrack);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        fabPreference = (FloatingActionButton) findViewById(R.id.fabPreference);

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started) {
                    stopService();
                    btnTrack.setText("START SERVICE");
                } else {
                    startService();
                    btnTrack.setText("STOP SERVICE");
                }

                started = !started;
            }
        });

        btnTrack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showButtonVisualFeedback(btnTrack, event);
                return false;
            }
        });

        btnHistory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showButtonVisualFeedback(btnHistory, event);
                return false;
            }
        });

        fabPreference.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
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
