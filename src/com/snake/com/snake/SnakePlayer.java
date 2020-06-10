package com.snake;

import javax.swing.*;

public class SnakePlayer extends Snake{

    SnakePlayer(){
        loadImageSnake();
        this.turnSnakeRight();
    }

    @Override
    public void loadImageSnake(){
        ImageIcon iih = new ImageIcon(getClass().getResource("/res/headPlayer.png"));
        this.head = iih.getImage();

        ImageIcon iid = new ImageIcon(getClass().getResource("/res/dotPlayer.png"));
        this.dot = iid.getImage();
    }

}
