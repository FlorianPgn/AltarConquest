package com.example.florian.altarconquest.View;

import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.Model.TeamColor;
import com.example.florian.altarconquest.R;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.florian.altarconquest.Model.EcomieEnergie;
import com.example.florian.altarconquest.Model.Flag;
import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionBasesPositions;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionPlayersInformations;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionFlagsPositions;
import com.example.florian.altarconquest.ServerInteractions.ServerSendDeletedPlayer;
import com.example.florian.altarconquest.ServerInteractions.ServerSendPlayerHoldAFlag;
import com.example.florian.altarconquest.ServerInteractions.ServerSendPlayersInformations;

import android.location.LocationListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.florian.altarconquest.ServerInteractions.ServerSendPlayerScore;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class EcranJeu extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    public GoogleMap mMap;
    public EcomieEnergie economieEnergie;

    private Button mapButton, flagButton, qrCodeButton, treeButton, unactiveTreeButton;
    private static ImageView attackToken;
    private static ImageView defenceToken;
    private Boolean attackTokenAvailable = true, defenseTokenAvailable = true;
    private RelativeLayout ecran;
    private ArrayList<Button> boutonsDeployables;
    private ArrayList<Player> joueursAvecDrapeau;
    public ImageView imageEconomie;
    private TextView timerTextView, scoreBlueTeamTextView, scoreRedTeamTextView;

    public static Map<String, Circle> coordinates;

    private int lastFlagCaptured = 0;

    private int score = 0;
    private float DISTANCE_MAXIMUM_REQCUISE = 5;
    private int minutes = 15;
    private int seconds = 0;

    private String pseudo;
    private TeamColor myTeamColor;
    private TeamColor enemyTeamColor;
    private Location location;

    private Game game;

    private final int REQUEST_CODE = 128;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        
        imageEconomie = (ImageView) findViewById(R.id.economyEnergie);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        scoreBlueTeamTextView = (TextView) findViewById(R.id.scoreBlueTeamTextView);
        scoreRedTeamTextView = (TextView) findViewById(R.id.scoreRedTeamTextView);


        mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ouvrirEcranAutel();
            }
        });
        coordinates = new HashMap<>();

        economieEnergie = new EcomieEnergie(this);
        economieEnergie.start();

        //Récupère l'objet Game du lobby
        Bundle extras = getIntent().getExtras();
        game = extras.getParcelable("game");

        //Récupère et sécurise les données passées depuis le lobby
        String color;
        if (savedInstanceState == null) {
            if (extras == null) {
                pseudo = null;
                color = null;
            } else {
                pseudo = extras.getString("STRING_PSEUDO");
                color = extras.getString("STRING_COLOR");
            }
        } else {
            pseudo = (String) savedInstanceState.getSerializable("STRING_PSEUDO");
            color = (String) savedInstanceState.getSerializable("STRING_COLOR");
        }

        //Initialise la team du joueur pendant cette partie
        if (color.equals(TeamColor.BLUE.toString())) {
            myTeamColor = TeamColor.BLUE;
            enemyTeamColor = TeamColor.RED;
        } else if (color.equals(TeamColor.RED.toString())) {
            myTeamColor = TeamColor.RED;
            enemyTeamColor = TeamColor.BLUE;
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double depInfoLat = 48.08574927627401, depInfoLng = -0.7584989108085632;
        double echologiaLat = 48.10922932860948, echologiaLng = -0.7235687971115112;
        double hugoLat = 48.069250, hugoLng = -0.774704;

        // Initialisation de la position de départ de la caméra
        LatLng startCameraPosition = new LatLng(depInfoLat, depInfoLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startCameraPosition, 17.0f));
        retirerMouvementCameraMarkers();

        //Ajout de la carte d'Ecologia en background
        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
        BitmapDescriptor image = BitmapDescriptorFactory.fromResource(R.drawable.echologia_map);
        groundOverlayOptions.image(image).position(new LatLng(48.10872932860948, -0.7233687971115112), 380f).transparency(0.2f); //Positions de la carte
        GroundOverlay imageOverlay = mMap.addGroundOverlay(groundOverlayOptions);

        //Initialisation de la partie
        creationMenuDeroulant();
        demanderPermissionGps();
        recupererLesDrapeauxSurLeServeur();
        recupererLesBasesSurLeServeur();

        //Timer qui lance toutes les requêtes serveur pour les coordonnéesà toutes les 2 sec
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ServerReceptionPlayersInformations src = new ServerReceptionPlayersInformations(game);
                src.execute(String.valueOf(game.getId()));
                runOnUiThread(new Runnable() {
                    public void run() {
                        afficherJoueurs();
                        updateScores();
                    }
                });
                ServerSendPlayersInformations ssc = new ServerSendPlayersInformations();
                if (location != null) {
                    ssc.execute(pseudo, String.valueOf(game.getId()), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                }

            }

        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000 * 2);

        TimerTask timerTaskUpdateTimer = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateTimer();
                    }
                });
            }
        };
        Timer timerUpdateTimer = new Timer();
        timerUpdateTimer.schedule(timerTaskUpdateTimer, 0, 1000);

        setAttackToken(true);
        setDefencetoken(true);

    }


    //Méthodes pour gérer les compteurs de drapeaux
    public void updateScores(){
        selectFlagCount(myTeamColor).setText(String.valueOf(game.getTeam(myTeamColor).getScore()));
        selectFlagCount(enemyTeamColor).setText(String.valueOf(game.getTeam(enemyTeamColor).getScore()));
    }

    public TextView selectFlagCount(TeamColor teamColor){
        if (teamColor == TeamColor.BLUE) {
            return scoreBlueTeamTextView;
        } else {
            return scoreRedTeamTextView;
        }
    }


    //Méthode pour le timer
    public void updateTimer() {
        if (minutes == 0 && seconds == 0) {
            Intent intent = new Intent(this, EcranFinJeu.class);
            startActivity(intent);
            finish();
        }
        if(seconds == 0) {
            minutes--;
            seconds = 59;
        } else {
            seconds--;
        }
        if (timerTextView != null) {
            timerTextView.setText((minutes<10?"0"+minutes:minutes)+":"+(seconds<10?"0"+seconds:seconds));
        }
        if (minutes == 0 && seconds == 0){
            Intent intent = new Intent(this, EcranFinJeu.class);
            intent.putExtra("Score", game);
        }

    }

    //Méthodes pour afficher les drapeaux au démarage de l'activité
    public void afficherDrapeaux() {
        for (Flag flag : game.getBlueTeam().getListofFlags()) {
            MarkerOptions marker = new MarkerOptions().position(flag.getCoordonnees()).title(flag.getName());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.drapeaubleu));
            mMap.addMarker(marker);
        }
        for (Flag flag : game.getRedTeam().getListofFlags()) {
            MarkerOptions marker = new MarkerOptions().position(flag.getCoordonnees()).title(flag.getName());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.drapeaurouge));
            mMap.addMarker(marker);
        }
    }

    public void recupererLesDrapeauxSurLeServeur() {
        ServerReceptionFlagsPositions srf = new ServerReceptionFlagsPositions(this);
        srf.execute();
    }

    //Méthodes pour afficher les bases au démarage de l'activité
    public void afficherBases() {
        MarkerOptions marker = new MarkerOptions().position(game.getBlueTeam().getBase().getCoordonnees()).title(game.getBlueTeam().getBase().getName());
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tour_bleue));
        mMap.addMarker(marker);
        marker = new MarkerOptions().position(game.getRedTeam().getBase().getCoordonnees()).title(game.getRedTeam().getBase().getName());
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tour_rouge));
        mMap.addMarker(marker);
    }

    public void recupererLesBasesSurLeServeur() {
        ServerReceptionBasesPositions srbp = new ServerReceptionBasesPositions(this);
        srbp.execute();
    }


    //Méthodes pour afficher la position des joueurs dont on est censé avoir la position
    public void afficherJoueurs() {
        for (Player player : game.getTeam(myTeamColor).getListeDesPlayers()) {
            if (!player.getPseudo().equals(pseudo))
                updateAffichagePositionJoueurs(player);
        }
        afficherJoueursEnnemiesAvecDrapeau();
    }

    public void updateAffichagePositionJoueurs(Player player) {
        Circle circle = coordinates.get(player.getPseudo());
        if (circle == null) {
            // Instantiates a new CircleOptions object and defines the center and radius
            CircleOptions circleOptions = new CircleOptions()
                    .center(player.getCoordonnees())
                    .radius(2); // In meters
            circle = mMap.addCircle(circleOptions);
            coordinates.put(player.getPseudo(), circle);
        } else {
            //circle.setVisible(true);
            circle.setCenter(player.getCoordonnees());
        }
    }

    public void afficherJoueursEnnemiesAvecDrapeau() {
        for (Player player : game.getTeam(enemyTeamColor).getListeDesPlayers()) {
            if (player.isHoldingAFlag() == true) {
                Toast.makeText(this, "Un ennemi a capturé un de vos drapeaux !", Toast.LENGTH_LONG).show();
                updateAffichagePositionJoueurs(player);
            } else {
                Circle circle = coordinates.get(player.getPseudo());
                if (circle != null) {
                    circle.remove();
                }
                coordinates.remove(player.getPseudo());
            }
        }
    }

    //Retire le mouvement de caméra quand on clic sur un Marker
    public void retirerMouvementCameraMarkers(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            Marker currentShown;
            public boolean onMarkerClick(Marker marker) {
                if (marker.equals(currentShown)) {
                    marker.hideInfoWindow();
                    currentShown = null;
                } else {
                    marker.showInfoWindow();
                    currentShown = marker;
                }
                return true;
            }
        });
    }

    //Methodes pour le menu déployable en bas de l'écran
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        attackToken = (ImageView) findViewById(R.id.attackToken);
        defenceToken = (ImageView) findViewById(R.id.defenceToken);

        Player player = game.getTeam(myTeamColor).getJoueur(pseudo);

        if (player != null) {
            if (player.isAttackTokenAvailable() == true) {
                attackToken.setBackgroundResource(R.drawable.jeton_attaque);
            } else {
                attackToken.setBackgroundResource(R.drawable.jeton_vide);
            }

            if (player.isDefenceTokenAvailable() == true) {
                defenceToken.setBackgroundResource(R.drawable.jeton_defense);
            } else {
                defenceToken.setBackgroundResource(R.drawable.jeton_vide);
            }
        }

        unactiveTreeButton = (Button) findViewById(R.id.unactiveTreeButton);

        unactiveTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrCodeButton.isClickable()) {
                    for (Button leBouton : boutonsDeployables) {
                        leBouton.setVisibility(View.INVISIBLE);
                        leBouton.setClickable(false);
                    }

                    treeButton.setVisibility(View.VISIBLE);
                    treeButton.setClickable(true);
                }
            }
        });
    }

    public void creationMenuDeroulant() {
        boutonsDeployables = new ArrayList<Button>();

        attackToken = (ImageView) findViewById(R.id.attackToken);
        defenceToken = (ImageView) findViewById(R.id.defenceToken);


        mapButton = (Button) findViewById(R.id.mapButton);
        boutonsDeployables.add(mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvre la grande map avec l'altar
                // A compléter
            }
        });

        flagButton = (Button) findViewById(R.id.flagButton);
        boutonsDeployables.add(flagButton);
        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean someoneHaveAFlag = false;
                for (Player enemy : game.getTeam(enemyTeamColor).getListeDesPlayers()) {
                    if (enemy.isHoldingAFlag()){
                        someoneHaveAFlag = true;
                        if (DISTANCE_MAXIMUM_REQCUISE >= calculEcartCoor(game.getTeam(myTeamColor).getJoueur(pseudo).getCoordonnees(), enemy.getCoordonnees())) {
                            Toast.makeText(EcranJeu.this, "Le drapeau a été recupéré", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(EcranJeu.this, "L'ennemi n'est pas a portée", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                if (!someoneHaveAFlag) {
                    Toast.makeText(EcranJeu.this, "Il n'y a pas de drapeau entrain d'être volé", Toast.LENGTH_LONG).show();
                }
            }
        });

        qrCodeButton = (Button) findViewById(R.id.qrCodeButton);
        boutonsDeployables.add(qrCodeButton);
        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirScanQrCode();
            }
        });

        for (Button leBouton : boutonsDeployables) {
            leBouton.setVisibility(View.INVISIBLE);
            leBouton.setClickable(false);
        }

        treeButton = (Button) findViewById(R.id.treeButton);
        treeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On clique sur le bouton du milieu, affichage
                // des trois autres boutons
                for (Button leBouton : boutonsDeployables) {
                    leBouton.setVisibility(View.VISIBLE);
                    leBouton.setClickable(true);

                    treeButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // nous utilisons la classe IntentIntegrator et sa fonction parseActivityResult pour parser le résultat du scan
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(requestCode == 0) {
            if (resultCode == RESULT_OK) {

                // nous récupérons le contenu du code barre
                String scanContent = intent.getStringExtra("SCAN_RESULT");

                gestionQRcodes(scanContent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Aucune donnée reçu!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

    public float calculEcartCoor(LatLng coordonneesPlayer, LatLng coordonneesEnemy) {
        float distance = 0;
        Location locPlayer = new Location("");
        locPlayer.setLatitude(coordonneesPlayer.latitude);
        locPlayer.setLongitude(coordonneesPlayer.longitude);

        Location locEnemy = new Location("");
        locEnemy.setLatitude(coordonneesEnemy.latitude);
        locEnemy.setLongitude(coordonneesEnemy.longitude);

        distance = locPlayer.distanceTo(locEnemy);

        return distance;
    }

    private void gestionQRcodes(String scanContent) {
        Player player = game.getTeam(myTeamColor).getJoueur(pseudo);

        switch (scanContent) {
            case "base":
                if (lastFlagCaptured != 0){
                    player.setAttackTokenAvailable(true);
                    Log.i("score", ""+player.getScore());
                    ServerSendPlayerScore ssps = new ServerSendPlayerScore();
                    ssps.execute(pseudo, String.valueOf(player.getScore()+1));
                    ServerSendPlayerHoldAFlag ssphaf = new ServerSendPlayerHoldAFlag();
                    ssphaf.execute(pseudo, String.valueOf(game.getId()), "0");
                    lastFlagCaptured = 0;
                    Toast.makeText(this, "BRAVO VOUS AVEZ GAGNÉ UN POINT !", Toast.LENGTH_LONG).show();
                }
                else {
                    player.setAttackTokenAvailable(true);
                    player.setDefenseTokenAvailable(true);
                    Toast.makeText(this, "Vous avez rechargé votre Jeton d'Attaque et Défense", Toast.LENGTH_LONG).show();
                }
                break;
            case "1":
                scanQuestion(1, 1, player, scanContent);
                break;
            case "2":
                scanQuestion(4, 2, player, scanContent);
                break;
            case "3":
                scanQuestion(7, 3, player, scanContent);
                break;
            case "4":
                scanQuestion(10, 4, player, scanContent);
                break;
            case "5":
                scanQuestion(13, 5, player, scanContent);
                break;
            case "6":
                scanQuestion(16, 6, player, scanContent);
                break;
        }

    }

    public void scanQuestion(int numLotQuestion, int lastFlagCaptured, Player player, String scanContent){
        this.lastFlagCaptured = lastFlagCaptured;
        player.setAttackTokenAvailable(false);
        player.setHoldingAFlag(true);
        ServerSendPlayerHoldAFlag ssphaf = new ServerSendPlayerHoldAFlag();
        ssphaf.execute(pseudo, String.valueOf(game.getId()), "1");
        Intent intent = new Intent(this, EcranQuestions.class);
        intent.putExtra("Questions", numLotQuestion);
        Log.e("Ce qu'on a récupérer","" + scanContent);
	    startActivity(intent);
    }

    public void ouvrirEcranAutel() {
        Intent intent = new Intent(this, EcranAutel.class);
        intent.putExtra("game", game);

        startActivity(intent);
    }

    public void ouvrirScanQrCode() {
        try {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            startActivity(marketIntent);
            finish();
        }
    }
    //Méthodes en rapport avec la demande d'accès au GPS
    public void demanderPermissionGps() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setCompassEnabled(false);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (bestProvider == null) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {}
                        @Override
                        public void onProviderEnabled(String provider) {}
                        @Override
                        public void onProviderDisabled(String provider) {}
                        @Override
                        public void onLocationChanged(final Location location) {}
                    }
            );
        }
        location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    demanderPermissionGps();
                }
                break;
            }
        }
    }

    public static void setDefencetoken(boolean defenceTokenAvailable) {
        if (defenceTokenAvailable) {
            defenceToken.setBackgroundResource(R.drawable.jeton_defense);
        } else {
            defenceToken.setBackgroundResource(R.drawable.jeton_vide);
        }
    }

    public static void setAttackToken(boolean attackTokenAvailable) {
        if (attackTokenAvailable) {
            attackToken.setBackgroundResource(R.drawable.jeton_attaque);
        } else {
            attackToken.setBackgroundResource(R.drawable.jeton_vide);
        }
    }

    //Met à jour les coordonnées du joueur si elles ont changées
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Log.i("Location", location.getLatitude() + " " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("EcranJeu Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        ServerSendDeletedPlayer ssdp = new ServerSendDeletedPlayer();
        ssdp.execute(pseudo);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public Game getGame(){
        return game;
    }

    @Override
    public void onBackPressed() {

    }


}
