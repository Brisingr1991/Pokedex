package com.ShaHar91.Pokedex.Moves;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R.drawable;
import com.ShaHar91.Pokedex.R.id;
import com.ShaHar91.Pokedex.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewMove extends Activity {

    private long rowID;

    public static final String ROW_ID = "row_id";
    public static final String NAME = "name";

    TextView name, PP, Att, Acc, Cat, TMno, battleEffect, depthEffect,
            depthEffectTitle, secondAtt, effectRate;
    Button type, buttonTesting;

    SimpleCursorAdapter moveAdapter;
    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseConnector.openDataBase();

        setContentView(layout.moves_view_move);

        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");

        name = (TextView) findViewById(id.nameMoveTv);
        PP = (TextView) findViewById(id.movePPTv);
        Att = (TextView) findViewById(id.moveAttTv);
        Acc = (TextView) findViewById(id.moveAccTv);
        Cat = (TextView) findViewById(id.moveCategoryTv);
        TMno = (TextView) findViewById(id.moveTMTv);
        battleEffect = (TextView) findViewById(id.moveBattleEffectTv);
        depthEffect = (TextView) findViewById(id.moveDepthEffectTv);
        depthEffectTitle = (TextView) findViewById(id.moveDepthTitleTv);
        secondAtt = (TextView) findViewById(id.moveSecondAttTv);
        effectRate = (TextView) findViewById(id.moveEffectRateTv);
        type = (Button) findViewById(id.moveTypeBtn);
        buttonTesting = (Button) findViewById(id.buttontesting);
        buttonTesting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent viewMove = new Intent(ViewMove.this,
                        MoveListExpandable.class);

                viewMove.putExtra(ROW_ID, rowID);
                viewMove.putExtra(NAME, getTitle().toString());
                startActivity(viewMove);

            }
        });

    }

    private class GetMoveTask extends AsyncTask<Long, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Long... params) {
            return databaseConnector.getOneMove(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToFirst();

            int nameIndex = result.getColumnIndex("name");
            int typeIndex = result.getColumnIndex("type");

            int PPIndex = result.getColumnIndex("power_points");
            int AttIndex = result.getColumnIndex("base_power");
            int AccIndex = result.getColumnIndex("accuracy");
            int CatIndex = result.getColumnIndex("category");
            int TMnoIndex = result.getColumnIndex("tm_no");
            int battleEffectIndex = result.getColumnIndex("battle_effect");
            int depthEffectIndex = result.getColumnIndex("depth_effect");
            int secondAttIndex = result.getColumnIndex("second_effect");
            int effectRateIndex = result.getColumnIndex("effect_rate");

            name.setText(result.getString(nameIndex));
            setTitle("Move - " + result.getString(nameIndex));

            PP.setText(result.getString(PPIndex));
            Att.setText(result.getString(AttIndex));
            Acc.setText(result.getString(AccIndex));
            Cat.setText(result.getString(CatIndex));
            TMno.setText(result.getString(TMnoIndex));
            battleEffect.setText(result.getString(battleEffectIndex));
            depthEffect.setText(result.getString(depthEffectIndex));
            secondAtt.setText(result.getString(secondAttIndex));
            effectRate.setText(result.getString(effectRateIndex));
            type.setText(result.getString(typeIndex));

            if (result.getString(result.getColumnIndex("type")).contains("Bug")) {
                type.setBackgroundResource(drawable.bug_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Dark")) {
                type.setBackgroundResource(drawable.dark_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Dragon")) {
                type.setBackgroundResource(drawable.dragon_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Electric")) {
                type.setBackgroundResource(drawable.electric_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Fairy")) {
                type.setBackgroundResource(drawable.fairy_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Fight")) {
                type.setText("Fight");
                type.setBackgroundResource(drawable.fight_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Fire")) {
                type.setBackgroundResource(drawable.fire_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Flying")) {
                type.setBackgroundResource(drawable.flying_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Ghost")) {
                type.setBackgroundResource(drawable.ghost_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Grass")) {
                type.setBackgroundResource(drawable.grass_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Ground")) {
                type.setBackgroundResource(drawable.ground_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Ice")) {
                type.setBackgroundResource(drawable.ice_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Normal")) {
                type.setBackgroundResource(drawable.normal_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Poison")) {
                type.setBackgroundResource(drawable.poison_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Psychic")) {
                type.setBackgroundResource(drawable.psychic_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Rock")) {
                type.setBackgroundResource(drawable.rock_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Steel")) {
                type.setBackgroundResource(drawable.steel_button_border);
            } else if (result.getString(result.getColumnIndex("type"))
                    .contains("Water")) {
                type.setBackgroundResource(drawable.water_button_border);
            }

            result.close();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetMoveTask().execute(rowID);
    }

}