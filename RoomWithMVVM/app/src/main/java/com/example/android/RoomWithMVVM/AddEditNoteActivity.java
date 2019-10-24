package com.example.android.RoomWithMVVM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    // Static Keys For Intent.
    public static final String EXTRA_ID_KEY = "EXTRA_ID";
    public static final String EXTRA_TITLE_KEY = "EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION_KEY = "EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY_KEY = "EXTRA_PRIORITY";

    // Init Views.
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Get views by IDs.
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        // Set Min Value.
        numberPickerPriority.setMinValue(1);
        // Set Mzx Value.
        numberPickerPriority.setMaxValue(10);

        // Add Close Icon To ActionBar.
        // When Click On it move to main Activity.
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // Get Intent & Store Data Into intent.
        Intent intent = getIntent();

        // Check if intent EXTRA_ID_KEY.
        if (intent.hasExtra(EXTRA_ID_KEY)){
            // setTitle Of ActionBar.
            setTitle("Edit Note");

            // Set Text for Views.
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE_KEY));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION_KEY));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY_KEY, 1));
        }else
            // setTitle Of ActionBar.
            setTitle("Add Note");
    }

    // Allow Menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu, menu);
        return true;
    }

    // When Use Item Of Menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                // Call SaveNote Method.
                SaveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // SaveNote Method.
    private void SaveNote() {

        // Get Text & Values.
        String Title = editTextTitle.getText().toString();
        String Description = editTextDescription.getText().toString();
        int Priority = numberPickerPriority.getValue();

        // If Title Or Description Is Empty.
        if (Title.trim().isEmpty() || Description.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title and description!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Intent.
        Intent data = new Intent();

        // putExtra
        data.putExtra(EXTRA_TITLE_KEY, Title);
        data.putExtra(EXTRA_DESCRIPTION_KEY, Description);
        data.putExtra(EXTRA_PRIORITY_KEY, Priority);

        // Get Id, If There's not Id ,, Id is -1 (Default Value).
        int id = getIntent().getIntExtra(EXTRA_ID_KEY, -1);

        // Check If Id Not Equal -1.
        if (id != -1)
            // PutExtra For Id.
            data.putExtra(EXTRA_ID_KEY, id);

        // set Result.
        setResult(RESULT_OK, data);
        // Close Activity.
        finish();
    }
}