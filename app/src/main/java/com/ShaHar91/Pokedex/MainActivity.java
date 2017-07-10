package com.ShaHar91.Pokedex;

import com.ShaHar91.Pokedex.Abilities.ListAbilities;
import com.ShaHar91.Pokedex.Calc.ViewCalc;
import com.ShaHar91.Pokedex.IVLibrary.IVLibrary;
import com.ShaHar91.Pokedex.IVLibrary.IVLibraryDatabaseConnector;
import com.ShaHar91.Pokedex.Items.PocketList;
import com.ShaHar91.Pokedex.Moves.ListMoves;
import com.ShaHar91.Pokedex.TypeChart.TypeChart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity implements OnClickListener {
    private DatabaseConnector databaseConnector = null;
    private IVLibraryDatabaseConnector IVdatabaseConnector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IVdatabaseConnector = new IVLibraryDatabaseConnector(MainActivity.this);
        IVdatabaseConnector.createDataBase();
        databaseConnector = new DatabaseConnector(MainActivity.this);
        databaseConnector.createDataBase();

        setContentView(R.layout.main_layout);

        Button gotoLibrary = (Button) findViewById(R.id.libraryBtn);
        Button pokedex = (Button) findViewById(R.id.pokedexBtn);
        Button abilities = (Button) findViewById(R.id.abilityBtn);
        Button moves = (Button) findViewById(R.id.movesBtn);
        Button typeChart = (Button) findViewById(R.id.typeChartBtn);
        Button about = (Button) findViewById(R.id.aboutBtn);
        Button calc = (Button) findViewById(R.id.calcBtn);
        Button items = (Button) findViewById(R.id.itemsBtn);

        pokedex.setOnClickListener(this);
        gotoLibrary.setOnClickListener(this);
        abilities.setOnClickListener(this);
        moves.setOnClickListener(this);
        typeChart.setOnClickListener(this);
        about.setOnClickListener(this);
        calc.setOnClickListener(this);
        items.setOnClickListener(this);
        items.setVisibility(View.GONE);

//        AdView mAdView = (AdView) findViewById(R.id.adView);
//		AdRequest request = new AdRequest.Builder()
//				.addTestDevice("86D27541E8C4847FEDB6814DB793E1C0")
//				.build();
//		mAdView.loadAd(request);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.libraryBtn) {
            Intent ivLibrary = new Intent(MainActivity.this, IVLibrary.class);
            startActivity(ivLibrary);
        } else if (id == R.id.abilityBtn) {
            Intent abilities = new Intent(MainActivity.this, ListAbilities.class);
            startActivity(abilities);
        } else if (id == R.id.pokedexBtn) {
            Intent pokedex = new Intent(MainActivity.this, Pokedex.class);
            startActivity(pokedex);
        } else if (id == R.id.movesBtn) {
            Intent moves = new Intent(MainActivity.this, ListMoves.class);
            startActivity(moves);
        } else if (id == R.id.typeChartBtn) {
            Intent typeChart = new Intent(MainActivity.this, TypeChart.class);
            startActivity(typeChart);
        } else if (id == R.id.calcBtn) {
            Intent calc = new Intent(MainActivity.this, ViewCalc.class);
            startActivity(calc);
        } else if (id == R.id.itemsBtn) {
            Intent items = new Intent(MainActivity.this, PocketList.class);
            startActivity(items);
        } else if (id == R.id.aboutBtn) {
            Intent about = new Intent(MainActivity.this, About.class);
            startActivity(about);
        }
    }
}
