package com.example.fingerpainter_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class BrushActivity extends AppCompatActivity {

    private Button button_square;
    private ImageButton button_round;
    private String shape = "round";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);
        button_square = findViewById(R.id.button_square);
        button_round = findViewById(R.id.button_round);
    }

    public void onClickSquare(View v){
        button_square.setBackgroundColor(Color.parseColor("#969CAA"));
        button_round.setBackgroundColor(Color.parseColor("#5F6165"));
        shape = "square";
    }

    public void onClickCircle(View v){
        button_round.setBackgroundColor(Color.parseColor("#969CAA"));
        button_square.setBackgroundColor(Color.parseColor("#5F6165"));
        shape = "round";
    }

    public void onClickDone(View v){
        finish();
    }

    @Override
    public void finish(){
        int width = 10;
        EditText et = findViewById(R.id.widthField);
        if(!et.getText().toString().equals("")){
            width = Integer.parseInt(et.getText().toString());
        }
        if(width<1 || width>500){
            width = 10;
        }
        Intent i = new Intent();
        i.putExtra("shape",shape);
        i.putExtra("width",width);
        setResult(RESULT_OK,i);
        super.finish();
    }
}