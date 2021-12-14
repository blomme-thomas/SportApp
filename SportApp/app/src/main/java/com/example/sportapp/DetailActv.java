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

public class DetailActv extends AppCompatActivity {


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

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Activite").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Actv actv = dataSnapshot.getValue(Actv.class);

                if (actv.getSport() != null) {
                    mNameTextView.setText(actv.getSport());
                }

                if (actv.getDescription() != null) {
                    mDescriptionTextView.setText(actv.getDescription());
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DetailActv.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}