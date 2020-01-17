package com.example.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class FirstGameThread extends Thread {

    /**
     * Where the fish tank items are drawn.
     */
    private FirstGameView gameView;
    /**
     * The canvas container.
     */
    private final SurfaceHolder surfaceHolder;
    /**
     * Whether the thread is running.
     */
    private boolean isRunning;
    /**
     * The canvas on which to draw the fish tank.
     */
    public static Canvas canvas;

    /**
     * Construct the thread.
     *
     * @param surfaceHolder the canvas container.
     * @param view          where the fish tank items are drawn.
     */
    public FirstGameThread(SurfaceHolder surfaceHolder, FirstGameView view) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = view;
    }

    @Override
    public void run() {
        while (isRunning) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                this.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}