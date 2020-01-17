package com.example.game;

import android.widget.ImageButton;
import android.content.Context;

import androidx.appcompat.widget.AppCompatImageButton;


/*
Picture objects are the fundamental objects that make up the puzzle.
A puzzle is consists of four pictures, each in one of four positions:
up-left, up-right, down-left, down-right.
*/

class Picture extends AppCompatImageButton {

    /*  ------------------------------- Instance Variables ---------------------------------  */

    // Integer that represents this picture's position
    private int position;

    // The picture that can trigger next step with this picture
    private Picture activePic;

    // The previous ImageButton this picture stored
    private ImageButton prevButton;

    // The ImageButton this picture currently stores
    private ImageButton imageButton;

    // The next ImageButton this picture should store
    private ImageButton nextButton;


    /*  ------------------------------------- Methods -------------------------------------  */

    Picture(Context context) {
        super(context);
    }

    Picture(Context context, ImageButton imageButton) {
        super(context);
        this.imageButton = imageButton;

    }

    /*
    Stores the next ImageButton when game goes to the next step
     */
    void nextStep() {
        this.prevButton = this.imageButton;
        this.imageButton = this.nextButton;
    }

    /* ----------- Getters and Setters for private attributes. ----------- */

    ImageButton getPrevButton() {
        return prevButton;
    }

    ImageButton getImageButton() {
        return imageButton;
    }

    void setImageButton(ImageButton imageButton) {
        this.prevButton = this.imageButton;
        this.imageButton = imageButton;
    }

    int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    Picture getActivePic() {
        return activePic;
    }

    void setActivePic(Picture activePic) {
        this.activePic = activePic;
    }

    void setNextButton(ImageButton nextButton) {
        this.nextButton = nextButton;
    }
}
