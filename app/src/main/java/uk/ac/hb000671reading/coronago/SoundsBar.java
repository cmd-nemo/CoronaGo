package uk.ac.hb000671reading.coronago;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


public class SoundsBar {

    private static SoundPool soundPool;
    private static int scorePointSound;
    private static int hitCoronaSound;
    private final int SOUND_POOL_MAX = 2;

    public SoundsBar(Context context) {

        soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);

        // Load sounds.
        scorePointSound = soundPool.load(context, R.raw.hit, 1);
        hitCoronaSound = soundPool.load(context, R.raw.over, 1);
    }

    public static void playScorePointSound () {
            soundPool.play(scorePointSound, 1.0f, 1.0f, 1, 0, 1.0f);
        }


            public static void playHitCoronaSound () {
            soundPool.play(hitCoronaSound, 1.0f, 1.0f, 1, 0, 1.0f);
        }
}