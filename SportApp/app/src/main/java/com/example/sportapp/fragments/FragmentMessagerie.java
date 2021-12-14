package com.example.sportapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.sportapp.R;

import java.util.List;


public class FragmentMessagerie extends Fragment {


    private ListView conversations;
    private Button send;
    public FragmentMessagerie() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messagerie, container, false);

        conversations = (ListView) view.findViewById(R.id.conversations);
        send = (Button) view.findViewById(R.id.newContact);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frman = getFragmentManager();
                FragmentTransaction ftran = frman.beginTransaction();
                Fragment ffrag = new FragmentAddContact();
                ftran.replace(R.id.fl_wrapper, ffrag);
                ftran.commit();
            }
        });

        return view;
    }
}