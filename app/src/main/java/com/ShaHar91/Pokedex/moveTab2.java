package com.ShaHar91.Pokedex;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class moveTab2 extends ListFragment {
    private long rowID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.move_tab_2_egg, container,
                false);
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();
        rowID = extras.getLong("row_id");

        DatabaseConnector databaseConnector = new DatabaseConnector(
                getActivity());

        databaseConnector.openDataBase();

        Cursor listCursor = databaseConnector.getAllPokeMoves(rowID, 2);

        setListAdapter(new MyListAdapter(getActivity(), listCursor));
    }

    private class MyListAdapter extends ResourceCursorAdapter {

        public MyListAdapter(Context context, Cursor c) {
            super(context, R.layout.move_tab_2_egg_item, c);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView lvl = (TextView) view.findViewById(R.id.lvlMoveTv);
            TextView name = (TextView) view.findViewById(R.id.nameMoveTv);
            TextView basePower = (TextView) view.findViewById(R.id.basePowerTv);
            TextView powerPoints = (TextView) view
                    .findViewById(R.id.powerPointsTv);
            Button type = (Button) view.findViewById(R.id.typeBtn);

            name.setText(cursor.getString(cursor.getColumnIndex("name")));
            basePower.setText(cursor.getString(cursor
                    .getColumnIndex("base_power")));
            powerPoints.setText(cursor.getString(cursor
                    .getColumnIndex("power_points")));

            ImageView imView = (ImageView) view
                    .findViewById(R.id.pokeSpriteTab2);

            AssetManager assetManager = getActivity().getAssets();

            type.setText(cursor.getString(cursor.getColumnIndex("type")));

            if (cursor.getString(cursor.getColumnIndex("type")).contains("Bug")) {
                type.setBackgroundResource(R.drawable.bug_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Dark")) {
                type.setBackgroundResource(R.drawable.dark_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Dragon")) {
                type.setBackgroundResource(R.drawable.dragon_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Electric")) {
                type.setBackgroundResource(R.drawable.electric_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Fairy")) {
                type.setBackgroundResource(R.drawable.fairy_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Fight")) {
                type.setText("Fight");
                type.setBackgroundResource(R.drawable.fight_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Fire")) {
                type.setBackgroundResource(R.drawable.fire_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Flying")) {
                type.setBackgroundResource(R.drawable.flying_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Ghost")) {
                type.setBackgroundResource(R.drawable.ghost_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Grass")) {
                type.setBackgroundResource(R.drawable.grass_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Ground")) {
                type.setBackgroundResource(R.drawable.ground_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Ice")) {
                type.setBackgroundResource(R.drawable.ice_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Normal")) {
                type.setBackgroundResource(R.drawable.normal_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Poison")) {
                type.setBackgroundResource(R.drawable.poison_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Psychic")) {
                type.setBackgroundResource(R.drawable.psychic_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Rock")) {
                type.setBackgroundResource(R.drawable.rock_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Steel")) {
                type.setBackgroundResource(R.drawable.steel_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Water")) {
                type.setBackgroundResource(R.drawable.water_button_border);
            }

        }
    }
}