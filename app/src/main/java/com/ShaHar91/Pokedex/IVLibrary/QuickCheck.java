package com.ShaHar91.Pokedex.IVLibrary;

import com.ShaHar91.Pokedex.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class QuickCheck extends Activity {

    CheckBox hpCBox, attCBox, defCBox, spAttCBox, spDefCBox, speedCBox,
            hp2CBox, att2CBox, def2CBox, spAtt2CBox, spDef2CBox, speed2CBox,
            hp3CBox, att3CBox, def3CBox, spAtt3CBox, spDef3CBox, speed3CBox,
            hp4CBox, att4CBox, def4CBox, spAtt4CBox, spDef4CBox, speed4CBox,
            hp5CBox, att5CBox, def5CBox, spAtt5CBox, spDef5CBox, speed5CBox,
            hp6CBox, att6CBox, def6CBox, spAtt6CBox, spDef6CBox, speed6CBox;
    Button clearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivlibrary_quickcheck);

        MethodHolder();

    }

    private void MethodHolder() {

        hpCBox = (CheckBox) findViewById(R.id.hp1CBox);
        attCBox = (CheckBox) findViewById(R.id.att1CBox);
        defCBox = (CheckBox) findViewById(R.id.def1CBox);
        spAttCBox = (CheckBox) findViewById(R.id.spAtt1CBox);
        spDefCBox = (CheckBox) findViewById(R.id.spDef1CBox);
        speedCBox = (CheckBox) findViewById(R.id.speed1CBox);

        hp2CBox = (CheckBox) findViewById(R.id.hp2CBox);
        att2CBox = (CheckBox) findViewById(R.id.att2CBox);
        def2CBox = (CheckBox) findViewById(R.id.def2CBox);
        spAtt2CBox = (CheckBox) findViewById(R.id.spAtt2CBox);
        spDef2CBox = (CheckBox) findViewById(R.id.spDef2CBox);
        speed2CBox = (CheckBox) findViewById(R.id.speed2CBox);

        hp3CBox = (CheckBox) findViewById(R.id.hp3CBox);
        att3CBox = (CheckBox) findViewById(R.id.att3CBox);
        def3CBox = (CheckBox) findViewById(R.id.def3CBox);
        spAtt3CBox = (CheckBox) findViewById(R.id.spAtt3CBox);
        spDef3CBox = (CheckBox) findViewById(R.id.spDef3CBox);
        speed3CBox = (CheckBox) findViewById(R.id.speed3CBox);

        hp4CBox = (CheckBox) findViewById(R.id.hp4CBox);
        att4CBox = (CheckBox) findViewById(R.id.att4CBox);
        def4CBox = (CheckBox) findViewById(R.id.def4CBox);
        spAtt4CBox = (CheckBox) findViewById(R.id.spAtt4CBox);
        spDef4CBox = (CheckBox) findViewById(R.id.spDef4CBox);
        speed4CBox = (CheckBox) findViewById(R.id.speed4CBox);

        hp5CBox = (CheckBox) findViewById(R.id.hp5CBox);
        att5CBox = (CheckBox) findViewById(R.id.att5CBox);
        def5CBox = (CheckBox) findViewById(R.id.def5CBox);
        spAtt5CBox = (CheckBox) findViewById(R.id.spAtt5CBox);
        spDef5CBox = (CheckBox) findViewById(R.id.spDef5CBox);
        speed5CBox = (CheckBox) findViewById(R.id.speed5CBox);

        hp6CBox = (CheckBox) findViewById(R.id.hp6CBox);
        att6CBox = (CheckBox) findViewById(R.id.att6CBox);
        def6CBox = (CheckBox) findViewById(R.id.def6CBox);
        spAtt6CBox = (CheckBox) findViewById(R.id.spAtt6CBox);
        spDef6CBox = (CheckBox) findViewById(R.id.spDef6CBox);
        speed6CBox = (CheckBox) findViewById(R.id.speed6CBox);

        clearAll = (Button) findViewById(R.id.clearBtn);
        clearAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hpCBox.setChecked(false);
                attCBox.setChecked(false);
                defCBox.setChecked(false);
                spAttCBox.setChecked(false);
                spDefCBox.setChecked(false);
                speedCBox.setChecked(false);

                hp2CBox.setChecked(false);
                att2CBox.setChecked(false);
                def2CBox.setChecked(false);
                spAtt2CBox.setChecked(false);
                spDef2CBox.setChecked(false);
                speed2CBox.setChecked(false);

                hp3CBox.setChecked(false);
                att3CBox.setChecked(false);
                def3CBox.setChecked(false);
                spAtt3CBox.setChecked(false);
                spDef3CBox.setChecked(false);
                speed3CBox.setChecked(false);

                hp4CBox.setChecked(false);
                att4CBox.setChecked(false);
                def4CBox.setChecked(false);
                spAtt4CBox.setChecked(false);
                spDef4CBox.setChecked(false);
                speed4CBox.setChecked(false);

                hp5CBox.setChecked(false);
                att5CBox.setChecked(false);
                def5CBox.setChecked(false);
                spAtt5CBox.setChecked(false);
                spDef5CBox.setChecked(false);
                speed5CBox.setChecked(false);

                hp6CBox.setChecked(false);
                att6CBox.setChecked(false);
                def6CBox.setChecked(false);
                spAtt6CBox.setChecked(false);
                spDef6CBox.setChecked(false);
                speed6CBox.setChecked(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quick_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.how_to) {
            Intent i = new Intent(this, HowIUseIt.class);
            startActivity(i);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
