package com.ShaHar91.Pokedex.Items;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PocketList extends ListActivity {

    public static final String ROW_ID = "row_id";

    SimpleCursorAdapter pocketAdapter;
    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_concept);

        new GetPocketsTask().execute();

        String[] from = new String[]{"Name"};
        int[] to = new int[]{android.R.id.text1};
        pocketAdapter = new SimpleCursorAdapter(PocketList.this,
                android.R.layout.simple_list_item_1, null, from, to);
        setListAdapter(pocketAdapter);

    }

    private class GetPocketsTask extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Object... params) {
            databaseConnector.openDataBase();

            return databaseConnector.getAllPockets();
        }

        @Override
        protected void onPostExecute(Cursor result) {
            pocketAdapter.changeCursor(result);
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent viewItemList = new Intent(PocketList.this, ItemList.class);

        viewItemList.putExtra(ROW_ID, id);
        startActivity(viewItemList);

    }
}
