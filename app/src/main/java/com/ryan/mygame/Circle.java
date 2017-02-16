package com.ryan.mygame;

/**
 * Created by Ryan on 2/15/2017.
 */

public class Circle {
    private int radius;
    private int x;
    private int y;

    public Circle(int ra, int xa, int ya){
        this.radius = ra;
        this.x = xa;
        this.y = ya;
    }

    //not working correctly
    public boolean contains(Circle c){
        // check distance between the two centers, see if it is less than the combined radius
        boolean answer;

        double distance = getHypotenuse(c);
        double minDist = getRadius() + c.getRadius();

        if(distance <= minDist){
            answer = true;
        }
        else{
            answer = false;
        }

        return answer;
    }

    public boolean contains(int x, int y){
        Circle point = new Circle(3, x, y);
        return this.contains(point);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    private double getHypotenuse(Circle c){
        return Math.sqrt(Math.pow((this.y-c.y),2) + Math.pow((this.x-c.x),2));
    }

    private double getAngle(Circle c){
        return Math.toRadians(Math.atan((Math.abs((this.y - c.y)))/(Math.abs((c.x - this.x)))));
    }

    public int[] getCenter(){
        return new int[]{this.x, this.y};
    }

    public int getRadius(){
        return this.radius;
    }
}
