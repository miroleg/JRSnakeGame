package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    
    private static final int GOAL = 28;
    private  Snake snake;
    private  int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private int score;
   
   
    
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        turnDelay = 450;
        setTurnTimer(turnDelay);
        createNewApple();
        isGameStopped = false; 
        drawScene();
        score = 0;
        setScore(score);
       
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.GREEN, "");
             }
        }
        snake.draw(this);
        apple.draw(this);
    }
     @Override
     public  void onTurn(int turnOk){
         snake.move(apple);
         if (!apple.isAlive) {
            createNewApple();
            score = score + 7;
            setScore(score);
            turnDelay = turnDelay - 15;
            setTurnTimer(turnDelay);
         }
 
         if (snake.isAlive == false) gameOver();
         if (snake.getLength() > GOAL) win();
         drawScene();
     }

    @Override
    public  void onKeyPress(Key key) {
        if (key == Key.RIGHT) snake.setDirection(Direction.RIGHT);
        if (key == Key.LEFT) snake.setDirection(Direction.LEFT);
        if (key == Key.UP) snake.setDirection(Direction.UP);
        if (key == Key.DOWN) snake.setDirection(Direction.DOWN);
        if (key == Key.SPACE && isGameStopped) createGame();
        
    }
    
    private void createNewApple(){
        apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while (snake.checkCollision(apple)) {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }    
        
    }
    
    
    
    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.RED, 40);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "YOU WIN", Color.BLUE, 70);
    }

}
