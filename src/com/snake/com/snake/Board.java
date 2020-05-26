package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    // Board size
    private final int BOARD_WIDTH = 300;
    private final int BOARD_HEIGHT = 300;
    // Grid of the board
    private final int GRID_NUM = 900;
    // Timer
    private Timer timer;
    private final int DELAY = 140;
    // In game flag
    public boolean inGame;

    // Game objects
    private SnakePlayer snakePlayer;
    private SnakeAI snakeAI;
    private Points points;

    // Board Constructor
    public Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        points = new Points();
        points.start();
        snakePlayer = new SnakePlayer();
        snakePlayer.start();
        snakeAI = new SnakeAI();
        snakeAI.start();
        initGame();
    }

    public void initGame(){
        this.snakePlayer.setDots(3);
        this.snakePlayer.setJointsSize(GRID_NUM);
        inGame = true;

        for (int z = 0; z < this.snakePlayer.getDots(); z++){
            this.snakePlayer.setJointX(z, (40-z*10));
            this.snakePlayer.setJointY(z,40);
        }

        this.snakeAI.setDots(3);
        this.snakeAI.setJointsSize(GRID_NUM);

        for (int z = 0; z < this.snakeAI.getDots(); z++){
            this.snakeAI.setJointX(z, (260+z*10));
            this.snakeAI.setJointY(z,260);
        }

        this.points.locateApple();
        this.points.locateFrog();

        this.timer = new Timer(DELAY, this);
        this.timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void drawSnake(Snake snakeLoc, Graphics g){
        for (int z = 0; z < snakeLoc.getDots(); z++){
            if(z == 0){
                g.drawImage(snakeLoc.getHeadImage(), snakeLoc.getJointX(z), snakeLoc.getJointY(z), this);
            } else{
                g.drawImage(snakeLoc.getDotImage(), snakeLoc.getJointX(z), snakeLoc.getJointY(z), this);
            }
        }

    }

    private void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(points.getAppleImage(), points.getApple_x(), points.getApple_y(), this);
            g.drawImage(points.getFrogImage(), points.getFrog_x(), points.getFrog_y(), this);
            drawSnake(snakePlayer, g);
            drawSnake(snakeAI, g);

            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }

    private void gameOver(Graphics g){
        System.out.println("Game Over");
        String msg = "Error: no winners";

        if(snakeAI.isLoser){
            msg = "Game Over, Player Won";
        }else{
            msg = "Game Over, AI Won";
        }

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(BOARD_WIDTH - metr.stringWidth(msg))/2, BOARD_HEIGHT/2);
    }


    private void checkPoints(Snake snakeLoc){
        if((snakeLoc.getJointX(0) == points.getApple_x()) &&
           (snakeLoc.getJointY(0) == points.getApple_y())) {

            snakeLoc.incrementDots();
            points.locateApple();
        }

        if((snakeLoc.getJointX(0) == points.getFrog_x()) &&
           (snakeLoc.getJointY(0) == points.getFrog_y())) {

            snakeLoc.incrementDots();
            points.locateFrog();
        }
    }

    private void checkBoardCollisions(Snake snakeLoc) {

        if (snakeLoc.getJointY(0) >= BOARD_HEIGHT) {
            System.out.println("BoardCollision");
            snakeLoc.isLoser = true;
            this.inGame = false;
        } else if (snakeLoc.getJointY(0) < 0) {
            System.out.println("BoardCollision");
            snakeLoc.isLoser = true;
            this.inGame = false;
        } else if (snakeLoc.getJointX(0) >= BOARD_WIDTH) {
            System.out.println("BoardCollision");
            snakeLoc.isLoser = true;
            this.inGame = false;
        } else if (snakeLoc.getJointX(0) < 0) {
            System.out.println("BoardCollision");
            snakeLoc.isLoser = true;
            this.inGame = false;
        }
    }

    public boolean checkTwoSnakeCollision(Snake snakeAI, Snake snakePlayer){
        for (int z = snakeAI.getDots()-1; z >= 0; z--) {
            if((snakeAI.getJointX(0) == snakePlayer.getJointX(z)) &&
                    (snakeAI.getJointY(0) == snakePlayer.getJointY(z))) {
                System.out.println("AI into Player Snake Collision");
                snakeAI.isLoser = true;
                return true;
            }
        }

        for (int z = snakePlayer.getDots()-1; z >= 0; z--) {
            if((snakePlayer.getJointX(0) == snakeAI.getJointX(z)) &&
                    (snakePlayer.getJointY(0) == snakeAI.getJointY(z))) {
                System.out.println("Player into AI Snake Collision");
                snakePlayer.isLoser = true;
                return true;
            }
        }
        return false;
    }

    public void checkAllCollisions(Snake snakePlayer, Snake snakeAI, Timer timer){

        if(inGame)
            checkBoardCollisions(snakePlayer);

        if(inGame)
            checkBoardCollisions(snakeAI);

        if(inGame)
            inGame = !snakePlayer.checkSingleSnakeCollision();

        if(inGame)
            inGame = !snakeAI.checkSingleSnakeCollision();

        if(inGame)
            inGame = !checkTwoSnakeCollision(snakeAI, snakePlayer);

        if(!inGame)
            System.out.println("inGame = false");

        if(!inGame)
            timer.stop();
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkPoints(snakePlayer);
            checkPoints(snakeAI);
            checkAllCollisions(this.snakePlayer, this.snakeAI, this.timer);
            points.moveFrog();
            snakeAI.avoidCollision();
            //snakeAI.moveSnake();
            snakePlayer.moveSnake();
        }

        repaint();
    }

    public class TAdapter extends KeyAdapter {
        // TODO Move this to SnakePlayer

        @Override
        public void keyPressed(KeyEvent e) {

            int Key = e.getKeyCode();

            if(Key == KeyEvent.VK_LEFT && !snakePlayer.rightDirection){
                snakePlayer.leftDirection = true;
                snakePlayer.upDirection = false;
                snakePlayer.downDirection = false;
            }

            if(Key == KeyEvent.VK_RIGHT && !snakePlayer.leftDirection){
                snakePlayer.rightDirection = true;
                snakePlayer.upDirection = false;
                snakePlayer.downDirection = false;
            }

            if(Key == KeyEvent.VK_UP && !snakePlayer.downDirection){
                snakePlayer.upDirection = true;
                snakePlayer.leftDirection = false;
                snakePlayer.rightDirection = false;
            }

            if(Key == KeyEvent.VK_DOWN && !snakePlayer.upDirection){
                snakePlayer.downDirection = true;
                snakePlayer.leftDirection = false;
                snakePlayer.rightDirection = false;
            }
        }
    }

}
