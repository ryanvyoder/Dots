package com.ryan.mygame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by Ryan on 5/11/2016.
 */
public class Background extends GameObject{

    private Bitmap image;
    private int x, y, dy;
    private int red, blue, green;
    private Color colour = new Color();
    private int colorInt = 0;
    private int rateOfChange = 15;

    public Background(Bitmap res){
        image = res;
        red = 255;
        //only needed if the background moves
        dy = GamePanel.MOVESPEED;

    }

    private void changeColor(){
        if(colorInt == 0){
            if(red < 255){
                red+=rateOfChange;
                green-=rateOfChange;
            }
            else{
                colorInt = 1;
            }
        }
        else if(colorInt == 1){
            if(blue < 255){
                blue+=rateOfChange;
                red-=rateOfChange;
            }
            else{
                colorInt = 2;
            }
        }
        else if(colorInt == 2){
            if(green < 255){
                green+=rateOfChange;
                blue-=rateOfChange;
            }
            else{
                colorInt = 0;
            }
        }
    }

    public void update(){
        changeColor();
        //background SCROLLS VERTICALLY
        //y += dy;
        //if (y < -GamePanel.HEIGHT){
        //    y = 0;
        //}
    }

    public void draw(Canvas canvas){

        canvas.drawColor(Color.BLACK);

        //canvas.drawBitmap(image, x, y, null);

        //Setting up a second image that will be after this image to make the scrolling look continuous
        /*if(y < 0){
            canvas.drawBitmap(image, x, y + GamePanel.HEIGHT, null);
        }*/
    }
}
