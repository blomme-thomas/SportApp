package com.example.sportapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Actvvis extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.characterRecyclerView)
    RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.newFloatingActionButton)
    FloatingActionButton mNewFloatingActionButton;

    ActvAdaptater actvAdaptater;

    LinearLayoutManager mLayoutManager;

    private ArrayList<Actv> actvArrayList;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actvvis);

        ButterKnife.bind(this);

        actvArrayList = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Activite");

        mNewFloatingActionButton.setOnClickListener(v -> {
            Intent intent=new Intent(Actvvis.this, AddActv.class);
            startActivity(intent);
        });

        Recycler();
    }

    public void Recycler() {

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        actvAdaptater = new ActvAdaptater(actvArrayList);
        mRecyclerView.setAdapter(actvAdaptater);
        Content();
        deleteSwipe();
    }

    private void Content() {

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                actvArrayList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Actv actv = postSnapshot.getValue(Actv.class);

                    if (actv != null) {
                        actv.setKey(postSnapshot.getKey());
                    }

                    actvArrayList.add(actv);

                }
                actvAdaptater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Actvvis.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteSwipe() {

        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mDatabaseReference.child(actvArrayList.get(viewHolder.getAdapterPosition()).getKey()).setValue(null);
                actvAdaptater.deleteItem(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

}