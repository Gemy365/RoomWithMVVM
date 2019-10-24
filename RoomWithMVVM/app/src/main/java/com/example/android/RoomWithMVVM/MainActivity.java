package com.example.android.RoomWithMVVM;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chootdev.recycleclick.RecycleClick;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Unique Vars for requests.
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    // viewModel.
    private NoteViewModel noteViewModel;

    // recyclerView.
    private RecyclerView recyclerView;

    // recyclerViewAdapter.
    private NoteRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button.
        FloatingActionButton buttonAddNote = findViewById(R.id.btn_add_note);

        // When click On Button.
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Goto AddEditNoteActivity.
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);

                // startActivityForResult Work with onActivityResult Override Method.
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        // get recyclerView by ID.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Make it LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Same Size.
        recyclerView.setHasFixedSize(true);

        // Adapter
        adapter = new NoteRecyclerViewAdapter();

        // setAdapter
        recyclerView.setAdapter(adapter);

        // Observe NoteViewModel.
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                // Update RecyclerView.
                adapter.setNotes(notes);
            }
        });

        /**   Gradle Lib Allowable Click Item Of Recyclerview.
         *     implementation 'com.chootdev:recycleclick:1.0.0'
         *     RecycleClick.addTo(YOUR_RECYCLEVIEW).setOnItemClickListener()
         */
        RecycleClick.addTo(recyclerView).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                // Get Item By Position And Cast It Into Adapter Class [DrinksDay Getter & Setter].
                // Get list [ArrayList] by Position.
                Note note = NoteRecyclerViewAdapter.notes.get(i);

                // GoTo UpdateDeleteData Group.
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                // putExtra Some Values With Unique Keys To Use Them Into onActivityResult Override Method.
                intent.putExtra(AddEditNoteActivity.EXTRA_ID_KEY, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE_KEY, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION_KEY, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY_KEY, note.getPriority());
                // startActivityForResult Work with onActivityResult Override Method.
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        // When Swipe item to LEFT or RIGHT.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Delete Note.
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));

                // Remove note from ArrayList.
                NoteRecyclerViewAdapter.notes.remove(viewHolder.getAdapterPosition());

                Toast.makeText(MainActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();
            }
            // Active with recyclerView.
        }).attachToRecyclerView(recyclerView);
    }

    // onActivityResult work with Intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check If Request is ADD_NOTE_REQUEST.
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            // Get Extras From Intent.
            String Title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE_KEY);
            String Description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION_KEY);
            int Priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY_KEY, 1);

            // Send Data Into Constructor of Note.
            Note note = new Note(Title, Description, Priority);

            // Make Insert To Data.
            noteViewModel.insert(note);

            // Message.
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();

            // Check If Request is EDIT_NOTE_REQUEST.
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            // Get Id Of This Note.
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID_KEY, -1);

            // if id == -1 that means Note can't be updated!
            if (id == -1){
                // Message.
                Toast.makeText(this, "Note can't be updated!", Toast.LENGTH_SHORT).show();

                //close.
                return;
            }
            // Otherwise.

            // Get Extras From Intent.
            String Title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE_KEY);
            String Description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION_KEY);
            int Priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY_KEY, 1);

            // Send Data Into Constructor of Note.
            Note note = new Note(Title, Description, Priority);
            // setID.
            note.setId(id);
            // Make Update To Data.
            noteViewModel.update(note);

            // Message.
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        }

        else
            // Message.
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show();

    }

    // Allow Menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // When Use Item Of Menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_note_menu:
                // Call deleteAllNotes() Method.
                noteViewModel.deleteAllNotes();
                Toast.makeText(MainActivity.this, "All notes are deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}