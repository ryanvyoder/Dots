package com.ryan.mygame;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Game extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

   public void startButtonOnClick(View v){
       Button button = (Button) v;
       //((Button)v).setText("Clicked");

       //Make Fullscreen
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       //switch to game
       setContentView(new GamePanel(this));
   }

    public void closeButtonOnClick(View v){
        //Button button = (Button) v;
        finish();
        System.exit(0);
    }
}
