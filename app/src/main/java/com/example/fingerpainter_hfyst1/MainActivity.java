package com.example.fingerpainter_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int request_colour_code = 5;
    private static final int request_brush_code = 6;
    private String currentColour = "#ff000000";
    FingerPainterView myFingerPainterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFingerPainterView = new FingerPainterView(this);
        myFingerPainterView.setId(R.id.myFingerPainterViewId);

    }

    public void importImage(View v){

    }

    public void startBrushActivity(View v){
        Intent i = new Intent(this,BrushActivity.class);
        startActivityForResult(i,request_brush_code);

    }

    public void startColourActivity(View v){
        Intent i = new Intent(this,ColourActivity.class);
        i.putExtra("cColour",currentColour);
        startActivityForResult(i, request_colour_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== request_colour_code && resultCode==RESULT_OK){
            String colourPicked = data.getExtras().getString("colourPicked","#ff000000");
            if (colourPicked != null){
                myFingerPainterView.setColour(Color.parseColor(colourPicked));
                currentColour = colourPicked;
            }

        }else if(requestCode==request_brush_code && resultCode == RESULT_OK){
            String shape = data.getExtras().getString("shape","round");
            if(!shape.equals("round")){
                myFingerPainterView.setBrush(Paint.Cap.SQUARE);
            }else{
                myFingerPainterView.setBrush(Paint.Cap.ROUND);
            }
            int width = data.getExtras().getInt("width",20);
            if(width!=20){
                myFingerPainterView.setBrushWidth(width);
            }
        }//end elif
    }//end onresult
}