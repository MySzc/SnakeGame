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
            if((futureHeadX == this.getJointX(z)) &&
                    (futureHeadY == this.getJointY(z))) {
                System.out.println("Single Snake AI Collision");
                return true;
            }
        }

        if(futureHeadX >= 300 || futureHeadX < 0 || futureHeadY >= 300 || futureHeadY < 0) {
            System.out.println("future Wall AI Collision");
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

    public void completeAI(int frogX, int frogY, int appleX, int appleY){
        this.avoidCollision();
        this.chasePoints(frogX,frogY,appleX,appleY);
        //this.avoidCollision();
    }

    public void chasePoints(int frogX, int frogY, int appleX, int appleY){

        int closestX;
        int closestY;

        if(getDistance(this.getJointX(0),this.getJointY(0), appleX, appleY) <
                getDistance(this.getJointX(0),this.getJointY(0), frogX, frogY))
        {
            closestX = appleX;
            closestY = appleY;
        }else{
            closestX = frogX;
            closestY = frogY;
        }

        int directionX = this.getJointX(0) - closestX;
        int directionY = this.getJointY(0) - closestY;


        if(Math.abs(directionX) > Math.abs(directionY)){

                if(directionX < 0 && !checkFutureSingleSnakeCollision(turnRightX(), turnRightY())){
                    this.rightDirection = true;
                    this.upDirection = false;
                    this.downDirection = false;
                    this.leftDirection = false;
                }else if(directionX >= 0 && !checkFutureSingleSnakeCollision(turnLeftX(), turnLeftY())){
                    this.leftDirection = true;
                    this.upDirection = false;
                    this.downDirection = false;
                    this.rightDirection = false;
                }
        }else{
            if(directionY > 0 && !checkFutureSingleSnakeCollision(turnUpX(), turnUpY())){
                this.upDirection = true;
                this.rightDirection = false;
                this.downDirection = false;
                this.leftDirection = false;
            }else if(directionY <= 0 && !checkFutureSingleSnakeCollision(turnDownX(), turnDownY())){
                this.downDirection = true;
                this.upDirection = false;
                this.leftDirection = false;
                this.rightDirection = false;
            }
        }
    }

    int getDistance(int snakeX, int snakeY, int pointX, int pointY){
        return (int)Math.sqrt((snakeX-pointX)*(snakeX-pointX)+(snakeY-pointY)*(snakeY-pointY));
    }

}
