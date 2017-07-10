package com.ShaHar91.Pokedex.Items;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ResourceCursorTreeAdapter;
import android.widget.TextView;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;

public class ItemList extends ExpandableListActivity {

    // public static final String ROW_ID = "row_id";

    ExpandableListView elv;

    DatabaseConnector databaseConnector = new DatabaseConnector(this);

    private long rowID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_expand_concept);

        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");

        databaseConnector.openDataBase();

        elv = (ExpandableListView) findViewById(android.R.id.list);
        fillData();

        // String[] from = new String[] { "name" };
        // int[] to = new int[] { android.R.id.text1 };
        // pocketAdapter = new SimpleCursorAdapter(ItemList.this,
        // android.R.layout.simple_list_item_1, null, from, to);
        // setListAdapter(pocketAdapter);

    }

    private void fillData() {
        Cursor mGroupsCursor = databaseConnector.fetchItemGroup(rowID);
        mGroupsCursor.moveToFirst();

        setListAdapter(new MyExpandableListAdaptersecond(ItemList.this,
                mGroupsCursor, R.layout.list_expand_concept_grouprow,
                R.layout.list_expand_concept_childrow));

        Integer group_count = mGroupsCursor.getCount();

        for (int i = 0; i < group_count; i++) {
            elv.expandGroup(i);
        }

    }

    public class MyExpandableListAdaptersecond extends
            ResourceCursorTreeAdapter {

        public MyExpandableListAdaptersecond(Context context, Cursor cursor,
                                             int groupLayout, int childLayout) {
            super(context, cursor, R.layout.list_expand_concept_grouprow,
                    R.layout.list_expand_concept_childrow);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            Long cat_id = groupCursor
                    .getLong(groupCursor.getColumnIndex("_id"));

            Cursor childCursor = databaseConnector.fetchItemChildren(rowID,
                    cat_id);

            childCursor.moveToFirst();
            return childCursor;
        }

        @Override
        protected void bindGroupView(View view, Context context, Cursor cursor,
                                     boolean isExpanded) {

            TextView group = (TextView) view.findViewById(R.id.group_text);
            TextView count = (TextView) view.findViewById(R.id.count_text);

            Long cat_id = cursor.getLong(cursor.getColumnIndex("_id"));

            Cursor childCursor = databaseConnector.fetchItemChildren(rowID,
                    cat_id);

            childCursor.moveToFirst();

            group.setText(cursor.getString(cursor.getColumnIndex("name")));

            count.setText("(" + childCursor.getCount() + ")");

        }

        @Override
        protected void bindChildView(View view, Context context, Cursor cursor,
                                     boolean isLastChild) {
            TextView item = (TextView) view.findViewById(R.id.itemTv);

            item.setText(cursor.getString(cursor.getColumnIndex("name")));

        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return super.getChildrenCount(groupPosition);
        }

    }

}
