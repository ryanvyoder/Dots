package com.ryan.mygame;

import android.content.Context;
//import android.support.annotation.MainThread;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ryan on 5/10/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 856;
    public static final int MOVESPEED = -15;
    private boolean playerTouch = false;
    private boolean p2Touch = false;
    private MainThread thread;
    private Background bg;
    private Player player;
    private Player p2;
    int numBlues = 10;
    private Blue[] blues = new Blue[numBlues];
    private Context context;

    public GamePanel(Context context){
        super(context);
        for(int i = 0; i < numBlues; i++){
            blues[i] = new Blue();
        }
        //adding ability to intercept events like touch screen presses
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.context = context;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            /*player.setUp(true);
            p2.setUp(true);
            //player.setX((int) (event.getX() / scaleFactorX));
            //player.setY((int)event.getY());
            // If you touch within the radius of the player (x+, x-, y+, y-), have the player follow you
            if(((event.getX() / scaleFactorX) >= (player.getX() - (player.getR()*2))) && ((event.getX() / scaleFactorX) <= (player.getX())) && ((event.getY() / scaleFactorY) >= (player.getY() - (player.getR()*2))) && ((event.getY() / scaleFactorY) <= (player.getY()))) {
                playerTouch = true;
                player.setDx((int) (((event.getX() / scaleFactorX) - (player.getX() - player.getR()))));
                player.setDy((int) (((event.getY() / scaleFactorY) - (player.getY() - player.getR()))));
            }
            if(((event.getX() / scaleFactorX) >= (p2.getX() - (p2.getR()*2))) && ((event.getX() / scaleFactorX) <= (p2.getX())) && ((event.getY() / scaleFactorY) >= (p2.getY() - (p2.getR()*2))) && ((event.getY() / scaleFactorY) <= (p2.getY()))) {
                p2Touch = true;
                p2.setDx((int) (((event.getX() / scaleFactorX) - (p2.getX() - p2.getR()))));
                p2.setDy((int) (((event.getY() / scaleFactorY) - (p2.getY() - p2.getR()))));
            }

            System.out.println("Touch X: " + (int)event.getX() + " Touch Y: " + (int)event.getY() + " SCALE FACTOR: " + scaleFactorX + " DX: " + player.getDx());
            */
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
           /* player.setUp(false);
            playerTouch = false;
            p2.setUp(false);
            p2Touch = false;
            //player.setDx(0);*/
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            //player.setX((int)(event.getX() / scaleFactorX));
            //set velocity to position we're touching - position we're at divided by the absolute value of the same number
            /*if(playerTouch){
                player.setDx((int) (((event.getX() / scaleFactorX) - (player.getX() - player.getR()))));
                player.setDy((int) (((event.getY() / scaleFactorY) - (player.getY() - player.getR()))));
            }
            if(p2Touch && !playerTouch){
                p2.setDx((int) (((event.getX() / scaleFactorX) - (p2.getX() - p2.getR()))));
                p2.setDy((int) (((event.getY() / scaleFactorY) - (p2.getY() - p2.getR()))));
            }
            System.out.println("Touch X: " + (int)event.getX() + " Touch Y: " + (int)event.getY() + " SCALE FACTOR: " + scaleFactorX);*/
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        final MediaPlayer soundtrack = MediaPlayer.create(context, R.raw.dbr);
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.appbg));
        //player = new Player();
        //p2 = new Player();
        soundtrack.start();

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    public void update(){
        //For some reason this isn't executing...
        /*if(Rect.intersects(player.getRectangle(), p2.getRectangle()) || Rect.intersects(p2.getRectangle(), player.getRectangle())){
            System.out.println("WORKING");
            player.collisionWithPlayer();
            p2.collisionWithPlayer();
        }*/

        //Manual collision; to be put into a separate method
        /*if(player.collidesWith(p2)){
            player.collisionWithPlayer(p2);
        }
        System.out.println(player.getRectangle() +"    " + p2.getRectangle());
        player.update();
        p2.update();*/
        bg.update();
        for(int i = 0; i < numBlues; i++){
            blues[i].update();
        }
    }

    //testing these changes
    //trying to get linked to github
    public void draw(Canvas canvas){
        super.draw(canvas);
        //This is the width of the screen divided by the width of our game
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if (canvas != null){
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            for(int i = 0; i < numBlues; i++){
                blues[i].draw(canvas);
            }
            /*player.draw(canvas);
            p2.draw(canvas);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(player.getRectangle(), paint);
            Paint paint2 = new Paint();
            paint2.setColor(Color.BLUE);
            paint2.setStyle(Paint.Style.FILL);
            canvas.drawRect(p2.getRectangle(), paint2);*/
        }
    }
}
