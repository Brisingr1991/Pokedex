package com.ShaHar91.Pokedex.TypeChart;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;
import com.ShaHar91.Pokedex.R.color;
import com.ShaHar91.Pokedex.R.id;
import com.ShaHar91.Pokedex.R.layout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TypeChart extends Activity {

    LinearLayout containerLl, containerNoPokes;
    String type1Temp, type2Temp = "";
    Spinner type1, type2;
    TextView normalTv, fireTv, waterTv, electricTv, grassTv, iceTv, fightTv,
            poisonTv, groundTv, flyingTv, psychicTv, bugTv, rockTv, ghostTv,
            dragonTv, darkTv, steelTv, fairyTv, noPokes;
    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.type_chart);

        databaseConnector.openDataBase();

        containerLl = (LinearLayout) findViewById(id.containerLl);
        containerNoPokes = (LinearLayout) findViewById(id.containerNoPokes);

        type1 = (Spinner) findViewById(id.type1Sp);
        type2 = (Spinner) findViewById(id.type2Sp);

        normalTv = (TextView) findViewById(id.normalXTv);
        fireTv = (TextView) findViewById(id.fireXTv);
        waterTv = (TextView) findViewById(id.waterXTv);
        electricTv = (TextView) findViewById(id.electricXTv);
        grassTv = (TextView) findViewById(id.grassXTv);
        iceTv = (TextView) findViewById(id.iceXTv);
        fightTv = (TextView) findViewById(id.fightXTv);
        poisonTv = (TextView) findViewById(id.poisonXTv);
        groundTv = (TextView) findViewById(id.groundXTv);
        flyingTv = (TextView) findViewById(id.flyingXTv);
        psychicTv = (TextView) findViewById(id.psychicXTv);
        bugTv = (TextView) findViewById(id.bugXTv);
        rockTv = (TextView) findViewById(id.rockXTv);
        ghostTv = (TextView) findViewById(id.ghostXTv);
        dragonTv = (TextView) findViewById(id.dragonXTv);
        darkTv = (TextView) findViewById(id.darkXTv);
        steelTv = (TextView) findViewById(id.steelXTv);
        fairyTv = (TextView) findViewById(id.fairyXTv);

        noPokes = (TextView) findViewById(id.noPokes);

        type1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Toast.makeText(com.ShaHar91.Pokedex.TypeChart.this,
                // type1.getSelectedItem().toString(), Toast.LENGTH_SHORT)
                // .show();

                type1Temp = type1.getSelectedItem().toString();
                if (type1Temp.toString().isEmpty()) {
                    invisible();

                } else if (type1Temp.toString() == type2Temp.toString()) {
                    Toast.makeText(TypeChart.this,
                            "Please don't use the same types in both spinners",
                            Toast.LENGTH_SHORT).show();

                } else {

                    new LoadWeaknessTask().execute(type1Temp.toString(),
                            type2Temp.toString());

                    new LoadNoPokesTask().execute("", type1Temp.toString(),
                            type2Temp.toString());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        type2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                type2Temp = type2.getSelectedItem().toString();

                type1Temp = type1.getSelectedItem().toString();
                if (type1Temp.toString().isEmpty()) {
                    invisible();

                } else if (type1Temp.toString() == type2Temp.toString()) {
                    Toast.makeText(TypeChart.this,
                            "Please don't use the same types in both spinners",
                            Toast.LENGTH_SHORT).show();

                } else {

                    new LoadWeaknessTask().execute(type1Temp.toString(),
                            type2Temp.toString());

                    new LoadNoPokesTask().execute("", type1Temp.toString(),
                            type2Temp.toString());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//                AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest request = new AdRequest.Builder()
//                .addTestDevice("86D27541E8C4847FEDB6814DB793E1C0")
//                .build();
//        mAdView.loadAd(request);

    }

    protected void invisible() {
        containerLl.setVisibility(View.INVISIBLE);
        containerNoPokes.setVisibility(View.INVISIBLE);

    }

    private class LoadWeaknessTask extends AsyncTask<String, Object, Cursor> {

        @Override
        protected Cursor doInBackground(String... params) {
            databaseConnector.openDataBase();
            // get a cursor containing all data on given entry
            return databaseConnector.getWeakness(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToFirst(); // move to first item

            containerLl.setVisibility(View.VISIBLE);
            // get the column index for each data item
            int normalIndex = result.getColumnIndex("normal");
            int fireIndex = result.getColumnIndex("fire");
            int waterIndex = result.getColumnIndex("water");
            int electricIndex = result.getColumnIndex("electric");
            int grassIndex = result.getColumnIndex("grass");
            int iceIndex = result.getColumnIndex("ice");
            int fightIndex = result.getColumnIndex("fight");
            int poisonIndex = result.getColumnIndex("poison");
            int groundIndex = result.getColumnIndex("ground");
            int flyingIndex = result.getColumnIndex("flying");
            int psychicIndex = result.getColumnIndex("psychic");
            int bugIndex = result.getColumnIndex("bug");
            int rockIndex = result.getColumnIndex("rock");
            int ghostIndex = result.getColumnIndex("ghost");
            int dragonIndex = result.getColumnIndex("dragon");
            int darkIndex = result.getColumnIndex("dark");
            int steelIndex = result.getColumnIndex("steel");
            int fairyIndex = result.getColumnIndex("fairy");

            normalTv.setText(result.getString(normalIndex));
            fireTv.setText(result.getString(fireIndex));
            waterTv.setText(result.getString(waterIndex));
            electricTv.setText(result.getString(electricIndex));
            grassTv.setText(result.getString(grassIndex));
            iceTv.setText(result.getString(iceIndex));
            fightTv.setText(result.getString(fightIndex));
            poisonTv.setText(result.getString(poisonIndex));
            groundTv.setText(result.getString(groundIndex));
            flyingTv.setText(result.getString(flyingIndex));
            psychicTv.setText(result.getString(psychicIndex));
            bugTv.setText(result.getString(bugIndex));
            rockTv.setText(result.getString(rockIndex));
            ghostTv.setText(result.getString(ghostIndex));
            dragonTv.setText(result.getString(dragonIndex));
            darkTv.setText(result.getString(darkIndex));
            steelTv.setText(result.getString(steelIndex));
            fairyTv.setText(result.getString(fairyIndex));

            if (result.getString(normalIndex).contains("x0.0")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        color.x0_0));
            } else if (result.getString(normalIndex).contains("x0.25")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(normalIndex).contains("x0.5")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        color.x0_5));
            } else if (result.getString(normalIndex).contains("x2.0")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        color.x2_0));
            } else if (result.getString(normalIndex).contains("x4.0")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        color.x4_0));
            } else {
                normalTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fireIndex).contains("x0.0")) {
                fireTv.setBackgroundColor(getResources().getColor(color.x0_0));
            } else if (result.getString(fireIndex).contains("x0.25")) {
                fireTv.setBackgroundColor(getResources()
                        .getColor(color.x0_25));
            } else if (result.getString(fireIndex).contains("x0.5")) {
                fireTv.setBackgroundColor(getResources().getColor(color.x0_5));
            } else if (result.getString(fireIndex).contains("x2.0")) {
                fireTv.setBackgroundColor(getResources().getColor(color.x2_0));
            } else if (result.getString(fireIndex).contains("x4.0")) {
                fireTv.setBackgroundColor(getResources().getColor(color.x4_0));
            } else {
                fireTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(waterIndex).contains("x0.0")) {
                waterTv.setBackgroundColor(getResources()
                        .getColor(color.x0_0));
            } else if (result.getString(waterIndex).contains("x0.25")) {
                waterTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(waterIndex).contains("x0.5")) {
                waterTv.setBackgroundColor(getResources()
                        .getColor(color.x0_5));
            } else if (result.getString(waterIndex).contains("x2.0")) {
                waterTv.setBackgroundColor(getResources()
                        .getColor(color.x2_0));
            } else if (result.getString(waterIndex).contains("x4.0")) {
                waterTv.setBackgroundColor(getResources()
                        .getColor(color.x4_0));
            } else {
                waterTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(electricIndex).contains("x0.0")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        color.x0_0));
            } else if (result.getString(electricIndex).contains("x0.25")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(electricIndex).contains("x0.5")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        color.x0_5));
            } else if (result.getString(electricIndex).contains("x2.0")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        color.x2_0));
            } else if (result.getString(electricIndex).contains("x4.0")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        color.x4_0));
            } else {
                electricTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(grassIndex).contains("x0.0")) {
                grassTv.setBackgroundColor(getResources()
                        .getColor(color.x0_0));
            } else if (result.getString(grassIndex).contains("x0.25")) {
                grassTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(grassIndex).contains("x0.5")) {
                grassTv.setBackgroundColor(getResources()
                        .getColor(color.x0_5));
            } else if (result.getString(grassIndex).contains("x2.0")) {
                grassTv.setBackgroundColor(getResources()
                        .getColor(color.x2_0));
            } else if (result.getString(grassIndex).contains("x4.0")) {
                grassTv.setBackgroundColor(getResources()
                        .getColor(color.x4_0));
            } else {
                grassTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(iceIndex).contains("x0.0")) {
                iceTv.setBackgroundColor(getResources().getColor(color.x0_0));
            } else if (result.getString(iceIndex).contains("x0.25")) {
                iceTv.setBackgroundColor(getResources().getColor(color.x0_25));
            } else if (result.getString(iceIndex).contains("x0.5")) {
                iceTv.setBackgroundColor(getResources().getColor(color.x0_5));
            } else if (result.getString(iceIndex).contains("x2.0")) {
                iceTv.setBackgroundColor(getResources().getColor(color.x2_0));
            } else if (result.getString(iceIndex).contains("x4.0")) {
                iceTv.setBackgroundColor(getResources().getColor(color.x4_0));
            } else {
                iceTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fightIndex).contains("x0.0")) {
                fightTv.setBackgroundColor(getResources()
                        .getColor(color.x0_0));
            } else if (result.getString(fightIndex).contains("x0.25")) {
                fightTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(fightIndex).contains("x0.5")) {
                fightTv.setBackgroundColor(getResources()
                        .getColor(color.x0_5));
            } else if (result.getString(fightIndex).contains("x2.0")) {
                fightTv.setBackgroundColor(getResources()
                        .getColor(color.x2_0));
            } else if (result.getString(fightIndex).contains("x4.0")) {
                fightTv.setBackgroundColor(getResources()
                        .getColor(color.x4_0));
            } else {
                fightTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(poisonIndex).contains("x0.0")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        color.x0_0));
            } else if (result.getString(poisonIndex).contains("x0.25")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(poisonIndex).contains("x0.5")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        color.x0_5));
            } else if (result.getString(poisonIndex).contains("x2.0")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        color.x2_0));
            } else if (result.getString(poisonIndex).contains("x4.0")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        color.x4_0));
            } else {
                poisonTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(groundIndex).contains("x0.0")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        color.x0_0));
            } else if (result.getString(groundIndex).contains("x0.25")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(groundIndex).contains("x0.5")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        color.x0_5));
            } else if (result.getString(groundIndex).contains("x2.0")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        color.x2_0));
            } else if (result.getString(groundIndex).contains("x4.0")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        color.x4_0));
            } else {
                groundTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(flyingIndex).contains("x0.0")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        color.x0_0));
            } else if (result.getString(flyingIndex).contains("x0.25")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(flyingIndex).contains("x0.5")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        color.x0_5));
            } else if (result.getString(flyingIndex).contains("x2.0")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        color.x2_0));
            } else if (result.getString(flyingIndex).contains("x4.0")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        color.x4_0));
            } else {
                flyingTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(psychicIndex).contains("x0.0")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        color.x0_0));
            } else if (result.getString(psychicIndex).contains("x0.25")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(psychicIndex).contains("x0.5")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        color.x0_5));
            } else if (result.getString(psychicIndex).contains("x2.0")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        color.x2_0));
            } else if (result.getString(psychicIndex).contains("x4.0")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        color.x4_0));
            } else {
                psychicTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(bugIndex).contains("x0.0")) {
                bugTv.setBackgroundColor(getResources().getColor(color.x0_0));
            } else if (result.getString(bugIndex).contains("x0.25")) {
                bugTv.setBackgroundColor(getResources().getColor(color.x0_25));
            } else if (result.getString(bugIndex).contains("x0.5")) {
                bugTv.setBackgroundColor(getResources().getColor(color.x0_5));
            } else if (result.getString(bugIndex).contains("x2.0")) {
                bugTv.setBackgroundColor(getResources().getColor(color.x2_0));
            } else if (result.getString(bugIndex).contains("x4.0")) {
                bugTv.setBackgroundColor(getResources().getColor(color.x4_0));
            } else {
                bugTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(rockIndex).contains("x0.0")) {
                rockTv.setBackgroundColor(getResources().getColor(color.x0_0));
            } else if (result.getString(rockIndex).contains("x0.25")) {
                rockTv.setBackgroundColor(getResources()
                        .getColor(color.x0_25));
            } else if (result.getString(rockIndex).contains("x0.5")) {
                rockTv.setBackgroundColor(getResources().getColor(color.x0_5));
            } else if (result.getString(rockIndex).contains("x2.0")) {
                rockTv.setBackgroundColor(getResources().getColor(color.x2_0));
            } else if (result.getString(rockIndex).contains("x4.0")) {
                rockTv.setBackgroundColor(getResources().getColor(color.x4_0));
            } else {
                rockTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(ghostIndex).contains("x0.0")) {
                ghostTv.setBackgroundColor(getResources()
                        .getColor(color.x0_0));
            } else if (result.getString(ghostIndex).contains("x0.25")) {
                ghostTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(ghostIndex).contains("x0.5")) {
                ghostTv.setBackgroundColor(getResources()
                        .getColor(color.x0_5));
            } else if (result.getString(ghostIndex).contains("x2.0")) {
                ghostTv.setBackgroundColor(getResources()
                        .getColor(color.x2_0));
            } else if (result.getString(ghostIndex).contains("x4.0")) {
                ghostTv.setBackgroundColor(getResources()
                        .getColor(color.x4_0));
            } else {
                ghostTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(dragonIndex).contains("x0.0")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        color.x0_0));
            } else if (result.getString(dragonIndex).contains("x0.25")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(dragonIndex).contains("x0.5")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        color.x0_5));
            } else if (result.getString(dragonIndex).contains("x2.0")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        color.x2_0));
            } else if (result.getString(dragonIndex).contains("x4.0")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        color.x4_0));
            } else {
                dragonTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(darkIndex).contains("x0.0")) {
                darkTv.setBackgroundColor(getResources().getColor(color.x0_0));
            } else if (result.getString(darkIndex).contains("x0.25")) {
                darkTv.setBackgroundColor(getResources()
                        .getColor(color.x0_25));
            } else if (result.getString(darkIndex).contains("x0.5")) {
                darkTv.setBackgroundColor(getResources().getColor(color.x0_5));
            } else if (result.getString(darkIndex).contains("x2.0")) {
                darkTv.setBackgroundColor(getResources().getColor(color.x2_0));
            } else if (result.getString(darkIndex).contains("x4.0")) {
                darkTv.setBackgroundColor(getResources().getColor(color.x4_0));
            } else {
                darkTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(steelIndex).contains("x0.0")) {
                steelTv.setBackgroundColor(getResources()
                        .getColor(color.x0_0));
            } else if (result.getString(steelIndex).contains("x0.25")) {
                steelTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(steelIndex).contains("x0.5")) {
                steelTv.setBackgroundColor(getResources()
                        .getColor(color.x0_5));
            } else if (result.getString(steelIndex).contains("x2.0")) {
                steelTv.setBackgroundColor(getResources()
                        .getColor(color.x2_0));
            } else if (result.getString(steelIndex).contains("x4.0")) {
                steelTv.setBackgroundColor(getResources()
                        .getColor(color.x4_0));
            } else {
                steelTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fairyIndex).contains("x0.0")) {
                fairyTv.setBackgroundColor(getResources()
                        .getColor(color.x0_0));
            } else if (result.getString(fairyIndex).contains("x0.25")) {
                fairyTv.setBackgroundColor(getResources().getColor(
                        color.x0_25));
            } else if (result.getString(fairyIndex).contains("x0.5")) {
                fairyTv.setBackgroundColor(getResources()
                        .getColor(color.x0_5));
            } else if (result.getString(fairyIndex).contains("x2.0")) {
                fairyTv.setBackgroundColor(getResources()
                        .getColor(color.x2_0));
            } else if (result.getString(fairyIndex).contains("x4.0")) {
                fairyTv.setBackgroundColor(getResources()
                        .getColor(color.x4_0));
            } else {
                fairyTv.setBackgroundColor(Color.TRANSPARENT);
            }
            // fill the TextView with the retrieved data

            result.close();

        }
    }

    private class LoadNoPokesTask extends AsyncTask<String, Object, Cursor> {

        @Override
        protected Cursor doInBackground(String... params) {
            databaseConnector.openDataBase();
            // get a cursor containing all data on given entry
            return databaseConnector.getPokemonWithTypes(params[0], params[1],
                    params[2]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToFirst(); // move to first item

            containerNoPokes.setVisibility(View.VISIBLE);

            Integer count = result.getCount();

            if (count == 0) {
                noPokes.setText(count.toString());
                noPokes.setTextColor(Color.WHITE);

            } else {
                noPokes.setText(count.toString());
                noPokes.setTextColor(Color.BLUE);
                noPokes.setPaintFlags(noPokes.getPaintFlags()
                        | Paint.UNDERLINE_TEXT_FLAG);
                noPokes.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent pokedex = new Intent(TypeChart.this,
                                TypeChartPokeList.class);

                        pokedex.putExtra("type1Temp", type1Temp);
                        pokedex.putExtra("type2Temp", type2Temp);

                        startActivity(pokedex);

                    }
                });
            }

            // fill the TextView with the retrieved data

            result.close();

        }
    }
}
