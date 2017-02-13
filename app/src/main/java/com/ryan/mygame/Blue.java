package com.ryan.mygame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Ryan on 2/12/2017.
 */

public class Blue extends GameObject {
    private int r;

    public Blue(){
        Random rand = new Random();
        x = rand.nextInt(GamePanel.WIDTH);
        y = rand.nextInt(GamePanel.HEIGHT);
        dy = rand.nextInt(10);
        dx = rand.nextInt(10);
        r = 50;
        width = r * 2;
        height = r * 2;
    }

    public void setDx(int speed){dx = speed; }

    public void setDy(int speed){dy = speed; }

    public void update(){

        y += dy;
        x += dx;

        int rightWall = GamePanel.WIDTH;
        int botWall = GamePanel.HEIGHT;
        int leftWall = 0;
        int topWall = 0;
        //if blue hits edge, reflect
        if(x >= rightWall || (x-(r*2)) <= leftWall){
            dx = dx * -1;
        }
        if(y >= botWall || (y-(r*2)) <= topWall){
            dy = dy * -1;
        }
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x - r, y - r, r, paint);
    }

}
