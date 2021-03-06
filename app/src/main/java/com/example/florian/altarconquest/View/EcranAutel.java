package com.example.florian.altarconquest.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.Model.TeamColor;
import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionPlayersInformations;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 10/01/2017.
 */

public class EcranAutel extends FragmentActivity implements OnMapReadyCallback {

    private Game game;
    private List<LatLng> listCoord;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autel);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageButton boutonRetour = (ImageButton) findViewById(R.id.imageButton);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ouvrirEcranJeu();
            }
        });

        listCoord = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        game = extras.getParcelable("game");
        latitude = extras.getDouble("DOUBLE_LAT");
        longitude = extras.getDouble("DOUBLE_LNG");

        ServerReceptionPlayersInformations src = new ServerReceptionPlayersInformations(game);
        src.execute(String.valueOf(game.getId()));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Initialisation de la position de départ de la caméra
        LatLng startCameraPosition = new LatLng(latitude, longitude);
        BitmapDescriptor autel = BitmapDescriptorFactory.fromResource(R.drawable.etoile);

        map.addMarker(new MarkerOptions().position(startCameraPosition).title("Autel").icon(autel));

        for (int i = 0; i < 20; i++) {
            LatLng pos = new LatLng(latitude + Math.random(),longitude + Math.random());
            listCoord.add(pos);
            map.addMarker(new MarkerOptions().position(pos).title("Autel Fake").icon(autel));
        }

        map.moveCamera(CameraUpdateFactory.newLatLng(startCameraPosition));
    }

    public void ouvrirEcranJeu()
    {
        finish();
    }
}