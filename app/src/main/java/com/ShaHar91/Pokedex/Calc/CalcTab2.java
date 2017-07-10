package com.ShaHar91.Pokedex.Calc;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CalcTab2 extends Fragment {

    private AutoCompleteTextView poke2Auto;

    DatabaseConnector databaseConnector;

    View rootView;

    Integer calcIvHp, minHp, maxHp, calcIvAtt, minAtt, maxAtt, calcIvDef,
            minDef, maxDef, calcIvSpAtt, minSpAtt, maxSpAtt, calcIvSpDef,
            minSpDef, maxSpDef, calcIvSpeed, calcIvSpeed2, minSpeed, maxSpeed,
            Hp, Att, Def, SpAtt, SpDef, Speed;

    private String poke2;

    StringBuilder builder = new StringBuilder();

    Spinner natureSpin2;

    EditText lvl2Et, stats_Hp, stats_Att, stats_Def, stats_SpAtt, stats_SpDef,
            stats_Speed, ev2_Hp, ev2_Att, ev2_Def, ev2_SpAtt, ev2_SpDef,
            ev2_Speed;

    TextView nat_dex2Tv, nature2_Att, nature2_Def, nature2_SpAtt,
            nature2_SpDef, nature2_Speed, base2_Hp, base2_Att, base2_Def,
            base2_SpAtt, base2_SpDef, base2_Speed, calcIv_Hp, calcIv_Att,
            calcIv_Def, calcIv_SpAtt, calcIv_SpDef, calcIv_Speed;

    Button reset2Btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.calc_tab_2, container, false);

        databaseConnector = new DatabaseConnector(getActivity());

        databaseConnector.openDataBase();
        SetViews();

        return rootView;
    }

    private void SetViews() {

        nature2_Att = (TextView) rootView.findViewById(R.id.nature2_Att);
        nature2_Def = (TextView) rootView.findViewById(R.id.nature2_Def);
        nature2_SpAtt = (TextView) rootView.findViewById(R.id.nature2_SpAtt);
        nature2_SpDef = (TextView) rootView.findViewById(R.id.nature2_SpDef);
        nature2_Speed = (TextView) rootView.findViewById(R.id.nature2_Speed);

        base2_Hp = (TextView) rootView.findViewById(R.id.base2_Hp);
        base2_Att = (TextView) rootView.findViewById(R.id.base2_Att);
        base2_Def = (TextView) rootView.findViewById(R.id.base2_Def);
        base2_SpAtt = (TextView) rootView.findViewById(R.id.base2_SpAtt);
        base2_SpDef = (TextView) rootView.findViewById(R.id.base2_SpDef);
        base2_Speed = (TextView) rootView.findViewById(R.id.base2_Speed);

        stats_Hp = (EditText) rootView.findViewById(R.id.stats_Hp);
        stats_Att = (EditText) rootView.findViewById(R.id.stats_Att);
        stats_Def = (EditText) rootView.findViewById(R.id.stats_Def);
        stats_SpAtt = (EditText) rootView.findViewById(R.id.stats_SpAtt);
        stats_SpDef = (EditText) rootView.findViewById(R.id.stats_SpDef);
        stats_Speed = (EditText) rootView.findViewById(R.id.stats_Speed);

        ev2_Hp = (EditText) rootView.findViewById(R.id.ev2_Hp);
        ev2_Att = (EditText) rootView.findViewById(R.id.ev2_Att);
        ev2_Def = (EditText) rootView.findViewById(R.id.ev2_Def);
        ev2_SpAtt = (EditText) rootView.findViewById(R.id.ev2_SpAtt);
        ev2_SpDef = (EditText) rootView.findViewById(R.id.ev2_SpDef);
        ev2_Speed = (EditText) rootView.findViewById(R.id.ev2_Speed);

        calcIv_Hp = (TextView) rootView.findViewById(R.id.calcIv_Hp);
        calcIv_Att = (TextView) rootView.findViewById(R.id.calcIv_Att);
        calcIv_Def = (TextView) rootView.findViewById(R.id.calcIv_Def);
        calcIv_SpAtt = (TextView) rootView.findViewById(R.id.calcIv_SpAtt);
        calcIv_SpDef = (TextView) rootView.findViewById(R.id.calcIv_SpDef);
        calcIv_Speed = (TextView) rootView.findViewById(R.id.calcIv_Speed);

        nat_dex2Tv = (TextView) rootView.findViewById(R.id.nat_dex2Tv);
        natureSpin2 = (Spinner) rootView.findViewById(R.id.nature2_Spin);
        lvl2Et = (EditText) rootView.findViewById(R.id.lvl2Et);

        reset2Btn = (Button) rootView.findViewById(R.id.reset2Btn);
        reset2Btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                poke2Auto.setText("");
                nat_dex2Tv.setText("");
                natureSpin2.setSelection(0);
                lvl2Et.setText("");

                base2_Hp.setText("");
                base2_Att.setText("");
                base2_Def.setText("");
                base2_SpAtt.setText("");
                base2_SpDef.setText("");
                base2_Speed.setText("");

                stats_Hp.setText("0");
                stats_Att.setText("0");
                stats_Def.setText("0");
                stats_SpAtt.setText("0");
                stats_SpDef.setText("0");
                stats_Speed.setText("0");

                ev2_Hp.setText("0");
                ev2_Att.setText("0");
                ev2_Def.setText("0");
                ev2_SpAtt.setText("0");
                ev2_SpDef.setText("0");
                ev2_Speed.setText("0");

                calcIv_Hp.setText("");
                calcIv_Att.setText("");
                calcIv_Def.setText("");
                calcIv_SpAtt.setText("");
                calcIv_SpDef.setText("");
                calcIv_Speed.setText("");

            }
        });

        natureSpin2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (position != 0) {
                    new LoadNatureTask().execute(natureSpin2.getSelectedItem()
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

        poke2Auto = (AutoCompleteTextView) rootView
                .findViewById(R.id.poke2Auto);

        String[] pokes2 = databaseConnector.getAllPokemonsIncMega();

        // use "GetActivity()" instead of "context" or "this"
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                R.layout.auto_list_item, pokes2);
        poke2Auto.setAdapter(adapter2);

        poke2Auto.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {

                poke2 = poke2Auto.getText().toString();
                new LoadBaseStats().execute(poke2);

            }
        });

        lvl2Et.addTextChangedListener(new TextWatcher() {

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

        stats_Hp.addTextChangedListener(new TextWatcher() {

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

                Toast toast = Toast.makeText(getActivity(),
                        "The value of HP Stats must be between " + minHp
                                + " and " + maxHp + ".", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }
        });

        stats_Att.addTextChangedListener(new TextWatcher() {

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

                Toast toast = Toast.makeText(getActivity(),
                        "The value of Attack Stats must be between " + minAtt
                                + " and " + maxAtt + ".", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }
        });

        stats_Def.addTextChangedListener(new TextWatcher() {

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

                Toast toast = Toast.makeText(getActivity(),
                        "The value of Defence Stats must be between " + minDef
                                + " and " + maxDef + ".", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }
        });

        stats_SpAtt.addTextChangedListener(new TextWatcher() {

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

                Toast toast = Toast.makeText(getActivity(),
                        "The value of Sp. Att Stats must be between "
                                + minSpAtt + " and " + maxSpAtt + ".",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }
        });

        stats_SpDef.addTextChangedListener(new TextWatcher() {

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

                Toast toast = Toast.makeText(getActivity(),
                        "The value of Sp. Def Stats must be between "
                                + minSpDef + " and " + maxSpDef + ".",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }
        });

        stats_Speed.addTextChangedListener(new TextWatcher() {

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

                Toast toast = Toast.makeText(getActivity(),
                        "The value of Speed Stats must be between " + minSpeed
                                + " and " + maxSpeed + ".", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }
        });

        ev2_Hp.addTextChangedListener(new TextWatcher() {

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

        ev2_Att.addTextChangedListener(new TextWatcher() {

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

        ev2_Def.addTextChangedListener(new TextWatcher() {

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
        ev2_SpAtt.addTextChangedListener(new TextWatcher() {

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
        ev2_SpDef.addTextChangedListener(new TextWatcher() {

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
        ev2_Speed.addTextChangedListener(new TextWatcher() {

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

            base2_Hp.setText(result.getString(baseHpIndex));
            base2_Att.setText(result.getString(baseAttIndex));
            base2_Def.setText(result.getString(baseDefIndex));
            base2_SpAtt.setText(result.getString(baseSpAttIndex));
            base2_SpDef.setText(result.getString(baseSpDefIndex));
            base2_Speed.setText(result.getString(baseSpeedIndex));
            nat_dex2Tv.setText("#" + result.getString(natDexIndex));

            lvl2Et.setText("1");

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

            nature2_Att.setText(result.getString(attIndex));
            nature2_Def.setText(result.getString(defIndex));
            nature2_SpAtt.setText(result.getString(spAttIndex));
            nature2_SpDef.setText(result.getString(spDefIndex));
            nature2_Speed.setText(result.getString(speedIndex));

            result.close();

        }
    }

    protected void calcHp() {

        try {
            minHp = (int) Math.floor((2 * Integer.valueOf(base2_Hp.getText()
                    .toString()) + 0 + Math.floor(Integer.valueOf(ev2_Hp
                    .getText().toString()) / 4))
                    * Integer.valueOf(lvl2Et.getText().toString())
                    / 100
                    + Integer.valueOf(lvl2Et.getText().toString()) + 10);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxHp = (int) Math.floor((2 * Integer.valueOf(base2_Hp.getText()
                    .toString()) + 31 + Math.floor(Integer.valueOf(ev2_Hp
                    .getText().toString()) / 4))
                    * Integer.valueOf(lvl2Et.getText().toString())
                    / 100
                    + Integer.valueOf(lvl2Et.getText().toString()) + 10);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            builder.setLength(0);

            for (int i = 0; i < 32; i++) {
                Hp = (int) Math
                        .floor((2
                                * Integer
                                .valueOf(base2_Hp.getText().toString())
                                + i + Math.floor(Integer.valueOf(ev2_Hp
                                .getText().toString()) / 4))
                                * Integer.valueOf(lvl2Et.getText().toString())
                                / 100
                                + Integer.valueOf(lvl2Et.getText().toString())
                                + 10);

                if (Integer.valueOf(stats_Hp.getText().toString()).equals(Hp)) {

                    if (builder.length() == 0) {
                        builder.append(i);
                    } else {
                        builder.append(", \n" + i);
                    }

                }
                if (builder.length() != 0) {
                    calcIv_Hp.setText(builder.toString());
                } else {
                    calcIv_Hp.setText("e.");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    protected void calcAtt() {

        try {
            minAtt = (int) Math.floor(Math.floor((2 * Integer.valueOf(base2_Att
                    .getText().toString()) + 0 + Math.floor(Integer
                    .valueOf(ev2_Att.getText().toString()) / 4))
                    * Integer.valueOf(lvl2Et.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature2_Att.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxAtt = (int) Math.floor(Math.floor((2 * Integer.valueOf(base2_Att
                    .getText().toString()) + 31 + Math.floor(Integer
                    .valueOf(ev2_Att.getText().toString()) / 4))
                    * Integer.valueOf(lvl2Et.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature2_Att.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            builder.setLength(0);

            for (int i = 0; i < 32; i++) {
                Att = (int) Math
                        .floor(Math.floor((2
                                * Integer.valueOf(base2_Att.getText()
                                .toString()) + i + Math.floor(Integer
                                .valueOf(ev2_Att.getText().toString()) / 4))
                                * Integer.valueOf(lvl2Et.getText().toString())
                                / 100 + 5)
                                * Double.valueOf(nature2_Att.getText()
                                .toString()));

                if (Integer.valueOf(stats_Att.getText().toString()).equals(Att)) {

                    if (builder.length() == 0) {
                        builder.append(i);
                    } else {
                        builder.append(", \n" + i);
                    }

                }
                if (builder.length() != 0) {
                    calcIv_Att.setText(builder.toString());
                } else {
                    calcIv_Att.setText("e.");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    protected void calcDef() {

        try {
            minDef = (int) Math.floor(Math.floor((2 * Integer.valueOf(base2_Def
                    .getText().toString()) + 0 + Math.floor(Integer
                    .valueOf(ev2_Def.getText().toString()) / 4))
                    * Integer.valueOf(lvl2Et.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature2_Def.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxDef = (int) Math.floor(Math.floor((2 * Integer.valueOf(base2_Def
                    .getText().toString()) + 31 + Math.floor(Integer
                    .valueOf(ev2_Def.getText().toString()) / 4))
                    * Integer.valueOf(lvl2Et.getText().toString()) / 100 + 5)
                    * Double.valueOf(nature2_Def.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            builder.setLength(0);

            for (int i = 0; i < 32; i++) {
                Def = (int) Math
                        .floor(Math.floor((2
                                * Integer.valueOf(base2_Def.getText()
                                .toString()) + i + Math.floor(Integer
                                .valueOf(ev2_Def.getText().toString()) / 4))
                                * Integer.valueOf(lvl2Et.getText().toString())
                                / 100 + 5)
                                * Double.valueOf(nature2_Def.getText()
                                .toString()));

                if (Integer.valueOf(stats_Def.getText().toString()).equals(Def)) {

                    if (builder.length() == 0) {
                        builder.append(i);
                    } else {
                        builder.append(", \n" + i);
                    }

                }
                if (builder.length() != 0) {
                    calcIv_Def.setText(builder.toString());
                } else {
                    calcIv_Def.setText("e.");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    protected void calcSpAtt() {

        try {
            minSpAtt = (int) Math
                    .floor(Math.floor((2 * Integer.valueOf(base2_SpAtt
                            .getText().toString()) + 0 + Math.floor(Integer
                            .valueOf(ev2_SpAtt.getText().toString()) / 4))
                            * Integer.valueOf(lvl2Et.getText().toString())
                            / 100 + 5)
                            * Double.valueOf(nature2_SpAtt.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxSpAtt = (int) Math
                    .floor(Math.floor((2 * Integer.valueOf(base2_SpAtt
                            .getText().toString()) + 31 + Math.floor(Integer
                            .valueOf(ev2_SpAtt.getText().toString()) / 4))
                            * Integer.valueOf(lvl2Et.getText().toString())
                            / 100 + 5)
                            * Double.valueOf(nature2_SpAtt.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            builder.setLength(0);

            for (int i = 0; i < 32; i++) {
                SpAtt = (int) Math
                        .floor(Math.floor((2
                                * Integer.valueOf(base2_SpAtt.getText()
                                .toString()) + i + Math.floor(Integer
                                .valueOf(ev2_SpAtt.getText().toString()) / 4))
                                * Integer.valueOf(lvl2Et.getText().toString())
                                / 100 + 5)
                                * Double.valueOf(nature2_SpAtt.getText()
                                .toString()));

                if (Integer.valueOf(stats_SpAtt.getText().toString()).equals(
                        SpAtt)) {

                    if (builder.length() == 0) {
                        builder.append(i);
                    } else {
                        builder.append(", \n" + i);
                    }
                }
                if (builder.length() != 0) {
                    calcIv_SpAtt.setText(builder.toString());
                } else {
                    calcIv_SpAtt.setText("e.");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    protected void calcSpDef() {

        try {
            minSpDef = (int) Math
                    .floor(Math.floor((2 * Integer.valueOf(base2_SpDef
                            .getText().toString()) + 0 + Math.floor(Integer
                            .valueOf(ev2_SpDef.getText().toString()) / 4))
                            * Integer.valueOf(lvl2Et.getText().toString())
                            / 100 + 5)
                            * Double.valueOf(nature2_SpDef.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxSpDef = (int) Math
                    .floor(Math.floor((2 * Integer.valueOf(base2_SpDef
                            .getText().toString()) + 31 + Math.floor(Integer
                            .valueOf(ev2_SpDef.getText().toString()) / 4))
                            * Integer.valueOf(lvl2Et.getText().toString())
                            / 100 + 5)
                            * Double.valueOf(nature2_SpDef.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            builder.setLength(0);

            for (int i = 0; i < 32; i++) {
                SpDef = (int) Math
                        .floor(Math.floor((2
                                * Integer.valueOf(base2_SpDef.getText()
                                .toString()) + i + Math.floor(Integer
                                .valueOf(ev2_SpDef.getText().toString()) / 4))
                                * Integer.valueOf(lvl2Et.getText().toString())
                                / 100 + 5)
                                * Double.valueOf(nature2_SpDef.getText()
                                .toString()));

                if (Integer.valueOf(stats_SpDef.getText().toString()).equals(
                        SpDef)) {

                    if (builder.length() == 0) {
                        builder.append(i);
                    } else {
                        builder.append(", \n" + i);
                    }
                }
                if (builder.length() != 0) {
                    calcIv_SpDef.setText(builder.toString());
                } else {
                    calcIv_SpDef.setText("e.");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    protected void calcSpeed() {

        try {
            minSpeed = (int) Math
                    .floor(Math.floor((2 * Integer.valueOf(base2_Speed
                            .getText().toString()) + 0 + Math.floor(Integer
                            .valueOf(ev2_Speed.getText().toString()) / 4))
                            * Integer.valueOf(lvl2Et.getText().toString())
                            / 100 + 5)
                            * Double.valueOf(nature2_Speed.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            maxSpeed = (int) Math
                    .floor(Math.floor((2 * Integer.valueOf(base2_Speed
                            .getText().toString()) + 31 + Math.floor(Integer
                            .valueOf(ev2_Speed.getText().toString()) / 4))
                            * Integer.valueOf(lvl2Et.getText().toString())
                            / 100 + 5)
                            * Double.valueOf(nature2_Speed.getText().toString()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            builder.setLength(0);

            for (int i = 0; i < 32; i++) {
                Speed = (int) Math
                        .floor(Math.floor((2
                                * Integer.valueOf(base2_Speed.getText()
                                .toString()) + i + Math.floor(Integer
                                .valueOf(ev2_Speed.getText().toString()) / 4))
                                * Integer.valueOf(lvl2Et.getText().toString())
                                / 100 + 5)
                                * Double.valueOf(nature2_Speed.getText()
                                .toString()));

                if (Integer.valueOf(stats_Speed.getText().toString()).equals(
                        Speed)) {

                    if (builder.length() == 0) {
                        builder.append(i);
                    } else {
                        builder.append(", \n" + i);
                    }

                }

                if (builder.length() != 0) {
//                    String testing = builder.toString();
//                    String test = testing.substring(0, 2);
                    calcIv_Speed.setText(builder.toString());
                } else {
                    calcIv_Speed.setText("e.");
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
}
