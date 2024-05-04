package com.example.ilyastp5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView splashIMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        splashIMG = findViewById(R.id.splashIMG);
        animateScaling(splashIMG);
        goToNextPage();
    }

    private void animateScaling(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.f, 0.5f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000); // Duration in milliseconds
        scaleAnimation.setFillAfter(true);

        // Start the animation
        view.startAnimation(scaleAnimation);
    }

    private void goToNextPage(){
        // Start the main activity after the splash screen duration
        splashIMG.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish(); // Close the splash screen activity
            }
        }, 3000);
    }

}