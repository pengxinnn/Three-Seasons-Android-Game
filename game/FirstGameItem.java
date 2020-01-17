package com.example.game;

public abstract class FirstGameItem {
    int x;                 // x refers to the coordinate of left line
    int y;                 // y refers to the coordinate of upper line
    int width;                 // width refers to the width of the picture (x)
    int height;                 // height refers to the length of the picture(y)
    FirstGameView view;

    public FirstGameItem(int x, int y, int width, int height, FirstGameView view) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height - y;                    // TODO: How to get the length!
        this.view = view;
    }

    abstract public void move();
}
