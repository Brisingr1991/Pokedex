package com.ShaHar91.Pokedex.IVLibrary;

import java.util.ArrayList;

import com.ShaHar91.Pokedex.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SearchView.OnQueryTextListener;

public class IVLibrary extends ListActivity {
    /*  18/13/2017
    * advanced searchbox code opgeruimd
    * clearCursor toegevoegd
    * */

    ListAdapter pokeListAdapter;
    ListView lvCustomList;
    String searchName = "";
    SearchView searchV;
    Cursor myCur = null;

    IVLibraryDatabaseConnector IVdatabaseConnector = new IVLibraryDatabaseConnector(
            this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivlibrary);

        lvCustomList = (ListView) findViewById(android.R.id.list);
        searchV = (SearchView) findViewById(R.id.abilitySearchV);

        IVdatabaseConnector.openDataBase();

        clearCursor("");

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
                searchName = newText;

                clearCursor(searchName);


                showFilterList();
                return false;
            }
        });

       /* AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("86D27541E8C4847FEDB6814DB793E1C0")
                .build();
        mAdView.loadAd(request);*/
    }

    public void clearCursor(String newText) {
        myCur = IVdatabaseConnector.getAllPokesFilter(
                newText, "", "", "", "", "", "", "", "");


    }

    private void showFilterList() {
        ArrayList<PokeListItems> pokeList = new ArrayList<>();
        pokeList.clear();
        IVdatabaseConnector.openDataBase();

        if (myCur != null && myCur.getCount() != 0) {
            if (myCur.moveToFirst()) {
                do {
                    PokeListItems pokeListItems = new PokeListItems(
                            myCur.getString(myCur.getColumnIndex("name")),
                            myCur.getString(myCur.getColumnIndex("gender")),
                            false,
                            myCur.getInt(myCur.getColumnIndex("hp")),
                            myCur.getInt(myCur.getColumnIndex("att")),
                            myCur.getInt(myCur.getColumnIndex("def")),
                            myCur.getInt(myCur.getColumnIndex("sp_att")),
                            myCur.getInt(myCur.getColumnIndex("sp_def")),
                            myCur.getInt(myCur.getColumnIndex("speed")),
                            myCur.getInt(myCur.getColumnIndex("_id")),
                            myCur.getString(myCur.getColumnIndex("nat_dex")));
                    pokeList.add(pokeListItems);
                } while (myCur.moveToNext());
            }
        }
        pokeListAdapter = new PokeListAdapter(IVLibrary.this, pokeList);
        lvCustomList.setAdapter(pokeListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add_poke) {
            if (IVdatabaseConnector != null)
                IVdatabaseConnector.close();
            Intent addPoke = new Intent(IVLibrary.this, AddPoke.class);
            startActivity(addPoke);
            return true;
        } else if (itemId == R.id.quick_check) {
            Intent quickCheck = new Intent(IVLibrary.this, QuickCheck.class);
            startActivity(quickCheck);
            return true;
        } else if (itemId == R.id.delete_poke) {
            if (IVdatabaseConnector != null)
                IVdatabaseConnector.close();
            Intent deleteMultiPoke = new Intent(IVLibrary.this,
                    DeleteMultiPoke.class);
            startActivity(deleteMultiPoke);
            return true;
        } else if (itemId == R.id.egg_groups) {
            Intent dexList = new Intent(IVLibrary.this, EggGroups.class);
            startActivity(dexList);
            return true;
        } else if (itemId == R.id.advanced_search) {
            searchV.setQuery("", false);
            searchV.clearFocus();

            final Dialog nagDialog = new Dialog(IVLibrary.this,
                    android.R.style.Theme_Holo_Dialog_NoActionBar);
            nagDialog.setCancelable(false);

            nagDialog.setContentView(R.layout.ivlibrary_advanced_search);
            Button multiSearch = (Button) nagDialog
                    .findViewById(R.id.multiSearchBtn);
            final Spinner nature = (Spinner) nagDialog
                    .findViewById(R.id.natureSpin);
            final Spinner egg_groups = (Spinner) nagDialog
                    .findViewById(R.id.egg_group_list);
            final CheckBox hp = (CheckBox) nagDialog
                    .findViewById(R.id.searchHpcBox);
            final CheckBox att = (CheckBox) nagDialog
                    .findViewById(R.id.searchAttcBox);
            final CheckBox def = (CheckBox) nagDialog
                    .findViewById(R.id.searchDefcBox);
            final CheckBox spAtt = (CheckBox) nagDialog
                    .findViewById(R.id.searchSpAttcBox);
            final CheckBox spDef = (CheckBox) nagDialog
                    .findViewById(R.id.searchSpDefcBox);
            final CheckBox speed = (CheckBox) nagDialog
                    .findViewById(R.id.searchSpeedcBox);
            multiSearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    myCur = IVdatabaseConnector.getAllPokesFilter(searchName, nature
                                    .getSelectedItem().toString(), egg_groups
                                    .getSelectedItem().toString(), attributeCheck(hp), attributeCheck(att),
                            attributeCheck(def), attributeCheck(spAtt), attributeCheck(spDef), attributeCheck(speed));
                    showFilterList();
                    nagDialog.dismiss();
                }
            });
            nagDialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public String attributeCheck(CheckBox attribute) {
        if (attribute.isChecked()) {
            return "1";
        } else {
            return "";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IVdatabaseConnector.openDataBase();
        myCur = null;
        clearCursor(searchName);
        showFilterList();
    }
}
