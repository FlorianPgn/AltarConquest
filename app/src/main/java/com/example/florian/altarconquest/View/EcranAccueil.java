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

        ImageButton boutonJouer = (ImageButton) findViewById(R.id.bouton_jouer);
        ImageButton boutonRegles = (ImageButton) findViewById(R.id.bouton_regles);
        ImageButton boutonCredits = (ImageButton) findViewById(R.id.bouton_credits);

        boutonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirCreationPartie();
            }
        });

        boutonRegles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirRegles();
            }
        });

        boutonCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirCredits();
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

    public void ouvrirCredits() {
        Intent intent = new Intent(this, EcranCredits.class);
        startActivity(intent);
    }
}
