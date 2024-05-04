package com.example.ilyastp5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllNotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllNotesFragment extends Fragment {


    FloatingActionButton fab;
    RecyclerView recycler ;
    NotesAdapter adapter;
    ArrayList<Note> notes;
    private NotesDB dbHelper;


    public AllNotesFragment() {
        // Required empty public constructor
    }

    public static AllNotesFragment newInstance() {
        AllNotesFragment fragment = new AllNotesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_notes, container, false);


        initialisation(view);
        onClicks(view);
        settingRecycler(view);


        return view;
    }

    private void initialisation(View v){
        recycler = v.findViewById(R.id.recycler);
        fab = v.findViewById(R.id.floating);
        dbHelper = new NotesDB(v.getContext());
    }

    private void onClicks(View view){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(v, view);
            }
        });
    }

    private void showInputDialog(View anchorView, View context) {

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
                    Toast.makeText(context.getContext(), "Please enter a description!", Toast.LENGTH_SHORT).show();
                } else {
                    // Adding the note to the database
                    Note note = new Note();
                    note.setDescription(inputText);
                    dbHelper.addNote(note);
                    settingRecycler(context);

                    popupWindow.dismiss();
                }
            }
        });
    }

    private void settingRecycler(View view){
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        notes = dbHelper.getAllNotes();

        adapter = new NotesAdapter(view.getContext(), notes);
        recycler.setAdapter(adapter);
    }

}