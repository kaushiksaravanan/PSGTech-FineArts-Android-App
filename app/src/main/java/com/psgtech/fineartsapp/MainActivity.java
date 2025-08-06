package com.psgtech.fineartsapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final static int SPLASH_SCREEN = 3000;
    ImageView logo;
    TextView creator;
    Animation topAnim, bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        // load views
        logo = findViewById(R.id.logo);
        creator = findViewById(R.id.creator);

        // set the animation
        logo.setAnimation(topAnim);
        creator.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logo, "logo_image");
                pairs[1] = new Pair<View, String>(creator, "logo_text");
                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, option.toBundle());

                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

}