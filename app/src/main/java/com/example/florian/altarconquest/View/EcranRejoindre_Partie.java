package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionGamesProperties;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EcranRejoindre_Partie extends Activity {
    public static Activity context;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejoindre_partie);



        creerTimer();

        ImageButton bouton_retour = (ImageButton) findViewById(R.id.bouton_retour);
        bouton_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirGestion_Partie();
            }
        });
    }

    public void generateListContent(List<Game> list) {
        //instantiate custom adapter
        MyListGameAdapter adapter = new MyListGameAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.liste_parties);
        lView.setAdapter(adapter);
    }

    public void ouvrirGestion_Partie() {
        timer.cancel();
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void creerTimer(){
        timer =  new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ServerReceptionGamesProperties srgp = new ServerReceptionGamesProperties(EcranRejoindre_Partie.this);
                srgp.execute();
            }
        };

        timer.schedule(timerTask, 0, 1000 * 4);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        creerTimer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ouvrirGestion_Partie();

    }
}