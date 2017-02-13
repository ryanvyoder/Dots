package com.ryan.mygame;
import android.graphics.Canvas;
import android.os.Build;
import android.view.SurfaceHolder;

/**
 * Created by Ryan on 5/10/2016.
 */
public class MainThread extends Thread{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        //call constructor of Thread class
        super();
        //set surfaceholder reference to surfaceholder that's passed in through the constructor
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        //capping FPS at 30
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS; //how many milliseconds you want the game loop to take (1000/30)

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    //everytime you go through the gameloop, you'll update the screen once and draw once
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {

            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            timeMillis = (System.nanoTime() - startTime) / 1000000; //how many milliseconds it took to update and draw game once
            waitTime = targetTime - timeMillis;

            //make the thread wait at waittime
            try {
                this.sleep(waitTime);
            } catch (Exception e) {

            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void setRunning(boolean b){
        running = b;
    }

}
