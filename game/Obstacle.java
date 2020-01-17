package com.example.game;

public class Obstacle extends FirstGameItem {


    boolean scored = false;


    public Obstacle(int width, int height, FirstGameView view) {
        super(view.getdWidth(), 40 * view.getdHeight() / 100, width, height, view);
        this.width = this.height;             // TODO: See how to get accurate length
    }

    public void move() {
        this.x -= 50;              // The obstacle moves to the left
    }
}
