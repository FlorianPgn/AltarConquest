package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;


import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.ServerInteractions.ServerSendGameProperties;
import com.example.florian.altarconquest.R;

public class EcranCreation_Partie extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    EditText nomPartie;
    EditText passwordPartie;
    Game game;
    char charList[] = {'-', '\"', '~', '\'', '_', '(', ')', '@', '[', ']', '{', '}', '#'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_partie);

        nomPartie = (EditText) findViewById(R.id.input_nom_de_partie);
        passwordPartie = (EditText) findViewById(R.id.input_mot_de_passe);

        spinner = (Spinner) findViewById(R.id.input_nb_joueurs);
        String[] items = {"2", "4", "6", "8", "10", "12"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        bouton_retour.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 ouvrirGestion_Partie();
                                             }
                                         }
        );

        ImageButton bouton_creer_la_partie = (ImageButton) findViewById(R.id.bouton_creer_la_partie);
        bouton_creer_la_partie.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          //Appel de la fonction vérifiant que les entrées ne posent pas de problème
                                                          verification();

                                                      }
                                                  }
        );
    }

    public String addQuote(String chaine) {
        return "'" + chaine + "'";
    }

    /**
     * @param name
     * @return false si le String contient un caractère interdit
     */
    private boolean isNameValid(String name) {

        for (int i = 0; i < charList.length; i++)
            if (name.contains(Character.toString(charList[i])))
                return false;
        return true;

    }

    /**
     * @param password
     * @return false si le String contient un caractère interdit
     */
    private boolean isPasswordValid(String password) {

        for (int i = 0; i < charList.length; i++) {
            if (password.contains(Character.toString(charList[i])))
                return false;
        }
        return true;

    }

    /**
     * fonction vérifiant les entrées et permettant ou non la création de la partie
     */
    private void verification() {

        // Reset errors
        nomPartie.setError(null);
        passwordPartie.setError(null);

        //enregistre les Strings venant des textEdits
        String name = nomPartie.getText().toString();
        String password = passwordPartie.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordPartie.setError("Caractères interdits dans le mot de passe");
            focusView = passwordPartie;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            nomPartie.setError("Ce champs est obligatoire");
            focusView = nomPartie;
            cancel = true;
        } else if (!isNameValid(name)) {
            nomPartie.setError("Caractères interdits dans le nom");
            focusView = nomPartie;
            cancel = true;
        }

        if (cancel) {
            // Focus sur le champs posant un problème
            focusView.requestFocus();
        } else {

            //Insertion game dans la bdd
            ServerSendGameProperties ssgp = new ServerSendGameProperties();
            ssgp.execute(addQuote(nomPartie.getText().toString()), addQuote(passwordPartie.getText().toString()), addQuote(String.valueOf(spinner.getSelectedItem())));

            ouvrirRejoindrePartie();

        }
    }

    public void ouvrirGestion_Partie() {
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void ouvrirRejoindrePartie() {
        Intent intent = new Intent(this, EcranRejoindre_Partie.class);
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
