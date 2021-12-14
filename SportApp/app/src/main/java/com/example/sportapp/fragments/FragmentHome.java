package com.example.sportapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class FragmentHome extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        SupportMapFragment supportMapFragment =(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback()
        {


            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                LatLng Chicoutimi =new LatLng(48.41874175444457, -71.05555394031455);
                googleMap.addMarker(new MarkerOptions().position(Chicoutimi).title("Votre position"));
                final LatLng Chicoutimi1 =new LatLng(48.42175168472164, -71.0646776700143);
                final LatLng Chicoutimi2 =new LatLng(48.418663106724836, -71.06284956389256);
                final LatLng Chicoutimi3 =new LatLng(48.42390219553129, -71.05293072907388);
                final LatLng Chicoutimi4 =new LatLng(48.41904982468472, -71.05184859865713);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng((Chicoutimi)));
                Marker marker1 = googleMap.addMarker(
                        new MarkerOptions()
                                .position(Chicoutimi1)
                                .title("Match foot")
                                .snippet("Loisir - 22 joueurs | 22/11 16-18h"));
                Marker marker2 = googleMap.addMarker(
                        new MarkerOptions()
                                .position(Chicoutimi2)
                                .title("Match tennis")
                                .snippet("Loisir - 2 joueurs | 20/11 09-11h"));
                Marker marker3 = googleMap.addMarker(
                        new MarkerOptions()
                                .position(Chicoutimi3)
                                .title("Match basket")
                                .snippet("Loisir - 10 joueurs | 17/11 15-16h"));
                Marker marker4 = googleMap.addMarker(
                        new MarkerOptions()
                                .position(Chicoutimi4)
                                .title("Match volley")
                                .snippet("Loisir - 12 joueurs | 24/11 20-22h"));
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()

                {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions= new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Ajouter un nouveau évenement");
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                    }
                });
            }
        });
        return view;
    }
}
