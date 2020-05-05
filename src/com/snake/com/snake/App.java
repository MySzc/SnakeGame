package com.snake;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    public App(){
        add(new Board());
        setResizable(false);
        pack();

        setTitle("Snek");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new App();
                ex.setVisible(true);
            }
        });
        System.out.print("Hello World");
    }
}
