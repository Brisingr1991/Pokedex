package com.ShaHar91.Pokedex;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDesc extends Activity {
    public static final String ROW_ID = "row_id";
    DatabaseConnector databaseConnector = new DatabaseConnector(ViewDesc.this);

    private long rowID;
    private TextView name, ruby, sapphire, emerald, fire, leaf, diamond, pearl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pokedex_entry_view_description);

        setView();

        new LoadDescTask().execute(rowID);

    }

    private void setView() {
        name = (TextView) findViewById(R.id.desc_nameTV);
        ruby = (TextView) findViewById(R.id.desc_rubyTv);
        sapphire = (TextView) findViewById(R.id.desc_sapTv);
        emerald = (TextView) findViewById(R.id.desc_emeraldTV);
        fire = (TextView) findViewById(R.id.desc_fireTV);
        leaf = (TextView) findViewById(R.id.desc_leafTV);

        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");
    }

    private class LoadDescTask extends AsyncTask<Long, Object, Cursor> {

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.openDataBase();

            // get a cursor containing all data on given entry
            return databaseConnector.getDescription(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);
            result.moveToFirst(); // move to first item

            // get the column index for each data item
            // int nameIndex = result.getColumnIndex("name");
            int rubyIndex = result.getColumnIndex("ruby_fla");
            int sapIndex = result.getColumnIndex("sap_fla");
            int emeraldIndex = result.getColumnIndex("emerald_fla");
            int fireIndex = result.getColumnIndex("fire_fla");
            int leafIndex = result.getColumnIndex("leaf_fla");

            // fill the TextView with the retrieved data
            // name.setText(result.getString(nameIndex));

            ruby.setText(result.getString(rubyIndex));
            sapphire.setText(result.getString(sapIndex));
            emerald.setText(result.getString(emeraldIndex));
            fire.setText(result.getString(fireIndex));
            leaf.setText(result.getString(leafIndex));

            result.close();
        }
    }

}