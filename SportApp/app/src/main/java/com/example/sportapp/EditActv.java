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

public class EditActv extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText mNameEditText;

    @BindView(R.id.animeEditText)
    EditText mAnimeEditText;


    @BindView(R.id.descriptionEditText)
    EditText mDescriptionEditText;

    @BindView(R.id.ActvButton)
    Button mCharacterButton;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_actv);
        ButterKnife.bind(this);
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Activite").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Actv actv = dataSnapshot.getValue(Actv.class);

                if (actv.getSport() != null) {
                    mNameEditText.setText(actv.getSport());
                }

                if (actv.getLieu()!= null) {
                    mAnimeEditText.setText(actv.getLieu());
                }

                if (actv.getDescription() != null) {
                    mDescriptionEditText.setText(actv.getDescription());
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditActv.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mCharacterButton.setOnClickListener(v -> {
            mDatabaseReference.child("Sport").setValue(mNameEditText.getText().toString());
            mDatabaseReference.child("Lieu").setValue(mAnimeEditText.getText().toString());
            mDatabaseReference.child("description").setValue(mDescriptionEditText.getText().toString());

            finish();
        });
    }
}