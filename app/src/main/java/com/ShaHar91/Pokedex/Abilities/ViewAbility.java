package com.ShaHar91.Pokedex.Abilities;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R.id;
import com.ShaHar91.Pokedex.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewAbility extends Activity {

    private long rowID;

    public static final String ROW_ID = "row_id";
    public static final String NAME = "name";

    TextView nameAbility, descriptionAbility, overworldAbility;
    Button buttonAbility;

    SimpleCursorAdapter abilityAdapter;
    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseConnector.openDataBase();

        setContentView(layout.ability_view_ability);

        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");

        nameAbility = (TextView) findViewById(id.ability_nameTv);
        descriptionAbility = (TextView) findViewById(id.ability_descriptionTv);
        overworldAbility = (TextView) findViewById(id.ability_overworldTv);

        buttonAbility = (Button) findViewById(id.ability_listBtn);
        buttonAbility.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent viewMove = new Intent(ViewAbility.this,
                        AbilitiesListExpandable.class);

                viewMove.putExtra(ROW_ID, rowID);
                viewMove.putExtra(NAME, getTitle().toString());
                startActivity(viewMove);

            }
        });

    }

    private class GetAbilityTask extends AsyncTask<Long, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Long... params) {
            return databaseConnector.getOneAbility(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToFirst();

            int nameIndex = result.getColumnIndex("name");
            int descriptionIndex = result.getColumnIndex("description");
            int overworldIndex = result.getColumnIndex("overworld");

            nameAbility.setText(result.getString(nameIndex));
            setTitle(result.getString(nameIndex));
            descriptionAbility.setText(result.getString(descriptionIndex));
            overworldAbility.setText(result.getString(overworldIndex));

            result.close();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetAbilityTask().execute(rowID);
    }

}