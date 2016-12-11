package com.example.florian.altarconquest.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.florian.altarconquest.Controller.JoinGameListener;
import com.example.florian.altarconquest.Model.Player;
import com.example.florian.altarconquest.R;

import java.util.List;

/**
 * Created by Hugo on 08/12/2016.
 */

public class MyListPlayerAdapter extends BaseAdapter implements ListAdapter {

    public List<Player> list;
    private Context context;

    public MyListPlayerAdapter(List<Player> list, Context context) {
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
            view = inflater.inflate(R.layout.list_player_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_text);
        listItemText.setText(list.get(position).getPseudo());

        return view;
    }
}