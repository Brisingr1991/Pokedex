package com.ShaHar91.Pokedex.TypeChart;

import java.util.ArrayList;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.PokeDexListItems;
import com.ShaHar91.Pokedex.R;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;

public class TypeChartPokeList extends ListActivity {
    public String eggGroup;
    public Spinner groups;

    public static final String ROW_ID = "row_id"; // Intent extra key
    ListAdapter pokeDexListAdapter;

    ListView lvCustomList;
    Spinner searchSpin;

    SearchView searchV;
    Bundle extras;

    Cursor myCur;

    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nat_dex_list);

        lvCustomList = (ListView) findViewById(android.R.id.list);
        searchV = (SearchView) findViewById(R.id.dexSearchV);
        searchSpin = (Spinner) findViewById(R.id.searchSpin);
        searchSpin.setVisibility(View.GONE);

        databaseConnector.openDataBase();

        extras = getIntent().getExtras();

        myCur = null;

        myCur = databaseConnector.getPokemonWithTypes("",
                extras.getString("type1Temp"), extras.getString("type2Temp"));
        showList();

        searchV.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchV.getWindowToken(), 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myCur = databaseConnector.getPokemonWithTypes(
                        newText.toString(), extras.getString("type1Temp"),
                        extras.getString("type2Temp"));

                showList();
                return false;
            }
        });
    }

    private void showList() {
        ArrayList<PokeDexListItems> pokeDexList = new ArrayList<PokeDexListItems>();

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

                    pokeDexListItems.setLongNatDex(myCur.getLong(myCur
                            .getColumnIndex("nat_dex")));

                    pokeDexListItems.setisMega(myCur.getInt(myCur
                            .getColumnIndex("isMega")));

                    pokeDexListItems.setRowIdTag(myCur.getInt(myCur
                            .getColumnIndex("_id")));

                    pokeDexListItems.setDrawable(myCur.getString(myCur
                            .getColumnIndex("_id")));

                    pokeDexList.add(pokeDexListItems);

                } while (myCur.moveToNext());
            }
        }

        myCur.close();

        pokeDexListAdapter = new TypeChartDexListAdapter(
                TypeChartPokeList.this, pokeDexList);
        lvCustomList.setAdapter(pokeDexListAdapter);

    }

    @Override
    protected void onResume() {
        databaseConnector.openDataBase();

        super.onResume();
    }

}
