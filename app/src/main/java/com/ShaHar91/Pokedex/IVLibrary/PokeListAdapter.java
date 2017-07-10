package com.ShaHar91.Pokedex.IVLibrary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.ShaHar91.Pokedex.R.id;
import com.ShaHar91.Pokedex.R.layout;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PokeListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater LInflator;
    ArrayList<PokeListItems> pokeList;

    public static final String ROW_ID = "row_id"; // Intent extra key

    public PokeListAdapter(Context context, ArrayList<PokeListItems> list) {
        ctx = context;
        pokeList = list;
        LInflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pokeList.size();
    }

    @Override
    public Object getItem(int position) {
        return pokeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PokeListItems pokeListItems = pokeList.get(position);

        if (convertView == null) {
            convertView = LInflator.inflate(layout.ivlibrary_list_item, null);
        }

        TextView pokeTv = (TextView) convertView.findViewById(id.pokeTv);
        TextView genderTv = (TextView) convertView.findViewById(id.genderTv);

        RadioButton hpRb = (RadioButton) convertView.findViewById(id.hpRb);
        RadioButton attRb = (RadioButton) convertView.findViewById(id.attRb);
        RadioButton defRb = (RadioButton) convertView.findViewById(id.defRb);
        RadioButton spAttRb = (RadioButton) convertView
                .findViewById(id.spAttRb);
        RadioButton spDefRb = (RadioButton) convertView
                .findViewById(id.spDefRb);
        RadioButton speedRb = (RadioButton) convertView
                .findViewById(id.speedRb);

        ImageView pokeSprite = (ImageView) convertView
                .findViewById(id.pokeSpriteIV);

        pokeTv.setText(pokeListItems.getpokeName());
        genderTv.setText(pokeListItems.getGender());
        hpRb.setChecked((pokeListItems.getHP() == 0 ? false : true));
        attRb.setChecked((pokeListItems.getAtt() == 0 ? false : true));
        defRb.setChecked((pokeListItems.getDef() == 0 ? false : true));
        spAttRb.setChecked((pokeListItems.getSp_Att() == 0 ? false : true));
        spDefRb.setChecked((pokeListItems.getSp_Def() == 0 ? false : true));
        speedRb.setChecked((pokeListItems.getSpeed() == 0 ? false : true));

        AssetManager assetManager = ctx.getAssets();

        try {

            InputStream ims = assetManager.open("icon/ico_"
                    + pokeListItems.getImageIndex() + ".png");

            Drawable d = Drawable.createFromStream(ims, null);

            pokeSprite.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int tag = pokeListItems.getRowIdTag();
        pokeTv.setTag(tag);

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    Intent viewBook = new Intent(ctx, ViewPoke.class);

                    // pass the selected contact's row ID as an extra with the
                    // Intent
                    viewBook.putExtra(ROW_ID, getItemId(tag));

                    ctx.startActivity(viewBook); // start the ViewBook Activity
                } catch (Exception e) {
                    Toast.makeText(ctx, "This entry does not exist anymore!!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        return convertView;
    }
}
