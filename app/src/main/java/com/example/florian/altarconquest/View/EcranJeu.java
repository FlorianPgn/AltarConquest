package com.example.florian.altarconquest.View;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.florian.altarconquest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

public class EcranJeu extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button mapButton, flagButton, qrCodeButton, treeButton, unactiveTreeButton;
    private RelativeLayout ecran;
    private ArrayList<Button> boutonsDeployables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        boutonsDeployables = new ArrayList<Button>();


        mapButton = (Button) findViewById(R.id.mapButton);
        boutonsDeployables.add(mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvre la grande map avec l'altar
                // A compléter
            }
        });

        flagButton = (Button) findViewById(R.id.flagButton);
        boutonsDeployables.add(flagButton);
        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour intercepter un drapeau ennemi
                // A compléter
            }
        });

        qrCodeButton = (Button) findViewById(R.id.qrCodeButton);
        boutonsDeployables.add(qrCodeButton);
        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirScanQrCode();
            }
        });

        for(Button leBouton: boutonsDeployables){
            leBouton.setVisibility(View.INVISIBLE);
            leBouton.setClickable(false);
        }

        treeButton = (Button) findViewById(R.id.treeButton);
        treeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On clique sur le bouton du milieu, affichage
                // des trois autres boutons
                for(Button leBouton: boutonsDeployables){
                    leBouton.setVisibility(View.VISIBLE);
                    leBouton.setClickable(true);

                    treeButton.setVisibility(View.INVISIBLE);
                }
            }
        });



    }

    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        unactiveTreeButton = (Button)findViewById(R.id.unactiveTreeButton);

        unactiveTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qrCodeButton.isClickable()){
                    for(Button leBouton: boutonsDeployables){
                        leBouton.setVisibility(View.INVISIBLE);
                        leBouton.setClickable(false);
                    }

                    treeButton.setVisibility(View.VISIBLE);
                    treeButton.setClickable(true);
                }
            }
        });


    }

    public void ouvrirScanQrCode() {
        Intent intent = new Intent(this, EcranScanQrCode.class);
        startActivity(intent);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double depInfoLat = 48.08604927627401;
        double depInfoLng = -0.7595989108085632;

        double echologiaLat = 48.10922932860948;
        double echologiaLng = -0.7235687971115112;



        // Initialisation de la position de départ de la caméra
        LatLng startCameraPosition = new LatLng(echologiaLat, echologiaLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startCameraPosition, 18.0f));

        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
        BitmapDescriptor image = BitmapDescriptorFactory.fromResource(R.drawable.echologia_map);
        LatLngBounds bounds = LatLngBounds.builder()
                .include(new LatLng(depInfoLat, depInfoLng))
                .include(new LatLng(depInfoLat+2000, depInfoLng+2000))
                .build();

        groundOverlayOptions.image(image).position(new LatLng(48.10872932860948, -0.7233687971115112), 380f).transparency(0.2f);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(groundOverlayOptions);
    }
}
