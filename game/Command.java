package com.example.game;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

public class Command extends AppCompatActivity{
    private int color;

    // Integer that represents the color that the word's meaning indicates
    private int colorWord;

    // Word showing the color.
    private String word;
    // Integer that represents the shape.
    private int shape_int;

    Command() {
        this.color = chooseColor();
        this.word = setWord();
        this.shape_int = setShape();
    }
    // random choose the color of the command
    int chooseColor() {
        double d = Math.random();
        if (d < 0.3) {
            return Color.YELLOW;

        } else if (d >= 0.3 && d < 0.66) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }

    }
    // random choose the word of the command
    String setWord() {
        double d = Math.random();
        if (d < 0.3) {
            this.colorWord = Color.RED;
            return new String("Red");

        } else if (d >= 0.3 && d < 0.66) {
            this.colorWord = Color.YELLOW;
            return new String("Yellow");

        } else {
            this.colorWord = Color.GREEN;
            return new String("Green");

        }

    }

    // random choose the shape of the command
    int setShape() {
        double d = Math.random();
        if (d < 0.5) {
            // shape is round.
            return 0;
        } else {
            // shape is rectangle.
            return 1;
        }

    }

    // to access the correct answer for this command
    public int commandColor() {
        // if the shape is round, the correct answer is the background color.
        if (shape_int == 0) {
            return color;
        // if the shape is rectangle, the correct answer is the word.
        } else {
            return colorWord;
        }
    }
    public String getWord(){
        return this.word;
    }
    public int getColor(){
        return this.color;
    }
    public int getShape() { return shape_int; }
}

