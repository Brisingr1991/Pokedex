package com.ShaHar91.Pokedex.IVLibrary;

import com.ShaHar91.Pokedex.DatabaseConnector;
import com.ShaHar91.Pokedex.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;


public class AddPoke extends Activity {
    /* 20/03/2017
    - attributeCheck() aangemaakt
    - extra == null opgeruimd
    - showDialogs() aangemaakt
    - edit aangepast zodat je naam niet kan aanpassen maar attributen maar nature, gender, ability wel
    - checkboxen aangepast en werkt ook zoals bedoeld

     */

    int hp, att, def, spAtt, spDef, speed;
    IVLibraryDatabaseConnector ivLibrarydatabaseConnector = new IVLibraryDatabaseConnector(
            this);
    DatabaseConnector databaseConnector = new DatabaseConnector(this);
    String abilities[];
    private long rowID;
    private String name;
    private AutoCompleteTextView nameAuto, natureAuto, abilityAuto;
    private EditText egg1Et, egg2Et, dexEt, noteEt;
    private Spinner genderSpn;
    private CheckBox hpCBox, attCBox, defCBox, spAttCBox, spDefCBox, speedCBox;

    OnClickListener savePokeBtnClicked = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (nameAuto.getText().length() != 0) {
                if (dexEt.getText().length() != 0) {
                    AsyncTask<Object, Object, Object> savePokeTask = new AsyncTask<Object, Object, Object>() {
                        @Override
                        protected Object doInBackground(Object... params) {
                            savePoke(); // save book to the database
                            return null;
                        } // end method doInBackground

                        @Override
                        protected void onPostExecute(Object result) {
                            ivLibrarydatabaseConnector.close();
                            databaseConnector.close();

                            finish(); // return to the previous Activity
                        }// end method onPostExecute
                    }; // end AsyncTask
                    // save the pokemon to the database using a separate thread
                    savePokeTask.execute((Object[]) null);
                } else {
                    showDialogs();
                }
            } else {
                showDialogs();
            }
        }
    };

    private void showDialogs() {
        AlertDialog.Builder titlebuilder = new AlertDialog.Builder(
                AddPoke.this);

        // set dialog title & message, and provide Button to dismiss
        titlebuilder.setTitle(R.string.errorTitle);
        titlebuilder.setMessage(R.string.titleerrorMessage);
        titlebuilder.setPositiveButton(R.string.errorButton, null);
        titlebuilder.show(); // display the Dialog
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseConnector.openDataBase();
        ivLibrarydatabaseConnector.openDataBase();
        setContentView(R.layout.ivlibrary_add_edit_poke);

        MethodHolder();
    }

    private void MethodHolder() {
        nameAuto = (AutoCompleteTextView) findViewById(R.id.nameAuto);
        genderSpn = (Spinner) findViewById(R.id.genderSpn);
        egg1Et = (EditText) findViewById(R.id.egg1Et);
        egg2Et = (EditText) findViewById(R.id.egg2Et);
        dexEt = (EditText) findViewById(R.id.dexEt);
        natureAuto = (AutoCompleteTextView) findViewById(R.id.natureAuto);
        abilityAuto = (AutoCompleteTextView) findViewById(R.id.abilityAuto);
        noteEt = (EditText) findViewById(R.id.noteEt);
        hpCBox = (CheckBox) findViewById(R.id.hpCBox);
        attCBox = (CheckBox) findViewById(R.id.attCBox);
        defCBox = (CheckBox) findViewById(R.id.defCBox);
        spAttCBox = (CheckBox) findViewById(R.id.spAttCBox);
        spDefCBox = (CheckBox) findViewById(R.id.spDefCBox);
        speedCBox = (CheckBox) findViewById(R.id.speedCBox);
        Button savePokeButton = (Button) findViewById(R.id.saveBtn);
        savePokeButton.setOnClickListener(savePokeBtnClicked);

        Bundle extras = getIntent().getExtras(); // get Bundle of extras
        // if there are extras, use them to populate the EditTexts
        if (extras != null) {
            if (extras.containsKey("row_id")) {
                rowID = extras.getLong("row_id");
                new LoadEditPokeTask().execute(rowID);
            }
        }

        String natures[] = getResources().getStringArray(R.array.nature_list);
        ArrayAdapter<String> natureAdapter = new ArrayAdapter<>(this,
                R.layout.auto_list_item, natures);
        natureAuto.setAdapter(natureAdapter);

        String pokes[] = databaseConnector.getAllPokemons();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.auto_list_item, pokes);
        nameAuto.setAdapter(adapter);

        nameAuto.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                name = nameAuto.getText().toString();
                new LoadPokeTask().execute(name);
            }
        });

        final Drawable x = getResources().getDrawable(
                android.R.drawable.presence_offline);// your x image, this one from
        // standard android images looks
        // pretty good actually

        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        if (extras == null) {
            nameAuto.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (nameAuto.getCompoundDrawables()[2] == null) {
                        return false;
                    }
                    if (event.getAction() != MotionEvent.ACTION_UP) {
                        return false;
                    }
                    if (event.getX() > nameAuto.getWidth()
                            - nameAuto.getPaddingRight() - x.getIntrinsicWidth()) {
                        nameAuto.setText("");
                        clearSelection();

                        nameAuto.setCompoundDrawables(null, null, null, null);
                    }
                    return false;
                }
            });
            nameAuto.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    nameAuto.setCompoundDrawables(null, null, nameAuto.getText()
                            .toString().equals("") ? null : x, null);
                    clearSelection();
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }
            });
        }

        natureAuto.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (natureAuto.getCompoundDrawables()[2] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (event.getX() > natureAuto.getWidth()
                        - natureAuto.getPaddingRight() - x.getIntrinsicWidth()) {
                    natureAuto.setText("");
                    natureAuto.setCompoundDrawables(null, null, null, null);
                }
                return false;
            }
        });
        natureAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                natureAuto.setCompoundDrawables(null, null, natureAuto
                        .getText().toString().equals("") ? null : x, null);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        abilityAuto.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (abilityAuto.getCompoundDrawables()[2] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (event.getX() > abilityAuto.getWidth()
                        - abilityAuto.getPaddingRight() - x.getIntrinsicWidth()) {
                    abilityAuto.setText("");
                    abilityAuto.setCompoundDrawables(null, null, null, null);
                }
                return false;
            }
        });

        abilityAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                abilityAuto.setCompoundDrawables(null, null, abilityAuto
                        .getText().toString().equals("") ? null : x, null);

