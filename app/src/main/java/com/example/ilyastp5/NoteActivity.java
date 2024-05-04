package com.example.ilyastp5;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NoteActivity extends AppCompatActivity {

    TextView description;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        settingData();
        settingAUDIO();
    }

    private void settingData(){
        description = findViewById(R.id.discText);

        // getting data from previose screen
        String data = getIntent().getStringExtra("text");
        description.setText(data);
    }

    private void settingAUDIO(){
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start playing the audio when the activity is resumed
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause and release the MediaPlayer when the activity is paused
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }
}