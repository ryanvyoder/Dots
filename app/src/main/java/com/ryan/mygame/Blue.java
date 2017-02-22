package com.ryan.mygame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import static android.R.attr.direction;

/**
 * Created by Ryan on 2/12/2017.
 */

public class Blue extends GameObject {
    private int r;
    private Color colour = new Color();
    private float hue;
    private int dir = 0;
    private int rateOfChange = 2;

    public Blue(int[] rRange, int[] sRange){
        Random rand = new Random();
        hue = rand.nextInt(360);
        int max = rRange[1] - rRange[0];
        r = rand.nextInt(max);
        r += rRange[0];
        width = r * 2;
        height = r * 2;
        x = rand.nextInt(GamePanel.WIDTH);
        if(x <= this.width){
            x = this.width + 1;
        }
        if(x >= GamePanel.WIDTH ){
            x = GamePanel.WIDTH  - 1;
        }
        y = rand.nextInt(GamePanel.HEIGHT);
        if(y >= (GamePanel.HEIGHT - this.height)){
            y = GamePanel.HEIGHT - this.height - 1;
        }
        if( y == 0){y++;}
        int direction = rand.nextInt(2);
        dy = rand.nextInt(sRange[1]- sRange[0]) + sRange[0];
        if(direction == 0){
            dy *= -1;
        }
        dx = rand.nextInt(sRange[1]- sRange[0]) + sRange[0];
        direction = rand.nextInt(2);
        if(direction == 0){
            dx *= -1;
        }
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

    private void changeColor(){
        if(dir == 0){
            hue+= rateOfChange;
            if(hue >= 359){
                hue = 359;
                dir = 1;
            }
        }
        else{
            hue-=rateOfChange;
            if(hue <= 0){
                hue = 0;
                dir = 0;
            }
        }

    }

    public void update(){

        changeColor();

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
        //paint.setColor(Color.parseColor("#42cef4"));
        paint.setColor(colour.HSVToColor(new float[]{hue, 1, 1}));
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x - r, y + r, r, paint);
    }

}
