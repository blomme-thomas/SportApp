package com.example.sportapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sportapp.HomeActivity;
import com.example.sportapp.R;
import com.example.sportapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class FragmentProfil extends Fragment {

    private TextView username, age;
    private ListView sports;
    private Button addSport;

    private ArrayList<String> sportItems;
    private ArrayList<String> levelItems;
    private ArrayList<String> sportLevelItems;


    public User userProfile;

    ArrayAdapter<String> adapter;


    public FragmentProfil(User user_) {
        this.userProfile = user_;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        // Inflate the layout for this fragment
        username = (TextView) view.findViewById(R.id.username);
        age = (TextView) view.findViewById(R.id.age);
        sports = (ListView) view.findViewById(R.id.sports);
        addSport = (Button) view.findViewById(R.id.addSport);



        username.setText(userProfile.username);
        age.setText(userProfile.age+"ans");

        sportItems = userProfile.sports;
        levelItems = userProfile.levels;

        for (int i = 0; i < sportItems.size(); i++){
            sportLevelItems.add(sportItems.get(i)+"    "+levelItems.get(i)+"/10");
        }

        adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                sportLevelItems
        );

        sports.setAdapter(adapter);

        addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                Fragment ffrag = new FragmentAddSport(userProfile);
                ftran.replace(R.id.fl_wrapper, ffrag);
                ftran.commit();
            }
        });


        return inflater.inflate(R.layout.fragment_profil, container, false);
    }
}