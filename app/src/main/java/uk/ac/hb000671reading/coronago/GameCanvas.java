package uk.ac.hb000671reading.coronago;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neyma Siddiqui 25/03/20
 */
public class GameCanvas extends View {
    //Canvas
    private int canvasWidth;
    private int canvasHeight;

    //Player
    //private Bitmap Player player;
    private Bitmap player[] = new Bitmap[2];
    private int playerX = 10;
    private int playerY;
    private int playerSpeed;


    //Obstacle
    private int sanitizerX = 10;
    private int sanitizerY;
    private int sanitizerSpeed = 12;

    //obstacle 2
    private int glovesX = 5;
    private int glovesY;
    private int glovesSpeed = 8;

    //obstacle 3
    private int maskX = 2;
    private int maskY;
    private int maskSpeed = 2;

    //Corona
    private List<CoronaObject> coronaObjectList = new ArrayList<>();

    //Background
    private Bitmap backgroundImage;

    //Scorer
    private Paint scorerPaint = new Paint();
    private int scorer;

    private ScoreManager scoreManager;
    private int highScore;

    //Levels
    private Paint levelsPaint = new Paint();

    //Lives
    private Bitmap[] lives = new Bitmap[2];
    private int life_count;

    //Check status
    private boolean touch_flg = false;
    private int gameScene;
    private static final int START_GAME = 0;
    private static final int PLAY_GAME = 1;
    private static final int GAME_OVER = 2;


    //START/GAME OVER
    private Bitmap imageStart;
    private Bitmap imageGameOver;
    private Bitmap buttonStart;
    private Bitmap buttonReturn;
    private int imageButtonX;
    private int imageButtonY;
    private Paint highScoreTitlePaint = new Paint();

    //obstacles
    private final Bitmap sanitizer;
    private final Bitmap gloves;
    private final Bitmap mask;

    //Sound
    SoundsBar soundsBar;
    public GameCanvas(Context context) {
        super(context);
        player[0] = BitmapFactory.decodeResource(getResources(),R.drawable.doct1);
        player[1] = BitmapFactory.decodeResource(getResources(),R.drawable.doct2);

        imageStart = BitmapFactory.decodeResource(getResources(),R.drawable.start);
        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.hosppp);
        imageGameOver = BitmapFactory.decodeResource(getResources(),R.drawable.gameover);


        //Sanitizer
        sanitizer = BitmapFactory.decodeResource(getResources(), R.drawable.san1);
        //gloves
        gloves = BitmapFactory.decodeResource(getResources(), R.drawable.gloves1);
        //mask
        mask = BitmapFactory.decodeResource(getResources(), R.drawable.mask);


        scorerPaint.setColor(Color.BLUE);
        scorerPaint.setTextSize(35);
        scorerPaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorerPaint.setAntiAlias(true);

        levelsPaint.setColor((Color.BLACK));
        levelsPaint.setTextSize(34);
        levelsPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelsPaint.setTextAlign(Paint.Align.CENTER);
        levelsPaint.setAntiAlias(true);

        lives[0] = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
        lives[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_g);

        buttonStart = BitmapFactory.decodeResource(getResources(),R.drawable.start_btn);
        buttonReturn = BitmapFactory.decodeResource(getResources(),R.drawable.return_btn);

        highScoreTitlePaint.setTextSize(36);
        highScoreTitlePaint.setTypeface(Typeface.DEFAULT_BOLD);
        highScoreTitlePaint.setTextAlign(Paint.Align.CENTER);

        //Original Position

        gameScene = START_GAME;
        //sound

        soundsBar = new SoundsBar(context);

        //Score
        scoreManager = new ScoreManager(context);
        highScore = scoreManager.loadHighScore();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = getWidth();
        canvasHeight = getHeight();

        imageButtonX = canvasWidth / 2 - buttonStart.getWidth()/2;
        imageButtonY = canvasHeight/2 + (int)(buttonStart.getHeight()*1.5);

