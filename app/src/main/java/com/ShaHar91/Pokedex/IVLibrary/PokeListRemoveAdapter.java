package com.ShaHar91.Pokedex.IVLibrary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.ShaHar91.Pokedex.R.id;
import com.ShaHar91.Pokedex.R.layout;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PokeListRemoveAdapter extends BaseAdapter {
    public static final String ROW_ID = "row_id"; // Intent extra key

    private Context ctx;
    private LayoutInflater LInflator;
    private ArrayList<PokeListItems> pokeList;
    private OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getPokes((Integer) buttonView.getTag()).setSelected(isChecked);
        }
    };

    PokeListRemoveAdapter(Context context, ArrayList<PokeListItems> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        PokeListItems pokeListItems = pokeList.get(position);
        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = LInflator.inflate(layout.ivlibrary_list_remove_item,
                    null);

            holder.pokeTv = (TextView) convertView.findViewById(id.pokeTv);
            holder.genderTv = (TextView) convertView.findViewById(id.genderTv);
            holder.hpRb = (RadioButton) convertView.findViewById(id.hpRb);
            holder.attRb = (RadioButton) convertView.findViewById(id.attRb);
            holder.defRb = (RadioButton) convertView.findViewById(id.defRb);
            holder.spAttRb = (RadioButton) convertView
                    .findViewById(id.spAttRb);
            holder.spDefRb = (RadioButton) convertView
                    .findViewById(id.spDefRb);
            holder.speedRb = (RadioButton) convertView
                    .findViewById(id.speedRb);
            holder.pokeSprite = (ImageView) convertView
                    .findViewById(id.pokeSpriteIV);
            holder.box = (CheckBox) convertView
                    .findViewById(id.removeCBox);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) (convertView.getTag());
        }

        holder.pokeTv.setText(pokeListItems.getpokeName());
        holder.genderTv.setText(pokeListItems.getGender());
        holder.hpRb.setChecked((pokeListItems.getHP() != 0));
        holder.attRb.setChecked((pokeListItems.getAtt() != 0));
        holder.defRb.setChecked((pokeListItems.getDef() != 0));
        holder.spAttRb.setChecked((pokeListItems.getSp_Att() != 0));
        holder.spDefRb.setChecked((pokeListItems.getSp_Def() != 0));
        holder.speedRb.setChecked((pokeListItems.getSpeed() != 0));

        AssetManager assetManager = ctx.getAssets();

        try {
            InputStream ims = assetManager.open("icon/ico_"
                    + pokeListItems.getImageIndex() + ".png");

            Drawable d = Drawable.createFromStream(ims, null);

            holder.pokeSprite.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.pokeTv.setTag(pokeListItems.getRowIdTag());
        holder.box.setOnCheckedChangeListener(myCheckChangList);
        holder.box.setTag(position);
        holder.box.setChecked(getPokes(position).isSelected());

        return convertView;
    }

    ArrayList<PokeListItems> getBox() {
        ArrayList<PokeListItems> arrayBox = new ArrayList<>();
        for (PokeListItems listItems : pokeList) {
            if (listItems.isSelected())
                arrayBox.add(listItems);
        }
        return arrayBox;
    }

    private PokeListItems getPokes(int position) {
        return ((PokeListItems) getItem(position));
    }

    private class ViewHolder {
        TextView pokeTv;
        TextView genderTv;
        RadioButton hpRb;
        RadioButton attRb;
        RadioButton defRb;
        RadioButton spAttRb;
        RadioButton spDefRb;
        RadioButton speedRb;

        ImageView pokeSprite;

        CheckBox box;
    }
}
