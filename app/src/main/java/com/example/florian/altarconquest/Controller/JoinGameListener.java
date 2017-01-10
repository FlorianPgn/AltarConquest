package com.example.florian.altarconquest.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.ServerInteractions.ServerReceptionPlayersBeforeLobby;
import com.example.florian.altarconquest.ServerInteractions.ServerSendGameProperties;
import com.example.florian.altarconquest.ServerInteractions.ServerSendPlayerProperties;
import com.example.florian.altarconquest.View.EcranLobby_Partie;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by Florian on 05/12/2016.
 */

public class JoinGameListener implements View.OnClickListener {

    private Context context;
    private Game game;
    char charList[] = {'²', '&', '"', '\'', '(', '-', '_', ')', '=', '^', '$', '*', ',', ';', ':', '!', '<', '/', '*', '-', '+', '*', '°', '+', '¨', '£', '%', 'µ', '?', '.', '/', '§', '~', '#', '{', '[', '|', '`', '\\', '^', '@', ']', '¤', ']', '}'};
    private List<Player> playerList;
    boolean listeUpdated = false;
    String erreurPseudo = "Caractère(s) interdit(s)";


    public JoinGameListener(Context context, Game game) {
        this.game = game;
        this.context = context;
        this.playerList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {

        ServerReceptionPlayersBeforeLobby srpbl = new ServerReceptionPlayersBeforeLobby(JoinGameListener.this);
        srpbl.execute(String.valueOf(game.getId()));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Saisissez votre pseudo (mininum 4 caractères) :");

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

                if (validation(input) && listeUpdated == true) {
                    ServerSendPlayerProperties ssp = new ServerSendPlayerProperties();
                    ssp.execute(pseudo, String.valueOf(game.getId()));

                    //Serialize l'objet game
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("game", game);

                    Intent intent = new Intent(context, EcranLobby_Partie.class);
                    intent.putExtra("STRING_PSEUDO", pseudo);
                    intent.putExtra("STRING_GAMEID", String.valueOf(game.getId()));
                    intent.putExtra("STRING_JMAX", String.valueOf(game.getNbJoueursMax()));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
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

    /**
     * @param pseudo
     * @return false si le String contient un caractère interdit
     */
    private boolean isPseudoValid(String pseudo) {


        if (pseudo.length() < 3) {
            erreurPseudo = "Ce pseudonyme est trop court ";
            return false;
        }

        for (Player p : playerList) {
            if (p.getPseudo().equals(pseudo)) {
                erreurPseudo = "Ce pseudonyme est déjà utilisé";
                return false;
            }
        }

        for (int i = 0; i < charList.length; i++) {

            if (pseudo.contains(Character.toString(charList[i]))) {
                return false;
            }
        }
        return true;

    }

    /**
     * fonction vérifiant les entrées et permettant ou non la création de la partie
     */
    private boolean validation(EditText input) {

        //enregistre les Strings venant des textEdits
        String name = input.getText().toString();
        boolean cancel = false;

        if (TextUtils.isEmpty(name)) {
            input.setError("Ce champ est obligatoire");
            Toast.makeText(context, input.getError(), Toast.LENGTH_SHORT).show();
            cancel = true;
        } else if (!isPseudoValid(name)) {
            input.setError(erreurPseudo);
            Toast.makeText(context, input.getError(), Toast.LENGTH_SHORT).show();
            cancel = true;
        }

        if (cancel) {
            return false;
        } else {
            return true;
        }
    }

    public void updateListJoueurs(List<Player> list) {
        this.playerList = list;
        listeUpdated = true;
    }

}
