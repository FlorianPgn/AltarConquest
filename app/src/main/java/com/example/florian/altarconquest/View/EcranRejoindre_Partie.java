package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionGamesProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EcranRejoindre_Partie extends Activity
{

    private ArrayList<Game> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejoindre_partie);

        //generate list
        list = new ArrayList<>();


        Timer timer =  new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ServerReceptionGamesProperties srgp = new ServerReceptionGamesProperties(EcranRejoindre_Partie.this);
                srgp.execute();
            }
        };

        timer.schedule(timerTask, 0, 1000 * 4);

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirGestion_Partie();
            }
        });

        ImageButton bouton_rejoindre_partie = (ImageButton) findViewById(R.id.bouton_rejoindre_partie);
        bouton_rejoindre_partie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirChoix_Equipe();
            }
        });
    }

    public void setActiveGame() {

    }

    public void generateListContent(List<Game> list) {
        Log.i("generate","");
        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.liste_parties);
        lView.setAdapter(adapter);
    }

    public void ouvrirGestion_Partie() {
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void ouvrirChoix_Equipe() {
        Intent intent = new Intent(this, EcranChoix_Equipe.class);
        startActivity(intent);
    }
}