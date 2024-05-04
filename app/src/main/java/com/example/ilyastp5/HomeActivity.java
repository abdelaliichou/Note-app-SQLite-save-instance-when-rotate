package com.example.ilyastp5;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recycler ;
    NotesAdapter adapter;
    ArrayList<Note> notes;
    private NotesDB dbHelper;

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // setting the layout based on the orientation

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.home_landscape);

             settingFragments();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_home);

            initialisation();
            onClicks();
            settingRecycler();


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        initialisation();
        onClicks();
        settingRecycler();

    }

    private void settingFragments(){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.allnotes_frame, NoteFragment.newInstance("gone"));
        transaction.replace(R.id.note_frame, AllNotesFragment.newInstance());

        // Commit the transaction
        transaction.commit();
    }

    private void settingRecycler(){
        recycler.setLayoutManager(new LinearLayoutManager(this));

        notes = dbHelper.getAllNotes();

        adapter = new NotesAdapter(this, notes);
        recycler.setAdapter(adapter);
    }

    private void initialisation(){
        recycler = findViewById(R.id.recycler);
        fab = findViewById(R.id.floating);
        dbHelper = new NotesDB(this);
    }

    private void onClicks(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(v);
            }
        });
    }

    private void showInputDialog(View anchorView) {

        View dialogView = this.getLayoutInflater().inflate(R.layout.popup_layout, null);

        EditText editText = dialogView.findViewById(R.id.editText);
        Button submitButton = dialogView.findViewById(R.id.button);

        // Create a PopupWindow with match_parent width and wrap_content height
        PopupWindow popupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setElevation(50f);

        // Show the popup window at the bottom of the anchor view
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = editText.getText().toString();

                if (inputText.isEmpty()){
                    Toast.makeText(HomeActivity.this, "Please enter a description!", Toast.LENGTH_SHORT).show();
                } else {
                    // Adding the note to the database
                    Note note = new Note();
                    note.setDescription(inputText);
                    dbHelper.addNote(note);
                    settingRecycler();

                    popupWindow.dismiss();
                }
            }
        });
    }

}