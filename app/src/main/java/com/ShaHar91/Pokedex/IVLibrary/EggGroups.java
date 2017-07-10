package com.ShaHar91.Pokedex.IVLibrary;

import java.io.IOException;
import java.io.InputStream;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class EggGroups extends ListActivity {
    public String eggGroup = "";
    public Spinner groups;

    MyAdapter mListAdapter;

    Button goBtn;

    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseConnector.openDataBase();

        setContentView(R.layout.ivlibrary_egg_group_list);

        Bundle extras = getIntent().getExtras();

        groups = (Spinner) findViewById(R.id.groupSpin);

        if (extras != null) {
            eggGroup = extras.getString("eggGroup");

            ArrayAdapter<String> array_spinner = (ArrayAdapter<String>) groups
                    .getAdapter();

            groups.setSelection(array_spinner.getPosition(eggGroup));

        }

        Cursor myCur = null;

        myCur = databaseConnector.filterGroups(eggGroup);

        mListAdapter = new MyAdapter(EggGroups.this, myCur);
        setListAdapter(mListAdapter);

        goBtn = (Button) findViewById(R.id.goBtn);
        goBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                databaseConnector.openDataBase();
                try {
                    mListAdapter.changeCursor(databaseConnector
                            .filterGroups(groups.getSelectedItem().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        databaseConnector.openDataBase();

        super.onResume();
    }

    private class MyAdapter extends ResourceCursorAdapter {

        public MyAdapter(Context context, Cursor cur) {
            super(context, R.layout.ivlibrary_egg_group_list_item, cur);
        }

        @Override
        public void bindView(View view, Context context, Cursor cur) {

            TextView natDexTv = (TextView) view.findViewById(R.id.natDexTv);
            TextView pokeTv = (TextView) view.findViewById(R.id.nameDexTv);
            TextView egggroup1DexTv = (TextView) view
                    .findViewById(R.id.eggGroup1DexTv);
            TextView egggroup2DexTv = (TextView) view
                    .findViewById(R.id.eggGroup2DexTv);

            ImageView pokeSprite = (ImageView) view
                    .findViewById(R.id.pokeSpriteIV);

            natDexTv.setText(cur.getString(cur.getColumnIndex("nat_dex")));
            pokeTv.setText(cur.getString(cur.getColumnIndex("name")));
            egggroup1DexTv.setText(cur.getString(cur
                    .getColumnIndex("egg_group1")));
            egggroup2DexTv.setText(cur.getString(cur
                    .getColumnIndex("egg_group2")));

            AssetManager assetManager = getAssets();

            int imageIndex = cur.getColumnIndex("_id");
            try {
                InputStream ims = assetManager.open("icon/ico_"
                        + cur.getString(imageIndex) + ".png");

                Drawable d = Drawable.createFromStream(ims, null);

                pokeSprite.setImageDrawable(d);

            } catch (IOException ex) {
                return;
            }

        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent dexIntent = new Intent(EggGroups.this, AddPoke.class);
        // pass the selected contact's row ID as an extra with the Intent

        Cursor c = mListAdapter.getCursor();
        String name = c.getString(c.getColumnIndex("name"));
        String nat_dex = c.getString(c.getColumnIndex("nat_dex"));
        String egg1 = c.getString(c.getColumnIndex("egg_group1"));
        String egg2 = c.getString(c.getColumnIndex("egg_group2"));

        dexIntent.putExtra("name", name);
        dexIntent.putExtra("gender", "");
        dexIntent.putExtra("nat_dex", nat_dex);
        dexIntent.putExtra("egg_group1", egg1);
        dexIntent.putExtra("egg_group2", egg2);
        dexIntent.putExtra("nature", "");
        dexIntent.putExtra("ability", "");
        dexIntent.putExtra("note", "");
        dexIntent.putExtra("hp", 0);
        dexIntent.putExtra("att", 0);
        dexIntent.putExtra("def", 0);
        dexIntent.putExtra("sp_att", 0);
        dexIntent.putExtra("sp_def", 0);
        dexIntent.putExtra("speed", 0);

        startActivity(dexIntent); // start the ViewBook Activity

    }
}
