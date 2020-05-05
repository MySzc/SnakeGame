package com.snake;

import javax.swing.*;

public class SnakeAI extends Snake {

    SnakeAI(){
        loadImageSnake();
    }

    @Override
    public void loadImageSnake(){
        ImageIcon iih = new ImageIcon(getClass().getResource("/res/head.png"));
        this.head = iih.getImage();

        ImageIcon iid = new ImageIcon(getClass().getResource("/res/dot.png"));
        this.dot = iid.getImage();
    }
}
