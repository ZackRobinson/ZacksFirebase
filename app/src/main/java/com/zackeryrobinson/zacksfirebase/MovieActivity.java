package com.zackeryrobinson.zacksfirebase;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zackeryrobinson.zacksfirebase.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zack on 9/21/2017.
 */

public class MovieActivity extends AppCompatActivity{

    private static final String TAG = "MovieActivityTag";
    private String uid;
    private FirebaseDatabase database;
    private DatabaseReference myRefUsers;
    private EditText etName;
    private EditText etYear;
    private EditText etDirector;
    private EditText etLeadActor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        etName = (EditText) findViewById(R.id.etName);
        etYear = (EditText) findViewById(R.id.etYear);
        etDirector = (EditText) findViewById(R.id.etDirector);
        etLeadActor = (EditText) findViewById(R.id.etLeadActor);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uid = user.getUid();
        }

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRefUsers = database.getReference("users");

    }

    public void getMovies(View view){
        final List<Movie> movies = new ArrayList<>();

        myRefUsers = database.getReference("users");
        // Read from the database
        myRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //This method is called once with the initial value and again
                //whenever data at this location is updated.
                boolean hasChildren = dataSnapshot.child(uid)
                        .child("movies")
                        .hasChildren();

                if(hasChildren){
                    Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());

                    for(DataSnapshot snapshot : dataSnapshot
                            .child(uid)
                            .child("movies")
                            .getChildren()){
                        Movie movie = snapshot.getValue(Movie.class);
                        Log.d(TAG, "onDataChange: " + movie.toString());
                        movies.add(movie);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addMovie(View view){
        String name = etName.getText().toString();
        String year = etYear.getText().toString();
        String director = etDirector.getText().toString();
        String leadActor = etLeadActor.getText().toString();

        Movie movie = new Movie(name, year, director, leadActor);

        myRefUsers
                .child(uid)
                .child("movies")
                .push()
                .setValue(movie)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MovieActivity.this, "Movie was added", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MovieActivity.this, "Movie wasn't added", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });

    }
}
