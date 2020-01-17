package com.example.game;

public class Player extends FirstGameItem {

    static int velocity = 0;          // velocity of knight move up and down
    static int gravity = 11;           // gravity on the screen

    public Player(int width, int height, FirstGameView view) {
        super(view.getdWidth() / 10, view.getdHeight() / 3, width, height, view);
        this.width = this.height;              // TODO: How to get the accurate length
    }

    public boolean collision(Obstacle monster) {
        // Multiple cases that collision happens
        if (this.y + this.height - 5 <= monster.y) {              // knight is over the monster
            return false;
        } else if (this.x + this.width < monster.x) {         // knight is totally left to the monster
            return false;
        } else if (this.x > monster.x + monster.width) {     // knight is totally right to the monster
            return false;
        } else {
            return true;
        }
    }


    public void move() {
        if (velocity < 0 | this.y < this.view.getdHeight() / 3) {
            velocity += gravity;
            this.y += velocity;
        }
    }
}
