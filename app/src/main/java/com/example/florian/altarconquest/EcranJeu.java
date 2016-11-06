package com.example.florian.altarconquest;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

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

public class EcranJeu extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button mapButton, flagButton, qrCodeButton, treeButton;
    private RelativeLayout ecran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setVisibility(View.INVISIBLE);
        mapButton.setClickable(false);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvre la grande map avec l'altar
                // A compléter
            }
        });

        flagButton = (Button) findViewById(R.id.flagButton);
        flagButton.setVisibility(View.INVISIBLE);
        flagButton.setClickable(false);
        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour intercepter un drapeau ennemi
                // A compléter
            }
        });

        qrCodeButton = (Button) findViewById(R.id.qrCodeButton);
        qrCodeButton.setVisibility(View.INVISIBLE);
        qrCodeButton.setClickable(false);
        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirScanQrCode();
            }
        });

        treeButton = (Button) findViewById(R.id.treeButton);
        treeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On clique sur le bouton du milieu, affichage
                // des trois autres boutons
                mapButton.setVisibility(View.VISIBLE);
                mapButton.setClickable(true);

                flagButton.setVisibility(View.VISIBLE);
                flagButton.setClickable(true);

                qrCodeButton.setVisibility(View.VISIBLE);
                qrCodeButton.setClickable(true);

                treeButton.setVisibility(View.INVISIBLE);
                treeButton.setClickable(false);
            }
        });

        ecran = (RelativeLayout)findViewById(R.id.ecran);
        ecran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagButton.getVisibility() == View.VISIBLE){
                    mapButton.setVisibility(View.INVISIBLE);
                    mapButton.setClickable(false);

                    flagButton.setVisibility(View.INVISIBLE);
                    flagButton.setClickable(false);

                    qrCodeButton.setVisibility(View.INVISIBLE);
                    qrCodeButton.setClickable(false);

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
