package com.example.florian.altarconquest.View;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.florian.altarconquest.Model.Game;
import com.example.florian.altarconquest.R;

import java.util.List;

/**
 * Created by Florian on 04/12/2016.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {

    private List<Game> list;
    private Context context;

    public MyCustomAdapter(List<Game> list, Context context) {
        this.list = list;
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
            view = inflater.inflate(R.layout.list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_text);
        listItemText.setText(list.get(position).getName());

        TextView listNbJoueurs = (TextView)view.findViewById(R.id.list_nb_joueurs);
        listNbJoueurs.setText("Nombre de joueurs : " + list.get(position).getNbJoueurs() + "/" + list.get(position).getNbJoueursMax());

        //Handle buttons and add onClickListeners
        Button joinBtn = (Button)view.findViewById(R.id.list_item_button);

        joinBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Log.i("NB JOUEURS - " + list.get(position).getNbJoueurs(), "NB JOUEURS MAX -" + list.get(position).getNbJoueursMax());
                if (list.get(position).getNbJoueurs() <= list.get(position).getNbJoueursMax())
                {
                    list.get(position).setNbJoueurs(list.get(position).getNbJoueurs() + 1);
                    Intent intent = new Intent(context, EcranChoix_Equipe.class);
                    list.remove(position); //or some other task
                    context.startActivity(intent);
                    notifyDataSetChanged();
                }
                else
                {
                    Toast toast = Toast.makeText(context, "Cette partie est complète, veuillez en choisir une autre :)", Toast.LENGTH_SHORT);
                    toast.show();
                }



            }
        });

        return view;
    }
}