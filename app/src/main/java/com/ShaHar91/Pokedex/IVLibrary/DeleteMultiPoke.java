package com.ShaHar91.Pokedex.IVLibrary;

import java.util.ArrayList;

import com.ShaHar91.Pokedex.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

/* 23/03/2017
onlistitemclick toggles checkbox as well

TODO == wijziging van landscape mode reset deze de views
 */
public class DeleteMultiPoke extends ListActivity {
    public static final String ROW_ID = "row_id"; // Intent extra key
    Long rowID;
    MenuItem delete;
    ListAdapter pokeListAdapter;
    ArrayList<PokeListItems> pokeList = new ArrayList<>();
    ListView lvCustomList;
    IVLibraryDatabaseConnector ivLibrarydatabaseConnector = new IVLibraryDatabaseConnector(
            this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ability_list);
        lvCustomList = (ListView) findViewById(android.R.id.list);
        SearchView sv = (SearchView) findViewById(R.id.abilitySearchV);
        sv.setVisibility(View.GONE);
        ivLibrarydatabaseConnector.openDataBase();
        showList();
    }

    private void showList() {
        pokeList.clear();
        Cursor myCur;
        myCur = ivLibrarydatabaseConnector.getAllPokes();
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

        pokeListAdapter = new PokeListRemoveAdapter(DeleteMultiPoke.this,
                pokeList);
        lvCustomList.setAdapter(pokeListAdapter);

        lvCustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PokeListItems pokelist = (PokeListItems) parent.getItemAtPosition(position);
                CheckBox box = (CheckBox) view.findViewById(R.id.removeCBox);
                if (pokelist.isSelected()) {
                    pokelist.setSelected(false);
                    box.setChecked(false);
                } else {
                    pokelist.setSelected(true);
                    box.setChecked(true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.poke_menu_remove, menu);
        delete = menu.findItem(R.id.deletepokes);
        delete.setEnabled(true);
        delete.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.canceldeleting) {
            finish();
            return true;
        } else if (itemId == R.id.deletepokes) {
            // create a new AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    DeleteMultiPoke.this);
            builder.setTitle(R.string.confirmTitle); // title bar string
            builder.setMessage(R.string.confirmMessage); // message to display
            // provide an OK button that simply dismisses the dialog
            builder.setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int button) {
                    for (final PokeListItems p : ((PokeListRemoveAdapter) pokeListAdapter).getBox()) {
                        if (p.isSelected()) {
                            AsyncTask<Long, Object, Object> deleteTask = new AsyncTask<Long, Object, Object>() {

                                @Override
                                protected Object doInBackground(
                                        Long... params) {
                                    ivLibrarydatabaseConnector.deletePoke(pokeListAdapter
                                            .getItemId(p.getRowIdTag()));
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(
                                        Object result) {
                                    ivLibrarydatabaseConnector
                                            .openDataBase();
                                    showList();

                                    finish(); // return to the
                                    // BookLibrary Activity
                                }
                            };
                            // delete the AsyncTask to delete book at
                            // rowID
                            deleteTask.execute(rowID);
                        }
                    }
                }// end method onClick
            });
            builder.setNegativeButton(R.string.button_cancel, null);
            builder.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivLibrarydatabaseConnector.openDataBase();
        showList();
    }
}
