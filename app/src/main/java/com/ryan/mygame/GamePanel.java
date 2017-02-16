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

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<Blue> blues = new ArrayList<Blue>();
    private List<Red> reds = new ArrayList<Red>();
    private Context context;

    public GamePanel(Context context){
        super(context);
        for(int i = 0; i < numBlues; i++){
            blues.add(new Blue());
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

            int correctPress = 0;
            for(int i = 0; i < numBlues; i++){
                //doesnt work for some reason
                if(blues.get(i).isTouched((int)(event.getX() / scaleFactorX), (int)(event.getY() / scaleFactorY))){
                    System.out.println("TOUCHED TOUCHED TOUCHED TOUCHED");
                    blues.remove(i);
                    numBlues--;
                    correctPress = 1;
                }
            }

            if(correctPress == 0){
                reds.add(new Red((int)((event.getX() / scaleFactorX) + Red.getR()), (int)((event.getY() / scaleFactorY)-Red.getR())));
            }

            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        final MediaPlayer soundtrack = MediaPlayer.create(context, R.raw.dbr);
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.appbg));
        soundtrack.start();

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    public void update(){
        bg.update();
        for(int i = 0; i < blues.size(); i++){
            blues.get(i).update();
        }
        for(int i = 0; i < reds.size(); i++){
            reds.get(i).update();
        }
        for(int i = 0; i < reds.size(); i++){
            for(int j = 0; j < blues.size(); j++){
                if (reds.get(i).getCircle().contains(blues.get(j).getCircle())){
                    blues.remove(j);
                    numBlues--;
                }
            }
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        //This is the width of the screen divided by the width of our game
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if (canvas != null){
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            for(int i = 0; i < blues.size(); i++){
                blues.get(i).draw(canvas);
            }
            for(int i = 0; i < reds.size(); i++){
                reds.get(i).draw(canvas);
            }
        }
    }
}
