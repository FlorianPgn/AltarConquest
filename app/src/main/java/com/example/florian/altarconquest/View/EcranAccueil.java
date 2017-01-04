package com.example.florian.altarconquest.View;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.florian.altarconquest.R;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class EcranAccueil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Log.i("density", ""+getApplicationContext().getResources().getDisplayMetrics().density);

        ImageButton bouton_jouer = (ImageButton) findViewById(R.id.bouton_jouer);
        ImageButton bouton_regles = (ImageButton) findViewById(R.id.bouton_regles);
        bouton_jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirCreationPartie();
            }
        });

        bouton_regles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirRegles();
            }
        });

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

    }



    public void ouvrirCreationPartie() {
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void ouvrirRegles() {
        Intent intent = new Intent(this, EcranRegles.class);
        startActivity(intent);
    }
}
