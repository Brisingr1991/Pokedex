package com.ShaHar91.Pokedex.TypeChart;

import java.io.IOException;
import java.io.InputStream;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.PokedexEntryMega;
import com.ShaHar91.Pokedex.R;
import com.ShaHar91.Pokedex.ViewPokeMoves;
import com.ShaHar91.Pokedex.Abilities.ViewAbility;
import com.ShaHar91.Pokedex.CustomViews.TextProgressBar;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

@SuppressWarnings("deprecation")
public class TypeChartPokedexEntry extends ListActivity implements
        OnClickListener, OnCheckedChangeListener {
    DatabaseConnector databaseConnector = new DatabaseConnector(
            TypeChartPokedexEntry.this);
    private ImageView pokeDrawable, pokeDrawable2;

    public static final String ROW_ID = "row_id";
    public static final String NAT_DEX = "nat_dex";

    private long rowID;
    private long natDex;

    private String type1Temp, type2Temp, hasMegaTemp = "";

    private Button type1, type2, moves;

    private Menu menu;
    private MenuItem previous, next;

    private ToggleButton toggle_loc, toggle_desc;

    private ImageButton megaImg;

    private LinearLayout loc_layout, desc_layout;

    private TextView name, nat_dex, species, height, weight, gender, locationX,
            locationY, descriptionX, descriptionY, normalTv, fireTv, waterTv,
            electricTv, grassTv, iceTv, fightTv, poisonTv, groundTv, flyingTv,
            psychicTv, bugTv, rockTv, ghostTv, dragonTv, darkTv, steelTv,
            fairyTv, notesTv;

    private ScrollView dexScroll;

    private SimpleCursorAdapter abilityAdapter;

    private TextProgressBar hp, att, def, spAtt, spDef, speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pokedex_entry);

        setViews();

        new LoadAbilityDexTask().execute(rowID);
        // new GetCaught().execute(rowID);

        new LoadPokeDexTask().execute(rowID);
        new LoadPokeBaseStatTask().execute(rowID);

        String[] from = new String[]{"ability"};
        int[] to = new int[]{R.id.abilityNameTv};
        abilityAdapter = new SimpleCursorAdapter(TypeChartPokedexEntry.this,
                R.layout.ability_dex_list_item, null, from, to);
        setListAdapter(abilityAdapter);

        type2Temp = type2.getText().toString();

    }

    private void setViews() {

        dexScroll = (ScrollView) findViewById(R.id.dexScroll);

        name = (TextView) findViewById(R.id.name_dexTv);
        nat_dex = (TextView) findViewById(R.id.dex_natNoTv);
        species = (TextView) findViewById(R.id.dex_speciesTv);
        type1 = (Button) findViewById(R.id.type1Btn);
        type2 = (Button) findViewById(R.id.type2Btn);
        height = (TextView) findViewById(R.id.height_dexTv);
        weight = (TextView) findViewById(R.id.weight_dexTv);
        gender = (TextView) findViewById(R.id.gender_dexTv);
        locationX = (TextView) findViewById(R.id.location_xTv);
        locationY = (TextView) findViewById(R.id.location_yTv);
        descriptionX = (TextView) findViewById(R.id.description_xTv);
        descriptionY = (TextView) findViewById(R.id.description_yTv);
        moves = (Button) findViewById(R.id.moveBtn);
        megaImg = (ImageButton) findViewById(R.id.megaImg);
        megaImg.setOnClickListener(this);
        moves.setOnClickListener(this);

        normalTv = (TextView) findViewById(R.id.dexnormalXTv);
        fireTv = (TextView) findViewById(R.id.dexfireXTv);
        waterTv = (TextView) findViewById(R.id.dexwaterXTv);
        electricTv = (TextView) findViewById(R.id.dexelectricXTv);
        grassTv = (TextView) findViewById(R.id.dexgrassXTv);
        iceTv = (TextView) findViewById(R.id.dexiceXTv);
        fightTv = (TextView) findViewById(R.id.dexfightXTv);
        poisonTv = (TextView) findViewById(R.id.dexpoisonXTv);
        groundTv = (TextView) findViewById(R.id.dexgroundXTv);
        flyingTv = (TextView) findViewById(R.id.dexflyingXTv);
        psychicTv = (TextView) findViewById(R.id.dexpsychicXTv);
        bugTv = (TextView) findViewById(R.id.dexbugXTv);
        rockTv = (TextView) findViewById(R.id.dexrockXTv);
        ghostTv = (TextView) findViewById(R.id.dexghostXTv);
        dragonTv = (TextView) findViewById(R.id.dexdragonXTv);
        darkTv = (TextView) findViewById(R.id.dexdarkXTv);
        steelTv = (TextView) findViewById(R.id.dexsteelXTv);
        fairyTv = (TextView) findViewById(R.id.dexfairyXTv);
        notesTv = (TextView) findViewById(R.id.notesTv);

        toggle_loc = (ToggleButton) findViewById(R.id.toggleButton1);
        toggle_loc.setOnCheckedChangeListener(this);
        loc_layout = (LinearLayout) findViewById(R.id.Loc_layout);

        toggle_desc = (ToggleButton) findViewById(R.id.toggleButton2);
        toggle_desc.setOnCheckedChangeListener(this);
        desc_layout = (LinearLayout) findViewById(R.id.Desc_layout);

        hp = (TextProgressBar) findViewById(R.id.hpProgressBar);
        att = (TextProgressBar) findViewById(R.id.attProgressBar);
        def = (TextProgressBar) findViewById(R.id.defProgressBar);
        spAtt = (TextProgressBar) findViewById(R.id.spAttProgressBar);
        spDef = (TextProgressBar) findViewById(R.id.spDefProgressBar);
        speed = (TextProgressBar) findViewById(R.id.speedProgressBar);

        pokeDrawable = (ImageView) findViewById(R.id.pokeDrawable);
        pokeDrawable2 = (ImageView) findViewById(R.id.pokeDrawable2);

        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");

    }

    // private class GetCaught extends AsyncTask<Long, Object, Cursor> {
    //
    // // perform the database access
    // @Override
    // protected Cursor doInBackground(Long... params) {
    // databaseConnector.openDataBase();
    //
    // // get a cursor containing all data on given entry
    // return databaseConnector.getCaught(params[0]);
    // }
    //
    // @Override
    // protected void onPostExecute(Cursor result) {
    // super.onPostExecute(result);
    // if (result.moveToFirst()) { // move to first item
    // Toast.makeText(PokedexEntry.this, "the record already exist",
    // Toast.LENGTH_SHORT).show();
    // } else {
    // Toast.makeText(PokedexEntry.this, "the record does not exist",
    // Toast.LENGTH_SHORT).show();
    //
    // }
    //
    // // // get the column index for each data item
    // // int nameIndex = result.getColumnIndex("name");
    // //
    // // // fill the TextView with the retrieved data
    // // name.setText(result.getString(nameIndex));
    //
    // result.close();
    // }
    // }

    private class LoadAbilityDexTask extends AsyncTask<Long, Object, Cursor> {

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.openDataBase();

            // get a cursor containing all data on given entry
            return databaseConnector.getAbilityForOnePoke(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {

            abilityAdapter.changeCursor(result);

        }
    }

    private class LoadPokeDexTask extends AsyncTask<Long, Object, Cursor> {

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.openDataBase();

            // get a cursor containing all data on given entry
            return databaseConnector.getPokeForDex(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);
            result.moveToFirst(); // move to first item

            // get the column index for each data item
            int nameIndex = result.getColumnIndex("name");
            int genderIndex = result.getColumnIndex("gender_spread");
            int imageIndex = result.getColumnIndex("_id");
            int nat_dexIndex = result.getColumnIndex("nat_dex");
            int speciesIndex = result.getColumnIndex("species");
            int type1Index = result.getColumnIndex("type1");
            int temptype1Index = result.getColumnIndex("type1");
            int type2Index = result.getColumnIndex("type2");
            int temptype2Index = result.getColumnIndex("type2");

            int heightIndex = result.getColumnIndex("height");
            int weightIndex = result.getColumnIndex("weight");
            int locationXIndex = result.getColumnIndex("location_x");
            int locationYIndex = result.getColumnIndex("location_y");
            int descriptionXIndex = result.getColumnIndex("description_x");
            int descriptionYIndex = result.getColumnIndex("description_y");
            int notesIndex = result.getColumnIndex("notes");

            int hasMegaIndex = (result.getColumnIndex("hasMega"));

            // fill the TextView with the retrieved data
            name.setText(result.getString(nameIndex));
            setTitle(result.getString(nameIndex));

            gender.setText(result.getString(genderIndex));
            nat_dex.setText(result.getString(nat_dexIndex));
            natDex = result.getLong(nat_dexIndex);
            species.setText(result.getString(speciesIndex));
            type1.setText(result.getString(type1Index));
            type1Temp = result.getString(temptype1Index);

            hasMegaTemp = result.getString(hasMegaIndex);

            if (hasMegaTemp.contains("1")) {
                megaImg.setVisibility(View.VISIBLE);
            } else {
                megaImg.setVisibility(View.INVISIBLE);
            }

            if (result.getString(type1Index).contains("Bug")) {
                type1.setBackgroundResource(R.drawable.bug_button_border);
            } else if (result.getString(type1Index).contains("Dark")) {
                type1.setBackgroundResource(R.drawable.dark_button_border);
            } else if (result.getString(type1Index).contains("Dragon")) {
                type1.setBackgroundResource(R.drawable.dragon_button_border);
            } else if (result.getString(type1Index).contains("Electric")) {
                type1.setBackgroundResource(R.drawable.electric_button_border);
            } else if (result.getString(type1Index).contains("Fairy")) {
                type1.setBackgroundResource(R.drawable.fairy_button_border);
            } else if (result.getString(type1Index).contains("Fight")) {
                type1.setBackgroundResource(R.drawable.fight_button_border);
            } else if (result.getString(type1Index).contains("Fire")) {
                type1.setBackgroundResource(R.drawable.fire_button_border);
            } else if (result.getString(type1Index).contains("Flying")) {
                type1.setBackgroundResource(R.drawable.flying_button_border);
            } else if (result.getString(type1Index).contains("Ghost")) {
                type1.setBackgroundResource(R.drawable.ghost_button_border);
            } else if (result.getString(type1Index).contains("Grass")) {
                type1.setBackgroundResource(R.drawable.grass_button_border);
            } else if (result.getString(type1Index).contains("Ground")) {
                type1.setBackgroundResource(R.drawable.ground_button_border);
            } else if (result.getString(type1Index).contains("Ice")) {
                type1.setBackgroundResource(R.drawable.ice_button_border);
            } else if (result.getString(type1Index).contains("Normal")) {
                type1.setBackgroundResource(R.drawable.normal_button_border);
            } else if (result.getString(type1Index).contains("Poison")) {
                type1.setBackgroundResource(R.drawable.poison_button_border);
            } else if (result.getString(type1Index).contains("Psychic")) {
                type1.setBackgroundResource(R.drawable.psychic_button_border);
            } else if (result.getString(type1Index).contains("Rock")) {
                type1.setBackgroundResource(R.drawable.rock_button_border);
            } else if (result.getString(type1Index).contains("Steel")) {
                type1.setBackgroundResource(R.drawable.steel_button_border);
            } else if (result.getString(type1Index).contains("Water")) {
                type1.setBackgroundResource(R.drawable.water_button_border);
            }

            type2.setText(result.getString(type2Index));
            type2Temp = result.getString(temptype2Index);

            if (result.getString(type2Index).contains("Bug")) {
                type2.setBackgroundResource(R.drawable.bug_button_border);
            } else if (result.getString(type2Index).contains("Dark")) {
                type2.setBackgroundResource(R.drawable.dark_button_border);
            } else if (result.getString(type2Index).contains("Dragon")) {
                type2.setBackgroundResource(R.drawable.dragon_button_border);
            } else if (result.getString(type2Index).contains("Electric")) {
                type2.setBackgroundResource(R.drawable.electric_button_border);
            } else if (result.getString(type2Index).contains("Fairy")) {
                type2.setBackgroundResource(R.drawable.fairy_button_border);
            } else if (result.getString(type2Index).contains("Fight")) {
                type2.setBackgroundResource(R.drawable.fight_button_border);
            } else if (result.getString(type2Index).contains("Fire")) {
                type2.setBackgroundResource(R.drawable.fire_button_border);
            } else if (result.getString(type2Index).contains("Flying")) {
                type2.setBackgroundResource(R.drawable.flying_button_border);
            } else if (result.getString(type2Index).contains("Ghost")) {
                type2.setBackgroundResource(R.drawable.ghost_button_border);
            } else if (result.getString(type2Index).contains("Grass")) {
                type2.setBackgroundResource(R.drawable.grass_button_border);
            } else if (result.getString(type2Index).contains("Ground")) {
                type2.setBackgroundResource(R.drawable.ground_button_border);
            } else if (result.getString(type2Index).contains("Ice")) {
                type2.setBackgroundResource(R.drawable.ice_button_border);
            } else if (result.getString(type2Index).contains("Normal")) {
                type2.setBackgroundResource(R.drawable.normal_button_border);
            } else if (result.getString(type2Index).contains("Poison")) {
                type2.setBackgroundResource(R.drawable.poison_button_border);
            } else if (result.getString(type2Index).contains("Psychic")) {
                type2.setBackgroundResource(R.drawable.psychic_button_border);
            } else if (result.getString(type2Index).contains("Rock")) {
                type2.setBackgroundResource(R.drawable.rock_button_border);
            } else if (result.getString(type2Index).contains("Steel")) {
                type2.setBackgroundResource(R.drawable.steel_button_border);
            } else if (result.getString(type2Index).contains("Water")) {
                type2.setBackgroundResource(R.drawable.water_button_border);
            } else {
                type2.setVisibility(View.INVISIBLE);
            }
            height.setText(result.getString(heightIndex));
            weight.setText(result.getString(weightIndex));
            locationX.setText(result.getString(locationXIndex));
            locationY.setText(result.getString(locationYIndex));
            descriptionX.setText(result.getString(descriptionXIndex));
            descriptionY.setText(result.getString(descriptionYIndex));

            if (result.getString(notesIndex) == null) {
                notesTv.setVisibility(View.GONE);
            } else {
                notesTv.setText(result.getString(notesIndex));
            }

            AssetManager assetManager = getAssets();

            try {

                InputStream ims = assetManager.open("pokes/p_"
                        + result.getString(imageIndex) + ".png");

                Drawable d = Drawable.createFromStream(ims, null);

                pokeDrawable.setImageDrawable(d);

                InputStream ims2 = assetManager.open("pokes/p_"
                        + result.getString(imageIndex) + "_s.png");

                Drawable d2 = Drawable.createFromStream(ims2, null);

                pokeDrawable2.setImageDrawable(d2);
            } catch (IOException e) {
                e.printStackTrace();
            }

            new LoadWeaknessTask().execute(type1Temp.toString(),
                    type2Temp.toString());

            result.close();
        }
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
                        R.color.x0_0));
            } else if (result.getString(normalIndex).contains("x0.25")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(normalIndex).contains("x0.5")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(normalIndex).contains("x2.0")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(normalIndex).contains("x4.0")) {
                normalTv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                normalTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fireIndex).contains("x0.0")) {
                fireTv.setBackgroundColor(getResources().getColor(R.color.x0_0));
            } else if (result.getString(fireIndex).contains("x0.25")) {
                fireTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_25));
            } else if (result.getString(fireIndex).contains("x0.5")) {
                fireTv.setBackgroundColor(getResources().getColor(R.color.x0_5));
            } else if (result.getString(fireIndex).contains("x2.0")) {
                fireTv.setBackgroundColor(getResources().getColor(R.color.x2_0));
            } else if (result.getString(fireIndex).contains("x4.0")) {
                fireTv.setBackgroundColor(getResources().getColor(R.color.x4_0));
            } else {
                fireTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(waterIndex).contains("x0.0")) {
                waterTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(waterIndex).contains("x0.25")) {
                waterTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(waterIndex).contains("x0.5")) {
                waterTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(waterIndex).contains("x2.0")) {
                waterTv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(waterIndex).contains("x4.0")) {
                waterTv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                waterTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(electricIndex).contains("x0.0")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(electricIndex).contains("x0.25")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(electricIndex).contains("x0.5")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(electricIndex).contains("x2.0")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(electricIndex).contains("x4.0")) {
                electricTv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                electricTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(grassIndex).contains("x0.0")) {
                grassTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(grassIndex).contains("x0.25")) {
                grassTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(grassIndex).contains("x0.5")) {
                grassTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(grassIndex).contains("x2.0")) {
                grassTv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(grassIndex).contains("x4.0")) {
                grassTv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                grassTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(iceIndex).contains("x0.0")) {
                iceTv.setBackgroundColor(getResources().getColor(R.color.x0_0));
            } else if (result.getString(iceIndex).contains("x0.25")) {
                iceTv.setBackgroundColor(getResources().getColor(R.color.x0_25));
            } else if (result.getString(iceIndex).contains("x0.5")) {
                iceTv.setBackgroundColor(getResources().getColor(R.color.x0_5));
            } else if (result.getString(iceIndex).contains("x2.0")) {
                iceTv.setBackgroundColor(getResources().getColor(R.color.x2_0));
            } else if (result.getString(iceIndex).contains("x4.0")) {
                iceTv.setBackgroundColor(getResources().getColor(R.color.x4_0));
            } else {
                iceTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fightIndex).contains("x0.0")) {
                fightTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(fightIndex).contains("x0.25")) {
                fightTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(fightIndex).contains("x0.5")) {
                fightTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(fightIndex).contains("x2.0")) {
                fightTv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(fightIndex).contains("x4.0")) {
                fightTv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                fightTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(poisonIndex).contains("x0.0")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(poisonIndex).contains("x0.25")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(poisonIndex).contains("x0.5")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(poisonIndex).contains("x2.0")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(poisonIndex).contains("x4.0")) {
                poisonTv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                poisonTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(groundIndex).contains("x0.0")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(groundIndex).contains("x0.25")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(groundIndex).contains("x0.5")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(groundIndex).contains("x2.0")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(groundIndex).contains("x4.0")) {
                groundTv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                groundTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(flyingIndex).contains("x0.0")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(flyingIndex).contains("x0.25")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(flyingIndex).contains("x0.5")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(flyingIndex).contains("x2.0")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(flyingIndex).contains("x4.0")) {
                flyingTv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                flyingTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(psychicIndex).contains("x0.0")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(psychicIndex).contains("x0.25")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(psychicIndex).contains("x0.5")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(psychicIndex).contains("x2.0")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(psychicIndex).contains("x4.0")) {
                psychicTv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                psychicTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(bugIndex).contains("x0.0")) {
                bugTv.setBackgroundColor(getResources().getColor(R.color.x0_0));
            } else if (result.getString(bugIndex).contains("x0.25")) {
                bugTv.setBackgroundColor(getResources().getColor(R.color.x0_25));
            } else if (result.getString(bugIndex).contains("x0.5")) {
                bugTv.setBackgroundColor(getResources().getColor(R.color.x0_5));
            } else if (result.getString(bugIndex).contains("x2.0")) {
                bugTv.setBackgroundColor(getResources().getColor(R.color.x2_0));
            } else if (result.getString(bugIndex).contains("x4.0")) {
                bugTv.setBackgroundColor(getResources().getColor(R.color.x4_0));
            } else {
                bugTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(rockIndex).contains("x0.0")) {
                rockTv.setBackgroundColor(getResources().getColor(R.color.x0_0));
            } else if (result.getString(rockIndex).contains("x0.25")) {
                rockTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_25));
            } else if (result.getString(rockIndex).contains("x0.5")) {
                rockTv.setBackgroundColor(getResources().getColor(R.color.x0_5));
            } else if (result.getString(rockIndex).contains("x2.0")) {
                rockTv.setBackgroundColor(getResources().getColor(R.color.x2_0));
            } else if (result.getString(rockIndex).contains("x4.0")) {
                rockTv.setBackgroundColor(getResources().getColor(R.color.x4_0));
            } else {
                rockTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(ghostIndex).contains("x0.0")) {
                ghostTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(ghostIndex).contains("x0.25")) {
                ghostTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(ghostIndex).contains("x0.5")) {
                ghostTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(ghostIndex).contains("x2.0")) {
                ghostTv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(ghostIndex).contains("x4.0")) {
                ghostTv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                ghostTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(dragonIndex).contains("x0.0")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(dragonIndex).contains("x0.25")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(dragonIndex).contains("x0.5")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(dragonIndex).contains("x2.0")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(dragonIndex).contains("x4.0")) {
                dragonTv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                dragonTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(darkIndex).contains("x0.0")) {
                darkTv.setBackgroundColor(getResources().getColor(R.color.x0_0));
            } else if (result.getString(darkIndex).contains("x0.25")) {
                darkTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_25));
            } else if (result.getString(darkIndex).contains("x0.5")) {
                darkTv.setBackgroundColor(getResources().getColor(R.color.x0_5));
            } else if (result.getString(darkIndex).contains("x2.0")) {
                darkTv.setBackgroundColor(getResources().getColor(R.color.x2_0));
            } else if (result.getString(darkIndex).contains("x4.0")) {
                darkTv.setBackgroundColor(getResources().getColor(R.color.x4_0));
            } else {
                darkTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(steelIndex).contains("x0.0")) {
                steelTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(steelIndex).contains("x0.25")) {
                steelTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(steelIndex).contains("x0.5")) {
                steelTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(steelIndex).contains("x2.0")) {
                steelTv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(steelIndex).contains("x4.0")) {
                steelTv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                steelTv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fairyIndex).contains("x0.0")) {
                fairyTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(fairyIndex).contains("x0.25")) {
                fairyTv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(fairyIndex).contains("x0.5")) {
                fairyTv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(fairyIndex).contains("x2.0")) {
                fairyTv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(fairyIndex).contains("x4.0")) {
                fairyTv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                fairyTv.setBackgroundColor(Color.TRANSPARENT);
            }
            // fill the TextView with the retrieved data

            result.close();

        }
    }

    private class LoadPokeBaseStatTask extends AsyncTask<Long, Object, Cursor> {

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.openDataBase();

            // get a cursor containing all data on given entry
            return databaseConnector.getOnePokeBaseStat(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            if (result.moveToFirst()) {
                // move to first item
                // get the column index for each data item
                int HPIndex = result.getColumnIndex("hp");
                int AttIndex = result.getColumnIndex("att");
                int DefIndex = result.getColumnIndex("def");
                int SpAttIndex = result.getColumnIndex("sp_att");
                int SpDefIndex = result.getColumnIndex("sp_def");
                int SpeedIndex = result.getColumnIndex("speed");
                // fill the TextView with the retrieved data
                hp.setText("HP " + result.getString(HPIndex));
                hp.setProgress(result.getInt(HPIndex));
                att.setText("Att " + result.getString(AttIndex));
                att.setProgress(result.getInt(AttIndex));
                def.setText("Def " + result.getString(DefIndex));
                def.setProgress(result.getInt(DefIndex));
                spAtt.setText("Sp. Att " + result.getString(SpAttIndex));
                spAtt.setProgress(result.getInt(SpAttIndex));
                spDef.setText("Sp. Def " + result.getString(SpDefIndex));
                spDef.setProgress(result.getInt(SpDefIndex));
                speed.setText("Speed " + result.getString(SpeedIndex));
                speed.setProgress(result.getInt(SpeedIndex));
                result.close();
            } else {
                hp.setProgress(0);
                att.setProgress(0);
                def.setProgress(0);
                spAtt.setProgress(0);
                spDef.setProgress(0);
                speed.setProgress(0);

            }

        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent viewAbility = new Intent(TypeChartPokedexEntry.this,
                ViewAbility.class);

        viewAbility.putExtra(ROW_ID, id);

        startActivity(viewAbility);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.moveBtn) {
            Intent viewMoves = new Intent(TypeChartPokedexEntry.this,
                    ViewPokeMoves.class);
            viewMoves.putExtra(ROW_ID, rowID);
            startActivity(viewMoves);
        } else if (id == R.id.megaImg) {
            Intent viewMega = new Intent(TypeChartPokedexEntry.this,
                    PokedexEntryMega.class);

            viewMega.putExtra(NAT_DEX, natDex);
            startActivity(viewMega);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.toggleButton1) {
            if (isChecked) {
                loc_layout.setVisibility(View.VISIBLE);
            } else {
                loc_layout.setVisibility(View.GONE);
            }
        } else if (id == R.id.toggleButton2) {
            if (isChecked) {
                desc_layout.setVisibility(View.VISIBLE);
            } else {
                desc_layout.setVisibility(View.GONE);
            }
        }

    }

}