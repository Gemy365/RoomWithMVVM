package com.example.android.RoomWithMVVM;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// NoteRecyclerViewAdapter class extends RecyclerView.Adapter<This Class.InternalClassIntoThisClass>{
public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder> {

    // init.
    static List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Connect Class With item_get_data Xml.
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        // Call Internal Class & Send view As Param To Constructor.
        return new NoteViewHolder(viewItem);
    }

    // Method To Active Views That Into MyViewHolder Internal Class.
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Note currentNote = notes.get(position);

        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));

    }

    // Method Return Size of Repeated Data.
    @Override
    public int getItemCount() {
        return notes.size();
    }

    // setNotes Method.
    public void setNotes(List<Note> note){
        notes = note;

        // Observe to any change.
        notifyDataSetChanged();
    }

    // Get Note By Its Position.
    public Note getNoteAt(int position){
        return notes.get(position);
    }

    // NoteViewHolder Internal Class extends RecyclerView.ViewHolder.
    class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
