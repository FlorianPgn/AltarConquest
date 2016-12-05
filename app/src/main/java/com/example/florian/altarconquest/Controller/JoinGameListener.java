package com.example.florian.altarconquest.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.florian.altarconquest.View.EcranLobbyPartie;

/**
 * Created by Florian on 05/12/2016.
 */

public class JoinGameListener implements View.OnClickListener {

    private Context context;

    public JoinGameListener(Context context){
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Saisissez votre pseudo :");

        // Set up the input
        final EditText input = new EditText(context);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pseudo = input.getText().toString();
                Intent intent = new Intent(context, EcranLobbyPartie.class);
                intent.putExtra("STRING_PSEUDO", pseudo);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
