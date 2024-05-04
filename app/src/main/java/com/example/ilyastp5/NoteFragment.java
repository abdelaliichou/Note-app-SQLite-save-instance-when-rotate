package com.example.ilyastp5;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NoteFragment extends Fragment {

    private static final String ARG_PARAM1 = "text";
    private String noteText;

    TextView description;
    MediaPlayer mediaPlayer;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(String param1) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteText = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        if (noteText.equals("gone")) {
            view.setVisibility(View.GONE);
            return null;
        }

        settingData(view);
        settingAUDIO(view);


        return view;
    }

    private void settingData(View view){
        description = view.findViewById(R.id.discText);

        // getting data from previous screen
        if (noteText != null){
            description.setText(noteText);
        }
    }

    private void settingAUDIO(View view){
        mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.music);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (noteText.equals("gone")) {
            return;
        }

        // Start playing the audio when the activity is resumed
        // Check if mediaPlayer is not null and is in the prepared state before starting
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (noteText.equals("gone")) {
            return;
        }

        stopMediaPlayer();

    }

    private void stopMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}