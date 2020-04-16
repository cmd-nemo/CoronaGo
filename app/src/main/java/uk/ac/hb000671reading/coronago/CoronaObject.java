package uk.ac.hb000671reading.coronago;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class CoronaObject {

    private BitmapFactory bm = new BitmapFactory();
    private int xCoronaPos;
    private int yCoronaPos;
    private final Bitmap coronaObject;



    public CoronaObject(int x, int y) {
        //create new Corona Object
        xCoronaPos = x;
        yCoronaPos = y;

        coronaObject = BitmapFactory.decodeResource(getResources(),R.drawable.covid);
    }


    public int getxCoronaPos() {
        return xCoronaPos;
    }
    public int getyCoronaPos(){
        return yCoronaPos;
    }
    public void moveCorona(int coronaSpeed){
        xCoronaPos -= coronaSpeed;
    }
    public void draw (Canvas canvas){

    }
}
