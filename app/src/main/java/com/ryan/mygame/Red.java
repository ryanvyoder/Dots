package com.ryan.mygame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import static com.ryan.mygame.MainThread.canvas;

/**
 * Created by Ryan on 2/15/2017.
 */

public class Red extends GameObject {
    private static int r = 50;

    public Red (int x, int y){
        width = r * 2;
        height = r * 2;
        Random rand = new Random();
        this.x = x;
        this.y = y;
        dy = rand.nextInt(10) - 5;
        dx = rand.nextInt(10) - 5;
    }

    public static int getR(){
        return r;
    }

    public Circle getCircle(){
        return new Circle(r, x-r, y+r);
    }

    public void update(){
        y += dy;
        x += dx;

        int rightWall = GamePanel.WIDTH;
        int botWall = GamePanel.HEIGHT;
        int leftWall = 0;
        int topWall = 0;
        //if red hits edge, reflect
        if(x >= rightWall || (x-(r*2)) <= leftWall){
            dx = dx * -1;
        }
        if(y + (r*2) >= botWall || (y) <= topWall){
            dy = dy * -1;
        }
    }

    public void draw(Canvas canvass){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x-r, y+r, r, paint);
    }

}
