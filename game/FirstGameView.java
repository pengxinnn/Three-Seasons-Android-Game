package com.example.game;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class FirstGameView extends SurfaceView implements SurfaceHolder.Callback {


    /**
     * The part of the program that manages time.
     */
    private FirstGameThread thread;

    /**
     * The part of the program that scales and draws the background image.
     */
    private Bitmap background;
    private Rect rect;
    private int dWidth, dHeight;  //Device's width and height
    private Point point;

    /**
     * The part of the program that display a as-if running character.
     */
    private Bitmap[] knightsFigure;
    private Bitmap[] elfFigure;

    /**
     * The part of the program that stores game elements.
     */
    private Bitmap monsterBmp;
    private int monsterWidth;
    private int monsterHeight;
    private FirstGameManager manager;
    private long startTime;
    private Context context;
    private int frame; //Used to change movement of the character

    /**
     * The part of the program that keep track of time between each touch on event.
     */
    private long lastJumpTime = 0;
    private long thisJumpTime = 0;
    private long duration = 2000;
    private int choose;

    public FirstGameView(Context context, int choose) {
        super(context);
        getHolder().addCallback(this);
        thread = new FirstGameThread(getHolder(), this);
        setFocusable(true);

        this.choose = choose; //choose figure

        //The background image
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        getScalingRect();

        int figureWidth;
        int figureHeight;
        if (choose == 1) {
            //Images for the running knight figure
            knightsFigure = new Bitmap[2];
            knightsFigure[0] = BitmapFactory.decodeResource(getResources(), R.drawable.knight1);
            knightsFigure[1] = BitmapFactory.decodeResource(getResources(), R.drawable.knight2);
            figureWidth = knightsFigure[0].getWidth();
            figureHeight = knightsFigure[0].getHeight();
        } else {
            //Images for the running elf figure
            elfFigure = new Bitmap[2];
            elfFigure[0] = BitmapFactory.decodeResource(getResources(), R.drawable.elf1);
            elfFigure[1] = BitmapFactory.decodeResource(getResources(), R.drawable.elf2);
            figureWidth = elfFigure[0].getWidth();
            figureHeight = elfFigure[0].getHeight();
        }


        //Image for the monster
        monsterBmp = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle1);
        monsterWidth = monsterBmp.getWidth();
        monsterHeight = monsterBmp.getHeight();

        //Create game elements
        manager = new FirstGameManager(figureWidth, figureHeight, context, this);
        this.context = context;
    }

    private void getScalingRect() {
        //Create a rectangle with the device's width and height for scaling background's image
        //Code reference: https://www.youtube.com/watch?v=3BLESx44GL4
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0, 0, dWidth, dHeight);
    }

    public int getdWidth() {
        return dWidth;
    }

    public int getdHeight() {
        return dHeight;
    }

    public int getMonsterWidth() {
        return monsterWidth;
    }

    public int getMonsterHeight() {
        return monsterHeight;
    }

    public long getStartTime() {
        return startTime;
    }

    public FirstGameManager getManager() {
        return manager;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Draw the background
        canvas.drawBitmap(background, null, rect, null);
        //Draw a as-if running character
        drawRunningCharacter(canvas);

        /*
         * Draw every obstacle that is in the monsters at this moment
         */
        for (int i = 0; i < manager.getMonsters().size(); i++) {
            canvas.drawBitmap(monsterBmp, manager.getMonsters().get(i).x,
                    manager.getMonsters().get(i).y,
                    null);
        }

        manager.drawStatistics(canvas);
    }


    public void update() {

        manager.update();

        if (this.manager.getIsGameOver()) {
            this.thread.setRunning(false);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {  //Tap detected on the screen
            thisJumpTime = System.currentTimeMillis();
            duration = thisJumpTime - lastJumpTime;
            //Player cannot do consecutive jumps between a short period of time.
            if (duration >= 2800) {
                Player.velocity = -65;
            }
        } else if (action == MotionEvent.ACTION_UP) {
            lastJumpTime = System.currentTimeMillis();
        }
        invalidate();
        return true;
    }

    /**
     * A helper method for drawing the running figure.
     */
    private void drawRunningCharacter(Canvas canvas) {
        //Code reference: https://www.youtube.com/watch?v=3BLESx44GL4
        //Change movement every time when draw is called
        if (frame == 0) {
            frame = 1;
        } else {
            frame = 0;
        }

        if (this.choose == 1) {
            drawCharacter(canvas, knightsFigure);
        } else {
            drawCharacter(canvas, elfFigure);
        }
    }

    /**
     * A helper method for drawing the character.
     */
    private void drawCharacter(Canvas canvas, Bitmap[] figures) {
        if (manager.getPlayer().y >= dHeight / 3) {  //prevent knight from falling below the ground
            canvas.drawBitmap(figures[frame], manager.getPlayer().x,
                    dHeight / 3, null);
        } else if (manager.getPlayer().y <= 0) {  //prevent knight from moving outside of the screen
            canvas.drawBitmap(figures[frame], manager.getPlayer().x, 0, null);
        } else {
            canvas.drawBitmap(figures[frame], manager.getPlayer().x,
                    manager.getPlayer().y,
                    null);
        }
    }

}