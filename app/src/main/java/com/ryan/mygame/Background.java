package com.ryan.mygame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Ryan on 5/11/2016.
 */
public class Background extends GameObject{

    private Bitmap image;
    private int x, y, dy;

    public Background(Bitmap res){
        image = res;
        //only needed if the background moves
        dy = GamePanel.MOVESPEED;

    }

    public void update(){
        //background SCROLLS VERTICALLY
        //y += dy;
        //if (y < -GamePanel.HEIGHT){
        //    y = 0;
        //}
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(image, x, y, null);

        //Setting up a second image that will be after this image to make the scrolling look continuous
        if(y < 0){
            canvas.drawBitmap(image, x, y + GamePanel.HEIGHT, null);
        }
    }
}
