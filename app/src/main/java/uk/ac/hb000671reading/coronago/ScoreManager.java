package uk.ac.hb000671reading.coronago;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreManager {

    private static Context context;

    public ScoreManager(Context context){
        ScoreManager.context = context;
    }

    public void saveScore(int score){
        SharedPreferences settings = context.getSharedPreferences("GAME_DATA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("HIGH_SCORE", score);
        editor.commit();
    }
    public int loadHighScore(){
        SharedPreferences settings = context.getSharedPreferences("GAME_DATA",Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE",0);
        return highScore;
    }
}
