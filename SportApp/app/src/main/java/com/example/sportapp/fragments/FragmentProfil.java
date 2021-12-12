package com.example.sportapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportapp.HomeActivity;
import com.example.sportapp.R;
import com.example.sportapp.Register;
import com.example.sportapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class FragmentProfil extends Fragment {

    private TextView username, age;
    private ListView sports;
    private Button addSport;
    private User userProfile;

    ArrayList<String> sportLevelItems = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


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

        username = (TextView) view.findViewById(R.id.username);
        age = (TextView) view.findViewById(R.id.age);
        sports = (ListView) view.findViewById(R.id.sports);
        addSport = (Button) view.findViewById(R.id.addSport);

        username.setText(userProfile.username);
        age.setText(userProfile.age+" ans");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        for (int i = 0; i < userProfile.sports.size(); i++){
            sportLevelItems.add(userProfile.sports.get(i)+"     "+userProfile.levels.get(i)+"/10");
        }

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, sportLevelItems);

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

        sports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Supprimer")
                        .setMessage("Voulez-vous supprimer ce sport: "+System.getProperty("line.separator")+sportLevelItems.get(position))
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sportLevelItems.remove(position);
                                userProfile.sports.remove(position);
                                userProfile.levels.remove(position);
                                reference.child(userID).child("sports").setValue(userProfile.sports);
                                reference.child(userID).child("levels").setValue(userProfile.levels);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Non", null)
                        .show();
            }
        });


        return view;
    }
}