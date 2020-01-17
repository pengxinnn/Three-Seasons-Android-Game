package com.example.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class ThirdGameActivity extends AppCompatActivity {

    /*  ------------------------------- Instance Variables ---------------------------------  */

    // Game manager that controls the statistics of this game
    private ThirdGameManager thirdGameManager;

    // Four pictures displayed on the screen.
    private Picture picture1;
    private Picture picture2;
    private Picture picture3;
    private Picture picture4;

    // The active picture for step three
    private Picture active;

    // Texts displaying number of moves and score.
    private TextView numMoves;
    private TextView numScore;

    // Constants representing the four possible positions for pictures
    private int UP_LEFT = 1;
    private int UP_RIGHT = 2;
    private int DOWN_LEFT = 3;
    private int DOWN_RIGHT = 4;

    // The minimum length of scrolling to be detected
    private int MIN_MAGNITUDE = 60;

    // The duration of animations
    private int DURATION = 800;

    // Constants for animations to certain positions
    private int POS_LEFT = 415;
    private int POS_RIGHT = 895;
    private int POS_UP = 0;
    private int POS_DOWN = 480;

    // The initial X and Y coordinates of a touch
    private double initX = 0;
    private double initY = 0;


    /*  ------------------------------------- Methods -------------------------------------  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdgame);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        thirdGameManager = new ThirdGameManager(this, this);
        numMoves = findViewById(R.id.numMove);
        numScore = findViewById(R.id.score);

        picture1 = thirdGameManager.getUpLeftPicture();
        picture2 = thirdGameManager.getUpRightPicture();
        picture3 = thirdGameManager.getDownLeftPicture();
        picture4 = thirdGameManager.getDownRightPicture();

        setOnTouchListeners();

    }

    /*
    Set onTouchListener for the four image buttons displaying on the screen
     */
    private void setOnTouchListeners() {
        picture1.getImageButton().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return resolveTouch(picture1, view, motionEvent);
            }
        });

        picture2.getImageButton().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return resolveTouch(picture2, view, motionEvent);
            }
        });

        picture3.getImageButton().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return resolveTouch(picture3, view, motionEvent);
            }
        });

        picture4.getImageButton().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return resolveTouch(picture4, view, motionEvent);
            }
        });
    }

    /*
    Figure out the type of the touch and determine how pictures should behave.
     */
    public boolean resolveTouch(Picture p, View v, MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                initX = e.getX();
                initY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                double finalX = e.getX();
                double finalY = e.getY();
                double deltaX = Math.abs(finalX - initX);
                double deltaY = Math.abs(finalY - initY);
                double moveMagnitude = Math.pow(Math.pow(deltaX, 2) + Math.pow(deltaY, 2), 0.5);

                if (moveMagnitude > MIN_MAGNITUDE) {
                    if (deltaX > 2 * deltaY) {
                        if (finalX - initX > 0) {
                            moveLeftToRight(p);
                        } else {
                            moveRightToLeft(p);
                        }
                    } else if (deltaY > 2 * deltaX) {
                        if (finalY - initY > 0) {
                            moveUpToDown(p);
                        } else {
                            moveDownToUp(p);
                        }
                    }
                }
                break;
        }
        return false;
    }

    /*
    Move picture from left side to right side.
    Figure out if pictures should animate into the next step.
     */
    void moveLeftToRight(Picture p) {

        if (p.getPosition() == UP_LEFT) {

            Picture p_switch = thirdGameManager.getUpRightPicture();

            if (p_switch == p.getActivePic()) { // Animate into next step

                p_switch.nextStep();
                chooseStep(p, p_switch);

                ImageButton b = p.getPrevButton();
                ImageButton b_switch = p_switch.getPrevButton();
                ImageButton new_b = p.getImageButton();
                ImageButton new_b_switch = p_switch.getImageButton();

                buttonAnimationRight(b, 0);
                buttonAnimationAlpha(b, 0, 0);
                buttonAnimationAlpha(b_switch, 0, 0);

                new_b.setX(POS_LEFT);
                new_b.setY(POS_UP);
                new_b_switch.setX(POS_RIGHT);
                new_b_switch.setY(POS_UP);

                buttonAnimationAlpha(new_b, 200, 500);

                new_b_switch.animate().alpha(200).setDuration(2 * DURATION).setStartDelay(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        thirdGameManager.getUpLeftPicture().getPrevButton().setX(0);
                        thirdGameManager.getUpRightPicture().getPrevButton().setX(0);
                        renewElevation();
                        endStep();
                    }
                });


            } else {// Switch two pictures' positions when they cannot trigger the next step

                ImageButton b = p.getImageButton();
                ImageButton b_switch = p_switch.getImageButton();

                buttonAnimationRight(b, 0);
                buttonAnimationLeft(b_switch, 0);

                thirdGameManager.setUpRightPicture(p);
                thirdGameManager.setUpLeftPicture(p_switch);

            }

        } else if (p.getPosition() == DOWN_LEFT) {
            Picture p_switch = thirdGameManager.getDownRightPicture();

            if (p_switch == p.getActivePic()) { // Animate into next step

                p_switch.nextStep();
                chooseStep(p, p_switch);

                ImageButton b = p.getPrevButton();
                ImageButton b_switch = p_switch.getPrevButton();
                ImageButton new_b = p.getImageButton();
                ImageButton new_b_switch = p_switch.getImageButton();

                buttonAnimationRight(b, 0);
                buttonAnimationAlpha(b, 0, 0);
                buttonAnimationAlpha(b_switch, 0, 0);

                new_b.setX(POS_LEFT);
                new_b.setY(POS_DOWN);
                new_b_switch.setX(POS_RIGHT);
                new_b_switch.setY(POS_DOWN);

                buttonAnimationAlpha(new_b, 200, 500);

                new_b_switch.animate().alpha(200).setDuration(2 * DURATION).setStartDelay(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        thirdGameManager.getDownLeftPicture().getPrevButton().setX(0);
                        thirdGameManager.getDownRightPicture().getPrevButton().setX(0);
                        renewElevation();
                        endStep();
                    }
                });

            } else {// Switch two pictures' positions when they cannot trigger the next step

                ImageButton b = p.getImageButton();
                ImageButton b_switch = p_switch.getImageButton();

                buttonAnimationRight(b, 0);
                buttonAnimationLeft(b_switch, 0);

                thirdGameManager.setDownRightPicture(p);
                thirdGameManager.setDownLeftPicture(p_switch);
            }
        }
        addMove();
    }


    /*
        Move picture from right side to left side.
        Figure out if pictures should animate into the next step.
     */
    void moveRightToLeft(Picture p) {
        if (p.getPosition() == UP_RIGHT) {

            Picture p_switch = thirdGameManager.getUpLeftPicture();

            if (p_switch == p.getActivePic()) {// Animate into next step

                p_switch.nextStep();

                chooseStep(p, p_switch);

                ImageButton b = p.getPrevButton();
                ImageButton b_switch = p_switch.getPrevButton();
                ImageButton new_b = p.getImageButton();
                ImageButton new_b_switch = p_switch.getImageButton();

                buttonAnimationLeft(b, 0);
                buttonAnimationAlpha(b, 0, 0);
                buttonAnimationAlpha(b_switch, 0, 0);

                new_b.setX(POS_RIGHT);
                new_b.setY(POS_UP);
                new_b_switch.setX(POS_LEFT);
                new_b_switch.setY(POS_UP);

                buttonAnimationAlpha(new_b, 200, 500);

                new_b_switch.animate().alpha(200).setDuration(2 * DURATION).setStartDelay(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        thirdGameManager.getUpLeftPicture().getPrevButton().setX(0);
                        thirdGameManager.getUpRightPicture().getPrevButton().setX(0);
                        renewElevation();
                        endStep();
                    }
                });
            } else {// Switch two pictures' positions when they cannot trigger the next step

                ImageButton b = p.getImageButton();
                ImageButton b_switch = p_switch.getImageButton();

                buttonAnimationLeft(b, 0);
                buttonAnimationRight(b_switch, 0);

                thirdGameManager.setUpLeftPicture(p);
                thirdGameManager.setUpRightPicture(p_switch);
            }

        } else if (p.getPosition() == DOWN_RIGHT) {

            Picture p_switch = thirdGameManager.getDownLeftPicture();

            if (p_switch == p.getActivePic()) { // Animate into next step

                p_switch.nextStep();
                chooseStep(p, p_switch);

                ImageButton b = p.getPrevButton();
                ImageButton b_switch = p_switch.getPrevButton();
                ImageButton new_b = p.getImageButton();
                ImageButton new_b_switch = p_switch.getImageButton();

                buttonAnimationLeft(b, 0);
                buttonAnimationAlpha(b, 0, 0);
                buttonAnimationAlpha(b_switch, 0, 0);

                new_b.setX(POS_RIGHT);
                new_b.setY(POS_DOWN);
                new_b_switch.setX(POS_LEFT);
                new_b_switch.setY(POS_DOWN);

                buttonAnimationAlpha(new_b, 200, 500);

                new_b_switch.animate().alpha(200).setDuration(2 * DURATION).setStartDelay(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        thirdGameManager.getDownLeftPicture().getPrevButton().setX(0);
                        thirdGameManager.getDownRightPicture().getPrevButton().setX(0);
                        renewElevation();
                        endStep();
                    }
                });

            } else {// Switch two pictures' positions when they cannot trigger the next step

                ImageButton b = p.getImageButton();
                ImageButton b_switch = p_switch.getImageButton();

                buttonAnimationLeft(b, 0);
                buttonAnimationRight(b_switch, 0);

                thirdGameManager.setDownLeftPicture(p);
                thirdGameManager.setDownRightPicture(p_switch);
            }
        }
        addMove();
    }

    /*
    Move picture from up to down.
    Figure out if pictures should animate into the next step.
     */
    void moveUpToDown(Picture p) {
        if (p.getPosition() == UP_LEFT) {

            Picture p_switch = thirdGameManager.getDownLeftPicture();

            if (p_switch == p.getActivePic()) {// Animate into next step

                p_switch.nextStep();
                chooseStep(p, p_switch);

                ImageButton b = p.getPrevButton();
                ImageButton b_switch = p_switch.getPrevButton();
                ImageButton new_b = p.getImageButton();
                ImageButton new_b_switch = p_switch.getImageButton();

                buttonAnimationDown(b, 0);
                buttonAnimationAlpha(b, 0, 0);
                buttonAnimationAlpha(b_switch, 0, 0);

                new_b.setX(POS_LEFT);
                new_b.setY(POS_UP);
                new_b_switch.setX(POS_LEFT);
                new_b_switch.setY(POS_DOWN);

                buttonAnimationAlpha(new_b, 200, 500);

                new_b_switch.animate().alpha(200).setDuration(2 * DURATION).setStartDelay(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        thirdGameManager.getUpLeftPicture().getPrevButton().setX(0);
                        thirdGameManager.getDownLeftPicture().getPrevButton().setX(0);
                        renewElevation();
                        endStep();
                    }
                });

            } else {// Switch two pictures' positions when they cannot trigger the next step

                ImageButton b = p.getImageButton();
                ImageButton b_switch = p_switch.getImageButton();

                buttonAnimationDown(b, 0);
                buttonAnimationUp(b_switch, 0);

                thirdGameManager.setDownLeftPicture(p);
                thirdGameManager.setUpLeftPicture(p_switch);
            }

        } else if (p.getPosition() == UP_RIGHT) {

            Picture p_switch = thirdGameManager.getDownRightPicture();

            if (p_switch == p.getActivePic()) {// Animate into next step

                p_switch.nextStep();

                chooseStep(p, p_switch);

                ImageButton b = p.getPrevButton();
                ImageButton b_switch = p_switch.getPrevButton();
                ImageButton new_b = p.getImageButton();
                ImageButton new_b_switch = p_switch.getImageButton();

                buttonAnimationDown(b, 0);
                buttonAnimationAlpha(b, 0, 0);
                buttonAnimationAlpha(b_switch, 0, 0);

                new_b.setX(POS_RIGHT);
                new_b.setY(POS_UP);
                new_b_switch.setX(POS_RIGHT);
                new_b_switch.setY(POS_DOWN);

                buttonAnimationAlpha(new_b, 200, 500);

                new_b_switch.animate().alpha(200).setDuration(2 * DURATION).setStartDelay(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        thirdGameManager.getUpRightPicture().getPrevButton().setX(0);
                        thirdGameManager.getDownRightPicture().getPrevButton().setX(0);
                        renewElevation();
                        endStep();

                    }
                });

            } else {// Switch two pictures' positions when they cannot trigger the next step

                ImageButton b = p.getImageButton();
                ImageButton b_switch = p_switch.getImageButton();

                buttonAnimationDown(b, 0);
                buttonAnimationUp(b_switch, 0);

                thirdGameManager.setDownRightPicture(p);
                thirdGameManager.setUpRightPicture(p_switch);
            }

        }
        addMove();
    }

    /*
    Move picture from down to up.
    Figure out if pictures should animate into the next step.
     */
    void moveDownToUp(Picture p) {
        if (p.getPosition() == DOWN_LEFT) {

            Picture p_switch = thirdGameManager.getUpLeftPicture();

            if (p_switch == p.getActivePic()) {// Animate into next step

                p_switch.nextStep();
                chooseStep(p, p_switch);

                ImageButton b = p.getPrevButton();
                ImageButton b_switch = p_switch.getPrevButton();
                ImageButton new_b = p.getImageButton();
                ImageButton new_b_switch = p_switch.getImageButton();

                buttonAnimationUp(b, 0);
                buttonAnimationAlpha(b, 0, 0);
                buttonAnimationAlpha(b_switch, 0, 0);

                new_b.setX(POS_LEFT);
                new_b.setY(POS_DOWN);
                new_b_switch.setX(POS_LEFT);
                new_b_switch.setY(POS_UP);

                buttonAnimationAlpha(new_b, 200, 500);

                new_b_switch.animate().alpha(200).setDuration(2 * DURATION).setStartDelay(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        thirdGameManager.getDownLeftPicture().getPrevButton().setX(0);
                        thirdGameManager.getUpLeftPicture().getPrevButton().setX(0);
                        renewElevation();
                        endStep();
                    }
                });


            } else {/// Switch two pictures' positions when they cannot trigger the next step

                ImageButton b = p.getImageButton();
                ImageButton b_switch = p_switch.getImageButton();

                buttonAnimationUp(b, 0);
                buttonAnimationDown(b_switch, 0);

                thirdGameManager.setUpLeftPicture(p);
                thirdGameManager.setDownLeftPicture(p_switch);
            }

        } else if (p.getPosition() == DOWN_RIGHT) {

            Picture p_switch = thirdGameManager.getUpRightPicture();

            if (p_switch == p.getActivePic()) {// Animate into next step

                p_switch.nextStep();

                chooseStep(p, p_switch);


                ImageButton b = p.getPrevButton();
                ImageButton b_switch = p_switch.getPrevButton();
                ImageButton new_b = p.getImageButton();
                ImageButton new_b_switch = p_switch.getImageButton();

                buttonAnimationUp(b, 0);
                buttonAnimationAlpha(b, 0, 0);
                buttonAnimationAlpha(b_switch, 0, 0);

                new_b.setX(POS_RIGHT);
                new_b.setY(POS_DOWN);
                new_b_switch.setX(POS_RIGHT);
                new_b_switch.setY(POS_UP);

                buttonAnimationAlpha(new_b, 200, 500);

                new_b_switch.animate().alpha(200).setDuration(2 * DURATION).setStartDelay(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        thirdGameManager.getDownRightPicture().getPrevButton().setX(0);
                        thirdGameManager.getUpRightPicture().getPrevButton().setX(0);
                        renewElevation();
                        endStep();
                    }
                });

            } else {// Switch two pictures' positions when they cannot trigger the next step

                ImageButton b = p.getImageButton();
                ImageButton b_switch = p_switch.getImageButton();

                buttonAnimationUp(b, 0);
                buttonAnimationDown(b_switch, 0);

                thirdGameManager.setUpRightPicture(p);
                thirdGameManager.setDownRightPicture(p_switch);
            }
        }
        addMove();
    }


    /*
    Renew the game's states when one step is completed
     */
    void endStep() {

        setOnTouchListeners();
        addScore();
        thirdGameManager.addStep();

        if (thirdGameManager.getStep() == 5) {
            Intent EndGameIntent = new Intent(getApplicationContext(), ThirdGameOverActivity.class);
            Bundle b = new Bundle();
            b.putInt("Score", thirdGameManager.getScore());
            b.putInt("Time", thirdGameManager.getFinalTime());
            b.putInt("Moves", thirdGameManager.getNumMoves());
            EndGameIntent.putExtras(b);
            startActivity(EndGameIntent);
        }
    }

    /*
    Update number of moves on screen and reports to the thirdGameManager
     */
    void addMove() {

        thirdGameManager.addNumMoves();

        int move = thirdGameManager.getNumMoves();
        String update_move = "Moves: " + move;
        numMoves.setText(update_move);

    }

    /*
    Update score on screen and reports to the thirdGameManager
     */
    private void addScore() {
        int step = thirdGameManager.getStep();
        int move = thirdGameManager.getNumMoves();
        int add = (int) Math.ceil(10 / move) * step;
        thirdGameManager.addScore(add);
        int score = thirdGameManager.getScore();
        String update_score = "Score: " + score;
        numScore.setText(update_score);
    }

    /*
    Figure out the current step
     */
    void chooseStep(Picture p1, Picture p2) {
        if (thirdGameManager.getStep() == 1) {
            stepOneToTwo(p1, p2);
        } else if (thirdGameManager.getStep() == 2) {
            stepTwoToThree(p1, p2);
        } else if (thirdGameManager.getStep() == 3) {
            stepThreeToFour(p1, p2);
        } else if (thirdGameManager.getStep() == 4) {
            stepFourToFive(p1, p2);
        }
    }

    /*
    Transition between step one and two
     */
    void stepOneToTwo(Picture p1, Picture p2) {
        p1.setImageButton(thirdGameManager.getButton_b3());

        picture3.nextStep();
        ImageButton b = picture3.getPrevButton();
        ImageButton new_b = picture3.getImageButton();
        new_b.setX(b.getX());
        new_b.setY(b.getY());
        buttonAnimationAlpha(b, 0, DURATION);
        new_b.animate().alpha(200).setDuration(DURATION).setStartDelay(DURATION).withEndAction(new Runnable() {
            @Override
            public void run() {
                if (picture3 == thirdGameManager.getUpLeftPicture()) {
                    thirdGameManager.getUpLeftPicture().getPrevButton().setX(0);
                } else if (picture3 == thirdGameManager.getUpRightPicture()) {
                    thirdGameManager.getUpRightPicture().getPrevButton().setX(0);
                } else if (picture3 == thirdGameManager.getDownLeftPicture()) {
                    thirdGameManager.getDownLeftPicture().getPrevButton().setX(0);
                } else if (picture3 == thirdGameManager.getDownRightPicture()) {
                    thirdGameManager.getDownRightPicture().getPrevButton().setX(0);
                }
            }
        });
        p1.setActivePic(null);
        p2.setActivePic(picture3);
        picture3.setActivePic(p2);

        p2.setNextButton(thirdGameManager.getButton4_2());
        picture3.setNextButton(thirdGameManager.getButton4_2());
    }

    /*
    Transition between step two and three
     */
    private void stepTwoToThree(Picture p1, Picture p2) {
        p1.setImageButton(thirdGameManager.getButton3_1());
        picture2.nextStep();

        active = p2;
        ImageButton b = picture2.getPrevButton();
        ImageButton new_b = picture2.getImageButton();
        new_b.setX(b.getX());
        new_b.setY(b.getY());
        buttonAnimationAlpha(b, 0, DURATION);
        new_b.animate().alpha(200).setDuration(DURATION).setStartDelay(DURATION).withEndAction(new Runnable() {
            @Override
            public void run() {
                if (picture2 == thirdGameManager.getUpLeftPicture()) {
                    thirdGameManager.getUpLeftPicture().getPrevButton().setX(0);
                } else if (picture2 == thirdGameManager.getUpRightPicture()) {
                    thirdGameManager.getUpRightPicture().getPrevButton().setX(0);
                } else if (picture2 == thirdGameManager.getDownLeftPicture()) {
                    thirdGameManager.getDownLeftPicture().getPrevButton().setX(0);
                } else if (picture2 == thirdGameManager.getDownRightPicture()) {
                    thirdGameManager.getDownRightPicture().getPrevButton().setX(0);
                }
            }
        });
        p1.setActivePic(picture2);
        picture2.setActivePic(p1);

        p1.setNextButton(thirdGameManager.getButton4_1());
        picture2.setNextButton(thirdGameManager.getButton4_1());
    }

    /*
    Transition between step three and four
     */
    private void stepThreeToFour(Picture p1, Picture p2) {
        p1.setImageButton((ImageButton) findViewById(R.id.blank4));
        p2.setActivePic(active);
        active.setActivePic(p2);

        p2.setNextButton(thirdGameManager.getButton5_1());
        active.setNextButton(thirdGameManager.getButton5_1());
    }

    /*
    Transition between step four and five
     */
    private void stepFourToFive(Picture p1, Picture p2) {
        p1.setImageButton((ImageButton) findViewById(R.id.blank5));
    }

    /*
    Set elevation of pictures currently displaying
     */
    private void renewElevation() {
        picture1.getImageButton().setElevation(1);
        picture2.getImageButton().setElevation(1);
        picture3.getImageButton().setElevation(1);
        picture4.getImageButton().setElevation(1);
    }

    /* --- Animations for the game--- */

    void buttonAnimationRight(ImageButton b, int delay) {
        b.animate().x(POS_RIGHT).setDuration(DURATION).setStartDelay(delay);
    }

    void buttonAnimationLeft(ImageButton b, int delay) {
        b.animate().x(POS_LEFT).setDuration(DURATION).setStartDelay(delay);
    }

    void buttonAnimationDown(ImageButton b, int delay) {
        b.animate().y(POS_DOWN).setDuration(DURATION).setStartDelay(delay);
    }

    void buttonAnimationUp(ImageButton b, int delay) {
        b.animate().y(POS_UP).setDuration(DURATION).setStartDelay(delay);
    }

    void buttonAnimationAlpha(ImageButton b, int alpha, int delay) {
        b.animate().alpha(alpha).setDuration(2 * DURATION).setStartDelay(delay);
    }

}

