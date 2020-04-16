package uk.ac.hb000671reading.coronago;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import static uk.ac.hb000671reading.coronago.R.*;

public class SoundsBar {
    private static SoundPool soundpool;
    private static int scorePointSound;
    private static int hitCoronaSound;
    private final int SOUND_POOL_MAX = 3;
    private static MediaPlayer mediaPlayer;


    public  SoundsBar(Context context){
        soundpool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        scorePointSound = soundpool.load(context, R.raw.hit,1);
        hitCoronaSound = soundpool.load(context, R.raw.over,1);

        //BackgroundSound
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.7f,0.7f);

    }
    public static void playScorePointSound(){
        //play(soundID, leftVolume, rightVolume, priority, loop, rate)
        soundpool.play(scorePointSound, 1.0f,1.0f,1,0,1.0f);
    }
    public static void playHitCoronaSound() {
        soundpool.play(hitCoronaSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public static void playBackgroundMusic() {
        if(mediaPlayer != null) mediaPlayer.start();
    }

    public static void pauseBackgroundMusic(){
        if(mediaPlayer != null) mediaPlayer.pause();
    }

    public static void seektoTapforSound(){
        if(mediaPlayer != null) mediaPlayer.seekTo(0);
    }
}
