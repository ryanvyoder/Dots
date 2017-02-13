package com.ryan.mygame;

import android.graphics.Rect;

/**
 * GameObject class: this class is an abstract class that holds all of the defining features of a Game Object. It also holds accessor and mutator methods for different
 * fields within these objects.
 * Created by Ryan on 5/11/2016.
 */
public abstract class GameObject {
    // All game objects have these fields
    protected int x, y, dy, dx, width, height;

    // Mutator methods for the Game Object abstract class
    public void setX(int x){this.x = x;}
    public void setY(int y){
        this.y = y;
    }
    public void setDx(int dx) {this.dx = dx; }
    public void setDy(int dy) {this.dy = dy; }
    public int getDx(){return dx; }
    public int getDy() {return dy;}
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int[] getPos(){return new int[]{dx,dy};}
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    // Accessor method for the Game Object's rectangle of occupancy
    public Rect getRectangle(){
        return new Rect(x, y, x - width, y - height);
    }

    // hhhh
    public boolean collidesWith(GameObject object){
        if((getX() >= (object.getX()-(object.getWidth())) && (getX()-getWidth()) <= (object.getX()))&&((getY()) >= (object.getY()-object.getHeight()) && (getY()-getHeight()) <= (object.getY()))){
            return true;
        }
        return false;
    }
}
