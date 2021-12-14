package com.example.sportapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditEquipeActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_edit_actv);
        ButterKnife.bind(this);
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Equipe").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Equipe equipe = dataSnapshot.getValue(Equipe.class);

                if (equipe.getNom() != null) {
                    mNameEditText.setText(equipe.getNom());
                }

                if (equipe.getNiveau()!= null) {
                    mAnimeEditText.setText(equipe.getNiveau());
                }

                if (equipe.getDescription() != null) {
                    mDescriptionEditText.setText(equipe.getDescription());
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditEquipeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        equipeButton.setOnClickListener(v -> {
            mDatabaseReference.child("Nom").setValue(mNameEditText.getText().toString());
            mDatabaseReference.child("Niveau").setValue(mAnimeEditText.getText().toString());
            mDatabaseReference.child("description").setValue(mDescriptionEditText.getText().toString());

            finish();
        });
    }
}