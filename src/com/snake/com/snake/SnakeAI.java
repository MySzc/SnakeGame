package com.snake;

import javax.swing.*;

public class SnakeAI extends Snake {

    SnakeAI(){
        loadImageSnake();
        leftDirection = true;
    }

    @Override
    public void loadImageSnake(){
        ImageIcon iih = new ImageIcon(getClass().getResource("/res/headAI.png"));
        this.head = iih.getImage();

        ImageIcon iid = new ImageIcon(getClass().getResource("/res/dotAI.png"));
        this.dot = iid.getImage();
    }

    public int turnRightY(){
        return this.getJointY(0);
    }
    public int turnRightX(){
        return this.getJointX(0)  + this.getDOT_SIZE();
    }

    public int turnLeftY(){
        return this.getJointY(0);
    }
    public int turnLeftX(){
        return this.getJointX(0)  - this.getDOT_SIZE();
    }

    public int turnUpY(){
        return this.getJointY(0)  - this.getDOT_SIZE();
    }
    public int turnUpX(){
        return this.getJointX(0);
    }

    public int turnDownY(){
        return this.getJointY(0)  + this.getDOT_SIZE();
    }
    public int turnDownX(){
        return this.getJointX(0);
    }

    public boolean checkFutureSingleSnakeCollision(int futureHeadX, int futureHeadY){
        for (int z = this.getDots(); z > 0; z--) {
            if((this.getJointX(0) == futureHeadX) &&
                    (this.getJointY(0) == futureHeadY)) {
                System.out.println("Single Snake AI Collision");
                this.isLoser = true;
                return true;
            }
        }

        if(futureHeadX >= 300 || futureHeadX < 0 || futureHeadY >= 300 || futureHeadY < 0) {
            System.out.println("Wall AI Collision");
            this.isLoser = true;
            return true;
        }

        return false;
    }

    public void avoidCollision(){

        if(this.upDirection){
            if(checkFutureSingleSnakeCollision(turnUpX(), turnUpY())){
                if(!checkFutureSingleSnakeCollision(turnRightX(), turnRightY())){
                    this.rightDirection = true;
                    this.upDirection = false;
                    this.downDirection = false;
                    this.leftDirection = false;
                }else {
                    this.leftDirection = true;
                    this.upDirection = false;
                    this.downDirection = false;
                    this.rightDirection = false;
                }
            }
        }else

        if(this.downDirection){
            if(checkFutureSingleSnakeCollision(turnDownX(), turnDownY())){
                if(!checkFutureSingleSnakeCollision(turnRightX(), turnRightY())){
                    this.rightDirection = true;
                    this.upDirection = false;
                    this.downDirection = false;
                    this.leftDirection = false;
                }else {
                    this.leftDirection = true;
                    this.upDirection = false;
                    this.downDirection = false;
                    this.rightDirection = false;
                }
            }
        }else

        if(this.leftDirection){
            if(checkFutureSingleSnakeCollision(turnLeftX(), turnLeftY())){
                if(!checkFutureSingleSnakeCollision(turnUpX(), turnUpY())){
                    this.upDirection = true;
                    this.rightDirection = false;
                    this.downDirection = false;
                    this.leftDirection = false;
                }else {
                    this.downDirection = true;
                    this.upDirection = false;
                    this.leftDirection = false;
                    this.rightDirection = false;
                }
            }
        }else

        if(this.rightDirection){
            if(checkFutureSingleSnakeCollision(turnRightX(), turnRightY())){
                if(!checkFutureSingleSnakeCollision(turnUpX(), turnUpY())){
                    this.upDirection = true;
                    this.rightDirection = false;
                    this.downDirection = false;
                    this.leftDirection = false;
                }else {
                    this.downDirection = true;
                    this.upDirection = false;
                    this.leftDirection = false;
                    this.rightDirection = false;
                }
            }
        }
    }
/*
    void chasePoints(int frogX, int frogY, int appleX, int appleY){

        if(getDistance(this.getJointX(0),this.getJointY(0), appleX, appleY) <
        getDistance(this.getJointX(0),this.getJointY(0), frogX, frogY)){
            if(this.getJointX(0) - appleX > this.getJointY(0) - appleY){

            }
        }

    }
    */
    int getDistance(int snakeX, int snakeY, int pointX, int pointY){
        return (int)Math.sqrt((snakeX-pointX)^2+(snakeY-pointY)^2);
    }

}
