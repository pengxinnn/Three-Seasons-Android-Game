package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Customization extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.knight:
                Intent knight = new Intent(getApplicationContext(), FirstGameActivity.class);
                Bundle b = new Bundle();
                b.putInt("knight", 1);
                knight.putExtras(b);
                startActivity(knight);
                break;

            case R.id.elf:
                Intent elf = new Intent(getApplicationContext(), FirstGameActivity.class);
                Bundle c = new Bundle();
                c.putInt("elf", 0);
                elf.putExtras(c);
                startActivity(elf);
                break;
        }
    }
}
