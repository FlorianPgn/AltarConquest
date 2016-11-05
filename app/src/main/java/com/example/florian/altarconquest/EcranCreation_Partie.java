package com.example.florian.altarconquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EcranCreation_Partie extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_partie);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ouvrirGestion_Partie();
            }}
        );

        ImageButton bouton_creer_la_partie = (ImageButton) findViewById(R.id.bouton_creer_la_partie);
        bouton_creer_la_partie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ouvrirChoix_Equipe();
            }}
        );
    }

    public void ouvrirGestion_Partie()
    {
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void ouvrirChoix_Equipe()
    {
        Intent intent = new Intent(this, EcranChoix_Equipe.class);
        startActivity(intent);
    }
}
