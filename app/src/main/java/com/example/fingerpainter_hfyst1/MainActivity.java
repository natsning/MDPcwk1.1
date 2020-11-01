package com.example.fingerpainter_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int request_colour_code = 5;
    private static final int request_brush_code = 6;
    private static final int request_photo_code = 7;

    private FingerPainterView myFingerPainterView;

    /**
     * default colour
     */
    private String currentColour = "#ff000000";
    /**
     * default brush width
     */
    private int currentWidth = 20;
    /**
     * default brush shape
     */
    private String currentShape = "round";
    /**
     * Uri for file image
     */
    private Uri uri;

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //if activity is restarted due to state, retain the old settings for brush colour, size and shape
        if(savedInstanceState != null){
            loadBrush(currentShape,currentWidth);
            loadColour(currentColour);
        }

        setContentView(R.layout.activity_main);

        myFingerPainterView = new FingerPainterView(this);
        myFingerPainterView.setId(R.id.myFingerPainterViewId);
        FrameLayout myFrameLayout = findViewById(R.id.myFrameLayout);
        myFrameLayout.addView(myFingerPainterView);

        // handles IMPLICIT intent
        Intent i = getIntent();
        uri = i.getData();
        if(Intent.ACTION_VIEW.equals(i.getAction()) && i.getType() != null){
            if (i.getType().startsWith("image/")) {
                loadImage(uri);
            }
        }

    }

    /**
     * onClick method for palette icon.
     * Creates a dialogue intent to choose colour of brush.
     * @param v button
     */
    public void startColourActivity(View v){
        Intent i = new Intent(this,ColourActivity.class);
        i.putExtra("cColour",currentColour);
        startActivityForResult(i, request_colour_code);
    }

    /**
     * onClick method for brush icon.
     * Creates a dialogue intent to choose brush size and brush shape.
     * @param v button
     */
    public void startBrushActivity(View v){
        Intent i = new Intent(this,BrushActivity.class);
        i.putExtra("currentWidth",currentWidth);
        i.putExtra("currentShape",currentShape);
        startActivityForResult(i,request_brush_code);

    }

    /**
     * OnClick method for import image button.
     * Creates an intent to load image from external files of the device.
     * @param v button
     */
    public void importImage(View v){
        Intent i = new Intent(Intent.ACTION_PICK);

        File path = new File(Environment.getExternalStorageState(),"Downloads/");
        String pathString = path.getPath();
        Uri data = Uri.parse(pathString);
        i.setDataAndType(data,"image/*");
        startActivityForResult(i,request_photo_code);
    }

    /**
     * Handles the data the intents have returned
     * @param requestCode code to differentiate intents
     * @param resultCode code to check if the result returned is usable
     * @param data data returned by the intents
     * @see MainActivity#loadColour(String)
     * @see MainActivity#loadBrush(String, int)
     * @see MainActivity#loadImage(Uri)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== request_colour_code && resultCode==RESULT_OK){
            String colourPicked = data.getExtras().getString("colourPicked","#ff000000");
            loadColour(colourPicked);

        }else if(requestCode==request_brush_code && resultCode == RESULT_OK){
            String shape = data.getExtras().getString("shape", "round");
            int width = data.getExtras().getInt("width",currentWidth);
           loadBrush(shape,width);

        }else if(requestCode==request_photo_code && resultCode == RESULT_OK){
            loadImage(data.getData());

        }

    }

    /**
     * Loads image to the canvas by using the file path indicated and setting it to drawable.
     * @param uri file path of the image
     * @see FingerPainterView#load(Uri)
     * @see FingerPainterView#setBackground(Drawable)
     */
    private void loadImage(Uri uri){
        try{
            myFingerPainterView.load(uri);
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Drawable drawable = Drawable.createFromStream(inputStream, uri.toString() );
            myFingerPainterView.setBackground(drawable);
        }catch (IOException e ){
            Toast.makeText(getApplicationContext(),"Picture Unavailable",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets the colour of the brush to the colour picked
     * @param colourPicked colour code (in hex format eg. #FFFFFFFF) of the picked colour
     * @see ColourActivity#finish()
     */
    private void loadColour(String colourPicked){
        if (colourPicked != null){
            myFingerPainterView.setColour(Color.parseColor(colourPicked));
            currentColour = colourPicked;
        }
    }

    /**
     * Sets the shape and width of the brush
     * @param shape String "round" or "square"
     * @param width integers to represent width in pixels, [1-500) only
     * @see BrushActivity#finish()
     */
    private void loadBrush(String shape,int width){
        if(!shape.isEmpty()){
            if(shape.equals("round")){
                myFingerPainterView.setBrush(Paint.Cap.ROUND);
                currentShape = "round";
            }else{
                myFingerPainterView.setBrush(Paint.Cap.SQUARE);
                currentShape = "square";
            }
        }

        // only change the width if it's different from the current width
        if(width!=currentWidth){
            myFingerPainterView.setBrushWidth(width);
            currentWidth = width;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("currentColor",currentColour);
        savedInstanceState.putString("currentShape",currentShape);
        savedInstanceState.putInt("currentWidth",currentWidth);
        super.onSaveInstanceState(savedInstanceState);
    }
}