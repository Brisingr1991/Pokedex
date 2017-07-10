package com.ShaHar91.Pokedex.Abilities;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;
import com.ShaHar91.Pokedex.R.id;
import com.ShaHar91.Pokedex.R.layout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;

public class ListAbilities extends ListActivity {

    public static final String ROW_ID = "row_id";

    SimpleCursorAdapter abilityAdapter;
    DatabaseConnector databaseConnector = new DatabaseConnector(this);
    SearchView abilitySearchV;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(layout.ability_list);
        abilitySearchV = (SearchView) findViewById(id.abilitySearchV);

        new GetAbilitiesTask().execute();

        String[] from = new String[]{"name"};
        int[] to = new int[]{android.R.id.text1};
        abilityAdapter = new SimpleCursorAdapter(ListAbilities.this,
                android.R.layout.simple_list_item_1, null, from, to);
        setListAdapter(abilityAdapter);

        abilitySearchV.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(abilitySearchV.getWindowToken(), 0);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                abilityAdapter.getFilter().filter(newText.toString());
                DatabaseConnector databaseConnector = new DatabaseConnector(
                        ListAbilities.this);

                databaseConnector.close();
                return false;
            }
        });

        abilityAdapter.setFilterQueryProvider(new FilterQueryProvider() {

            @Override
            public Cursor runQuery(CharSequence constraint) {
                DatabaseConnector databaseConnector = new DatabaseConnector(
                        ListAbilities.this);

                databaseConnector.openDataBase();
                return databaseConnector.filterAbilities(constraint);

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

    private class GetAbilitiesTask extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            databaseConnector.openDataBase();

            return databaseConnector.getAllAbilities();
        }

        @Override
        protected void onPostExecute(Cursor result) {
            abilityAdapter.changeCursor(result);
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent viewAbility = new Intent(ListAbilities.this, ViewAbility.class);

        viewAbility.putExtra(ROW_ID, id);
        startActivity(viewAbility);

    }

}
