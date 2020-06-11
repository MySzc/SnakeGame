package com.snake;

import javax.swing.*;

// Class defining a snake used by the player
public class SnakePlayer extends Snake{

    SnakePlayer(){
        loadImageSnake();
        this.turnSnakeRight();
    }

    // Loading the sprites needed to draw the object
    @Override
    public void loadImageSnake(){
        ImageIcon iih = new ImageIcon(getClass().getResource("/res/headPlayer.png"));
        this.head = iih.getImage();

        ImageIcon iid = new ImageIcon(getClass().getResource("/res/dotPlayer.png"));
        this.dot = iid.getImage();
    }

}
