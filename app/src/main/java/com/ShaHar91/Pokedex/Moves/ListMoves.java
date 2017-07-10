package com.ShaHar91.Pokedex.Moves;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;
import com.ShaHar91.Pokedex.R.drawable;
import com.ShaHar91.Pokedex.R.id;
import com.ShaHar91.Pokedex.R.layout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;

@SuppressWarnings("deprecation")
public class ListMoves extends ListActivity {
    public static final String ROW_ID = "row_id";

    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    Spinner searchMoveSpin;
    SearchView movesSearchV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.moves_list);

        movesSearchV = (SearchView) findViewById(id.movesSearchV);
        searchMoveSpin = (Spinner) findViewById(id.searchMoveSpin);

        if (Build.VERSION.SDK_INT >= 14) {
            searchMoveSpin
                    .setOnItemSelectedListener(new OnItemSelectedListener() {

                        @SuppressLint("NewApi")
                        @Override
                        public void onItemSelected(AdapterView<?> parent,
                                                   View view, int position, long id) {

                            switch (position) {
                                case 0:
                                    movesSearchV
                                            .setInputType(InputType.TYPE_CLASS_TEXT);
                                    break;
                                case 1:
                                    movesSearchV
                                            .setInputType(InputType.TYPE_CLASS_NUMBER);

                                    Cursor filterCursor = databaseConnector
                                            .getFilterMoves(searchMoveSpin
                                                            .getSelectedItem().toString(),
                                                    "");
                                    startManagingCursor(filterCursor);

                                    setListAdapter(new MyListAdapter(
                                            ListMoves.this, filterCursor));

                                    break;
                                case 2:
                                    movesSearchV
                                            .setInputType(InputType.TYPE_CLASS_TEXT);

                                    break;
                                case 3:
                                    movesSearchV
                                            .setInputType(InputType.TYPE_CLASS_NUMBER);

                                    break;
                                case 4:
                                    movesSearchV
                                            .setInputType(InputType.TYPE_CLASS_NUMBER);

                                    break;

                                default:
                                    movesSearchV
                                            .setInputType(InputType.TYPE_CLASS_TEXT);

                                    break;
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
        }

        databaseConnector.openDataBase();

        Cursor listCursor = databaseConnector.getAllMoves();
        startManagingCursor(listCursor);

        setListAdapter(new MyListAdapter(this, listCursor));

        movesSearchV.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(movesSearchV.getWindowToken(), 0);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor filterCursor = databaseConnector.getFilterMoves(
                        searchMoveSpin.getSelectedItem().toString(),
                        newText.toString());
                startManagingCursor(filterCursor);

                setListAdapter(new MyListAdapter(ListMoves.this, filterCursor));
                return false;
            }
        });

//		        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("86D27541E8C4847FEDB6814DB793E1C0")
                .build();
        mAdView.loadAd(request);


    }

    private class MyListAdapter extends ResourceCursorAdapter {

        public MyListAdapter(Context context, Cursor c) {
            super(context, layout.moves_list_item, c);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView name = (TextView) view.findViewById(id.nameMoveTv);
            TextView basePower = (TextView) view.findViewById(id.basePowerTv);
            TextView accuracy = (TextView) view.findViewById(id.accuracyTv);
            TextView powerPoints = (TextView) view
                    .findViewById(id.powerPointsTv);
            Button type = (Button) view.findViewById(id.typeBtn);

            name.setText(cursor.getString(cursor.getColumnIndex("name")));


            basePower.setText(cursor.getString(cursor
                    .getColumnIndex("base_power")));
            accuracy.setText(cursor.getString(cursor.getColumnIndex("accuracy")));
            powerPoints.setText(cursor.getString(cursor
                    .getColumnIndex("power_points")));

            type.setText(cursor.getString(cursor.getColumnIndex("type")));

            if (cursor.getString(cursor.getColumnIndex("type")).contains("Bug")) {
                type.setBackgroundResource(drawable.bug_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Dark")) {
                type.setBackgroundResource(drawable.dark_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Dragon")) {
                type.setBackgroundResource(drawable.dragon_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Electric")) {
                type.setBackgroundResource(drawable.electric_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Fairy")) {
                type.setBackgroundResource(drawable.fairy_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Fight")) {
                type.setText("Fight");
                type.setBackgroundResource(drawable.fight_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Fire")) {
                type.setBackgroundResource(drawable.fire_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Flying")) {
                type.setBackgroundResource(drawable.flying_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Ghost")) {
                type.setBackgroundResource(drawable.ghost_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Grass")) {
                type.setBackgroundResource(drawable.grass_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Ground")) {
                type.setBackgroundResource(drawable.ground_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Ice")) {
                type.setBackgroundResource(drawable.ice_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Normal")) {
                type.setBackgroundResource(drawable.normal_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Poison")) {
                type.setBackgroundResource(drawable.poison_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Psychic")) {
                type.setBackgroundResource(drawable.psychic_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Rock")) {
                type.setBackgroundResource(drawable.rock_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Steel")) {
                type.setBackgroundResource(drawable.steel_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type"))
                    .contains("Water")) {
                type.setBackgroundResource(drawable.water_button_border);
            } else {
                type.setBackgroundResource(drawable.normal_button_border);
            }

        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent viewMove = new Intent(ListMoves.this, ViewMove.class);

        viewMove.putExtra(ROW_ID, id);
        startActivity(viewMove);

    }
}
