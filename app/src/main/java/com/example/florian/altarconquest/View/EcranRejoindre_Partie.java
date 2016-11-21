package com.example.florian.altarconquest.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.florian.altarconquest.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EcranRejoindre_Partie extends Activity
{

    private ArrayList<String> liste_de_nom_de_parties = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejoindre_partie);

        ListView liste_parties = (ListView) findViewById(R.id.liste_parties);
        generateListContent();
        liste_parties.setAdapter(new myListAdapater(this, R.layout.list_item, liste_de_nom_de_parties));

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

    public void generateListContent()
    {
        liste_de_nom_de_parties.add("Partie de Hugo");
        liste_de_nom_de_parties.add("Partie de Matthieu");
        liste_de_nom_de_parties.add("Partie de Thomas");
        liste_de_nom_de_parties.add("Partie de Nico");
        liste_de_nom_de_parties.add("Partie de Flo");
        liste_de_nom_de_parties.add("Partie de Iza");
        liste_de_nom_de_parties.add("Partie de Nenet the God");
    }

    public void ouvrirGestion_Partie()
    {
        Intent intent = new Intent(this, EcranGestion_Partie.class);
        startActivity(intent);
    }

    public void ouvrirChoix_Equipe()
    {
        Intent intent = new Intent(this, EcranChoix_Equipe.class);
        startActivity(intent);
    }

    private class myListAdapater extends ArrayAdapter<String>
    {
        private int layout;
        private myListAdapater(Context context, int resource, List<String> objects)
        {
            super(context, resource, objects);
            layout = resource;
        }

        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder mainViewHolder = null;
            if (convertView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.pic = (ImageView) convertView.findViewById(R.id.list_item_pic);
                viewHolder.text = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_button);
                viewHolder.button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(getContext(), "Vous avez rejoint la partie : " + liste_de_nom_de_parties.get(position), Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(viewHolder);
            }
            else
            {
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.text.setText(getItem(position));
            }
            return convertView;
        }
    }

    public class ViewHolder
    {
        ImageView pic;
        TextView text;
        Button button;
    }
}