package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.Model.ServerSendGameProperties;
import com.example.florian.altarconquest.R;

public class EcranCreation_Partie extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    EditText nomPartie;
    EditText passwordPartie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_partie);

        nomPartie = (EditText) findViewById(R.id.input_nom_de_partie);
        passwordPartie = (EditText) findViewById(R.id.input_mot_de_passe);

        spinner = (Spinner) findViewById(R.id.input_nb_joueurs);
        String[] items = {"2", "4", "6", "8", "9", "10", "11", "12"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

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
                Log.i("Création partie", "");
                ServerSendGameProperties ssgp = new ServerSendGameProperties(EcranCreation_Partie.this);
                ssgp.execute(addQuote(nomPartie.getText().toString()), addQuote(passwordPartie.getText().toString()), addQuote(String.valueOf(spinner.getSelectedItem())));
                ouvrirChoix_Equipe();
            }}
        );
    }

    public String addQuote(String chaine){
        return "'"+chaine+"'";
    }

    public void ouvrirGestion_Partie() {
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void ouvrirChoix_Equipe() {
        Log.i("Choix equipe", "");
        Intent intent = new Intent(this, EcranChoix_Equipe.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getSelectedItemPosition();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
