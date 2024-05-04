package com.example.ilyastp5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Note> notes;
    private Context mContext;

    public NotesAdapter(Context context, ArrayList<Note> list) {
        mContext = context;
        notes = list;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.text.setText(notes.get(position).getDescription());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orientation = mContext.getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    NoteFragment fragment = NoteFragment.newInstance(notes.get(position).getDescription());
                    fragmentManager.beginTransaction()
                            .replace(R.id.allnotes_frame, fragment)
                            .commit();
                } else {
                    mContext.startActivity(new Intent(mContext, NoteActivity.class).putExtra("text", notes.get(position).getDescription()));
                }
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertDialogBuilderLabelDelete = new AlertDialog.Builder(mContext);

                alertDialogBuilderLabelDelete.setTitle("Delete Note");
                alertDialogBuilderLabelDelete.setMessage("Are you sure to delete?");
                alertDialogBuilderLabelDelete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Deleting the note from the database note
                        NotesDB db = new NotesDB(mContext);
                        db.deleteNote(notes.get(position).getId());
                        // refresh
                        notes = db.getAllNotes();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                alertDialogBuilderLabelDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilderLabelDelete.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        CardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            text = itemView.findViewById(R.id.discText);
        }
    }
}
