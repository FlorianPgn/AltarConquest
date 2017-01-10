package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.florian.altarconquest.Model.TeamColor;
import com.example.florian.altarconquest.R;

/**
 * Created by Hugo on 09/01/2017.
 */

public class EcranFinJeu extends Activity {

    private TextView teamGagnanteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_jeu);

        teamGagnanteTextView = (TextView) findViewById(R.id.teamGagnanteTextView);

        Bundle extras = getIntent().getExtras();

        //Récupère et sécurise les données passées depuis le lobby
        String color;
        if (savedInstanceState == null) {
            if (extras == null) {
                color = null;
            } else {
                color = extras.getString("STRING_COLOR");
            }
        } else {
            color = (String) savedInstanceState.getSerializable("STRING_COLOR");
        }

        if(color.equals(TeamColor.BLUE.toString())) {
            teamGagnanteTextView.setText("L'équipe bleue \n a gagné !");
            teamGagnanteTextView.setTextColor(Color.BLUE);
        } else {
            teamGagnanteTextView.setText("L'équipe rouge \n a gagné !");
            teamGagnanteTextView.setTextColor(Color.RED);
        }

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
