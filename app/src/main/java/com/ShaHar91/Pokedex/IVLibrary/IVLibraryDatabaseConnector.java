package com.ShaHar91.Pokedex.IVLibrary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class IVLibraryDatabaseConnector extends SQLiteOpenHelper {

    @SuppressLint("SdCardPath")
    private static String DB_PATH = "/data/data/com.ShaHar91.Pokedex/databases/";
    private static String DB_NAME = "IVLibrary.sqlite";
    public SQLiteDatabase myDataBase;
    private static final int SCHEMA_VERSION = 1;
    private final Context myContext;

    public IVLibraryDatabaseConnector(Context context) {
        super(context, DB_NAME, null, SCHEMA_VERSION);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() {

        boolean dbExist = checkDataBase();
        if (!dbExist) {
            // By calling this method an empty database will be created into the
            // default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getWritableDatabase();

            copyDataBase();

        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
            checkDB.setLocale(Locale.getDefault());
        } catch (SQLiteException e) {
            Log.e("SqlHelper", "database not found");
        }
        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() {

        InputStream myInput = null;
        OutputStream myOutput = null;
        String dbFilePath = DB_PATH + DB_NAME;

        try {
            // Open your local db as the input stream
            myInput = myContext.getAssets().open(DB_NAME);

            // Open the empty db as the output stream
            myOutput = new FileOutputStream(dbFilePath);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            throw new Error("Problem copying database from resource file.");
        }

    }

    public void openDataBase() throws SQLException {
        // this has to be used in case you want an upgrade to happen for your
        // database,
        // without this it won't happen
        myDataBase = this.getWritableDatabase();

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            // this is the line of code that sends a real error message to the
            // log
            // this is not an error, just to show that this executed on the
            // emulator
            Log.e("WORKED!!!", "On upgrade executed");
        }

        if (newVersion > oldVersion) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            String dbFilePath = DB_PATH + DB_NAME;

            try {

                inputStream = myContext.getAssets().open("IVLibrary.sqlite");

                outputStream = new FileOutputStream(dbFilePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();

            } catch (IOException e) {
                throw new Error("Problem copying database from resource file.");

            }
        }

    }

    public void insertPoke(String name, String dex, String gender,
                           String egg_group1, String egg_group2, String nature,
                           String ability, String note, Integer hp, Integer att, Integer def,
                           Integer spAtt, Integer spDef, Integer speed) {
        ContentValues newPoke = new ContentValues();
        newPoke.put("name", name);
        newPoke.put("nat_dex", dex);
        newPoke.put("gender", gender);
        newPoke.put("egg_group1", egg_group1);
        newPoke.put("egg_group2", egg_group2);
        newPoke.put("nature", nature);
        newPoke.put("ability", ability);
        newPoke.put("note", note);
        newPoke.put("hp", hp);
        newPoke.put("att", att);
        newPoke.put("def", def);
        newPoke.put("sp_att", spAtt);
        newPoke.put("sp_def", spDef);
        newPoke.put("speed", speed);
        // newPoke.put("language", language);

        openDataBase(); // open the database
        myDataBase.insert("added_poke", null, newPoke);
    } // end method insertBook

    public void updatePoke(long id, String name, String dex, String gender,
                           String egg_group1, String egg_group2, String nature,
                           String ability, String note, Integer hp, Integer att, Integer def,
                           Integer spAtt, Integer spDef, Integer speed) {
        ContentValues editPoke = new ContentValues();
        editPoke.put("name", name);
        editPoke.put("nat_dex", dex);
        editPoke.put("gender", gender);

        editPoke.put("egg_group1", egg_group1);
        editPoke.put("egg_group2", egg_group2);
        editPoke.put("nature", nature);
        editPoke.put("ability", ability);
        editPoke.put("note", note);
        editPoke.put("hp", hp);
        editPoke.put("att", att);
        editPoke.put("def", def);
        editPoke.put("sp_att", spAtt);
        editPoke.put("sp_def", spDef);
        editPoke.put("speed", speed);

        openDataBase(); // open the database
        myDataBase.update("added_poke", editPoke, "_id=" + id, null);
    } // end method insertBook

    public Cursor getAllPokes() {
        return myDataBase.query("added_poke", null, null, null, null, null,
                "_id");

    }

    public Cursor getAllPokesFilter(String name, String nature,
                                    String egg_groups, String hpInt, String attInt, String defInt,
                                    String spAttInt, String spDefInt, String speedInt) {
        return myDataBase.query("added_poke", null, "name LIKE \"%" + name
                        + "%\" and nature LIKE \"%" + nature
                        + "%\" AND egg_group1 || \"+\" ||  egg_group2 LIKE \"%"
                        + egg_groups + "%\" and hp LIKE \"%" + hpInt
                        + "%\" and att LIKE \"%" + attInt + "%\" and def LIKE \"%"
                        + defInt + "%\" and sp_att LIKE \"%" + spAttInt
                        + "%\" and sp_def LIKE \"%" + spDefInt
                        + "%\" and speed LIKE \"%" + speedInt + "%\"", null, null,
                null, "_id");

    }

    public Cursor getAddedPoke(long id) {
        return myDataBase.query("added_poke", null, "_id=" + id, null, null,
                null, null);
    }

    public void deletePoke(long id) {
        openDataBase(); // open the database
        myDataBase.delete("added_poke", "_id=" + id, null);
    } // end method deleteBook
}