        switch(gameScene){
            case START_GAME:
                canvas.drawBitmap(imageStart,0,0,null);
                drawStartPage(canvas);
                break;
            case PLAY_GAME:
                canvas.drawBitmap(backgroundImage,0,0,null);
                drawPlayPage(canvas);
                break;
            case GAME_OVER:
                canvas.drawBitmap(imageGameOver,0,0,null);
              drawGameOverPage(canvas);
                break;


        }

    }
    public void drawPlayPage(Canvas canvas){

        //Player
        int minPlayerY = player[0].getHeight();
        int maxPlayerY = canvasHeight - player[0].getHeight()*3;

        playerY += playerSpeed;
        if(playerY < minPlayerY) playerY = minPlayerY;
        if(playerY > maxPlayerY) playerY = maxPlayerY;
        playerSpeed +=2;

        if(touch_flg) {
            //Walk
            canvas.drawBitmap(player[1], playerX, playerY, null);
            touch_flg = false;
        }
        else{
            canvas.drawBitmap(player[0],playerX, playerY, null);
        }
        //Sanitizer obstacle
        sanitizerX -= sanitizerSpeed;
        if(collisionCheck(sanitizerX, sanitizerY)){
            scorer +=10;
            sanitizerX =- 100;
            soundsBar.playScorePointSound();
        }
        if(sanitizerX<0){
            sanitizerX = canvasWidth + 20;
            sanitizerY = (int)Math.floor(Math.random()*(maxPlayerY-minPlayerY))+minPlayerY;
        }
        canvas.drawBitmap(sanitizer, sanitizerX,sanitizerY,null);

        //gloves obstacle
        glovesX -= glovesSpeed;
        if(collisionCheck(glovesX, glovesY)){
            scorer +=20;
            glovesX =- 100;
            soundsBar.playScorePointSound();
        }
        if(glovesX<0){
            glovesX = canvasWidth + 20;
            glovesY = (int)Math.floor(Math.random()*(maxPlayerY-minPlayerY))+minPlayerY;
        }
        canvas.drawBitmap(gloves, glovesX,glovesY,null);

        //masks obstacle
        maskX -= maskSpeed;
        if(collisionCheck(maskX, maskY)){
            scorer +=15;
            maskX =- 100;
            soundsBar.playScorePointSound();
        }
        if(maskX<0){
            maskX = canvasWidth + 20;
            maskY = (int)Math.floor(Math.random()*(maxPlayerY-minPlayerY))+minPlayerY;
        }
        canvas.drawBitmap(mask, maskX,maskY,null);

        //Levels Up
        int level = (int)Math.floor(scorer/50)+1;

        //Add Corona every level
        if(coronaObjectList.size() < level){
            for(int i = coronaObjectList.size(); i< level; i++) {
                int x = canvasWidth + 200;
                int y = (int) Math.floor(Math.random() * (maxPlayerY - minPlayerY)) + minPlayerY;
              //  CoronaObject obj = CoronaObject(getContext(),x,y);
              CoronaObject coronaObject = new CoronaObject(null,30,0);
                coronaObjectList.add(coronaObject);
            }
        }
for(int i= 0; i < coronaObjectList.size(); i++){
    CoronaObject coronaObject = coronaObjectList.get(i);
    coronaObject.moveCorona(20);

    if (collisionCheck(coronaObject.getxCoronaPos(),coronaObject.getyCoronaPos())){
        life_count --;
        soundsBar.playHitCoronaSound();

            if(life_count == 0){
                //Print Game Over
                if(scorer > highScore){
                    //save score
                   scoreManager.saveScore(scorer);
                    //update highscore
                    highScore = scorer;
                }
             gameScene = GAME_OVER;
             return;

            }
            //Remove from List
        coronaObjectList.remove(i);
            i--;
            continue;
    }
    if(coronaObject.getxCoronaPos() < 0){
        //remove from list
        coronaObjectList.remove(i);
        i--;
        continue;
    }
    coronaObject.draw(canvas,20,0);
}

        //Scores
        canvas.drawText("Score: 0" + scorer, 35, 60, scorerPaint);

        //Levels
        canvas.drawText("Level." + level, canvasWidth/2,60,levelsPaint);

        //Lives
        for(int i = 0; i<3; i++){
            int x = (int) (560 + lives[0].getWidth()*1.5*i);
            int y = 30;
            if(i < life_count){
                canvas.drawBitmap(lives[0],x,y,null);
            } else {
                canvas.drawBitmap(lives[1],x,y,null);
            }
        }

    }
    public void drawStartPage(Canvas canvas){
        playerY = canvasHeight/2;
        maskX = -100;
        glovesX = -100;
       // coronaX = -100;
        sanitizerX = -100;
        scorer = 0;
        life_count = 3;

        canvas.drawText("High Score :" + highScore, canvasWidth/2,canvasHeight/2,highScoreTitlePaint);
        canvas.drawBitmap(buttonStart, imageButtonX,imageButtonY,null);

    }
    public void drawGameOverPage(Canvas canvas){

        canvas.drawText("Score : " + highScore, canvasWidth/2,canvasHeight/2,highScoreTitlePaint);

        canvas.drawBitmap(buttonReturn,imageButtonX,imageButtonY,null);
    }


    public boolean collisionCheck(int x, int y){
        if(playerX < x && x < (playerX + player[0].getWidth())&& playerY < y && y <(playerY + player[0].getHeight())){
            return true;
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            switch(gameScene){
                case START_GAME:
                    //check start button
                    if(pressStart(buttonStart, (int)event.getX(),(int)event.getY())){
                        gameScene = PLAY_GAME;

                    }
                    break;
                case PLAY_GAME:
                    touch_flg = true;
                    playerSpeed = -20;
                    break;
                case GAME_OVER:
                    if(pressStart(buttonReturn, (int)event.getX(), (int)event.getY())){
                        gameScene = START_GAME;
                    }

            }

        }
        return true;
    }

    public boolean pressStart(Bitmap button, int x, int y){
        if(imageButtonX < x && x < imageButtonX +button.getWidth()&&
        imageButtonY < y && y < imageButtonY + button.getHeight()){
            return true;
        }
        return false;
    }
    public int getGameScene(){
        return gameScene;
    }
}
