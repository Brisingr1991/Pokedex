package com.ShaHar91.Pokedex.Moves;

import java.io.IOException;
import java.io.InputStream;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ResourceCursorTreeAdapter;
import android.widget.TextView;

// more info check
// http://stackoverflow.com/questions/10644914/android-expandablelistview-and-sqlite-database
public class MoveListExpandable extends ExpandableListActivity {

    ExpandableListView elv;

    private long rowID;
    private String nameMove;

    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.move_list_expandable);

        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");
        nameMove = extras.getString("name");

        setTitle(nameMove);
        databaseConnector.openDataBase();

        elv = (ExpandableListView) findViewById(android.R.id.list);
        fillData();

    }

    private void fillData() {
        Cursor mGroupsCursor = databaseConnector.fetchGroup();
        mGroupsCursor.moveToFirst();

        setListAdapter(new MyExpandableListAdaptersecond(
                MoveListExpandable.this, mGroupsCursor,
                R.layout.move_list_expandable_grouprow,
                R.layout.move_list_expandable_childrow));

        elv.expandGroup(0);
        elv.expandGroup(1);
        elv.expandGroup(2);
        elv.expandGroup(3);

    }

    public class MyExpandableListAdaptersecond extends
            ResourceCursorTreeAdapter {

        public MyExpandableListAdaptersecond(Context context, Cursor cursor,
                                             int groupLayout, int childLayout) {
            super(context, cursor, R.layout.move_list_expandable_grouprow,
                    R.layout.move_list_expandable_childrow);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            String text = groupCursor.getString(groupCursor
                    .getColumnIndex("_id"));

            Cursor childCursor = databaseConnector.fetchChildren(
                    text.toString(), rowID);

            childCursor.moveToFirst();
            return childCursor;
        }

        @Override
        protected void bindGroupView(View view, Context context, Cursor cursor,
                                     boolean isExpanded) {

            TextView group = (TextView) view.findViewById(R.id.group_text);
            TextView count = (TextView) view.findViewById(R.id.count_text);

            String text = cursor.getString(cursor.getColumnIndex("_id"));

            Cursor childCursor = databaseConnector.fetchChildren(
                    text.toString(), rowID);

            childCursor.moveToFirst();

            group.setText(cursor.getString(cursor.getColumnIndex("learnMoveBy")));

            count.setText("(" + childCursor.getCount() + ")");

        }

        @Override
        protected void bindChildView(View view, Context context, Cursor cursor,
                                     boolean isLastChild) {
            TextView child = (TextView) view.findViewById(R.id.nameDexTv);
            TextView no = (TextView) view.findViewById(R.id.natDexTv);
            TextView type1 = (Button) view.findViewById(R.id.type1Btn);
            TextView type2 = (Button) view.findViewById(R.id.type2Btn);
            ImageView imView = (ImageView) view.findViewById(R.id.pokeSpriteIV);

            AssetManager assetManager = getAssets();

            try {

                InputStream ims = assetManager.open("icon/ico_"
                        + cursor.getString(cursor
                        .getColumnIndex("pokemonOrder")) + ".png");

                Drawable d = Drawable.createFromStream(ims, null);

                imView.setImageDrawable(d);

            } catch (IOException e) {
                e.printStackTrace();
            }

            no.setText(cursor.getString(cursor.getColumnIndex("pokemonOrder")));
            type1.setText(cursor.getString(cursor.getColumnIndex("type1")));
            if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Bug")) {
                type1.setBackgroundResource(R.drawable.bug_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Dark")) {
                type1.setBackgroundResource(R.drawable.dark_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Dragon")) {
                type1.setBackgroundResource(R.drawable.dragon_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Electric")) {
                type1.setBackgroundResource(R.drawable.electric_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Fairy")) {
                type1.setBackgroundResource(R.drawable.fairy_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Fight")) {
                type1.setBackgroundResource(R.drawable.fight_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Fire")) {
                type1.setBackgroundResource(R.drawable.fire_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Flying")) {
                type1.setBackgroundResource(R.drawable.flying_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Ghost")) {
                type1.setBackgroundResource(R.drawable.ghost_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Grass")) {
                type1.setBackgroundResource(R.drawable.grass_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Ground")) {
                type1.setBackgroundResource(R.drawable.ground_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Ice")) {
                type1.setBackgroundResource(R.drawable.ice_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Normal")) {
                type1.setBackgroundResource(R.drawable.normal_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Poison")) {
                type1.setBackgroundResource(R.drawable.poison_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Psychic")) {
                type1.setBackgroundResource(R.drawable.psychic_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Rock")) {
                type1.setBackgroundResource(R.drawable.rock_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Steel")) {
                type1.setBackgroundResource(R.drawable.steel_button_border);
            } else if (cursor.getString(cursor.getColumnIndex("type1"))
                    .contains("Water")) {
                type1.setBackgroundResource(R.drawable.water_button_border);
            }

            type2.setText(cursor.getString(cursor.getColumnIndex("type2")));
            if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Bug")) {
                type2.setBackgroundResource(R.drawable.bug_button_border);
                type2.setVisibility(View.VISIBLE);
            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Dark")) {
                type2.setBackgroundResource(R.drawable.dark_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Dragon")) {
                type2.setBackgroundResource(R.drawable.dragon_button_border);
                type2.setVisibility(View.VISIBLE);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Electric")) {
                type2.setBackgroundResource(R.drawable.electric_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Fairy")) {
                type2.setBackgroundResource(R.drawable.fairy_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Fight")) {
                type2.setBackgroundResource(R.drawable.fight_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Fire")) {
                type2.setBackgroundResource(R.drawable.fire_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Flying")) {
                type2.setBackgroundResource(R.drawable.flying_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Ghost")) {
                type2.setBackgroundResource(R.drawable.ghost_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Grass")) {
                type2.setBackgroundResource(R.drawable.grass_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Ground")) {
                type2.setBackgroundResource(R.drawable.ground_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Ice")) {
                type2.setBackgroundResource(R.drawable.ice_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Normal")) {
                type2.setBackgroundResource(R.drawable.normal_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Poison")) {
                type2.setBackgroundResource(R.drawable.poison_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Psychic")) {
                type2.setBackgroundResource(R.drawable.psychic_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Rock")) {
                type2.setBackgroundResource(R.drawable.rock_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Steel")) {
                type2.setBackgroundResource(R.drawable.steel_button_border);
                type2.setVisibility(View.VISIBLE);

            } else if (cursor.getString(cursor.getColumnIndex("type2"))
                    .contains("Water")) {
                type2.setBackgroundResource(R.drawable.water_button_border);
                type2.setVisibility(View.VISIBLE);

            } else {
                type2.setVisibility(View.INVISIBLE);

            }

            if (cursor.getString(cursor.getColumnIndex("learnMoveBy"))
                    .contains("Level")) {
                child.setText(cursor.getString(cursor.getColumnIndex("name"))
                        + " \n --> Lvl. "
                        + cursor.getString(cursor
                        .getColumnIndex("moveTypeLevel")));
            } else {
                child.setText(cursor.getString(cursor.getColumnIndex("name")));
            }
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return super.getChildrenCount(groupPosition);
        }

    }

}