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
    /**
     * Default brush width
     */
    private int currentWidth = 20;
    /**
     * Default brush shape
     */
    private String currentShape = "round";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        button_square = findViewById(R.id.button_square);
        button_round = findViewById(R.id.button_round);

        // retrieve current width and shape, to handle the case when user did not input data
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            return;
        }
        currentWidth = extras.getInt("currentWidth");
        currentShape = extras.getString("currentShape");
    }

    /**
     * onClick method for square button, and updating UI to indicate selection
     * @param v button
     */
    public void onClickSquare(View v){
        button_square.setBackgroundColor(Color.parseColor("#969CAA"));
        button_round.setBackgroundColor(Color.parseColor("#5F6165"));
        currentShape = "square";
    }

    /**
     * onClick method for round button, and updating UI to indicate selection
     * @param v button
     */
    public void onClickCircle(View v){
        button_round.setBackgroundColor(Color.parseColor("#969CAA"));
        button_square.setBackgroundColor(Color.parseColor("#5F6165"));
        currentShape = "round";
    }

    /**
     * onClick method to indicate selection complete
     * @param v button
     */
    public void onClickDone(View v){
        finish();
    }

    /**
     * send data back to the activity that called it
     * handles the minimum and maximum size allowed for the brush
     */
    @Override
    public void finish(){
        int width = currentWidth;
        EditText et = findViewById(R.id.widthField);

        //only update the width if user inputs the data
        if(!et.getText().toString().equals("")){
            width = Integer.parseInt(et.getText().toString());
            // only width of [1-500) accepted
            if(width<1 || width>500){
                width = 20;
            }
        }

        Intent i = new Intent();
        i.putExtra("shape",currentShape);
        i.putExtra("width",width);
        setResult(RESULT_OK,i);
        super.finish();
    }
}