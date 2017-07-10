package com.ShaHar91.Pokedex;

import java.io.IOException;
import java.io.InputStream;

import com.ShaHar91.Pokedex.Abilities.ViewAbility;
import com.ShaHar91.Pokedex.CustomViews.TextProgressBar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PokedexEntryMega extends Activity {
    DatabaseConnector databaseConnector = new DatabaseConnector(
            PokedexEntryMega.this);

    private ImageView pokeDrawable, pokeDrawable2, pokeDrawable3,
            pokeDrawable4;

    public static final String NAT_DEX = "nat_dex";
    public static final String ROW_ID = "row_id";

    private long natDex;

    private String type1Temp, type2Temp, type12Temp, type22Temp = "";

    private Button type1Mega, type2Mega, type1Mega2, type2Mega2;

    private ListView list1, list2;

    private TextView name, nat_dex, species, height, weight, gender, name2,
            nat_dex2, species2, height2, weight2, gender2, normalTv, fireTv,
            waterTv, electricTv, grassTv, iceTv, fightTv, poisonTv, groundTv,
            flyingTv, psychicTv, bugTv, rockTv, ghostTv, dragonTv, darkTv,
            steelTv, fairyTv, normal2Tv, fire2Tv, water2Tv, electric2Tv,
            grass2Tv, ice2Tv, fight2Tv, poison2Tv, ground2Tv, flying2Tv,
            psychic2Tv, bug2Tv, rock2Tv, ghost2Tv, dragon2Tv, dark2Tv,
            steel2Tv, fairy2Tv, notesTv, notes2Tv;

    private SimpleCursorAdapter abilityAdapter1, abilityAdapter2;

    private TextProgressBar hp, att, def, spAtt, spDef, speed, hp2, att2, def2,
            spAtt2, spDef2, speed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pokedex_mega_entry);

        setViews();

        // new LoadAbilityDexTask().execute(rowID);
        // new GetCaught().execute(rowID);

        new LoadPokeDexTask().execute(natDex);

        String[] from = new String[]{"ability"};
        int[] to = new int[]{R.id.abilityNameTv};
        abilityAdapter1 = new SimpleCursorAdapter(PokedexEntryMega.this,
                R.layout.ability_dex_list_item, null, from, to);
        list1.setAdapter(abilityAdapter1);

        String[] from2 = new String[]{"ability"};
        int[] to2 = new int[]{R.id.abilityNameTv};
        abilityAdapter2 = new SimpleCursorAdapter(PokedexEntryMega.this,
                R.layout.ability_dex_list_item, null, from2, to2);
        list2.setAdapter(abilityAdapter2);

        type2Temp = type2Mega.getText().toString();

    }

    private void setViews() {

        name = (TextView) findViewById(R.id.name_mega_dexTv);
        nat_dex = (TextView) findViewById(R.id.dex_Mega_natNoTv);
        species = (TextView) findViewById(R.id.dex_Mega_speciesTv);
        type1Mega = (Button) findViewById(R.id.type1MegaBtn);
        type2Mega = (Button) findViewById(R.id.type2MegaBtn);
        height = (TextView) findViewById(R.id.height_Mega_dexTv);
        weight = (TextView) findViewById(R.id.weight_Mega_dexTv);
        gender = (TextView) findViewById(R.id.gender_Mega_dexTv);

        name2 = (TextView) findViewById(R.id.name_mega2_dexTv);
        nat_dex2 = (TextView) findViewById(R.id.dex_Mega2_natNoTv);
        species2 = (TextView) findViewById(R.id.dex_Mega2_speciesTv);
        type1Mega2 = (Button) findViewById(R.id.type1Mega2Btn);
        type2Mega2 = (Button) findViewById(R.id.type2Mega2Btn);
        height2 = (TextView) findViewById(R.id.height_Mega2_dexTv);
        weight2 = (TextView) findViewById(R.id.weight_Mega2_dexTv);
        gender2 = (TextView) findViewById(R.id.gender_Mega2_dexTv);

        normalTv = (TextView) findViewById(R.id.dexnormalMegaXTv);
        fireTv = (TextView) findViewById(R.id.dexfireMegaXTv);
        waterTv = (TextView) findViewById(R.id.dexwaterMegaXTv);
        electricTv = (TextView) findViewById(R.id.dexelectricMegaXTv);
        grassTv = (TextView) findViewById(R.id.dexgrassMegaXTv);
        iceTv = (TextView) findViewById(R.id.dexiceMegaXTv);
        fightTv = (TextView) findViewById(R.id.dexfightMegaXTv);
        poisonTv = (TextView) findViewById(R.id.dexpoisonMegaXTv);
        groundTv = (TextView) findViewById(R.id.dexgroundMegaXTv);
        flyingTv = (TextView) findViewById(R.id.dexflyingMegaXTv);
        psychicTv = (TextView) findViewById(R.id.dexpsychicMegaXTv);
        bugTv = (TextView) findViewById(R.id.dexbugMegaXTv);
        rockTv = (TextView) findViewById(R.id.dexrockMegaXTv);
        ghostTv = (TextView) findViewById(R.id.dexghostMegaXTv);
        dragonTv = (TextView) findViewById(R.id.dexdragonMegaXTv);
        darkTv = (TextView) findViewById(R.id.dexdarkMegaXTv);
        steelTv = (TextView) findViewById(R.id.dexsteelMegaXTv);
        fairyTv = (TextView) findViewById(R.id.dexfairyMegaXTv);
        notesTv = (TextView) findViewById(R.id.notesMegaTv);
        list1 = (ListView) findViewById(R.id.list1);
        list1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent viewAbility = new Intent(PokedexEntryMega.this,
                        ViewAbility.class);

                viewAbility.putExtra(ROW_ID, id);

                startActivity(viewAbility);
            }
        });

        normal2Tv = (TextView) findViewById(R.id.dexnormalMega2XTv);
        fire2Tv = (TextView) findViewById(R.id.dexfireMega2XTv);
        water2Tv = (TextView) findViewById(R.id.dexwaterMega2XTv);
        electric2Tv = (TextView) findViewById(R.id.dexelectricMega2XTv);
        grass2Tv = (TextView) findViewById(R.id.dexgrassMega2XTv);
        ice2Tv = (TextView) findViewById(R.id.dexiceMega2XTv);
        fight2Tv = (TextView) findViewById(R.id.dexfightMega2XTv);
        poison2Tv = (TextView) findViewById(R.id.dexpoisonMega2XTv);
        ground2Tv = (TextView) findViewById(R.id.dexgroundMega2XTv);
        flying2Tv = (TextView) findViewById(R.id.dexflyingMega2XTv);
        psychic2Tv = (TextView) findViewById(R.id.dexpsychicMega2XTv);
        bug2Tv = (TextView) findViewById(R.id.dexbugMega2XTv);
        rock2Tv = (TextView) findViewById(R.id.dexrockMega2XTv);
        ghost2Tv = (TextView) findViewById(R.id.dexghostMega2XTv);
        dragon2Tv = (TextView) findViewById(R.id.dexdragonMega2XTv);
        dark2Tv = (TextView) findViewById(R.id.dexdarkMega2XTv);
        steel2Tv = (TextView) findViewById(R.id.dexsteelMega2XTv);
        fairy2Tv = (TextView) findViewById(R.id.dexfairyMega2XTv);
        notes2Tv = (TextView) findViewById(R.id.notesMega2Tv);
        list2 = (ListView) findViewById(R.id.list2);
        list2.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent viewAbility2 = new Intent(PokedexEntryMega.this,
                        ViewAbility.class);

                viewAbility2.putExtra(ROW_ID, id);

                startActivity(viewAbility2);
            }
        });

        hp = (TextProgressBar) findViewById(R.id.hpMegaProgressBar);
        att = (TextProgressBar) findViewById(R.id.attMegaProgressBar);
        def = (TextProgressBar) findViewById(R.id.defMegaProgressBar);
        spAtt = (TextProgressBar) findViewById(R.id.spAttMegaProgressBar);
        spDef = (TextProgressBar) findViewById(R.id.spDefMegaProgressBar);
        speed = (TextProgressBar) findViewById(R.id.speedMegaProgressBar);

        hp2 = (TextProgressBar) findViewById(R.id.hpMega2ProgressBar);
        att2 = (TextProgressBar) findViewById(R.id.attMega2ProgressBar);
        def2 = (TextProgressBar) findViewById(R.id.defMega2ProgressBar);
        spAtt2 = (TextProgressBar) findViewById(R.id.spAttMega2ProgressBar);
        spDef2 = (TextProgressBar) findViewById(R.id.spDefMega2ProgressBar);
        speed2 = (TextProgressBar) findViewById(R.id.speedMega2ProgressBar);

        pokeDrawable = (ImageView) findViewById(R.id.pokeMegaDrawable);
        pokeDrawable2 = (ImageView) findViewById(R.id.pokeMegaDrawable2);

        pokeDrawable3 = (ImageView) findViewById(R.id.pokeMega2Drawable);
        pokeDrawable4 = (ImageView) findViewById(R.id.pokeMega2Drawable2);

        Bundle extras = getIntent().getExtras();
        natDex = extras.getLong("nat_dex");

    }

    private class LoadPokeDexTask extends AsyncTask<Long, Object, Cursor> {

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.openDataBase();

            // get a cursor containing all data on given entry
            return databaseConnector.getPokeMegaForDex(params[0]);
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
            int notesIndex = result.getColumnIndex("notes");

            // fill the TextView with the retrieved data
            name.setText(result.getString(nameIndex));
            setTitle(result.getString(nameIndex));

            gender.setText(result.getString(genderIndex));
            nat_dex.setText(result.getString(nat_dexIndex));
            species.setText(result.getString(speciesIndex));
            type1Mega.setText(result.getString(type1Index));
            type1Temp = result.getString(temptype1Index);

            if (result.getString(type1Index).contains("Bug")) {
                type1Mega.setBackgroundResource(R.drawable.bug_button_border);
            } else if (result.getString(type1Index).contains("Dark")) {
                type1Mega.setBackgroundResource(R.drawable.dark_button_border);
            } else if (result.getString(type1Index).contains("Dragon")) {
                type1Mega
                        .setBackgroundResource(R.drawable.dragon_button_border);
            } else if (result.getString(type1Index).contains("Electric")) {
                type1Mega
                        .setBackgroundResource(R.drawable.electric_button_border);
            } else if (result.getString(type1Index).contains("Fairy")) {
                type1Mega.setBackgroundResource(R.drawable.fairy_button_border);
            } else if (result.getString(type1Index).contains("Fight")) {
                type1Mega.setBackgroundResource(R.drawable.fight_button_border);
            } else if (result.getString(type1Index).contains("Fire")) {
                type1Mega.setBackgroundResource(R.drawable.fire_button_border);
            } else if (result.getString(type1Index).contains("Flying")) {
                type1Mega
                        .setBackgroundResource(R.drawable.flying_button_border);
            } else if (result.getString(type1Index).contains("Ghost")) {
                type1Mega.setBackgroundResource(R.drawable.ghost_button_border);
            } else if (result.getString(type1Index).contains("Grass")) {
                type1Mega.setBackgroundResource(R.drawable.grass_button_border);
            } else if (result.getString(type1Index).contains("Ground")) {
                type1Mega
                        .setBackgroundResource(R.drawable.ground_button_border);
            } else if (result.getString(type1Index).contains("Ice")) {
                type1Mega.setBackgroundResource(R.drawable.ice_button_border);
            } else if (result.getString(type1Index).contains("Normal")) {
                type1Mega
                        .setBackgroundResource(R.drawable.normal_button_border);
            } else if (result.getString(type1Index).contains("Poison")) {
                type1Mega
                        .setBackgroundResource(R.drawable.poison_button_border);
            } else if (result.getString(type1Index).contains("Psychic")) {
                type1Mega
                        .setBackgroundResource(R.drawable.psychic_button_border);
            } else if (result.getString(type1Index).contains("Rock")) {
                type1Mega.setBackgroundResource(R.drawable.rock_button_border);
            } else if (result.getString(type1Index).contains("Steel")) {
                type1Mega.setBackgroundResource(R.drawable.steel_button_border);
            } else if (result.getString(type1Index).contains("Water")) {
                type1Mega.setBackgroundResource(R.drawable.water_button_border);
            }

            type2Mega.setText(result.getString(type2Index));
            type2Temp = result.getString(temptype2Index);

            if (result.getString(type2Index).contains("Bug")) {
                type2Mega.setBackgroundResource(R.drawable.bug_button_border);
            } else if (result.getString(type2Index).contains("Dark")) {
                type2Mega.setBackgroundResource(R.drawable.dark_button_border);
            } else if (result.getString(type2Index).contains("Dragon")) {
                type2Mega
                        .setBackgroundResource(R.drawable.dragon_button_border);
            } else if (result.getString(type2Index).contains("Electric")) {
                type2Mega
                        .setBackgroundResource(R.drawable.electric_button_border);
            } else if (result.getString(type2Index).contains("Fairy")) {
                type2Mega.setBackgroundResource(R.drawable.fairy_button_border);
            } else if (result.getString(type2Index).contains("Fight")) {
                type2Mega.setBackgroundResource(R.drawable.fight_button_border);
            } else if (result.getString(type2Index).contains("Fire")) {
                type2Mega.setBackgroundResource(R.drawable.fire_button_border);
            } else if (result.getString(type2Index).contains("Flying")) {
                type2Mega
                        .setBackgroundResource(R.drawable.flying_button_border);
            } else if (result.getString(type2Index).contains("Ghost")) {
                type2Mega.setBackgroundResource(R.drawable.ghost_button_border);
            } else if (result.getString(type2Index).contains("Grass")) {
                type2Mega.setBackgroundResource(R.drawable.grass_button_border);
            } else if (result.getString(type2Index).contains("Ground")) {
                type2Mega
                        .setBackgroundResource(R.drawable.ground_button_border);
            } else if (result.getString(type2Index).contains("Ice")) {
                type2Mega.setBackgroundResource(R.drawable.ice_button_border);
            } else if (result.getString(type2Index).contains("Normal")) {
                type2Mega
                        .setBackgroundResource(R.drawable.normal_button_border);
            } else if (result.getString(type2Index).contains("Poison")) {
                type2Mega
                        .setBackgroundResource(R.drawable.poison_button_border);
            } else if (result.getString(type2Index).contains("Psychic")) {
                type2Mega
                        .setBackgroundResource(R.drawable.psychic_button_border);
            } else if (result.getString(type2Index).contains("Rock")) {
                type2Mega.setBackgroundResource(R.drawable.rock_button_border);
            } else if (result.getString(type2Index).contains("Steel")) {
                type2Mega.setBackgroundResource(R.drawable.steel_button_border);
            } else if (result.getString(type2Index).contains("Water")) {
                type2Mega.setBackgroundResource(R.drawable.water_button_border);
            } else {
                type2Mega.setVisibility(View.INVISIBLE);
            }
            height.setText(result.getString(heightIndex));
            weight.setText(result.getString(weightIndex));

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

            new LoadAbilityDexTask().execute(result.getLong(imageIndex));

            new LoadWeaknessTask().execute(type1Temp.toString(),
                    type2Temp.toString());

            if (result.moveToNext()) {

                // get the column index for each data item
                int name2Index = result.getColumnIndex("name");
                int gender2Index = result.getColumnIndex("gender_spread");
                int image2Index = result.getColumnIndex("_id");
                int nat_dex2Index = result.getColumnIndex("nat_dex");
                int species2Index = result.getColumnIndex("species");
                int type12Index = result.getColumnIndex("type1");
                int temptype12Index = result.getColumnIndex("type1");
                int type22Index = result.getColumnIndex("type2");
                int temptype22Index = result.getColumnIndex("type2");

                int height2Index = result.getColumnIndex("height");
                int weight2Index = result.getColumnIndex("weight");
                int notes2Index = result.getColumnIndex("notes");

                // fill the TextView with the retrieved data
                name2.setText(result.getString(name2Index));
                gender2.setText(result.getString(gender2Index));
                nat_dex2.setText(result.getString(nat_dex2Index));
                species2.setText(result.getString(species2Index));
                type1Mega2.setText(result.getString(type12Index));
                type12Temp = result.getString(temptype12Index);

                if (result.getString(type12Index).contains("Bug")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.bug_button_border);
                } else if (result.getString(type12Index).contains("Dark")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.dark_button_border);
                } else if (result.getString(type12Index).contains("Dragon")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.dragon_button_border);
                } else if (result.getString(type12Index).contains("Electric")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.electric_button_border);
                } else if (result.getString(type12Index).contains("Fairy")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.fairy_button_border);
                } else if (result.getString(type12Index).contains("Fight")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.fight_button_border);
                } else if (result.getString(type12Index).contains("Fire")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.fire_button_border);
                } else if (result.getString(type12Index).contains("Flying")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.flying_button_border);
                } else if (result.getString(type12Index).contains("Ghost")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.ghost_button_border);
                } else if (result.getString(type12Index).contains("Grass")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.grass_button_border);
                } else if (result.getString(type12Index).contains("Ground")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.ground_button_border);
                } else if (result.getString(type12Index).contains("Ice")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.ice_button_border);
                } else if (result.getString(type12Index).contains("Normal")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.normal_button_border);
                } else if (result.getString(type12Index).contains("Poison")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.poison_button_border);
                } else if (result.getString(type12Index).contains("Psychic")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.psychic_button_border);
                } else if (result.getString(type12Index).contains("Rock")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.rock_button_border);
                } else if (result.getString(type12Index).contains("Steel")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.steel_button_border);
                } else if (result.getString(type12Index).contains("Water")) {
                    type1Mega2
                            .setBackgroundResource(R.drawable.water_button_border);
                }

                type2Mega2.setText(result.getString(type22Index));
                type22Temp = result.getString(temptype22Index);

                if (result.getString(type22Index).contains("Bug")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.bug_button_border);
                } else if (result.getString(type22Index).contains("Dark")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.dark_button_border);
                } else if (result.getString(type22Index).contains("Dragon")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.dragon_button_border);
                } else if (result.getString(type22Index).contains("Electric")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.electric_button_border);
                } else if (result.getString(type22Index).contains("Fairy")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.fairy_button_border);
                } else if (result.getString(type22Index).contains("Fight")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.fight_button_border);
                } else if (result.getString(type22Index).contains("Fire")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.fire_button_border);
                } else if (result.getString(type22Index).contains("Flying")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.flying_button_border);
                } else if (result.getString(type22Index).contains("Ghost")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.ghost_button_border);
                } else if (result.getString(type22Index).contains("Grass")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.grass_button_border);
                } else if (result.getString(type22Index).contains("Ground")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.ground_button_border);
                } else if (result.getString(type22Index).contains("Ice")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.ice_button_border);
                } else if (result.getString(type22Index).contains("Normal")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.normal_button_border);
                } else if (result.getString(type22Index).contains("Poison")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.poison_button_border);
                } else if (result.getString(type22Index).contains("Psychic")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.psychic_button_border);
                } else if (result.getString(type22Index).contains("Rock")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.rock_button_border);
                } else if (result.getString(type22Index).contains("Steel")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.steel_button_border);
                } else if (result.getString(type22Index).contains("Water")) {
                    type2Mega2
                            .setBackgroundResource(R.drawable.water_button_border);
                } else {
                    type2Mega2.setVisibility(View.INVISIBLE);
                }
                height2.setText(result.getString(height2Index));
                weight2.setText(result.getString(weight2Index));

                if (result.getString(notes2Index) == null) {
                    notes2Tv.setVisibility(View.GONE);
                } else {
                    notes2Tv.setText(result.getString(notes2Index));
                }

                AssetManager assetManager2 = getAssets();

                try {

                    InputStream ims3 = assetManager2.open("pokes/p_"
                            + result.getString(image2Index) + ".png");

                    Drawable d3 = Drawable.createFromStream(ims3, null);

                    pokeDrawable3.setImageDrawable(d3);

                    InputStream ims4 = assetManager2.open("pokes/p_"
                            + result.getString(image2Index) + "_s.png");

                    Drawable d4 = Drawable.createFromStream(ims4, null);

                    pokeDrawable4.setImageDrawable(d4);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int HP2Index = result.getColumnIndex("hp");
                int Att2Index = result.getColumnIndex("att");
                int Def2Index = result.getColumnIndex("def");
                int SpAtt2Index = result.getColumnIndex("sp_att");
                int SpDef2Index = result.getColumnIndex("sp_def");
                int Speed2Index = result.getColumnIndex("speed");
                // fill the TextView with the retrieved data
                hp2.setText("HP " + result.getString(HP2Index));
                hp2.setProgress(result.getInt(HP2Index));
                att2.setText("Att " + result.getString(Att2Index));
                att2.setProgress(result.getInt(Att2Index));
                def2.setText("Def " + result.getString(Def2Index));
                def2.setProgress(result.getInt(Def2Index));
                spAtt2.setText("Sp. Att " + result.getString(SpAtt2Index));
                spAtt2.setProgress(result.getInt(SpAtt2Index));
                spDef2.setText("Sp. Def " + result.getString(SpDef2Index));
                spDef2.setProgress(result.getInt(SpDef2Index));
                speed2.setText("Speed " + result.getString(Speed2Index));
                speed2.setProgress(result.getInt(Speed2Index));

                new LoadAbilityDexTask2().execute(result.getLong(image2Index));

                new LoadWeaknessTask2().execute(type12Temp.toString(),
                        type22Temp.toString());

            } else {
                LinearLayout mega2 = (LinearLayout) findViewById(R.id.mega2);
                mega2.setVisibility(View.GONE);
            }

            result.close();
        }
    }

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

            abilityAdapter1.changeCursor(result);

        }
    }

    private class LoadAbilityDexTask2 extends AsyncTask<Long, Object, Cursor> {

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.openDataBase();

            // get a cursor containing all data on given entry
            return databaseConnector.getAbilityForOnePoke(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {

            abilityAdapter2.changeCursor(result);

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

    private class LoadWeaknessTask2 extends AsyncTask<String, Object, Cursor> {

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
            int normal2Index = result.getColumnIndex("normal");
            int fire2Index = result.getColumnIndex("fire");
            int water2Index = result.getColumnIndex("water");
            int electric2Index = result.getColumnIndex("electric");
            int grass2Index = result.getColumnIndex("grass");
            int ice2Index = result.getColumnIndex("ice");
            int fight2Index = result.getColumnIndex("fight");
            int poison2Index = result.getColumnIndex("poison");
            int ground2Index = result.getColumnIndex("ground");
            int flying2Index = result.getColumnIndex("flying");
            int psychic2Index = result.getColumnIndex("psychic");
            int bug2Index = result.getColumnIndex("bug");
            int rock2Index = result.getColumnIndex("rock");
            int ghost2Index = result.getColumnIndex("ghost");
            int dragon2Index = result.getColumnIndex("dragon");
            int dark2Index = result.getColumnIndex("dark");
            int steel2Index = result.getColumnIndex("steel");
            int fairy2Index = result.getColumnIndex("fairy");

            normal2Tv.setText(result.getString(normal2Index));
            fire2Tv.setText(result.getString(fire2Index));
            water2Tv.setText(result.getString(water2Index));
            electric2Tv.setText(result.getString(electric2Index));
            grass2Tv.setText(result.getString(grass2Index));
            ice2Tv.setText(result.getString(ice2Index));
            fight2Tv.setText(result.getString(fight2Index));
            poison2Tv.setText(result.getString(poison2Index));
            ground2Tv.setText(result.getString(ground2Index));
            flying2Tv.setText(result.getString(flying2Index));
            psychic2Tv.setText(result.getString(psychic2Index));
            bug2Tv.setText(result.getString(bug2Index));
            rock2Tv.setText(result.getString(rock2Index));
            ghost2Tv.setText(result.getString(ghost2Index));
            dragon2Tv.setText(result.getString(dragon2Index));
            dark2Tv.setText(result.getString(dark2Index));
            steel2Tv.setText(result.getString(steel2Index));
            fairy2Tv.setText(result.getString(fairy2Index));

            if (result.getString(normal2Index).contains("x0.0")) {
                normal2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(normal2Index).contains("x0.25")) {
                normal2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(normal2Index).contains("x0.5")) {
                normal2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(normal2Index).contains("x2.0")) {
                normal2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(normal2Index).contains("x4.0")) {
                normal2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                normal2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fire2Index).contains("x0.0")) {
                fire2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(fire2Index).contains("x0.25")) {
                fire2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(fire2Index).contains("x0.5")) {
                fire2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(fire2Index).contains("x2.0")) {
                fire2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(fire2Index).contains("x4.0")) {
                fire2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                fire2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(water2Index).contains("x0.0")) {
                water2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(water2Index).contains("x0.25")) {
                water2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(water2Index).contains("x0.5")) {
                water2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(water2Index).contains("x2.0")) {
                water2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(water2Index).contains("x4.0")) {
                water2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                water2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(electric2Index).contains("x0.0")) {
                electric2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(electric2Index).contains("x0.25")) {
                electric2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(electric2Index).contains("x0.5")) {
                electric2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(electric2Index).contains("x2.0")) {
                electric2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(electric2Index).contains("x4.0")) {
                electric2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                electric2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(grass2Index).contains("x0.0")) {
                grass2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(grass2Index).contains("x0.25")) {
                grass2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(grass2Index).contains("x0.5")) {
                grass2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(grass2Index).contains("x2.0")) {
                grass2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(grass2Index).contains("x4.0")) {
                grass2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                grass2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(ice2Index).contains("x0.0")) {
                ice2Tv.setBackgroundColor(getResources().getColor(R.color.x0_0));
            } else if (result.getString(ice2Index).contains("x0.25")) {
                ice2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_25));
            } else if (result.getString(ice2Index).contains("x0.5")) {
                ice2Tv.setBackgroundColor(getResources().getColor(R.color.x0_5));
            } else if (result.getString(ice2Index).contains("x2.0")) {
                ice2Tv.setBackgroundColor(getResources().getColor(R.color.x2_0));
            } else if (result.getString(ice2Index).contains("x4.0")) {
                ice2Tv.setBackgroundColor(getResources().getColor(R.color.x4_0));
            } else {
                ice2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fight2Index).contains("x0.0")) {
                fight2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(fight2Index).contains("x0.25")) {
                fight2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(fight2Index).contains("x0.5")) {
                fight2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(fight2Index).contains("x2.0")) {
                fight2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(fight2Index).contains("x4.0")) {
                fight2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                fight2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(poison2Index).contains("x0.0")) {
                poison2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(poison2Index).contains("x0.25")) {
                poison2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(poison2Index).contains("x0.5")) {
                poison2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(poison2Index).contains("x2.0")) {
                poison2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(poison2Index).contains("x4.0")) {
                poison2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                poison2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(ground2Index).contains("x0.0")) {
                ground2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(ground2Index).contains("x0.25")) {
                ground2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(ground2Index).contains("x0.5")) {
                ground2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(ground2Index).contains("x2.0")) {
                ground2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(ground2Index).contains("x4.0")) {
                ground2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                ground2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(flying2Index).contains("x0.0")) {
                flying2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(flying2Index).contains("x0.25")) {
                flying2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(flying2Index).contains("x0.5")) {
                flying2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(flying2Index).contains("x2.0")) {
                flying2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(flying2Index).contains("x4.0")) {
                flying2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                flying2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(psychic2Index).contains("x0.0")) {
                psychic2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(psychic2Index).contains("x0.25")) {
                psychic2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(psychic2Index).contains("x0.5")) {
                psychic2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(psychic2Index).contains("x2.0")) {
                psychic2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(psychic2Index).contains("x4.0")) {
                psychic2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                psychic2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(bug2Index).contains("x0.0")) {
                bug2Tv.setBackgroundColor(getResources().getColor(R.color.x0_0));
            } else if (result.getString(bug2Index).contains("x0.25")) {
                bug2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_25));
            } else if (result.getString(bug2Index).contains("x0.5")) {
                bug2Tv.setBackgroundColor(getResources().getColor(R.color.x0_5));
            } else if (result.getString(bug2Index).contains("x2.0")) {
                bug2Tv.setBackgroundColor(getResources().getColor(R.color.x2_0));
            } else if (result.getString(bug2Index).contains("x4.0")) {
                bug2Tv.setBackgroundColor(getResources().getColor(R.color.x4_0));
            } else {
                bug2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(rock2Index).contains("x0.0")) {
                rock2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(rock2Index).contains("x0.25")) {
                rock2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(rock2Index).contains("x0.5")) {
                rock2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(rock2Index).contains("x2.0")) {
                rock2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(rock2Index).contains("x4.0")) {
                rock2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                rock2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(ghost2Index).contains("x0.0")) {
                ghost2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(ghost2Index).contains("x0.25")) {
                ghost2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(ghost2Index).contains("x0.5")) {
                ghost2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(ghost2Index).contains("x2.0")) {
                ghost2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(ghost2Index).contains("x4.0")) {
                ghost2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                ghost2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(dragon2Index).contains("x0.0")) {
                dragon2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(dragon2Index).contains("x0.25")) {
                dragon2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(dragon2Index).contains("x0.5")) {
                dragon2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(dragon2Index).contains("x2.0")) {
                dragon2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(dragon2Index).contains("x4.0")) {
                dragon2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                dragon2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(dark2Index).contains("x0.0")) {
                dark2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_0));
            } else if (result.getString(dark2Index).contains("x0.25")) {
                dark2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(dark2Index).contains("x0.5")) {
                dark2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x0_5));
            } else if (result.getString(dark2Index).contains("x2.0")) {
                dark2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x2_0));
            } else if (result.getString(dark2Index).contains("x4.0")) {
                dark2Tv.setBackgroundColor(getResources()
                        .getColor(R.color.x4_0));
            } else {
                dark2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(steel2Index).contains("x0.0")) {
                steel2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(steel2Index).contains("x0.25")) {
                steel2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(steel2Index).contains("x0.5")) {
                steel2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(steel2Index).contains("x2.0")) {
                steel2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(steel2Index).contains("x4.0")) {
                steel2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                steel2Tv.setBackgroundColor(Color.TRANSPARENT);
            }

            if (result.getString(fairy2Index).contains("x0.0")) {
                fairy2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_0));
            } else if (result.getString(fairy2Index).contains("x0.25")) {
                fairy2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_25));
            } else if (result.getString(fairy2Index).contains("x0.5")) {
                fairy2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x0_5));
            } else if (result.getString(fairy2Index).contains("x2.0")) {
                fairy2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x2_0));
            } else if (result.getString(fairy2Index).contains("x4.0")) {
                fairy2Tv.setBackgroundColor(getResources().getColor(
                        R.color.x4_0));
            } else {
                fairy2Tv.setBackgroundColor(Color.TRANSPARENT);
            }
            // fill the TextView with the retrieved data

            result.close();

        }
    }

}
