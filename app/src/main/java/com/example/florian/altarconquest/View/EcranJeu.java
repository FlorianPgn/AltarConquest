package com.example.florian.altarconquest.View;

import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.Model.TeamColor;
import com.example.florian.altarconquest.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionCoordinates;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionFlagsPositions;
import com.example.florian.altarconquest.ServerInteractions.ServerSendCoordinates;

import android.location.LocationListener;

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

import java.util.ArrayList;
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
    public ImageView imageEconomie;

    Map<String, Circle> coordinates;

    private String pseudo;
    private TeamColor myTeamColor;
    private Location location;

    private Game game;

    private final int REQUEST_CODE = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        imageEconomie = (ImageView) findViewById(R.id.economyEnergie);

        coordinates = new HashMap<>();

        economieEnergie = new EcomieEnergie(this);
        economieEnergie.start();

        //Récupère l'objet Game du lobby
        Bundle bundle = getIntent().getExtras();
        game = bundle.getParcelable("game");

        //Récupère et sécurise les données passées depuis le lobby
        String color;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
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
        } else if (color.equals(TeamColor.RED.toString())) {
            myTeamColor = TeamColor.RED;
        }
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

        // Initialisation de la position de départ de la caméra
        LatLng startCameraPosition = new LatLng(depInfoLat, depInfoLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startCameraPosition, 17.0f));
        retirerMouvementCameraMarkers();

        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
        BitmapDescriptor image = BitmapDescriptorFactory.fromResource(R.drawable.echologia_map);

        groundOverlayOptions.image(image).position(new LatLng(48.10872932860948, -0.7233687971115112), 380f).transparency(0.2f);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(groundOverlayOptions);

        creationMenuDeroulant();
        demanderPermissionGps();
        recupererLesDrapeauxSurLeServeur(game);

        //Timer qui fait toutes les requêtes à faire durant la partie
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ServerReceptionCoordinates src = new ServerReceptionCoordinates(game);
                src.execute(String.valueOf(game.getId()));
                runOnUiThread(new Runnable() {
                    public void run() {
                        afficherJoueurs();
                    }
                });
                ServerSendCoordinates ssc = new ServerSendCoordinates();
                ssc.execute(pseudo, String.valueOf(game.getId()), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            }
        };
        timer.schedule(timerTask, 0, 1000 * 2);
    }
    

    //Méthodes pour afficher les drapeaux au démarage de l'activité
    public void afficherDrapeaux(Game game) {
        for (Flag flag : game.getBlueTeam().getListofFlags()) {
            mMap.addMarker(new MarkerOptions().position(flag.getCoordonnees()).title(flag.getName()));
        }
        for (Flag flag : game.getRedTeam().getListofFlags()) {
            mMap.addMarker(new MarkerOptions().position(flag.getCoordonnees()).title(flag.getName()));
        }
    }

    public void recupererLesDrapeauxSurLeServeur(Game game) {
        Log.i("Début", "requete serveur flags");
        ServerReceptionFlagsPositions srf = new ServerReceptionFlagsPositions(game, this);
        srf.execute();
    }


    //Méthodes pour afficher la position des joueurs dont on est censé avoir la position
    public void afficherJoueurs() {
        for (Player player : game.getTeam(myTeamColor).getListeDesPlayers()) {
            if (!player.getPseudo().equals(pseudo))
                updateAffichagePositionJoueurs(player);
        }
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
            circle.setCenter(player.getCoordonnees());
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

    //Met à jour les coordonnées du joueur si elles ont changées
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Log.i("Location", location.getLatitude()+" "+location.getLongitude());
    }


    //Methodes pour le menu déployable en bas de l'écran
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        unactiveTreeButton = (Button)findViewById(R.id.unactiveTreeButton);

        unactiveTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qrCodeButton.isClickable()){
                    for(Button leBouton: boutonsDeployables){
                        leBouton.setVisibility(View.INVISIBLE);
                        leBouton.setClickable(false);
                    }

                    treeButton.setVisibility(View.VISIBLE);
                    treeButton.setClickable(true);
                }
            }
        });
    }

    public void creationMenuDeroulant(){
        boutonsDeployables = new ArrayList<Button>();

        attackToken = (ImageView) findViewById(R.id.attackToken);
        defenceToken = (ImageView) findViewById(R.id.defencetoken);


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
                // Code pour intercepter un drapeau ennemi
                // A compléter
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

        for(Button leBouton: boutonsDeployables){
            leBouton.setVisibility(View.INVISIBLE);
            leBouton.setClickable(false);
        }

        treeButton = (Button) findViewById(R.id.treeButton);
        treeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On clique sur le bouton du milieu, affichage
                // des trois autres boutons
                for(Button leBouton: boutonsDeployables){
                    leBouton.setVisibility(View.VISIBLE);
                    leBouton.setClickable(true);

                    treeButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void ouvrirScanQrCode() {
        Intent intent = new Intent(this, EcranScanQrCode.class);
        startActivity(intent);
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
        /*if (location != null) {
            onLocationChanged(location);
        }*/
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

    public static void setDefencetoken(boolean defenceTokenAvailable){
        if(defenceTokenAvailable){
            defenceToken.setImageResource(R.drawable.jeton_blanc);
        }
        else{
            defenceToken.setImageResource(R.drawable.jeton_blanc_et_tour);
        }
    }

    public static void setAttackToken(boolean attackTokenAvailable){
        if(attackTokenAvailable){
            attackToken.setImageResource(R.drawable.jeton_noir);
        }
        else{
            attackToken.setImageResource(R.drawable.jeton_noir_et_tour);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
