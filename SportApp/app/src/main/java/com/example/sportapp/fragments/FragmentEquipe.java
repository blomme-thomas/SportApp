package com.example.sportapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sportapp.AddEquipeActivity;
import com.example.sportapp.Equipe;
import com.example.sportapp.EquipeAdaptater;
import com.example.sportapp.R;
import com.example.sportapp.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class FragmentEquipe extends Fragment {


    private RecyclerView mRecyclerView;
    private User userProfile;


    private FloatingActionButton mNewFloatingActionButton;

    private EquipeAdaptater equipeAdaptater;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<Equipe> equipeArrayList;

    private DatabaseReference mDatabaseReference;

    public FragmentEquipe() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipe, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.characterRecyclerView);
        mNewFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.newFloatingActionButton);

        ButterKnife.bind(getActivity());

        equipeArrayList = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Equipe");

        mNewFloatingActionButton.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), AddEquipeActivity.class);
            startActivity(intent);
        });

        Recycler();

        return view;
    }

    public void Recycler() {

        mLayoutManager = new LinearLayoutManager(getActivity());
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
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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