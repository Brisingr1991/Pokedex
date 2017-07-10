package com.ShaHar91.Pokedex;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Pokedex extends ListActivity {
    public static final String ROW_ID = "row_id"; // Intent extra key
    ListAdapter pokeDexListAdapter;

    ListView lvCustomList;
    Spinner searchSpin;

    SearchView searchV;

    Cursor myCur;

    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nat_dex_list);

        lvCustomList = (ListView) findViewById(android.R.id.list);
        searchV = (SearchView) findViewById(R.id.dexSearchV);
        searchSpin = (Spinner) findViewById(R.id.searchSpin);

        searchSpin.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                searchV.setQuery("", true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        databaseConnector.openDataBase();

        myCur = null;

        myCur = databaseConnector.getFilterPokeList(searchSpin
                .getSelectedItem().toString(), "");

        showFilterList();

        searchV.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchV.getWindowToken(), 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myCur = databaseConnector.getFilterPokeList(searchSpin
                        .getSelectedItem().toString(), newText);
                startManagingCursor(myCur);

                showFilterList();
                return false;
            }
        });


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("86D27541E8C4847FEDB6814DB793E1C0")
                .build();
        mAdView.loadAd(request);

    }

    private void showFilterList() {
        ArrayList<PokeDexListItems> pokeDexList = new ArrayList<>();

        pokeDexList.clear();

        if (myCur != null && myCur.getCount() != 0) {
            if (myCur.moveToFirst()) {
                do {
                    PokeDexListItems pokeDexListItems = new PokeDexListItems();

                    pokeDexListItems.setpokeName(myCur.getString(myCur
                            .getColumnIndex("name")));
                    pokeDexListItems.setNatDex(myCur.getString(myCur
                            .getColumnIndex("nat_dex")));
                    pokeDexListItems.setType1Dex(myCur.getString(myCur
                            .getColumnIndex("type1")));
                    pokeDexListItems.setType2Dex(myCur.getString(myCur
                            .getColumnIndex("type2")));

                    pokeDexListItems.setRowIdTag(myCur.getInt(myCur
                            .getColumnIndex("_id")));

                    pokeDexListItems.setDrawable(myCur.getString(myCur
                            .getColumnIndex("_id")));

                    pokeDexList.add(pokeDexListItems);

                } while (myCur.moveToNext());
            }
        }

        pokeDexListAdapter = new PokeDexListAdapter(Pokedex.this, pokeDexList);
        lvCustomList.setAdapter(pokeDexListAdapter);

    }

    @Override
    protected void onResume() {
        databaseConnector.openDataBase();

        super.onResume();
    }

}
