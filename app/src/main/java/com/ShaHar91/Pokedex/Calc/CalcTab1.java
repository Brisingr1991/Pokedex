package com.ShaHar91.Pokedex.Calc;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CalcTab1 extends Fragment {

    Spinner natureSpin;

    private String poke;

    private AutoCompleteTextView pokeAuto;

    DatabaseConnector databaseConnector;

    View rootView;

    Integer calcHp, minHp, maxHp, calcAtt, minAtt, maxAtt, calcDef, minDef,
            maxDef, calcSpAtt, minSpAtt, maxSpAtt, calcSpDef, minSpDef,
            maxSpDef, calcSpeed, minSpeed, maxSpeed;

    EditText lvlEt, iv_Hp, iv_Att, iv_Def, iv_SpAtt, iv_SpDef, iv_Speed, ev_Hp,
            ev_Att, ev_Def, ev_SpAtt, ev_SpDef, ev_Speed;

    TextView nat_dexTv, nature_Att, nature_Def, nature_SpAtt, nature_SpDef,
            nature_Speed, base_Hp, base_Att, base_Def, base_SpAtt, base_SpDef,
            base_Speed, calc_Hp, calc_Att, calc_Def, calc_SpAtt, calc_SpDef,
            calc_Speed, min_Hp, min_Att, min_Def, min_SpAtt, min_SpDef,
            min_Speed, max_Hp, max_Att, max_Def, max_SpAtt, max_SpDef,
            max_Speed;

    Button resetBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.calc_tab_1, container, false);

        databaseConnector = new DatabaseConnector(getActivity());

        databaseConnector.openDataBase();
        SetViews();

        return rootView;
    }

    private void SetViews() {
        nature_Att = (TextView) rootView.findViewById(R.id.nature_Att);
        nature_Def = (TextView) rootView.findViewById(R.id.nature_Def);
        nature_SpAtt = (TextView) rootView.findViewById(R.id.nature_SpAtt);
        nature_SpDef = (TextView) rootView.findViewById(R.id.nature_SpDef);
        nature_Speed = (TextView) rootView.findViewById(R.id.natureSpeed);

        base_Hp = (TextView) rootView.findViewById(R.id.base_Hp);
        base_Att = (TextView) rootView.findViewById(R.id.base_Att);
        base_Def = (TextView) rootView.findViewById(R.id.base_Def);
        base_SpAtt = (TextView) rootView.findViewById(R.id.base_SpAtt);
        base_SpDef = (TextView) rootView.findViewById(R.id.base_SpDef);
        base_Speed = (TextView) rootView.findViewById(R.id.baseSpeed);

        calc_Hp = (TextView) rootView.findViewById(R.id.calc_Hp);
        calc_Att = (TextView) rootView.findViewById(R.id.calc_Att);
        calc_Def = (TextView) rootView.findViewById(R.id.calc_Def);
        calc_SpAtt = (TextView) rootView.findViewById(R.id.calc_SpAtt);
        calc_SpDef = (TextView) rootView.findViewById(R.id.calc_SpDef);
        calc_Speed = (TextView) rootView.findViewById(R.id.calc_Speed);

        min_Hp = (TextView) rootView.findViewById(R.id.min_Hp);
        min_Att = (TextView) rootView.findViewById(R.id.min_Att);
        min_Def = (TextView) rootView.findViewById(R.id.min_Def);
        min_SpAtt = (TextView) rootView.findViewById(R.id.min_SpAtt);
        min_SpDef = (TextView) rootView.findViewById(R.id.min_SpDef);
        min_Speed = (TextView) rootView.findViewById(R.id.min_Speed);

        max_Hp = (TextView) rootView.findViewById(R.id.max_Hp);
        max_Att = (TextView) rootView.findViewById(R.id.max_Att);
        max_Def = (TextView) rootView.findViewById(R.id.max_Def);
        max_SpAtt = (TextView) rootView.findViewById(R.id.max_SpAtt);
        max_SpDef = (TextView) rootView.findViewById(R.id.max_SpDef);
        max_Speed = (TextView) rootView.findViewById(R.id.max_Speed);

        iv_Hp = (EditText) rootView.findViewById(R.id.iv_Hp);
        iv_Att = (EditText) rootView.findViewById(R.id.iv_Att);
        iv_Def = (EditText) rootView.findViewById(R.id.iv_Def);
        iv_SpAtt = (EditText) rootView.findViewById(R.id.iv_SpAtt);
        iv_SpDef = (EditText) rootView.findViewById(R.id.iv_SpDef);
        iv_Speed = (EditText) rootView.findViewById(R.id.iv_Speed);

        ev_Hp = (EditText) rootView.findViewById(R.id.ev_Hp);
        ev_Att = (EditText) rootView.findViewById(R.id.ev_Att);
        ev_Def = (EditText) rootView.findViewById(R.id.ev_Def);
        ev_SpAtt = (EditText) rootView.findViewById(R.id.ev_SpAtt);
        ev_SpDef = (EditText) rootView.findViewById(R.id.ev_SpDef);
        ev_Speed = (EditText) rootView.findViewById(R.id.ev_Speed);

        nat_dexTv = (TextView) rootView.findViewById(R.id.nat_dexTv);
        natureSpin = (Spinner) rootView.findViewById(R.id.nature_Spin);
        lvlEt = (EditText) rootView.findViewById(R.id.lvlEt);

        resetBtn = (Button) rootView.findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                pokeAuto.setText("");
                nat_dexTv.setText("");
                natureSpin.setSelection(0);
                lvlEt.setText("");

                base_Hp.setText("");
                base_Att.setText("");
                base_Def.setText("");
                base_SpAtt.setText("");
                base_SpDef.setText("");
                base_Speed.setText("");

                iv_Hp.setText("0");
                iv_Att.setText("0");
                iv_Def.setText("0");
                iv_SpAtt.setText("0");
                iv_SpDef.setText("0");
                iv_Speed.setText("0");

                ev_Hp.setText("0");
                ev_Att.setText("0");
                ev_Def.setText("0");
                ev_SpAtt.setText("0");
                ev_SpDef.setText("0");
                ev_Speed.setText("0");

                calc_Hp.setText("");
                calc_Att.setText("");
                calc_Def.setText("");
                calc_SpAtt.setText("");
                calc_SpDef.setText("");
                calc_Speed.setText("");

                min_Hp.setText("");
                min_Att.setText("");
                min_Def.setText("");
                min_SpAtt.setText("");
                min_SpDef.setText("");
                min_Speed.setText("");

                max_Hp.setText("");
                max_Att.setText("");
                max_Def.setText("");
                max_SpAtt.setText("");
                max_SpDef.setText("");
                max_Speed.setText("");

            }
        });

        natureSpin.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (position != 0) {
                    new LoadNatureTask().execute(natureSpin.getSelectedItem()
                            .toString());

                    calcHp();
                    calcAtt();
                    calcDef();
                    calcSpAtt();
                    calcSpDef();
                    calcSpeed();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pokeAuto = (AutoCompleteTextView) rootView.findViewById(R.id.pokeAuto);

        String[] pokes = databaseConnector.getAllPokemonsIncMega();

        // use "GetActivity()" instead of "context" or "this"
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.auto_list_item, pokes);
        pokeAuto.setAdapter(adapter);

        pokeAuto.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {

                poke = pokeAuto.getText().toString();
                new LoadBaseStats().execute(poke);

            }
        });

        lvlEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calcHp();
                calcAtt();
                calcDef();
                calcSpAtt();
                calcSpDef();
                calcSpeed();

            }
        });

        iv_Hp.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calcHp();

            }
        });

        iv_Att.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calcAtt();

            }
        });

        iv_Def.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcDef();

            }
        });

        iv_SpAtt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcSpAtt();

            }
        });

        iv_SpDef.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcSpDef();

            }
        });

        iv_Speed.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcSpeed();

            }
        });

        ev_Hp.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calcHp();

            }
        });

        ev_Att.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcAtt();

            }
        });

        ev_Def.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcDef();

            }
        });
        ev_SpAtt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcSpAtt();

            }
        });
        ev_SpDef.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcSpDef();

            }
        });
        ev_Speed.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                calcSpeed();

            }
        });
    }

    protected void calcHp() {
        try {
            calcHp = (int) Math
                    .floor((2 * Integer.valueOf(base_Hp.getText().toString())
                            + Integer.valueOf(iv_Hp.getText().toString()) + Math
                            .floor(Integer.valueOf(ev_Hp.getText()
                                    .toString()) / 4))
                            * Integer.valueOf(lvlEt.getText().toString())
                            / 100
                            + Integer.valueOf(lvlEt.getText().toString()) + 10);

            calc_Hp.setText(Integer.toString(calcHp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            minHp = (int) Math.floor((2 * Integer.valueOf(base_Hp.getText()
                    .toString()) + 0 + Math.floor(0 / 4))
                    * Integer.valueOf(lvlEt.getText().toString())
                    / 100
                    + Integer.valueOf(lvlEt.getText().toString()) + 10);

            min_Hp.setText(Integer.toString(minHp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxHp = (int) Math.floor((2 * Integer.valueOf(base_Hp.getText()
                    .toString()) + 31 + Math.floor(255 / 4))
                    * Integer.valueOf(lvlEt.getText().toString())
                    / 100
                    + Integer.valueOf(lvlEt.getText().toString()) + 10);

            max_Hp.setText(Integer.toString(maxHp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    protected void calcAtt() {

        try {
            calcAtt = (int) Math
                    .floor(Math.floor((2
                            * Integer.valueOf(base_Att.getText().toString())
                            + Integer.valueOf(iv_Att.getText().toString()) + Math
                            .floor(Integer.valueOf(ev_Att.getText()
                                    .toString()) / 4))
                            * Integer.valueOf(lvlEt.getText().toString())
                            / 100
                            + 5)
                            * Double.valueOf(nature_Att.getText().toString()));

            calc_Att.setText(Integer.toString(calcAtt));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            minAtt = (int) Math.floor(Math.floor((2 * Integer.valueOf(base_Att
                    .getText().toString()) + 0 + Math.floor(0 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_Att.getText().toString()));

            min_Att.setText(Integer.toString(minAtt));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxAtt = (int) Math.floor(Math.floor((2 * Integer.valueOf(base_Att
                    .getText().toString()) + 31 + Math.floor(255 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_Att.getText().toString()));

            max_Att.setText(Integer.toString(maxAtt));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }


    protected void calcDef() {

        try {
            calcDef = (int) Math
                    .floor(Math.floor((2
                            * Integer.valueOf(base_Def.getText().toString())
                            + Integer.valueOf(iv_Def.getText().toString()) + Math
                            .floor(Integer.valueOf(ev_Def.getText()
                                    .toString()) / 4))
                            * Integer.valueOf(lvlEt.getText().toString())
                            / 100
                            + 5)
                            * Double.valueOf(nature_Def.getText().toString()));

            calc_Def.setText(Integer.toString(calcDef));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            minDef = (int) Math.floor(Math.floor((2 * Integer.valueOf(base_Def
                    .getText().toString()) + 0 + Math.floor(0 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_Def.getText().toString()));

            min_Def.setText(Integer.toString(minDef));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxDef = (int) Math.floor(Math.floor((2 * Integer.valueOf(base_Def
                    .getText().toString()) + 31 + Math.floor(255 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_Def.getText().toString()));

            max_Def.setText(Integer.toString(maxDef));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    protected void calcSpAtt() {

        try {
            calcSpAtt = (int) Math
                    .floor(Math.floor((2
                            * Integer.valueOf(base_SpAtt.getText().toString())
                            + Integer.valueOf(iv_SpAtt.getText().toString()) + Math
                            .floor(Integer.valueOf(ev_SpAtt.getText()
                                    .toString()) / 4))
                            * Integer.valueOf(lvlEt.getText().toString())
                            / 100
                            + 5)
                            * Double.valueOf(nature_SpAtt.getText().toString()));

            calc_SpAtt.setText(Integer.toString(calcSpAtt));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            minSpAtt = (int) Math.floor(Math.floor((2 * Integer
                    .valueOf(base_SpAtt.getText().toString()) + 0 + Math
                    .floor(0 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_SpAtt.getText().toString()));

            min_SpAtt.setText(Integer.toString(minSpAtt));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxSpAtt = (int) Math.floor(Math.floor((2 * Integer
                    .valueOf(base_SpAtt.getText().toString()) + 31 + Math
                    .floor(255 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_SpAtt.getText().toString()));

            max_SpAtt.setText(Integer.toString(maxSpAtt));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    protected void calcSpDef() {

        try {
            calcSpDef = (int) Math
                    .floor(Math.floor((2
                            * Integer.valueOf(base_SpDef.getText().toString())
                            + Integer.valueOf(iv_SpDef.getText().toString()) + Math
                            .floor(Integer.valueOf(ev_SpDef.getText()
                                    .toString()) / 4))
                            * Integer.valueOf(lvlEt.getText().toString())
                            / 100
                            + 5)
                            * Double.valueOf(nature_SpDef.getText().toString()));

            calc_SpDef.setText(Integer.toString(calcSpDef));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            minSpDef = (int) Math.floor(Math.floor((2 * Integer
                    .valueOf(base_SpDef.getText().toString()) + 0 + Math
                    .floor(0 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_SpDef.getText().toString()));

            min_SpDef.setText(Integer.toString(minSpDef));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxSpDef = (int) Math.floor(Math.floor((2 * Integer
                    .valueOf(base_SpDef.getText().toString()) + 31 + Math
                    .floor(255 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_SpDef.getText().toString()));

            max_SpDef.setText(Integer.toString(maxSpDef));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    protected void calcSpeed() {

        try {
            calcSpeed = (int) Math
                    .floor(Math.floor((2
                            * Integer.valueOf(base_Speed.getText().toString())
                            + Integer.valueOf(iv_Speed.getText().toString()) + Math
                            .floor(Integer.valueOf(ev_Speed.getText()
                                    .toString()) / 4))
                            * Integer.valueOf(lvlEt.getText().toString())
                            / 100
                            + 5)
                            * Double.valueOf(nature_Speed.getText().toString()));

            calc_Speed.setText(Integer.toString(calcSpeed));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            minSpeed = (int) Math.floor(Math.floor((2 * Integer
                    .valueOf(base_Speed.getText().toString()) + 0 + Math
                    .floor(0 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_Speed.getText().toString()));

            min_Speed.setText(Integer.toString(minSpeed));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxSpeed = (int) Math.floor(Math.floor((2 * Integer
                    .valueOf(base_Speed.getText().toString()) + 31 + Math
                    .floor(255 / 4))
                    * Integer.valueOf(lvlEt.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature_Speed.getText().toString()));

            max_Speed.setText(Integer.toString(maxSpeed));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private class LoadBaseStats extends AsyncTask<String, Object, Cursor> {

        // perform the database access
        @Override
        protected Cursor doInBackground(String... params) {
            databaseConnector.openDataBase();
            // get a cursor containing all data on given entry
            return databaseConnector.getBaseStats(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToFirst(); // move to first item

            // get the column index for each data item
            int baseHpIndex = result.getColumnIndex("hp");
            int baseAttIndex = result.getColumnIndex("att");
            int baseDefIndex = result.getColumnIndex("def");
            int baseSpAttIndex = result.getColumnIndex("sp_att");
            int baseSpDefIndex = result.getColumnIndex("sp_def");
            int baseSpeedIndex = result.getColumnIndex("speed");
            int natDexIndex = result.getColumnIndex("nat_dex");

            base_Hp.setText(result.getString(baseHpIndex));
            base_Att.setText(result.getString(baseAttIndex));
            base_Def.setText(result.getString(baseDefIndex));
            base_SpAtt.setText(result.getString(baseSpAttIndex));
            base_SpDef.setText(result.getString(baseSpDefIndex));
            base_Speed.setText(result.getString(baseSpeedIndex));
            nat_dexTv.setText("#" + result.getString(natDexIndex));

            lvlEt.setText("1");

            result.close();

        }
    }

    private class LoadNatureTask extends AsyncTask<String, Object, Cursor> {

        @Override
        protected Cursor doInBackground(String... params) {
            databaseConnector.openDataBase();
            // get a cursor containing all data on given entry
            return databaseConnector.getNatureEffect(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToFirst(); // move to first item

            // get the column index for each data item
            int attIndex = result.getColumnIndex("att");
            int defIndex = result.getColumnIndex("def");
            int spAttIndex = result.getColumnIndex("sp_att");
            int spDefIndex = result.getColumnIndex("sp_def");
            int speedIndex = result.getColumnIndex("speed");

            nature_Att.setText(result.getString(attIndex));
            nature_Def.setText(result.getString(defIndex));
            nature_SpAtt.setText(result.getString(spAttIndex));
            nature_SpDef.setText(result.getString(spDefIndex));
            nature_Speed.setText(result.getString(speedIndex));

            result.close();

        }
    }

}
