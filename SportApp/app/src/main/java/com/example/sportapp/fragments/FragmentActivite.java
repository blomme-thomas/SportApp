package com.example.sportapp.fragments;

import android.annotation.SuppressLint;
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

import com.example.sportapp.Actv;
import com.example.sportapp.ActvAdaptater;
import com.example.sportapp.Actvvis;
import com.example.sportapp.AddActv;
import com.example.sportapp.R;
import com.example.sportapp.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentActivite extends Fragment {


    RecyclerView mRecyclerView;
    private User userProfile;

    FloatingActionButton mNewFloatingActionButton;

    ActvAdaptater actvAdaptater;

    LinearLayoutManager mLayoutManager;

    private ArrayList<Actv> actvArrayList;

    private DatabaseReference mDatabaseReference;


    public FragmentActivite() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activite, container, false);

        ButterKnife.bind(view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.characterRecyclerView);
        mNewFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.newFloatingActionButton);


        actvArrayList = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Activite");

        mNewFloatingActionButton.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), AddActv.class);
            startActivity(intent);
        });

        Recycler();
        return view;
    }

    public void Recycler() {

        mLayoutManager = new LinearLayoutManager(getActivity());
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
                mDatabaseReference.child(actvArrayList.get(viewHolder.getAdapterPosition()).getKey()).setValue(null);
                actvAdaptater.deleteItem(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}