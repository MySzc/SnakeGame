package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;

//Class responsible for combining all elements of the game
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
    // High score
    public PriorityQueue<Integer> highScore = new PriorityQueue<Integer>(10, Collections.reverseOrder());

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
        readHighestScores();
        initGame();
    }

    // Setting all the pieces on the board
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

    // Swing function used to paint objects in the game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            doDrawing(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function used to draw the shape of all the dots making up the snake
    private void drawSnake(Snake snakeLoc, Graphics g){
        for (int z = 0; z < snakeLoc.getDots(); z++){
            if(z == 0){
                g.drawImage(snakeLoc.getHeadImage(), snakeLoc.getJointX(z), snakeLoc.getJointY(z), this);
            } else{
                g.drawImage(snakeLoc.getDotImage(), snakeLoc.getJointX(z), snakeLoc.getJointY(z), this);
            }
        }

    }

    // Function responsible for painting all the components of the in game screen
    private void doDrawing(Graphics g) throws IOException {
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

    // Function responsible for painting all the components of the game over screen
    private void gameOver(Graphics g) throws IOException {
        System.out.println("Game Over");
        String msg = "Error: no winners";
        highScore.add(this.snakePlayer.getDots());
        String pts = "Points: " + Integer.toString(this.snakePlayer.getDots());
        PriorityQueue<Integer> tmpQueue = new PriorityQueue<Integer>(highScore);
        String tmp = "Highest scores:";
        if(snakeAI.isLoser){
            msg = "Game Over, Player Won";
        }else{
            msg = "Game Over, AI Won";
        }


        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(BOARD_WIDTH - metr.stringWidth(msg))/2, BOARD_HEIGHT/2-100);
        g.drawString(pts,(BOARD_WIDTH - metr.stringWidth(pts))/2, BOARD_HEIGHT/2-50);
        g.drawString(tmp,(BOARD_WIDTH - metr.stringWidth(tmp))/2, BOARD_HEIGHT/2);

        for (int i = 0; i < 5 && !tmpQueue.isEmpty(); i++){
            String highest = Integer.toString(i+1) + ": " + Integer.toString(tmpQueue.poll());
            g.drawString(highest,(BOARD_WIDTH - metr.stringWidth(highest))/2, BOARD_HEIGHT/2+ 20+i*20);
        }

        writeHighestScores();
    }


    // Function responsible for checking if a snake has gotten a point and increasing its size if it happened
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

    // functon checking if any of the snakes went over the border
    private void checkBoardCollisions(Snake snakeLoc) {

        if (snakeLoc.getJointY(0) >= BOARD_HEIGHT) {
            snakeLoc.isLoser = true;
            this.inGame = false;
        } else if (snakeLoc.getJointY(0) < 0) {
            snakeLoc.isLoser = true;
            this.inGame = false;
        } else if (snakeLoc.getJointX(0) >= BOARD_WIDTH) {
            snakeLoc.isLoser = true;
            this.inGame = false;
        } else if (snakeLoc.getJointX(0) < 0) {

            snakeLoc.isLoser = true;
            this.inGame = false;
        }
    }

    // function checking if any of the snakes have bumped into each other
    public boolean checkTwoSnakeCollision(Snake snakeAI, Snake snakePlayer){
        for (int z = snakeAI.getDots()-1; z >= 0; z--) {
            if((snakeAI.getJointX(0) == snakePlayer.getJointX(z)) &&
                    (snakeAI.getJointY(0) == snakePlayer.getJointY(z))) {

                snakeAI.isLoser = true;
                return true;
            }
        }

        for (int z = snakePlayer.getDots()-1; z >= 0; z--) {
            if((snakePlayer.getJointX(0) == snakeAI.getJointX(z)) &&
                    (snakePlayer.getJointY(0) == snakeAI.getJointY(z))) {

                snakePlayer.isLoser = true;
                return true;
            }
        }
        return false;
    }

    //function combining all the collision checking functions
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

    // Main function responsible for updating the in game screen
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkPoints(snakePlayer);
            checkPoints(snakeAI);
            checkAllCollisions(this.snakePlayer, this.snakeAI, this.timer);
            points.moveFrog();
            snakeAI.completeAI(this.points.getFrog_x(),this.points.getFrog_y(),
                                this.points.getApple_x(),this.points.getApple_y(),
                                                               this.snakePlayer );

            snakeAI.moveSnake();
            snakePlayer.moveSnake();
        }

        repaint();
    }

    // Reads keyboard input from arrow keys and assigns them a movement
    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int Key = e.getKeyCode();

            if(Key == KeyEvent.VK_LEFT ){
                snakePlayer.turnSnakeLeft();
            }

            if(Key == KeyEvent.VK_RIGHT ){
                snakePlayer.turnSnakeRight();
            }

            if(Key == KeyEvent.VK_UP ){
                snakePlayer.turnSnakeUp();
            }

            if(Key == KeyEvent.VK_DOWN ){
                snakePlayer.turnSnakeDown();
            }
        }
    }

    // function that reads the current highest scores from a file
    void readHighestScores(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "HighScore.txt"));
            String line = reader.readLine();
            int score;
            try {
                line = reader.readLine();
            }
            catch (NumberFormatException e) {
                score = 0;
            }

            while (line != null) {
                System.out.println(line);
                score = Integer.parseInt(line);
                highScore.add(score);
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Function that writes the 5 highest scores to a file
    public void writeHighestScores() throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter("HighScore.txt", true));

        PriorityQueue<Integer> tmpQueue = new PriorityQueue<Integer>(highScore);
        for (int i = 0; i < 5 && !tmpQueue.isEmpty(); i++) {
            int tmp = tmpQueue.poll();
            bw.write(Integer.toString(tmp));
            bw.newLine();
        }

        bw.close();
    }

}