//                abilities = databaseConnector
//                        .getAllAbilityForPoke(nameAuto.getText().toString());
//                ArrayAdapter<String> abilityAdapter = new ArrayAdapter<>(
//                        AddPoke.this, R.layout.auto_list_item, abilities);
//                abilityAuto.setAdapter(abilityAdapter);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });
    }

    private void clearSelection() {
        egg1Et.setText("");
        egg2Et.setText("");
        dexEt.setText("");
        genderSpn.setSelection(0);
        natureAuto.setText("");
        abilityAuto.setText("");
        noteEt.setText("");

        hpCBox.setChecked(false);
        attCBox.setChecked(false);
        defCBox.setChecked(false);
        spAttCBox.setChecked(false);
        spDefCBox.setChecked(false);
        speedCBox.setChecked(false);

        ArrayAdapter<String> dummieAdapter = new ArrayAdapter<>(
                AddPoke.this, R.layout.auto_list_item, databaseConnector.getAllAbilityForPoke("zzzzzzzzzzz"));
        abilityAuto.setAdapter(dummieAdapter);
    }

    @Override
    protected void onResume() {
        // DatabaseConnector.openDataBase();
        super.onResume();
    }

    public int attributeCheck(CheckBox attribute) {
        if (attribute.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }

    private void savePoke() {
        // get DatabaseConnector to interact with the SQLite database
        if (getIntent().hasExtra("row_id")) {
            ivLibrarydatabaseConnector.updatePoke(rowID, nameAuto.getText()
                            .toString(), dexEt.getText().toString(), genderSpn
                            .getSelectedItem().toString(), egg1Et.getText().toString(),
                    egg2Et.getText().toString(), natureAuto.getText()
                            .toString(), abilityAuto.getText().toString(),
                    noteEt.getText().toString(), attributeCheck(hpCBox), attributeCheck(attCBox),
                    attributeCheck(defCBox), attributeCheck(spAttCBox), attributeCheck(spDefCBox),
                    attributeCheck(speedCBox));

        } // end if
        else {
            // insert the contact information into the database
            ivLibrarydatabaseConnector.insertPoke(
                    nameAuto.getText().toString(), dexEt.getText().toString(),
                    genderSpn.getSelectedItem().toString(), egg1Et.getText()
                            .toString(), egg2Et.getText().toString(),
                    natureAuto.getText().toString(), abilityAuto.getText()
                            .toString(), noteEt.getText().toString(), attributeCheck(hpCBox), attributeCheck(attCBox),
                    attributeCheck(defCBox), attributeCheck(spAttCBox), attributeCheck(spDefCBox),
                    attributeCheck(speedCBox));
        } // end else

    } // end class saveBook

    @Override
    protected void onStop() {

        super.onStop();

        if (databaseConnector != null)
            databaseConnector.close();
        if (ivLibrarydatabaseConnector != null)
            ivLibrarydatabaseConnector.close();
    }

    @Override
    protected void onPause() {

        super.onPause();

        if (databaseConnector != null)
            databaseConnector.close();
        if (ivLibrarydatabaseConnector != null)
            ivLibrarydatabaseConnector.close();
    }

    private class LoadEditPokeTask extends AsyncTask<Long, Object, Cursor> {
        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params) {
            ivLibrarydatabaseConnector.openDataBase();

            // get a cursor containing all data on given entry
            return ivLibrarydatabaseConnector.getAddedPoke(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);
            result.moveToFirst(); // move to first item

            // fill the TextView with the retrieved data
            nameAuto.setText(result.getString(result.getColumnIndex("name")));
            setTitle(result.getString(result.getColumnIndex("name")));
            nameAuto.setInputType(InputType.TYPE_NULL);
            nameAuto.setFocusable(false);

            String gender = result.getString(result.getColumnIndex("gender"));

            if (gender.contains("F")) {
                genderSpn.setSelection(1);
            } else if (gender.contains("M")) {
                genderSpn.setSelection(2);
            } else {
                genderSpn.setSelection(0);
            }

            dexEt.setText(result.getString(result.getColumnIndex("nat_dex")));
            dexEt.setInputType(InputType.TYPE_NULL);
            dexEt.setFocusable(false);
            egg1Et.setText(result.getString(result.getColumnIndex("egg_group1")));
            egg1Et.setInputType(InputType.TYPE_NULL);
            egg1Et.setFocusable(false);
            egg2Et.setText(result.getString(result.getColumnIndex("egg_group2")));
            egg2Et.setInputType(InputType.TYPE_NULL);
            egg2Et.setFocusable(false);
            natureAuto.setText(result.getString(result.getColumnIndex("nature")));
            abilityAuto.setText(result.getString(result.getColumnIndex("ability")));
            noteEt.setText(result.getString(result.getColumnIndex("note")));

            if (result.getInt(result.getColumnIndex("hp")) == 1) {
                hpCBox.setChecked(true);
            }
            if (result.getInt(result.getColumnIndex("att")) == 1) {
                attCBox.setChecked(true);
            }
            if (result.getInt(result.getColumnIndex("def")) == 1) {
                defCBox.setChecked(true);
            }
            if (result.getInt(result.getColumnIndex("sp_att")) == 1) {
                spAttCBox.setChecked(true);
            }
            if (result.getInt(result.getColumnIndex("sp_def")) == 1) {
                spDefCBox.setChecked(true);
            }
            if (result.getInt(result.getColumnIndex("speed")) == 1) {
                speedCBox.setChecked(true);
            }

            abilities = databaseConnector
                    .getAllAbilityForPoke(nameAuto.getText().toString());
            ArrayAdapter<String> abilityAdapter = new ArrayAdapter<>(
                    AddPoke.this, R.layout.auto_list_item, abilities);
            abilityAuto.setAdapter(abilityAdapter);

            ivLibrarydatabaseConnector.close();
            result.close();
        }
    }

    private class LoadPokeTask extends AsyncTask<String, Object, Cursor> {
        // perform the database access
        @Override
        protected Cursor doInBackground(String... params) {
            databaseConnector.openDataBase();
            // get a cursor containing all data on given entry
            return databaseConnector.getOnePoke(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToFirst(); // move to first item

            // get the column index for each data item
            int egg1Index = result.getColumnIndex("egg_group1");
            int egg2Index = result.getColumnIndex("egg_group2");
            int natdexIndex = result.getColumnIndex("nat_dex");

            // fill the TextView with the retrieved data
            egg1Et.setText(result.getString(egg1Index));
            egg2Et.setText(result.getString(egg2Index));
            dexEt.setText(result.getString(natdexIndex));

            result.close();
        }
    }
}
