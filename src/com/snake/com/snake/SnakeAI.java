package com.snake;

import javax.swing.*;

// Class defining a snake controlled by the AI
public class SnakeAI extends Snake {

    SnakeAI(){
        loadImageSnake();
        this.turnSnakeLeft();
    }

    // Loading the sprites needed to draw the object
    @Override
    public void loadImageSnake(){
        ImageIcon iih = new ImageIcon(getClass().getResource("/res/headAI.png"));
        this.head = iih.getImage();

        ImageIcon iid = new ImageIcon(getClass().getResource("/res/dotAI.png"));
        this.dot = iid.getImage();
    }

    // A set of methods that return the X and Y position of the snake if it moved to the right of its current position
    public int turnRightY(){
        return this.getJointY(0);
    }
    public int turnRightX(){
        return this.getJointX(0) + this.getDOT_SIZE();
    }

    // A set of methods that return the X and Y position of the snake if it moved to the left of its current position
    public int turnLeftY(){
        return this.getJointY(0);
    }
    public int turnLeftX(){
        return this.getJointX(0)  - this.getDOT_SIZE();
    }

    // A set of methods that return the X and Y position of the snake if it moved up of its current position
    public int turnUpY(){
        return this.getJointY(0)  - this.getDOT_SIZE();
    }
    public int turnUpX(){
        return this.getJointX(0);
    }

    // A set of methods that return the X and Y position of the snake if it moved down of its current position
    public int turnDownY(){
        return this.getJointY(0)  + this.getDOT_SIZE();
    }
    public int turnDownX(){
        return this.getJointX(0);
    }

    // checks if a position on the board is a wall or a part of a snake
    public boolean checkFutureSingleSnakeCollision(int futureHeadX, int futureHeadY, Snake snakePlayer){
        for (int z = this.getDots(); z > 0; z--) {
            if((futureHeadX == this.getJointX(z) || futureHeadX == snakePlayer.getJointX(z)) &&
                    (futureHeadY == this.getJointY(z) || futureHeadY == snakePlayer.getJointY(z))) {
                System.out.println("Future Snake AI Collision");
                return true;
            }
        }

        if(futureHeadX >= 300 || futureHeadX < 0 || futureHeadY >= 300 || futureHeadY < 0) {
            System.out.println("Future Wall AI Collision");
            return true;
        }

        return false;
    }



    // simple collision avoidance
    public void avoidCollision(Snake snakePlayer){

        // if the snake is moving up
        if(this.isUpDirection()){
            //and if the next move up is going to cause a collision
            if(checkFutureSingleSnakeCollision(turnUpX(), turnUpY(), snakePlayer)){

                // then turn to the right if it doesnt cause a collision
                if(!checkFutureSingleSnakeCollision(turnRightX(), turnRightY(), snakePlayer)){
                    this.turnSnakeRight();
                // or to the left if it does
                }else {
                    this.turnSnakeLeft();
                }
                // no other movement options are available
            }
        }else

        //the same process as before for all other directions
        if(this.isDownDirection()){
            if(checkFutureSingleSnakeCollision(turnDownX(), turnDownY(), snakePlayer)){
                if(!checkFutureSingleSnakeCollision(turnRightX(), turnRightY(), snakePlayer)){
                    this.turnSnakeRight();
                }else {
                    this.turnSnakeLeft();
                }
            }
        }else

        if(this.isLeftDirection()){
            if(checkFutureSingleSnakeCollision(turnLeftX(), turnLeftY(), snakePlayer)){
                if(!checkFutureSingleSnakeCollision(turnUpX(), turnUpY(), snakePlayer)){
                    this.turnSnakeUp();
                }else {
                    this.turnSnakeDown();
                }
            }
        }else

        if(this.isRightDirection()){
            if(checkFutureSingleSnakeCollision(turnRightX(), turnRightY(), snakePlayer)){
                if(!checkFutureSingleSnakeCollision(turnUpX(), turnUpY(), snakePlayer)){
                    this.turnSnakeUp();
                }else {
                    this.turnSnakeDown();
                }
            }
        }
    }

    // method combining both methods of movement prediction done by the enemy snake
    public void completeAI(int frogX, int frogY, int appleX, int appleY, Snake snakePlayer){
        this.avoidCollision(snakePlayer);
        this.chasePoints(frogX,frogY,appleX,appleY, snakePlayer);
    }

    // Follows the nearest point object while avoiding collisions
    public void chasePoints(int frogX, int frogY, int appleX, int appleY, Snake snakePlayer){

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

                if(directionX < 0 && !checkFutureSingleSnakeCollision(turnRightX(), turnRightY(), snakePlayer)){
                    this.turnSnakeRight();
                }else if(directionX >= 0 && !checkFutureSingleSnakeCollision(turnLeftX(), turnLeftY(), snakePlayer)){
                    this.turnSnakeLeft();
                }
        }else{
            if(directionY > 0 && !checkFutureSingleSnakeCollision(turnUpX(), turnUpY(), snakePlayer)){
                this.turnSnakeUp();
            }else if(directionY <= 0 && !checkFutureSingleSnakeCollision(turnDownX(), turnDownY(), snakePlayer)){
                this.turnSnakeDown();
            }
        }
    }

    // Deriving the distance from the object using pythagorean theorem
    int getDistance(int snakeX, int snakeY, int pointX, int pointY){
        return (int)Math.sqrt((snakeX-pointX)*(snakeX-pointX)+(snakeY-pointY)*(snakeY-pointY));
    }

}
