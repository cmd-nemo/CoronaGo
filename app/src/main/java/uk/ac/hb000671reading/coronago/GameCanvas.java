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

    //Player
    private Bitmap plyPlayer;

    //Obstacle
    private int sanitizerX = 10;
    private int sanitizerY;
    private int sanitizerSpeed = 15;


    //Background
    private Bitmap backgroundImage;

    //Scorer
    private Paint scorerPaint = new Paint();


    //Levels
    private Paint levelsPaint = new Paint();

    //Lives
    private Bitmap[] lives = new Bitmap[2];

    //Check status
    private boolean touch_flg = false;

    public GameCanvas(Context context) {
        super(context);
       player[0] = BitmapFactory.decodeResource(getResources(),R.drawable.dc1);
       player[1] = BitmapFactory.decodeResource(getResources(),R.drawable.dc2);

       backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.hosppp);

        //Sanitizer
        Bitmap sanitizer = BitmapFactory.decodeResource(getResources(), R.drawable.corona);

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

       //Original Position
        playerY = 50;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = getWidth();
        canvasHeight = getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);

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
        if(sanitizerX<0){
            sanitizerX = canvasWidth + 20;
            sanitizerY = (int)Math.floor(Math.random()*(maxPlayerY-minPlayerY))+minPlayerY;
        }
        //canvas.drawBitmap(sanitizerX,sanitizerY,10,??????); HELP


        canvas.drawText("Score: 0", 35, 60, scorerPaint);

        canvas.drawText("Level.1", canvasWidth/2,60,levelsPaint);

        canvas.drawBitmap(lives[0],560,30,null);
        canvas.drawBitmap(lives[0],620,30,null);
        canvas.drawBitmap(lives[1],680,30,null);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch_flg = true;
            playerSpeed = -20;
        }
        return true;
    }
}
