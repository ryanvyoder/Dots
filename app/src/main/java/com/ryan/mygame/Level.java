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
    private int stage;

    public Level(){
        stage = 1;
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

    public void nextStage(){
        stage++;
        level = 1;
        numBlues = 5;
        minDots = 2;
        dotsLeft = minDots;
        radiusRange[0] = 50;
        radiusRange[1] = 75;
        speedRange[0] = 0;
        speedRange[1] = 5;
        levelConstant = 3;
    }

    public void Reset(){
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
        stage = 1;
    }

    public void nextLevel(){
        //i think i want to change this so the percent you have to get becomes greater, but the number on screen caps off or really really slows
        if(stage == 1){ // no changes to speed
            currentLevel++;
            level = level + (currentLevel / 2);
            levelConstant = ((7 * numBlues) / 10);
            numBlues += (level / 5);
            minDots = numBlues - levelConstant;
            dotsLeft = minDots;
            radiusRange[0] = radiusRange[0] - 4;
            radiusRange[1] = radiusRange[1] - 5;
            if (radiusRange[0] < 10) {
                radiusRange[0] = 10;
                radiusRange[1] = 14;
            }
            if(currentLevel == 12){
                nextStage();
            }
        }
        else if(stage == 2){ //no changes to size
            currentLevel++;
            level = level + (currentLevel / 2);
            levelConstant = ((7 * numBlues) / 10);
            minDots = numBlues - levelConstant;
            dotsLeft = minDots;
            speedRange[0] = speedRange[0] + 2;
            speedRange[1] = speedRange[1] + 2;
            if (speedRange[0] >= 20) {
                speedRange[0] = 20;
                speedRange[1] = 25;
            }
            if(currentLevel == 24){
                nextStage();
            }
        }
        // make numbers increase a little bit slower
        else if(stage == 3){ //no changes to size
            currentLevel++;
            level = level + (currentLevel / 2);
            levelConstant = ((7 * numBlues) / 10);
            numBlues += Math.log(level);
            minDots = numBlues - levelConstant;
            dotsLeft = minDots;
            if(currentLevel % 2 == 0) {
                speedRange[0] = speedRange[0] + 1;
                speedRange[1] = speedRange[1] + 1;
            }
            if (speedRange[0] >= 20) {
                speedRange[0] = 20;
                speedRange[1] = 25;
            }
            if(currentLevel == 30){
                nextStage();
            }
        }
        else if(stage == 4) { // changes to size and speed
            currentLevel++;
            level = level + (currentLevel / 2);
            levelConstant = ((7 * numBlues) / 10);
            numBlues += (level / 2);
            minDots = numBlues - levelConstant;
            dotsLeft = minDots;
            radiusRange[0] = radiusRange[0] - 4;
            radiusRange[1] = radiusRange[1] - 4;
            speedRange[0] = speedRange[0] + 2;
            speedRange[1] = speedRange[1] + 2;
            if (speedRange[0] >= 20) {
                speedRange[0] = 20;
                speedRange[1] = 25;
            }
            if (radiusRange[0] < 10) {
                radiusRange[0] = 10;
                radiusRange[1] = 14;
            }
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
