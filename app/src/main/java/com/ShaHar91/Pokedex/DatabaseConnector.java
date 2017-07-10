package com.ShaHar91.Pokedex;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseConnector extends SQLiteOpenHelper {

    @SuppressLint("SdCardPath")
    private static String DB_PATH = "/data/data/com.ShaHar91.Pokedex/databases/";
    private static String DB_NAME = "Pokedex.jpeg";
    public SQLiteDatabase myDataBase;
    private static final int SCHEMA_VERSION = 12;
    private final Context myContext;

    public DatabaseConnector(Context context) {
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
            byte[] buffer = new byte[2048];
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

                inputStream = myContext.getAssets().open("Pokedex.jpeg");

                outputStream = new FileOutputStream(dbFilePath);

                byte[] buffer = new byte[2048];
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

    public Cursor fetchItemChildren(long pocket_id, long cat_id) {
        return myDataBase
                .rawQuery(
                        "SELECT item_id as _id, items.name, pocket_id, item_category.name as category_name, category_id, cost, short_effect, effect, flavor_text FROM items join item_category on (items.category_id = item_category._id) where version_group_id = 15 and pocket_id = "
                                + pocket_id
                                + " and category_id = "
                                + cat_id
                                + " order by items.name", new String[]{});
    }

    public Cursor fetchItemGroup(Long id) {
        return myDataBase.query("item_category", null, "pocket_id = " + id,
                null, null, null, null);

    }

    public Cursor fetchGroup() {
        return myDataBase.query("moveType", null, null, null, null, null, null);
    }

    public Cursor fetchAbilityGroup() {
        return myDataBase.query("abilityType", null, null, null, null, null, null);
    }

    public Cursor fetchChildren(String moveType, long move_id) {
        return myDataBase
                .rawQuery(
                        "SELECT pokemon_move.rowid as _id, pokemonOrder, id_move, type1, type2, pokemon.name, learnMoveBy, moveTypeLevel FROM pokemon_move JOIN moveType ON (pokemon_move.moveType = moveType._id) JOIN  pokemon ON (pokemon_move.pokemonOrder = pokemon._id) WHERE moveType = "
                                + moveType
                                + " and id_move = "
                                + move_id
                                + " and version_group_id = 15 ORDER BY pokemonOrder",
                        new String[]{});
    }

    public Cursor fetchAbilityChildren(String abilityType, long ability_id) {
        return myDataBase
                .rawQuery(
                        "SELECT pokemon_ability.rowid as _id, pokemonOrder, id_ability , type1, type2, pokemon.name, ability_type FROM pokemon_ability JOIN abilityType ON (pokemon_ability.is_hidden = abilityType._id) JOIN pokemon ON (pokemon_ability.pokemonOrder = pokemon._id) WHERE is_hidden = "
                                + abilityType
                                + " and id_ability = "
                                + ability_id +
                                "",
                        new String[]{});
    }

    public Cursor getCaught(long id) {
        return myDataBase.query("caught_poke", null, "_id=" + id, null, null,
                null, "_id");
    }

    public Cursor getEvos(long id) {
        return myDataBase
                .rawQuery(
                        "select pokemon_evolutions._id, pokemon.name, gen_id, evolves_from_id, evolution_chain_id, is_stage, ordering, evolution_method from pokemon_evolutions join pokemon on (pokemon_evolutions._id = pokemon._id) where evolution_chain_id = "
                                + id, new String[]{});
    }

    public Cursor getAbilityForOnePoke(long id) {
        return myDataBase
                .rawQuery(
                        "SELECT id_ability AS _id, pokemonOrder,  abilities.name AS ability FROM pokemon_ability JOIN abilities ON (pokemon_ability.id_ability = abilities._id) WHERE pokemonOrder = "
                                + id + " ORDER BY pokemon_ability._id",
                        new String[]{});
    }

    public Cursor getBaseStats(String name) {
        return myDataBase
                .rawQuery(
                        "SELECT pokemon._id AS _id, name, nat_dex, hp, att, def, sp_att, sp_def,speed FROM  base_stat JOIN pokemon ON (base_stat._id = pokemon._id) WHERE name = \""
                                + name + "\"", new String[]{});
    }

    public Cursor getAllPokeMoves(long id, Integer moveType) {
        return myDataBase
                .rawQuery(
                        "SELECT pokemon_move.rowid as _id, pokemon_move.pokemonOrder, pokemon_move.moveType, pokemon_move.moveTypeLevel, moves.name, moves.type, moves.category, moves.base_power, moves.power_points, moves.accuracy, moves.tm_no FROM  pokemon_move JOIN  moves ON (pokemon_move.id_move = moves._id) where version_group_id = 15 and moveType = "
                                + moveType
                                + " and pokemonOrder ="
                                + id
                                + " order by pokemon_move.moveTypeLevel, tm_no",
                        new String[]{});
    }

    public String[] getAllAbilityForPoke(String name) {
        Cursor cursor = myDataBase
                .rawQuery(
                        "SELECT id_ability AS _id, pokemonOrder,  abilities.name AS ability, pokemon.name AS name FROM pokemon_ability JOIN abilities ON (pokemon_ability.id_ability = abilities._id) JOIN pokemon ON (pokemon_ability.pokemonOrder = pokemon._id) WHERE pokemon.name = \""
                                + name + "\" ORDER BY pokemon_ability._id",
                        new String[]{});

        if (cursor.getCount() >= 0) {
            String[] str = new String[cursor.getCount()];
            int i = 0;

            while (cursor.moveToNext()) {
                str[i] = cursor.getString(cursor.getColumnIndex("ability"));
                i++;
            }
            return str;
        } else {
            return new String[]{};
        }
    }

    public Cursor getOnePoke(String name) {
        return myDataBase.query("pokemon", null, "name = \"" + name + "\"",
                null, null, null, null);
    }

    public Cursor getNatureEffect(String nature) {
        return myDataBase.query("natures", null, "name = \"" + nature + "\"",
                null, null, null, null);
    }

    public Cursor getWeakness(String type1, String type2) {
        return myDataBase.query("match_up", null, "type1 LIKE \"%" + type1
                        + "%\" AND type2 LIKE \"%" + type2 + "%\"", null, null, null,
                null);
    }

    public Cursor getPokemonWithTypes(String name, String type1, String type2) {
        return myDataBase.query("pokemon", null, "name LIKE \"%" + name
                        + "%\" AND type1 || \"+\" ||  type2 LIKE \"%" + type1
                        + "%\" AND type1 || \"+\" ||  type2 LIKE \"%" + type2 + "%\"",
                null, null, null, "nat_dex");
    }

    public Cursor getOnePokeBaseStat(long id) {
        return myDataBase.query("base_stat", null, "_id=" + id, null, null,
                null, null);
    }

    public Cursor getAllPokesForDex() {
        return myDataBase.query("pokemon", null, null, null, null, null,
                "nat_dex");
    }

    public Cursor getAllPockets() {
        return myDataBase.query("item_pockets", null, null, null, null, null,
                "_id");
    }

    public Cursor getAllItems(long id) {
        return myDataBase
                .rawQuery(
                        "SELECT item_id as _id, items.name, pocket_id, item_category.name as category_name, category_id, cost, short_effect, effect, flavor_text FROM items join item_category on (items.category_id = item_category._id) where version_group_id = 15 and pocket_id = "
                                + id + " order by items.name", new String[]{});
    }

    public Cursor getFilterMoves(String search, CharSequence constraint) {

        if (search.contains("Type")) {
            return myDataBase.query("moves",
                    new String[]{"_id", "name", "type", "base_power",
                            "accuracy", "power_points", "tm_no"},
                    "type LIKE \"%" + constraint + "%\"", null, null, null,
                    "name");
        } else if (search.contains("Attack")) {
            return myDataBase.query("moves",
                    new String[]{"_id", "name", "type", "base_power",
                            "accuracy", "power_points", "tm_no"},
                    "base_power LIKE \"%" + constraint + "%\"", null, null,
                    null, "name");
        } else if (search.contains("Accuracy")) {
            return myDataBase.query("moves",
                    new String[]{"_id", "name", "type", "base_power",
                            "accuracy", "power_points", "tm_no"},
                    "accuracy LIKE \"%" + constraint + "%\"", null, null, null,
                    "name");
        } else if (search.contains("PP")) {
            return myDataBase.query("moves",
                    new String[]{"_id", "name", "type", "base_power",
                            "accuracy", "power_points", "tm_no"},
                    "power_points LIKE \"%" + constraint + "%\"", null, null,
                    null, "name");
        } else if (search.contains("TM")) {
            return myDataBase.query("moves",
                    new String[]{"_id", "name", "type", "base_power",
                            "accuracy", "power_points", "tm_no"},
                    "tm_no LIKE \"%TM" + constraint + "%\"", null, null, null,
                    "tm_no");
        } else {
            return myDataBase.query("moves",
                    new String[]{"_id", "name", "type", "base_power",
                            "accuracy", "power_points", "tm_no"},
                    "name LIKE \"%" + constraint + "%\"", null, null, null,
                    "name");
        }
    }

    public Cursor getFilterPokeList(String search, String name) {

        if (search.contains("Types")) {
            return myDataBase.query("pokemon", null,
                    "type1" + " like '%" + name + "%' OR type2 like '%" + name
                            + "%' and isMega = 0", null, null, null, "nat_dex");
        } else if (search.contains("Dex No")) {
            return myDataBase.query("pokemon", null, "nat_dex LIKE \"%" + name
                    + "%\" and isMega = 0", null, null, null, "nat_dex");
        } else if (search.contains("EV Values")) {
            return myDataBase.query("pokemon", null, "evYield LIKE \"%" + name
                    + "%\" and isMega = 0", null, null, null, "nat_dex");
        } else {
            return myDataBase.query("pokemon", null, "name LIKE \"%" + name
                    + "%\" and isMega = 0", null, null, null, "nat_dex");
        }
    }

    public Cursor getAllAbilities() {
        return myDataBase.query("abilities", new String[]{"_id", "name",
                "nPokemons"}, null, null, null, null, "_id");
    }

    public Cursor getOneAbility(long id) {
        return myDataBase.query("abilities", null, "_id=" + id, null, null,
                null, null);
    }

    public Cursor filterAbilities(CharSequence constraint) {
        return myDataBase.query("abilities", null, "name LIKE \"%" + constraint
                + "%\"", null, null, null, null);
    }

    public Cursor getAllMoves() {
        return myDataBase.query("moves", new String[]{"_id", "name", "type",
                        "base_power", "accuracy", "power_points", "tm_no"}, null,
                null, null, null, "name");
    }

    public Cursor getOneMove(long id) {
        return myDataBase.query("moves", null, "_id=" + id, null, null, null,
                null);
    }

    public Cursor getPokeForDexOriginal(long id) {
        return myDataBase.query("pokemon", null, "_id=" + id, null, null, null,
                null);
    }

    public Cursor getPokeForDex(long id) {
        return myDataBase
                .rawQuery(
                        "select pokemon._id, pokemon.name, nat_dex, species, type1, type2, egg_group1, egg_group2, height, weight, gender_spread, evYield, egg_steps, base_happiness, capture_rate, growth, description_x, description_y, location_x, location_y, isMega, isAlternate, notes, hasMega, evolution_chain_id  from pokemon join pokemon_evolutions on (pokemon._id = pokemon_evolutions._id) where pokemon._id ="
                                + id, new String[]{});
    }

    public Cursor getPokeMegaForDex(long nat_dex) {
        return myDataBase
                .rawQuery(
                        "SELECT pokemon._id, pokemon.name AS name, nat_dex, species, type1, type2, height, weight, gender_spread, evYield, egg_steps, base_happiness, capture_rate, growth, notes, hp, att,def, sp_att,sp_def, speed, abilities.name AS ability FROM pokemon Join base_stat on (pokemon._id = base_stat._id)  join pokemon_ability on (pokemon._id = pokemon_ability.pokemonOrder) join abilities on (id_ability = abilities._id) where nat_dex="
                                + nat_dex + " and isMega=1", new String[]{});
    }

    public Cursor getDescription(long id) {
        return myDataBase.query("all_flavor", null, "_id=" + id, null, null,
                null, null);
    }

    public String[] getAllPokemons() {
        Cursor cursor = myDataBase.query("pokemon", new String[]{"_id",
                "name", "nat_dex"}, "isMega=0", null, null, null, "_id");

        if (cursor.getCount() >= 0) {
            String[] str = new String[cursor.getCount()];
            int i = 0;

            while (cursor.moveToNext()) {
                str[i] = cursor.getString(cursor.getColumnIndex("name"));
                i++;
            }
            return str;
        } else {
            return new String[]{};
        }
    }

    public String[] getAllPokemonsIncMega() {
        Cursor cursor = myDataBase.query("pokemon", new String[]{"_id",
                "name", "nat_dex"}, null, null, null, null, "_id");

        if (cursor.getCount() >= 0) {
            String[] str = new String[cursor.getCount()];
            int i = 0;

            while (cursor.moveToNext()) {
                str[i] = cursor.getString(cursor.getColumnIndex("name"));
                i++;
            }
            return str;
        } else {
            return new String[]{};
        }
    }

    public Cursor filterGroups(String group) {
        return myDataBase.query("pokemon", null, "egg_group1" + " like '%"
                        + group + "%' OR egg_group2 like '%" + group + "%'", null,
                null, null, "_id");
    }

    public Cursor filterDexByNo(CharSequence constraint) {
        return myDataBase.query("pokemon", null, "nat_no LIKE \"%" + constraint
                + "%\"", null, null, null, "nat_dex");
    }

}
