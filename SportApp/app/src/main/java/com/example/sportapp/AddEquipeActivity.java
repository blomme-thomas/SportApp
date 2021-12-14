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

public class AddEquipeActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText mNameEditText;

    @BindView(R.id.animeEditText)
    EditText mAnimeEditText;


    @BindView(R.id.descriptionEditText)
    EditText mDescriptionEditText;

    @BindView(R.id.EquipeButton)
    Button equipeButton;

    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipe);

        ButterKnife.bind(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Equipe");

        equipeButton.setOnClickListener(v -> {
            String nom = mNameEditText.getText().toString();
            String niveau = mAnimeEditText.getText().toString();
            String description = mDescriptionEditText.getText().toString();

            Equipe equipe = new Equipe(nom, niveau, description);
            String id = mDatabaseReference.push().getKey();
            if (id != null) { mDatabaseReference.child(id).setValue(equipe); }

            Intent intent=new Intent(this, EquipeActivity.class);
            startActivity(intent);
        });

    }
}