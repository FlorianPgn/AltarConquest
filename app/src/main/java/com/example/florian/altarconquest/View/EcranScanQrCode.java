package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        new IntentIntegrator(EcranScanQrCode.this).initiateScan();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // nous utilisons la classe IntentIntegrator et sa fonction parseActivityResult pour parser le résultat du scan
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            // nous récupérons le contenu du code barre
            String scanContent = scanningResult.getContents();

            // nous récupérons le format du code barre
            String scanFormat = scanningResult.getFormatName();

            TextView scan_format = (TextView) findViewById(R.id.scan_format);
            TextView scan_content = (TextView) findViewById(R.id.scan_content);

            gestionQRcodes(scanContent);

            // nous affichons le résultat dans nos TextView

            scan_format.setText("FORMAT: " + scanFormat);
            scan_content.setText("CONTENT: " + scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Aucune donnée reçu!", Toast.LENGTH_SHORT);
            toast.show();
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