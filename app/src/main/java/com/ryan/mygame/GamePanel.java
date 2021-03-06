package com.ryan.mygame;

import android.content.Context;
//import android.support.annotation.MainThread;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ryan.mygame.MainThread.canvas;

/**
 * PLANS:
 * Perhaps make the background "dance" with color alongside a upbeat electronic sort of music
 * Have the radius for blues shrink as levels pass, have them speed up
 * perhaps spawn more than just one red on misclick at harder levels
 * perhaps don't spawn so many so soon in the game
 *  EDIT:
 *      instead, perhaps have a set of "stages", where first all blues are slow, but many begin spawning
 *      then, it kind of resets but now the speeds increase a lot
 *      then finally have both happen in stage 3 as this is very difficult and will keep people trying
 *
 *      have the reds consume x number of blues before despawning
 * BUGS:
 * triple + digit remaining goes off screen
 * maybe just create a clearscreen method for every time display changes
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
    private int gameOver = 0;
    Level level = new Level();
    int numBlues = level.getNumBlues();
    private ArrayList<Blue> blues = new ArrayList<Blue>();
    private List<Red> reds = new ArrayList<Red>();
    private Context context;
    private int maxLevel = 0;

    public GamePanel(Context context){
        super(context);

        // loading in the previous high score (not working)
        String filename = "sav";
        FileInputStream is;

        try {
            //create file if not already found
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.close();
            is = context.openFileInput(filename);
            Scanner scan = new Scanner(is);
            maxLevel = scan.nextInt();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < numBlues; i++){
            blues.add(new Blue(level.getRadiusRange(), level.getSpeedRange()));
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

            if(gameOver == 1){
                String filename = "sav";
                FileOutputStream outputStream;

                try {
                    outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(Integer.toString(maxLevel).getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                level.Reset();
                gameOver = 0;
                numBlues = level.getNumBlues();
                for(int i = 0; i < numBlues; i++){
                    blues.add(new Blue(level.getRadiusRange(), level.getSpeedRange()));
                }
            }

            else {
                int correctPress = 0;
                for (int i = blues.size() - 1; i >= 0; i--) {
                    //doesnt work for some reason
                    if (blues.get(i).isTouched((int) (event.getX() / scaleFactorX), (int) (event.getY() / scaleFactorY))) {
                        System.out.println("TOUCHED TOUCHED TOUCHED TOUCHED");
                        blues.remove(i);
                        numBlues--;
                        correctPress = 1;
                        level.dotGot();
                        break;
                    }
                }

                if (correctPress == 0) {
                    reds.add(new Red((int) ((event.getX() / scaleFactorX) + Red.getR()), (int) ((event.getY() / scaleFactorY) - Red.getR())));
                }
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
            for(int j = blues.size() -1; j >= 0; j--){
                if (reds.get(i).getCircle().contains(blues.get(j).getCircle())){
                    blues.remove(j);
                    numBlues--;
                    break;
                }
            }
        }

        // will need to differentiate these; left is game over, right is next level
        if(level.getRemaining() == 0){
            for(int i = (blues.size() - 1); i >= 0; i--){
                blues.remove(i);
            }
            level.nextLevel();
            numBlues = level.getNumBlues();
            for(int i = 0; i < numBlues; i++){
                blues.add(new Blue(level.getRadiusRange(), level.getSpeedRange()));
            }
            for(int i = (reds.size() - 1); i >= 0; i--){
                reds.remove(i);
            }
        }
        else if(numBlues == 0){
            for(int i = (blues.size() - 1); i >= 0; i--){
                blues.remove(i);
            }
            for(int i = (reds.size() - 1); i >= 0; i--){
                reds.remove(i);
            }

            gameOver = 1;
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        //This is the width of the screen divided by the width of our game
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if (canvas != null){
            if(gameOver == 0) {
                canvas.scale(scaleFactorX, scaleFactorY);
                bg.draw(canvas);
                for (int i = 0; i < blues.size(); i++) {
                    blues.get(i).draw(canvas);
                }
                for (int i = 0; i < reds.size(); i++) {
                    reds.get(i).draw(canvas);
                }
                Paint textPaint = new Paint();
                textPaint.setColor(Color.YELLOW);
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setTextSize(72);
                textPaint.setFakeBoldText(true);
                canvas.drawText(Integer.toString(level.getRemaining()), WIDTH - 100, 82, textPaint);
            }
            else{
                // game over screen
                canvas.scale(scaleFactorX, scaleFactorY);
                bg.draw(canvas);
                Paint textPaint = new Paint();
                textPaint.setColor(Color.BLUE);
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setTextSize(100);
                textPaint.setFakeBoldText(true);
                textPaint.setTextAlign(Paint.Align.CENTER);

                Paint textBackground = new Paint();
                textBackground.setColor(Color.CYAN);
                textBackground.setStyle(Paint.Style.STROKE);
                textBackground.setStrokeWidth(3);
                textBackground.setTextSize(100);
                textBackground.setFakeBoldText(true);
                textBackground.setTextAlign(Paint.Align.CENTER);


                String output = "Max Level:";
                String output2 = "High Score:";
                canvas.drawText(output, WIDTH / 2, HEIGHT / 2, textPaint);
                canvas.drawText(Integer.toString(level.getLevel()), WIDTH /2, (HEIGHT/2) + 105, textPaint);
                canvas.drawText(output, WIDTH / 2, HEIGHT / 2, textBackground);
                canvas.drawText(Integer.toString(level.getLevel()), WIDTH /2, (HEIGHT/2) + 105, textBackground);
                textPaint.setTextSize(50);
                textBackground.setTextSize(50);
                if(maxLevel < level.getLevel()){
                    maxLevel = level.getLevel();
                }
                canvas.drawText(output2, WIDTH / 2, (HEIGHT / 2) + 210, textPaint);
                canvas.drawText(Integer.toString(maxLevel), WIDTH /2, (HEIGHT/2) + 315, textPaint);
                canvas.drawText(output2, WIDTH / 2, (HEIGHT / 2) + 210, textBackground);
                canvas.drawText(Integer.toString(maxLevel), WIDTH /2, (HEIGHT/2) + 315, textBackground);
            }
        }
    }
}
