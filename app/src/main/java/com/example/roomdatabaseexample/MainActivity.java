package com.example.roomdatabaseexample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomdatabaseexample.database.NoteDatabase;
import com.example.roomdatabaseexample.entities.Note;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Weak reference need it to get context and use it on the Asyncktask
        final WeakReference<Context> contextWeakReference = new WeakReference<>(getApplicationContext());

        Button btnAddNote = findViewById(R.id.btnAddNote);
        Button btnGetAllNotes = findViewById(R.id.btnGetAllNotes);
        Button btnGetNoteId = findViewById(R.id.btnGetIdNote);
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        Button btnDeleteNoteId = findViewById(R.id.btnDeleteNoteId);
        Button btnUpdateNote = findViewById(R.id.btnUpdateNote);

        //Inialize Database with dummy values
        initDatabase(contextWeakReference);


        btnGetAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetAllNotes getAllNotes = new GetAllNotes(contextWeakReference);
                getAllNotes.execute();

            }
        });

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNote addNote = new AddNote(contextWeakReference);
                Note newNote = new Note("Some note");
                addNote.execute(newNote);
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAllNotes deleteAllNotes = new DeleteAllNotes(contextWeakReference);
                deleteAllNotes.execute();
            }
        });

        btnGetNoteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSingleNote getSingleNote = new GetSingleNote(contextWeakReference);
                getSingleNote.execute(1);
            }
        });

        btnDeleteNoteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleleteNoteId deleleteNoteId = new DeleleteNoteId(contextWeakReference);
                deleleteNoteId.execute(5);
            }
        });


        btnUpdateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateNote updateNote = new UpdateNote(contextWeakReference);
                //Note noteToUpdate = new Note("Updated note");
                updateNote.execute("1", "NOTE UPDATED");
            }
        });


    }

    //Method to create some hardcoded dummy data
    private void initDatabase(WeakReference<Context> contextRef) {
        AddNote addNote1 = new AddNote(contextRef);
        AddNote addNote2 = new AddNote(contextRef);
        AddNote addNote3 = new AddNote(contextRef);
        AddNote addNote4 = new AddNote(contextRef);
        AddNote addNote5 = new AddNote(contextRef);


        addNote1.execute(new Note("Todo 1"));
        addNote2.execute(new Note("Todo 2"));
        addNote3.execute(new Note("Todo 3"));
        addNote4.execute(new Note("Todo 4"));
        addNote5.execute(new Note("Todo 5"));

    }

//-----------------------------------Asyncktasks to run ROOM operations---------------------------//
    //region Asyncktasks
    //Getting all stored Notes
    public class GetAllNotes extends AsyncTask<Void, Void, Void> {

        //AsyncTask shouldn't retain a strong reference to the activity context
        private WeakReference<Context> contextRef;

        public GetAllNotes(WeakReference<Context> contextRef) {
            this.contextRef = contextRef;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Context context = contextRef.get();
            System.out.println("List of notes = " + NoteDatabase.getInstance(context).NoteDao().getAllNotes());

            //This is not necessary but allows to use Toast in background task
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Check console to see all notes", Toast.LENGTH_SHORT).show();
                }
            });

            return null;
        }
    }
//------------------------------//Adding new Note//-------------------------------------------//

    public class AddNote extends AsyncTask<Note, Void, Void> {
        //AsyncTask shouldn't retain a strong reference to the activity context
        private WeakReference<Context> contextRef;

        public AddNote(WeakReference<Context> contextRef) {
            this.contextRef = contextRef;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            Context context = contextRef.get();
            NoteDatabase.getInstance(context).NoteDao().addNote(notes[0]);
            return null;
        }

    }

    //------------------------------//Deleting All Notes//--------------------------------------//

    public class DeleteAllNotes extends AsyncTask<Void, Void, Void> {
        //AsyncTask shouldn't retain a strong reference to the activity context
        private WeakReference<Context> contextRef;

        public DeleteAllNotes(WeakReference<Context> contextRef) {
            this.contextRef = contextRef;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Context context = contextRef.get();
            NoteDatabase.getInstance(context).NoteDao().deleteAll();
            //This is not necessary but allows to use Toast in background task
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "All Notes were deleted, check console", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }


    //------------------------------//Get specific Note by id//--------------------------------------//

    public class GetSingleNote extends AsyncTask<Integer, Void, Void> {
        //AsyncTask shouldn't retain a strong reference to the activity context
        private WeakReference<Context> contextRef;

        public GetSingleNote(WeakReference<Context> contextRef) {
            this.contextRef = contextRef;
        }

        @Override
        protected Void doInBackground(final Integer... integers) {
            Context context = contextRef.get();
            //This is not necessary but allows to use Toast in background task
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Check console to see note id: " + integers[0], Toast.LENGTH_SHORT).show();
                }
            });
            System.out.println("Note Selected = " + NoteDatabase.getInstance(context).NoteDao().getNote(integers[0]));
            return null;
        }
    }

    //------------------------------//Delete specific Note by id//--------------------------------------//

    public class DeleleteNoteId extends AsyncTask<Integer, Void, Void> {
        //AsyncTask shouldn't retain a strong reference to the activity context
        private WeakReference<Context> contextRef;

        public DeleleteNoteId(WeakReference<Context> contextRef) {
            this.contextRef = contextRef;
        }

        @Override
        protected Void doInBackground(final Integer... integers) {
            Context context = contextRef.get();
            NoteDatabase.getInstance(context).NoteDao().deleteNoteId(5);
            //This is not necessary but allows to use Toast in background task
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Note with id: " + integers[0] + " Was deleted", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }


    //------------------------------//Update specific Note by id//--------------------------------------//

    public class UpdateNote extends AsyncTask<String, Void, Void> {
        //AsyncTask shouldn't retain a strong reference to the activity context
        private WeakReference<Context> contextRef;

        public UpdateNote(WeakReference<Context> contextRef) {
            this.contextRef = contextRef;
        }

        @Override
        protected Void doInBackground(final String... notes) {
            Context context = contextRef.get();
            NoteDatabase.getInstance(context).NoteDao().updateNote(notes[0], notes[1]);
            //This is not necessary but allows to use Toast in background task
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Note with id: " + notes[0] + " Was updated", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }
    //endregion


}