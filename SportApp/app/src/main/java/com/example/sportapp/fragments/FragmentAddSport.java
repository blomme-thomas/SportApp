package com.example.sportapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sportapp.R;
import com.example.sportapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragmentAddSport extends Fragment {

    private Button addSport;
    private User userProfile;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private EditText sport,level;

    public FragmentAddSport(User user_) {
        this.userProfile = user_;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_sport, container, false);

        sport = (EditText) view.findViewById(R.id.sport);
        level = (EditText) view.findViewById(R.id.level);
        addSport = (Button) view.findViewById(R.id.addSport);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sportText = sport.getText().toString().trim();
                String levelText = level.getText().toString().trim();

                if (sportText.isEmpty()){
                    sport.setError("Entrez un sport");
                    sport.requestFocus();
                    return;
                }

                if (levelText.isEmpty()){
                    level.setError("Entrez un niveau");
                    level.requestFocus();
                    return;
                }

                userProfile.sports.add(sportText);
                userProfile.levels.add(levelText);

                reference.child(userID).child("sports").setValue(userProfile.sports);
                reference.child(userID).child("levels").setValue(userProfile.levels);

                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                Fragment ffrag = new FragmentProfil(userProfile);
                ftran.replace(R.id.fl_wrapper, ffrag);
                ftran.commit();
            }
        });

        return view;
    }
}