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
    private int currentLevel;

    public Level(){
        level = 1;
        numBlues = 5;
        minDots = 2;
        dotsLeft = minDots;
        radiusRange[0] = 50;
        radiusRange[1] = 75;
        speedRange[0] = 0;
        speedRange[1] = 5;
        levelConstant = 3;
        currentLevel = 1;
    }

    public void Reset(){
        level = 1;
        numBlues = 5;
        minDots = 2;
        dotsLeft = minDots;
        radiusRange[0] = 50;
        radiusRange[1] = 75;
        levelConstant = 3;
        currentLevel = 1;
    }

    public void nextLevel(){
        currentLevel++;
        level = level + (currentLevel/2);
        levelConstant = ((7*numBlues)/10);
        numBlues += (level / 2);
        minDots = numBlues - levelConstant;
        dotsLeft = minDots;
        radiusRange[0] = radiusRange[0]  - 4;
        radiusRange[1] = radiusRange[1] - 4;
        speedRange[0] = speedRange[0] + 2;
        speedRange[1] = speedRange[1] + 2;
        if(speedRange[0] >= 20){
            speedRange[0] = 20;
            speedRange[1] = 25;
        }
        if(radiusRange[0] < 10){
            radiusRange[0] = 10;
            radiusRange[1] = 14;
        }
    }

    public void dotGot(){
        this.dotsLeft -= 1;
    }

    public int getLevel(){
        return this.currentLevel;
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

    public int[] getSpeedRange(){
        return this.speedRange;
    }
}
