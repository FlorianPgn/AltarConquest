package com.example.florian.altarconquest.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.florian.altarconquest.Controller.JoinGameListener;
import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Florian on 04/12/2016.
 */

public class MyListGameAdapter extends BaseAdapter implements ListAdapter {

    private List<Game> list;
    private Context context;

    public MyListGameAdapter(List<Game> list, Context context) {
        this.list = new ArrayList<>();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Game game = (Game) it.next();
            if (!game.isEnCours()) {
                this.list.add(game);
            }
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return list.indexOf(list.get(pos));
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_game_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView) view.findViewById(R.id.list_item_text);
        listItemText.setText(list.get(position).getName());

        TextView listNbJoueurs = (TextView) view.findViewById(R.id.list_nb_joueurs);
        listNbJoueurs.setText("Nombre de joueurs : " + list.get(position).getNbJoueurs() + "/" + list.get(position).getNbJoueursMax());

        //Handle buttons and add onClickListeners
        Button joinBtn = (Button) view.findViewById(R.id.list_item_button);

        if (!(list.get(position).getNbJoueurs() >= list.get(position).getNbJoueursMax())) {
            joinBtn.setOnClickListener(new JoinGameListener(context, list.get(position)));
        }
        return view;
    }
}