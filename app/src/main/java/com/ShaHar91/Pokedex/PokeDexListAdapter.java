package com.ShaHar91.Pokedex;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PokeDexListAdapter extends BaseAdapter implements
        FilterQueryProvider {

    Context ctx;
    LayoutInflater LInflator;
    ArrayList<PokeDexListItems> pokeDexList;

    public static final String NAT_DEX = "nat_dex";
    public static final String ROW_ID = "row_id"; // Intent extra key

    public PokeDexListAdapter(Context context, ArrayList<PokeDexListItems> list) {
        ctx = context;
        pokeDexList = list;
        LInflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pokeDexList.size();
    }

    @Override
    public Object getItem(int position) {
        return pokeDexList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView pokeSprite;
        TextView pokeTv;
        TextView natDexTv;
        Button type1;
        Button type2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        PokeDexListItems pokeDexListItems = pokeDexList.get(position);

        if (convertView == null) {
            convertView = LInflator.inflate(R.layout.nat_dex_list_item, null);
            holder = new ViewHolder();
            holder.pokeTv = (TextView) convertView.findViewById(R.id.nameDexTv);
            holder.natDexTv = (TextView) convertView
                    .findViewById(R.id.natDexTv);
            holder.type1 = (Button) convertView.findViewById(R.id.type1Btn);
            holder.type2 = (Button) convertView.findViewById(R.id.type2Btn);

            holder.pokeSprite = (ImageView) convertView
                    .findViewById(R.id.pokeSpriteIV);
            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.pokeTv.setText(pokeDexListItems.getpokeName());
        holder.natDexTv.setText(pokeDexListItems.getNatDex());
        holder.type1.setText(pokeDexListItems.getType1());
        holder.natDexTv.setTag(pokeDexListItems.getisMega());
        holder.type2.setText(pokeDexListItems.getType2());

        AssetManager assetManager = ctx.getAssets();

        try {

            InputStream ims = assetManager.open("icon/ico_"
                    + pokeDexListItems.getImageIndex() + ".png");

            Drawable d = Drawable.createFromStream(ims, null);

            holder.pokeSprite.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (holder.type1.getText().toString().contains("Bug")) {
            holder.type1.setBackgroundResource(R.drawable.bug_button_border);
        } else if (holder.type1.getText().toString().contains("Dark")) {
            holder.type1.setBackgroundResource(R.drawable.dark_button_border);
        } else if (holder.type1.getText().toString().contains("Dragon")) {
            holder.type1.setBackgroundResource(R.drawable.dragon_button_border);
        } else if (holder.type1.getText().toString().contains("Electric")) {
            holder.type1
                    .setBackgroundResource(R.drawable.electric_button_border);
        } else if (holder.type1.getText().toString().contains("Fairy")) {
            holder.type1.setBackgroundResource(R.drawable.fairy_button_border);
        } else if (holder.type1.getText().toString().contains("Fight")) {
            holder.type1.setBackgroundResource(R.drawable.fight_button_border);
        } else if (holder.type1.getText().toString().contains("Fire")) {
            holder.type1.setBackgroundResource(R.drawable.fire_button_border);
        } else if (holder.type1.getText().toString().contains("Flying")) {
            holder.type1.setBackgroundResource(R.drawable.flying_button_border);
        } else if (holder.type1.getText().toString().contains("Ghost")) {
            holder.type1.setBackgroundResource(R.drawable.ghost_button_border);
        } else if (holder.type1.getText().toString().contains("Grass")) {
            holder.type1.setBackgroundResource(R.drawable.grass_button_border);
        } else if (holder.type1.getText().toString().contains("Ground")) {
            holder.type1.setBackgroundResource(R.drawable.ground_button_border);
        } else if (holder.type1.getText().toString().contains("Ice")) {
            holder.type1.setBackgroundResource(R.drawable.ice_button_border);
        } else if (holder.type1.getText().toString().contains("Normal")) {
            holder.type1.setBackgroundResource(R.drawable.normal_button_border);
        } else if (holder.type1.getText().toString().contains("Poison")) {
            holder.type1.setBackgroundResource(R.drawable.poison_button_border);
        } else if (holder.type1.getText().toString().contains("Psychic")) {
            holder.type1
                    .setBackgroundResource(R.drawable.psychic_button_border);
        } else if (holder.type1.getText().toString().contains("Rock")) {
            holder.type1.setBackgroundResource(R.drawable.rock_button_border);
        } else if (holder.type1.getText().toString().contains("Steel")) {
            holder.type1.setBackgroundResource(R.drawable.steel_button_border);
        } else if (holder.type1.getText().toString().contains("Water")) {
            holder.type1.setBackgroundResource(R.drawable.water_button_border);
        }

        if (holder.type2.getText().toString().contains("Bug")) {
            holder.type2.setBackgroundResource(R.drawable.bug_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Dark")) {
            holder.type2.setBackgroundResource(R.drawable.dark_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Dragon")) {
            holder.type2.setBackgroundResource(R.drawable.dragon_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Electric")) {
            holder.type2
                    .setBackgroundResource(R.drawable.electric_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Fairy")) {
            holder.type2.setBackgroundResource(R.drawable.fairy_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Fight")) {
            holder.type2.setBackgroundResource(R.drawable.fight_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Fire")) {
            holder.type2.setBackgroundResource(R.drawable.fire_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Flying")) {
            holder.type2.setBackgroundResource(R.drawable.flying_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Ghost")) {
            holder.type2.setBackgroundResource(R.drawable.ghost_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Grass")) {
            holder.type2.setBackgroundResource(R.drawable.grass_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Ground")) {
            holder.type2.setBackgroundResource(R.drawable.ground_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Ice")) {
            holder.type2.setBackgroundResource(R.drawable.ice_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Normal")) {
            holder.type2.setBackgroundResource(R.drawable.normal_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Poison")) {
            holder.type2.setBackgroundResource(R.drawable.poison_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Psychic")) {
            holder.type2
                    .setBackgroundResource(R.drawable.psychic_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Rock")) {
            holder.type2.setBackgroundResource(R.drawable.rock_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Steel")) {
            holder.type2.setBackgroundResource(R.drawable.steel_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else if (holder.type2.getText().toString().contains("Water")) {
            holder.type2.setBackgroundResource(R.drawable.water_button_border);
            holder.type2.setVisibility(View.VISIBLE);

        } else {
            holder.type2.setVisibility(View.INVISIBLE);
        }

        final int ISMega = pokeDexListItems.getisMega();
        holder.natDexTv.setTag(ISMega);

        final long natDex = pokeDexListItems.getLongNatDex();

        final int tag = pokeDexListItems.getRowIdTag();

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (ISMega == 0) {
                        Intent viewPoke = new Intent(ctx, PokedexEntry.class);

                        viewPoke.putExtra(ROW_ID, getItemId(tag));

                        ctx.startActivity(viewPoke);
                    } else if (ISMega == 1) {
                        Intent viewPoke = new Intent(ctx,
                                PokedexEntryMega.class);

                        viewPoke.putExtra(NAT_DEX, natDex);

                        ctx.startActivity(viewPoke);
                    }
                } catch (Exception e) {
                    Toast.makeText(ctx, "This entry does not exist anymore!!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        return convertView;
    }

    @Override
    public Cursor runQuery(CharSequence constraint) {
        return null;
    }

}
