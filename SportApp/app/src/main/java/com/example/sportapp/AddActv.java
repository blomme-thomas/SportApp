package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActv extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText mNameEditText;

    @BindView(R.id.animeEditText)
    EditText mAnimeEditText;


    @BindView(R.id.descriptionEditText)
    EditText mDescriptionEditText;

    @BindView(R.id.ActvButton)
    Button actvButton;

    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_actv);

        ButterKnife.bind(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Activite");

        actvButton.setOnClickListener(v -> {
            String sport = mNameEditText.getText().toString();
            String lieu = mAnimeEditText.getText().toString();
            String description = mDescriptionEditText.getText().toString();

            Actv actv = new Actv(sport, lieu, description);
            String id = mDatabaseReference.push().getKey();
            if (id != null) { mDatabaseReference.child(id).setValue(actv); }

            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
}