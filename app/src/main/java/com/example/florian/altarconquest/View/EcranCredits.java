package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.florian.altarconquest.R;

public class EcranCredits extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        ImageButton boutonRetour = (ImageButton) findViewById(R.id.bouton_retour);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
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