package com.example.sportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText username, age, email, password;
    private ProgressBar progressBar;
    private Button registerUser, back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        username = (EditText) findViewById(R.id.username);
        age = (EditText) findViewById(R.id.age);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        registerUser = (Button) findViewById(R.id.registerbutton);
        registerUser.setOnClickListener(this);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.back):
                startActivity(new Intent(this, MainActivity.class));
                break;
            case (R.id.registerbutton):
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String usernameText = username.getText().toString().trim();
        String ageText = age.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if (usernameText.isEmpty()){
            username.setError("Entrez un nom");
            username.requestFocus();
            return;
        }

        if (ageText.isEmpty()){
            age.setError("Entrez un age");
            age.requestFocus();
            return;
        }

        if (emailText.isEmpty()){
            email.setError("Entrez un email");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            email.setError("Entrez un email valide");
            email.requestFocus();
        }

        if (passwordText.isEmpty()){
            password.setError("Entrez un mot de passe");
            password.requestFocus();
            return;
        }

        if (passwordText.length() < 7){
            password.setError("Entrez un mot de passe de 8 lettres ou plus");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(usernameText, ageText, emailText);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Register.this, "L'utilisateur est enregistré", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);


                                    }else{
                                        Toast.makeText(Register.this, "L'utilisateur n'est pas enregistré", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(Register.this, "L'utilisateur n'est pas enregistré", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}