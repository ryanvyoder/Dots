package com.ryan.mygame;

/**
 * Created by Ryan on 5/11/2016.
 */
public class Level {
    private int level;
    private int numBlues;
    private int[] radiusRange = new int[2];
    private int[] speedRange = new int[2];
    private int minDots;
    private int levelConstant;
    private int dotsLeft;

    public Level(){
        level = 1;
        numBlues = 5;
        minDots = 2;
        dotsLeft = minDots;
        radiusRange[0] = 50;
        radiusRange[1] = 75;
        levelConstant = 3;
    }


    public void nextLevel(){
        level = level * 2;
        levelConstant = ((7*numBlues)/10);
        numBlues += (level / 2);
        minDots = numBlues - levelConstant;
        dotsLeft = minDots;
        radiusRange[0] = radiusRange[0]  - 4;
        radiusRange[1] = radiusRange[1] - 4;
        if(radiusRange[0] < 10){
            radiusRange[0] = 10;
            radiusRange[1] = 14;
        }
    }

    public void dotGot(){
        this.dotsLeft -= 1;
    }

    public int getRemaining(){
        return this.dotsLeft;
    }

    public int getNumBlues(){
        return this.numBlues;
    }

    public int[] getRadiusRange(){
        return this.radiusRange;
    }
}
