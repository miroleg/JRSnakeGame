package com.javarush.games.snake;
import com.javarush.engine.cell.*;
import java.util.*;

public class Snake extends SnakeGame {
    private  List<GameObject> snakeParts = new ArrayList<>();
    private static final  String HEAD_SIGN =  "\uD83D\uDC7E";
    private static final  String BODY_SIGN =  "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;


    public int x;
    public int y;
    public  Snake ( int x,  int y) {
        this.x = x;
        this.y = y;
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }


    public void setDirection(Direction direction) {
  
        if ((this.direction == Direction.LEFT
            || this.direction == Direction.RIGHT)
            && (snakeParts.get(0).x == snakeParts.get(1).x))
                return;
        if ((this.direction == Direction.DOWN                
            || this.direction == Direction.UP)
            && (snakeParts.get(0).y == snakeParts.get(1).y))
                return;
         
        if (this.direction == Direction.RIGHT 
         && !(direction == Direction.LEFT)) 
            this.direction = direction;
        if (this.direction == Direction.LEFT
         && !(direction == Direction.RIGHT))
            this.direction = direction;
        if (this.direction == Direction.UP 
         && !(direction ==Direction.DOWN))
            this.direction = direction;
        if (this.direction.equals(Direction.DOWN)
         && !(direction.equals(Direction.UP))) 
            this.direction = direction;
    }

    public void draw (Game game){
         if  (isAlive) game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE,  HEAD_SIGN, Color.BLUE, 75);
         else game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE,  HEAD_SIGN, Color.RED, 75);

        for (int i = 1; i < snakeParts.size(); i++)
            if  (isAlive) game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,Color.NONE,  BODY_SIGN, Color.YELLOW, 75);
            else game.setCellValueEx(snakeParts.get(i).x, snakeParts .get(i).y,Color.NONE,  BODY_SIGN, Color.RED, 75);
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }
        if (newHead.x >= SnakeGame.WIDTH ||
            newHead.x < 0 ||
            newHead.y >= SnakeGame.HEIGHT ||
            newHead.y < 0) {
            isAlive = false;

        } else if (apple.x == newHead.x && apple.y == newHead.y) {
            apple.isAlive = false;
            snakeParts.add(0, newHead);
            
        } else {
            snakeParts.add(0, newHead);
            removeTail();
        }
    }




    public GameObject createNewHead() {
        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;

        //if (direction.equals(Direction.LEFT))  return new GameObject(headX-1, headY);
        if (direction == Direction.RIGHT) return new GameObject(headX+1, headY);
        if (direction == Direction.DOWN)  return  new GameObject(headX, headY+1);
        if (direction == Direction.UP)   return new GameObject(headX, headY-1);

        return new GameObject(headX-1, headY);
    }
    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject gO) {
        boolean check =  false;
        for (int i = 0; i < snakeParts.size(); i++)
            if  ((gO.x == snakeParts.get(i).x)
                && (gO.y == snakeParts.get(i).y)) 
                    check = true;
         return check;
    }
    
    public int  getLength() {
        return snakeParts.size();
    }


}
