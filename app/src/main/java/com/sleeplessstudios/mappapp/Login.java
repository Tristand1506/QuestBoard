package com.sleeplessstudios.mappapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import UtilLib.AccountManager;

public class Login extends AppCompatActivity {

    String TAG ="Activity_Login";
   private ImageButton login;

   private EditText email;
   private EditText password;

   private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase Init
        mAuth = FirebaseAuth.getInstance();

        //button listener
        email = findViewById(R.id.login_username_txt);
        password = findViewById(R.id.login_password_txt);
        login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Please enter Email & Pasword",Toast.LENGTH_SHORT).show();
                }
                else {
                    LogInAccount(email.getText().toString(), password.getText().toString());
                }

            }
        });
    }

    public void openMapScreen()
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void LogInAccount(String email, String password){
        AccountManager.I().initBindUser();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            openMapScreen();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure, attempting User SignIn", task.getException());
                            Toast.makeText(Login.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}