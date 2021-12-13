package com.example.sportapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailEquipeActivity extends AppCompatActivity {


    @BindView(R.id.nameTextView)
    TextView mNameTextView;

    @BindView(R.id.descriptionTextView)
    TextView mDescriptionTextView;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_actv);

        ButterKnife.bind(this);
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Equipe").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Equipe equipe = dataSnapshot.getValue(Equipe.class);

                if (equipe.getNom() != null) {
                    mNameTextView.setText(equipe.getNom());
                }

                if (equipe.getDescription() != null) {
                    mDescriptionTextView.setText(equipe.getDescription());
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DetailEquipeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}