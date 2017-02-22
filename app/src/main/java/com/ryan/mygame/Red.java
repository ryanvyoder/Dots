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
    private int frame = 0;
    private boolean colorFlash = false;
    private int flashRate = 15;

    public Red (int x, int y){
        width = r * 2;
        height = r * 2;
        Random rand = new Random();
        this.x = x;
        this.y = y;
        if(this.x <= this.width){
            this.x = this.width + 1;
        }
        if(this.x >= GamePanel.WIDTH){
            this.x = GamePanel.WIDTH - 1;
        }
        if(this.y >= (GamePanel.HEIGHT - this.height)){
            this.y = GamePanel.HEIGHT - this.height - 1;
        }
        if( this.y <= 0){this.y = 1;}
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

    public Paint chooseColor(Paint p){
        if(frame % flashRate == 0){
            if(colorFlash) {
                p.setColor(Color.BLACK);
            }
            else{
                p.setColor(Color.DKGRAY);
            }
            colorFlash = !colorFlash;
        }
        else{
            if(!colorFlash) {
                p.setColor(Color.BLACK);
            }
            else{
                p.setColor(Color.DKGRAY);
            }
        }
        return p;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        //paint.setColor(Color.RED);
        paint = chooseColor(paint);
        paint.setStyle(Paint.Style.FILL);


        Paint paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(5);

        canvas.drawCircle(x-r, y+r, r, paint);
        canvas.drawCircle(x-r,y+r,r,paint2);
        frame++;
    }

}
