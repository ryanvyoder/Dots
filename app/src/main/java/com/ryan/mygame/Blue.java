package com.ryan.mygame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Ryan on 2/12/2017.
 */

public class Blue extends GameObject {
    private int r;

    public Blue(int[] rRange){
        int max = rRange[1] - rRange[0];
        Random rand = new Random();
        r = rand.nextInt(max);
        r += rRange[0];
        width = r * 2;
        height = r * 2;
        x = rand.nextInt(GamePanel.WIDTH);
        if(x < this.width){
            x = this.width;
        }
        y = rand.nextInt(GamePanel.HEIGHT);
        if(y > (GamePanel.HEIGHT - this.height)){
            y = GamePanel.HEIGHT - this.height ;
        }
        if( y == 0){y++;}
        dy = rand.nextInt(10);
        dx = rand.nextInt(10);

    }

    public Circle getCircle(){
        return new Circle(r, x-r, y+r);
    }

    public void setDx(int speed){dx = speed; }

    public void setDy(int speed){dy = speed; }

    public boolean isTouched(int x, int y){
        boolean touched;
        Circle myCircle = this.getCircle();

        touched = myCircle.contains(x, y);

        return touched;
    }

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
        if(y + (r*2) >= botWall || (y) <= topWall){
            dy = dy * -1;
        }
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#42cef4"));
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x - r, y + r, r, paint);
    }

}
