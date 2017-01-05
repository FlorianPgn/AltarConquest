package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.florian.altarconquest.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

// on importe les classes IntentIntegrator et IntentResult de la librairie zxing

public class EcranScanQrCode extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_scan_qr_code);

        //new IntentIntegrator(EcranScanQrCode.this).initiateScan();
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

    private void gestionQRcodes(String scanContent) {
        Intent intent = null;

        switch (scanContent) {
            case "1":
                intent = new Intent(this, EcranQuestions.class);
                intent.putExtra("Questions", 1);
                Log.e("Ce qu'on a récupérer",""+scanContent);
                break;
            case "2":
                intent = new Intent(this, EcranQuestions.class);
                intent.putExtra("Questions", 4);
                Log.e("Ce qu'on a récupérer",""+scanContent);
                break;
            case "3":
                intent = new Intent(this, EcranQuestions.class);
                intent.putExtra("Questions", 7);
                Log.e("Ce qu'on a récupérer",""+scanContent);
                break;
            case "4":
                intent = new Intent(this, EcranQuestions.class);
                intent.putExtra("Questions", 10);
                Log.e("Ce qu'on a récupérer",""+scanContent);
                break;
            case "5":
                intent = new Intent(this, EcranQuestions.class);
                intent.putExtra("Questions", 13);
                Log.e("Ce qu'on a récupérer",""+scanContent);
                break;
            case "6":
                intent = new Intent(this, EcranQuestions.class);
                intent.putExtra("Questions", 16);
                Log.e("Ce qu'on a récupérer",""+scanContent);
                break;
        }

        startActivity(intent);
        finish();


    }
}