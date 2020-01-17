package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class FirstGameManager {
    /**
     * Game statistics
     */
    private boolean isGameOver = false;
    private int score;
    private long duration;
    private int distance;

    /**
     * Game elements
     */
    private ArrayList<Obstacle> Monsters;
    private Player player;
    private Context context;
    private FirstGameView view;


    public FirstGameManager(int knightWidth, int knightHeight, Context context, FirstGameView view) {

        this.player = new Player(knightWidth, knightHeight, view);
        this.Monsters = new ArrayList<Obstacle>();
        this.context = context;
        this.view = view;

    }

    boolean getIsGameOver() {
        return this.isGameOver;
    }

    int getScore() {
        return this.score;
    }

    long getDuration() {
        return this.duration;
    }

    int getDistance() {
        return this.distance;
    }

    ArrayList<Obstacle> getMonsters() {
        return this.Monsters;
    }

    Player getPlayer() {
        return this.player;
    }


    private void updateScore() {
        for (Obstacle item : Monsters) {
            if ((!this.isGameOver) & (!item.scored) & (item.x + item.width) < player.x) {
                this.score += 10;
                item.scored = true;
            }
        }
    }

    /**
     * Update() updates all the items once:
     * (1) whether to create a new obstacle
     * (2) move all the items
     * (3) record the score
     * (4) check collision
     * (5) check if the game is over
     */
    void update() {
        player.move();

        /*
          Whether to create a new obstacle
         */
        //TODO: Collect garbage
        double create = Math.random();
        if (create > 0.9) {
            Obstacle m = new Obstacle(view.getMonsterWidth(),
                    view.getMonsterHeight(), view);
            this.Monsters.add(m);
        }
        for (int i = 0; i < Monsters.size(); i++) {
            this.Monsters.get(i).move();
        }

        /*
          Check collision
         */
        for (Obstacle monster : this.Monsters) {
            if (player.collision(monster)) {
                this.isGameOver = true;
            }
        }

        /*
          Update statistics
         */
        updateScore();
        long endTime = System.currentTimeMillis(); //Record the current time
        updateTime(endTime);
        updateDistance();

        /*
          Check if the game is over
         */
        if (this.isGameOver) {
            ((FirstGameActivity) context).gameOver();
        }
    }

    private void updateTime(long end) {
        if (!this.isGameOver) {
            duration = (end - view.getStartTime()) / 1000;
        }
    }

    private void updateDistance() {
        if (!this.isGameOver) {
            int y = Math.toIntExact(duration);
            distance = y * 10;
        }
    }

    void drawStatistics(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(70);

        //Draw Score
        String s = String.valueOf(this.score);
        String textS = "Score:" + s;
        canvas.drawText(textS, view.getdWidth() / 20,
                view.getdHeight() / 9, paint);

        //Draw Distance
        String dis = String.valueOf(this.distance);
        String textDis = "Distance:" + dis + " m";
        canvas.drawText(textDis, view.getdWidth() / 3,
                view.getdHeight() / 9, paint);
    }

}
