package com.snake;

import javax.swing.*;
import java.awt.*;

// Class defining points in the game
public class Points extends Thread{

    // Rand seed
    private final int RAND_POS = 29;
    private final int DOT_SIZE = 10;
    // Apple
    private final int APPLE_SIZE = 10;
    // Frog
    private final int FROG_SIZE = 10;



    // Position of the apple
    private int apple_x;
    private int apple_y;

    // Position of the frog
    private int frog_x;
    private int frog_y;

    // Images
    public Image frog;
    public Image apple;

    Points(){
        loadImageFrog();
        loadImageApple();
    }

    // Loading the sprites needed to draw the object
    public void loadImageFrog(){
        ImageIcon iif = new ImageIcon(getClass().getResource("/res/frog.png"));
        this.frog = iif.getImage();
    }

    // Loading the sprites needed to draw the object
    public void loadImageApple(){
        ImageIcon iia = new ImageIcon(getClass().getResource("/res/apple.png"));
        this.apple = iia.getImage();
    }

    // Apple access
    public int getApple_x(){
        return apple_x;
    }

    public void setApple_x(int x){
        this.apple_x = x;
    }

    public int getApple_y(){
        return apple_y;
    }

    public void setApple_y(int y){
        this.apple_y = y;
    }

    public Image getAppleImage(){
        return apple;
    }

    public void locateApple(){
        int r = (int) (Math.random() * RAND_POS);
        setApple_x(r * DOT_SIZE);

        r = (int) (Math.random() * RAND_POS);
        setApple_y(r * DOT_SIZE);
    }

    // Frog access
    public int getFrog_x(){
        return frog_x;
    }

    public void setFrog_x(int x){
        this.frog_x = x;
    }

    public int getFrog_y(){
        return frog_y;
    }

    public void setFrog_y(int y){
        this.frog_y = y;
    }

    public Image getFrogImage(){
        return frog;
    }

    public void locateFrog(){
        int r = (int) (Math.random() * RAND_POS);
        this.frog_x = r * DOT_SIZE;

        r = (int) (Math.random() * RAND_POS);
        this.frog_y = r * DOT_SIZE;

    }

    // Dictates how the from moves -- randomly unless it would cause it to go over the board border
    public void moveFrog(){

        int movement =(int) (Math.random()*20);

        if(movement == 0 && (this.frog_x/DOT_SIZE - 1) >= 0){
            this.frog_x -= DOT_SIZE;
        }else if(movement == 1 && (this.frog_x/DOT_SIZE + 1) <= 290) {
            this.frog_x += DOT_SIZE;
        }else if(movement == 2 && (this.frog_y/DOT_SIZE - 1) >= 0) {
            this.frog_y -= DOT_SIZE;
        }else if(movement == 3 && (this.frog_y/DOT_SIZE + 1) <= 290) {
            this.frog_y += DOT_SIZE;
        }
    }

    // Thread
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
