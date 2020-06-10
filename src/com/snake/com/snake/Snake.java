package com.snake;

import java.awt.*;

import java.util.ArrayList;

public class Snake extends Thread{

    // Dots making the body of the snake
    private int dots;
    private final int DOT_SIZE = 10;

    // Joints of the snake
    private int[] x;
    private int[] y;
    //private ArrayList<Integer> x;
    //private ArrayList<Integer> y;

    // TODO Move this to SnakePlayer
    // Movements of the snake
    private boolean rightDirection = false;
    private boolean leftDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;

    // Images of the snake
    public Image head;
    public Image dot;

    // Needed to check the winner of the game
    public boolean isLoser = false;


    // controlling movement of the snake
    public void turnSnakeRight(){
        if(!this.leftDirection){
            this.rightDirection = true;
            this.upDirection = false;
            this.downDirection = false;
        }
    }
    public void turnSnakeLeft(){
        if(!this.rightDirection){
            this.leftDirection = true;
            this.upDirection = false;
            this.downDirection = false;
        }
    }
    public void turnSnakeUp(){
        if(!this.downDirection){
            this.upDirection = true;
            this.rightDirection = false;
            this.leftDirection = false;
        }
    }
    public void turnSnakeDown(){
        if(!this.upDirection){
            this.downDirection = true;
            this.rightDirection = false;
            this.leftDirection = false;
        }
    }

    public boolean isRightDirection(){
        return this.rightDirection;
    }

    public boolean isLeftDirection(){
        return this.leftDirection;
    }

    public boolean isUpDirection(){
        return this.upDirection;
    }

    public boolean isDownDirection(){
        return this.downDirection;
    }

    // Snake movement
    public void moveSnake(){
        for (int z = this.dots; z > 0; z--){
            this.x[z] = this.x[z-1];
            this.y[z] = this.y[z-1];
        }

        if(rightDirection){
            this.x[0] += DOT_SIZE;
        }

        if(leftDirection){
            this.x[0] -= DOT_SIZE;
        }

        if(upDirection){
            this.y[0] -= DOT_SIZE;
        }

        if(downDirection){
            this.y[0] += DOT_SIZE;
        }
    }

    // Snake collision
    public boolean checkSingleSnakeCollision(){
        for (int z = this.dots; z > 0; z--) {
            if((this.x[0] == this.x[z]) &&
                (this.y[0] == this.y[z])) {
                System.out.println("Single Snake Collision");
                this.isLoser = true;
                return true;
            }
        }
        return false;
    }



    // Snake access
    public int getDOT_SIZE(){
        return this.DOT_SIZE;
    }

    public void loadImageSnake(){
        System.out.println("Default snake image");
    }

    public void setDots(int amount){
        this.dots = amount;
    }

    public int getDots(){
        return this.dots;
    }

    public void setJointsSize(int gridNum){
        this.x = new int[gridNum];
        this.y = new int[gridNum];
    }

    public void setJointX(int index, int value){
        this.x[index] = value;
    }

    public void setJointY(int index, int value){
        this.y[index] = value;
    }

    public int getJointX(int index){
        return this.x[index];
    }

    public int getJointY(int index){
        return this.y[index];
    }

    public Image getHeadImage(){
        return this.head;
    }

    public Image getDotImage(){
        return this.dot;
    }

    public void incrementDots(){
        this.dots += 1;
    }

    // Threads
    public void run() {
        try {
            // Displaying the thread that is running
            System.out.println ("Thread " +
                    Thread.currentThread().getId() +
                    " is running");

        } catch (Exception e) {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
    }

}
