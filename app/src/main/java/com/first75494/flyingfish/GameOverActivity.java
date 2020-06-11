package com.first75494.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button startGameAgain;
    private TextView displayScore;
    private  String Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Score = getIntent().getExtras().get("Score").toString(); //getting the score from FlyingFish.java putExtra() method

        startGameAgain = (Button) findViewById(R.id.play_again_btn);
        displayScore = (TextView) findViewById(R.id.displayScore);

        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main=new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(main);
            }
        });

        displayScore.setText("Score : "+Score);
    }
}
