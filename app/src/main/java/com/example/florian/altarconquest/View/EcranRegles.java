package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.View.EcranAccueil;

public class EcranRegles extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regles);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ouvrirAccueil();
            }
        });
    }

    public void ouvrirAccueil()
    {
        Intent intent = new Intent(this, EcranAccueil.class);
        startActivity(intent);
    }
}