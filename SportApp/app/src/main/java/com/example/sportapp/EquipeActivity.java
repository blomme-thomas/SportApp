package com.example.sportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class EquipeActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.characterRecyclerView)
    RecyclerView mRecyclerView;
    private User userProfile;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.newFloatingActionButton)
    FloatingActionButton mNewFloatingActionButton;

EquipeAdaptater equipeAdaptater;
    LinearLayoutManager mLayoutManager;

    private ArrayList<Equipe> equipeArrayList;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipe);

        ButterKnife.bind(this);

        equipeArrayList = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Equipe");

        mNewFloatingActionButton.setOnClickListener(v -> {
            Intent intent=new Intent(EquipeActivity.this, AddEquipeActivity.class);
            startActivity(intent);
        });

        Recycler();
    }

    public void Recycler() {

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        equipeAdaptater = new EquipeAdaptater(equipeArrayList);
        mRecyclerView.setAdapter(equipeAdaptater);
        Content();
        deleteSwipe();
    }

    private void Content() {

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                equipeArrayList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Equipe equipe = postSnapshot.getValue(Equipe.class);

                    if (equipe != null) {
                        equipe.setKey(postSnapshot.getKey());
                    }

                    equipeArrayList.add(equipe);

                }
                equipeAdaptater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EquipeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                mDatabaseReference.child(equipeArrayList.get(viewHolder.getAdapterPosition()).getKey()).setValue(null);
                equipeAdaptater.deleteItem(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

}