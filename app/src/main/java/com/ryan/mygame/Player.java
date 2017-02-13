package com.ryan.mygame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Ryan on 5/11/2016.
 */
public class Player extends GameObject{
    private int r;
    private boolean up;
    private boolean playing;
    private int score;
    private int followSpeed = 2; //Lower is faster

    public Player() {
        x = 100;
        //y = GamePanel.HEIGHT / 2; //vertically centered on screen at start
        y = 100;
        dy = 0;
        dx = 0;
        score = 0;
        r = 50;
        width = r * 2;
        height = r * 2;
        //super.x = x;
        //super.y = y;
    }

    public void setUp(boolean b){
        up = b;
    }

    public int getR(){ return r; }

    public void setDx(int speed){dx = speed; }

    public void setDy(int speed){dy = speed; }

    public void update(){

        y += dy / followSpeed;
        x += dx / followSpeed;

        int rightWall = GamePanel.WIDTH;
        int botWall = GamePanel.HEIGHT;
        int leftWall = 0;
        int topWall = 0;
        //if player hits edge, reflect
        if(x >= rightWall || (x-(r*2)) <= leftWall){
            dx = dx * -1;
        }
        if(y >= botWall || (y-(r*2)) <= topWall){
            dy = dy * -1;
        }
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x - r, y - r, r, paint);
    }


    /**
     *  Potential use: Bouncing off of each other
     */
    public void collisionWithPlayer(Player p2){
        /*
        this.dx = p2.getDx() * 2 + this.dx/2;
        this.dy = p2.getDy() * 2 + this.dy/2;
        p2.setDx(p2.getDx()/2 + this.dx * 2);
        p2.setDy(p2.getDy()/2 + this.dy * 2);*/
        // change x and y velocity to current + p2 trying to fix
    }
}

