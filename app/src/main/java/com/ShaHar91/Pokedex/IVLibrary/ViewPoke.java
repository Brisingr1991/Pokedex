package com.ShaHar91.Pokedex.IVLibrary;

import java.io.IOException;
import java.io.InputStream;

import com.ShaHar91.Pokedex.R;
import com.ShaHar91.Pokedex.R.id;
import com.ShaHar91.Pokedex.R.layout;
import com.ShaHar91.Pokedex.R.string;
import com.ShaHar91.Pokedex.CustomViews.ExpandableTextView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewPoke extends Activity implements OnClickListener {
    /* 20/03/2017
    - attributecheck aangemaakt
	- onPostExecute result Cursor opgeruimd

	 */

    public static final String Egg = "eggGroup"; // Intent extra key
    IVLibraryDatabaseConnector ivLibrarydatabaseConnector = new IVLibraryDatabaseConnector(
            this);
    private long rowID;
    private TextView nameTv, genderTv, egg1Tv, egg2Tv, natureTv, abilityTv,
            natDexTv;
    private CheckBox hpCBox, attCBox, defCBox, spAttCBox, spDefCBox, speedCBox;
    private ImageView pokeDrawable;
    private ExpandableTextView notesTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivLibrarydatabaseConnector.openDataBase();

        setContentView(layout.ivlibrary_view_poke);

        nameTv = (TextView) findViewById(id.name_dexTv);
        genderTv = (TextView) findViewById(id.genderTv);
        egg1Tv = (TextView) findViewById(id.egg1Tv);
        egg1Tv.setOnClickListener(this);
        egg2Tv = (TextView) findViewById(id.egg2Tv);
        egg2Tv.setOnClickListener(this);
        pokeDrawable = (ImageView) findViewById(id.pokeDrawable);
        natureTv = (TextView) findViewById(id.natureTv);
        abilityTv = (TextView) findViewById(id.abilityTv);
        natDexTv = (TextView) findViewById(id.natDexTv);
        notesTv = (ExpandableTextView) findViewById(id.notesTv);
        hpCBox = (CheckBox) findViewById(id.hpviewCBox);
        attCBox = (CheckBox) findViewById(id.attviewCBox);
        defCBox = (CheckBox) findViewById(id.defviewCBox);
        spAttCBox = (CheckBox) findViewById(id.spAttviewCBox);
        spDefCBox = (CheckBox) findViewById(id.spDefviewCBox);
        speedCBox = (CheckBox) findViewById(id.speedviewCBox);

        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");

        ivLibrarydatabaseConnector.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadPokeTask().execute(rowID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_poke, menu);
        return true;
    }

    public int attributeCheck(CheckBox attribute) {
        if (attribute.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == id.editpoke) {
            Intent addIntent = new Intent(this, AddPoke.class);
            addIntent.putExtra("row_id", rowID);

            startActivity(addIntent); // start the Activity
            return true;
        } else if (itemId == id.deletepoke) {
            // create a new AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewPoke.this);
            builder.setTitle(string.confirmTitle); // title bar string
            builder.setMessage(string.confirmMessage); // message to display
            // provide an OK button that simply dismisses the dialog
            builder.setPositiveButton(string.button_delete,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int button) {
                            // create an AsyncTask that deletes the contact in
                            // another tread, then calls finish after the deletion
                            AsyncTask<Long, Object, Object> deleteTask = new AsyncTask<Long, Object, Object>() {

                                @Override
                                protected Object doInBackground(Long... params) {
                                    ivLibrarydatabaseConnector
                                            .deletePoke(params[0]);
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Object result) {
                                    ivLibrarydatabaseConnector.close();
                                    finish(); // return to the BookLibrary
                                    // Activity
                                }
                            };

                            // delete the AsyncTask to delete book at rowID
                            deleteTask.execute(new Long[]{rowID});

                        } // end method onClick
                    });
            builder.setNegativeButton(string.button_cancel, null);
            builder.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.egg1Tv) {
            Intent dexList1 = new Intent(ViewPoke.this, EggGroups.class);
            dexList1.putExtra(Egg, egg1Tv.getText());
            startActivity(dexList1);
        } else if (id == R.id.egg2Tv) {
            if (egg2Tv.getText().length() > 0) {
                Intent dexList2 = new Intent(ViewPoke.this, EggGroups.class);
                dexList2.putExtra(Egg, egg2Tv.getText());

                startActivity(dexList2);
            }
        }
    }

    private class LoadPokeTask extends AsyncTask<Long, Object, Cursor> {
        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            ivLibrarydatabaseConnector.openDataBase();

            // get a cursor containing all data on given entry
            return ivLibrarydatabaseConnector.getAddedPoke(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);
            result.moveToFirst(); // move to first item

            // fill the TextView with the retrieved data
            nameTv.setText(result.getString(result.getColumnIndex("name")));
            setTitle(result.getString(result.getColumnIndex("name")));

            genderTv.setText(result.getString(result.getColumnIndex("gender")));
            natDexTv.setText(result.getString(result.getColumnIndex("nat_dex")));
            egg1Tv.setText(result.getString(result.getColumnIndex("egg_group1")));
            egg2Tv.setText(result.getString(result.getColumnIndex("egg_group2")));
            natureTv.setText(result.getString(result.getColumnIndex("nature")));
            abilityTv.setText(result.getString(result.getColumnIndex("ability")));
            notesTv.setText(result.getString(result.getColumnIndex("note")));

            hpCBox.setChecked((result.getInt(result.getColumnIndex("hp")) == 1) ? true : false);
            attCBox.setChecked((result.getInt(result.getColumnIndex("att")) == 1) ? true : false);
            defCBox.setChecked((result.getInt(result.getColumnIndex("def")) == 1) ? true : false);
            spAttCBox.setChecked((result.getInt(result.getColumnIndex("sp_att")) == 1) ? true : false);
            spDefCBox.setChecked((result.getInt(result.getColumnIndex("sp_def")) == 1) ? true : false);
            speedCBox.setChecked((result.getInt(result.getColumnIndex("speed")) == 1) ? true : false);

            AssetManager assetManager = getAssets();

            try {

                InputStream ims = assetManager.open("pokes/p_"
                        + result.getString(result.getColumnIndex("nat_dex")) + ".png");

                Drawable d = Drawable.createFromStream(ims, null);

                pokeDrawable.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ivLibrarydatabaseConnector.close();
            result.close();
        }
    }
}
