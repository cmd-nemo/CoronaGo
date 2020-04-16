package uk.ac.hb000671reading.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private GameCanvas gameCanvas;
    private Handler handler = new Handler();
    private final static long TIMER_INTERVAL = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

        gameCanvas = new GameCanvas(this);
        setContentView(gameCanvas);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameCanvas.invalidate();
                    }
                });
            }
        }, 0, TIMER_INTERVAL);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gameCanvas.getGameScene()==1)gameCanvas.soundsBar.playBackgroundMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameCanvas.soundsBar.pauseBackgroundMusic();
    }
}
