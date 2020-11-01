package com.example.fingerpainter_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ColourActivity extends AppCompatActivity {

    /**
     * Default colour
     */
    private String colourPicked = "#00000000";

    /**
     * Array of colours available
     */
    private final String[] colourCodes = {"#00000000","#ff000000","#ffaaaaaa","#ffffffff","#ffff4444",
                                    "#ffff8800","#ffffbb33","#ff0099cc","#ff00ddff","#ff99cc00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour);

        Bundle extras = getIntent().getExtras();
        if(extras==null){
            return;
        }
        setCurrentColour(extras.getString("cColour"));
    }


    public void setColour1(View v){
        setCurrentColour(1);
    }
    public void setColour2(View v){
        setCurrentColour(2);
    }
    public void setColour3(View v){
        setCurrentColour(3);
    }
    public void setColour4(View v){
        setCurrentColour(4);
    }
    public void setColour5(View v){
        setCurrentColour(5);
    }
    public void setColour6(View v){
        setCurrentColour(6);
    }
    public void setColour7(View v){
        setCurrentColour(7);
    }
    public void setColour8(View v){
        setCurrentColour(8);
    }
    public void setColour9(View v){
        setCurrentColour(9);
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
     */
    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("colourPicked",colourPicked);
        setResult(RESULT_OK,data);
        super.finish();
    }

    /**
     * Updates UI to indicate the current brush colour, and the colour that is currently being picked
     * @param i index of colourCode array
     */
    public void setCurrentColour(int i){
        Button b = findViewById(R.id.currentColour);
        if(i < colourCodes.length){
            b.setBackgroundColor(Color.parseColor(colourCodes[i]));
            colourPicked = colourCodes[i];
        }
    }

    /**
     * Method overloading
     * Updates UI to indicate the current brush colour, and the colour that is currently being picked
     * @param s the colour code (eg. #FFFFFFFF)
     */
    public void setCurrentColour(String s){
        Button b = findViewById(R.id.currentColour);
        b.setBackgroundColor(Color.parseColor(s));
        colourPicked = s;
    }
}