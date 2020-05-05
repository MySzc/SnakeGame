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
    private boolean inGame = true;

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
        snakePlayer = new SnakePlayer();
        snakeAI = new SnakeAI();
        initGame();
    }

    public void initGame(){
        this.snakePlayer.setDots(3);
        this.snakePlayer.setJointsSize(GRID_NUM);

        for (int z = 0; z < this.snakePlayer.getDots(); z++){
            this.snakePlayer.setJointX(z, (60-z*10));
            this.snakePlayer.setJointY(z,60);
        }

        this.snakeAI.setDots(3);
        this.snakeAI.setJointsSize(GRID_NUM);

        for (int z = 0; z < this.snakeAI.getDots(); z++){
            this.snakeAI.setJointX(z, (40-z*10));
            this.snakeAI.setJointY(z,40);
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
            drawSnake(snakePlayer, g);
            drawSnake(snakeAI, g);

            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }

    private void gameOver(Graphics g){
        String msg = "Game Over";
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

    private boolean checkBoardCollisions(Snake snakeLoc){

        if(snakeLoc.getJointY(0) >= BOARD_HEIGHT)
            return true;

        if(snakeLoc.getJointY(0) < 0)
            return true;

        if(snakeLoc.getJointX(0) >= BOARD_WIDTH)
            return true;

        if(snakeLoc.getJointX(0) < 0)
            return true;

        return false;
    }

    private void checkAllCollisions(Snake snakePlayer, Snake snakeAI, Timer timer, boolean inGame){

        inGame = !checkBoardCollisions(snakePlayer);
        inGame = !checkBoardCollisions(snakeAI);
        inGame = !snakePlayer.checkSingleSnakeCollision();
        inGame = !snakeAI.checkSingleSnakeCollision();
        // TODO Collision between snakes
        if(!inGame)
            timer.stop();
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkPoints(snakePlayer);
            checkPoints(snakeAI);
            checkAllCollisions(this.snakePlayer, this.snakeAI, this.timer, this.inGame);
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
