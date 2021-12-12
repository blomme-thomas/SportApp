package com.example.sportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportapp.fragments.FragmentActivite;
import com.example.sportapp.fragments.FragmentEquipe;
import com.example.sportapp.fragments.FragmentHome;
import com.example.sportapp.fragments.FragmentMessagerie;
import com.example.sportapp.fragments.FragmentProfil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Button logout;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private User userProfile;

    private String username, age, email;
    private ArrayList<String> sportItems;
    private ArrayList<String> levelItems;

    private TextView name;
    private TextView ageUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,

                    new FragmentHome()).commit();
        }

        name = (TextView) findViewById(R.id.username);
        ageUser = (TextView) findViewById(R.id.age);
        logout = (Button) findViewById(R.id.logoutbutton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    username = userProfile.username;
                    age = userProfile.age;
                    email = userProfile.email;
                    sportItems = userProfile.sports;
                    levelItems = userProfile.levels;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.ic_activite:
                            selectedFragment = new FragmentActivite();
                            break;
                        case R.id.ic_team:
                            selectedFragment = new FragmentEquipe();
                            break;
                        case R.id.ic_messagerie:
                            selectedFragment = new FragmentMessagerie();
                            break;
                        case R.id.ic_profil:
                            selectedFragment = new FragmentProfil(userProfile);
                            break;
                        case R.id.ic_home:
                            selectedFragment = new FragmentHome();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,
                            selectedFragment).commit();

                    return true;
                }
            };
}