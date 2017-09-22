package com.zackeryrobinson.zacksfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTag";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in" + user.getUid());
                    Intent intent = new Intent(MainActivity.this,MovieActivity.class);
                    startActivity(intent);
                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

    }


    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){

            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    public void usingFirebase(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        switch (view.getId()){
            case R.id.btnSignIn:
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(MainActivity.this, "Succesful",
                                            Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                                startActivity(intent);
                                // ...
                            }
                        });
                break;

            case R.id.btnSignUp:
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Failed",
                                            Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(MainActivity.this, "User Created",
                                            Toast.LENGTH_LONG).show();

                            }
                        });
                break;

        }


    }
}
