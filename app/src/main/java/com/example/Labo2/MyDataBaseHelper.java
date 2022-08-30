package com.example.Labo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "bdvoyages";


    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "CREATE TABLE IF NOT EXISTS voyages (" +
                "code INTEGER," +
                "depart TEXT," +
                "destination TEXT," +
                "transporteur TEXT)";
        db.execSQL(sql);


        //Initialisation de la table
        ContentValues values = new ContentValues();
        values.put("code", "1");
        values.put("depart", "Montreal");
        values.put("destination", "Quebec");
        values.put("transporteur", "Limocar Laurentide");
        db.insert("voyages", null, values);

        values = new ContentValues();
        values.put("code", "2");
        values.put("depart", "Montreal");
        values.put("destination", "Paris");
        values.put("transporteur", "Air France");
        db.insert("voyages", null, values);


        values = new ContentValues();
        values.put("code", "3");
        values.put("depart", "Montreal");
        values.put("destination", "New York");
        values.put("transporteur", "Air Canada");
        db.insert("voyages", null, values);


        values = new ContentValues();
        values.put("code", "4");
        values.put("depart", "Varsovie");
        values.put("destination", "Montreal");
        values.put("transporteur", "LOT");
        db.insert("voyages", null, values);

        values = new ContentValues();
        values.put("code", "5");
        values.put("depart", "Montreal");
        values.put("destination", "New York");
        values.put("transporteur", "Air Canada");
        db.insert("voyages", null, values);

        values = new ContentValues();
        values.put("code", "6");
        values.put("depart", "Montreal");
        values.put("destination", "Paris");
        values.put("transporteur", "Air Canada");
        db.insert("voyages", null, values);

        values = new ContentValues();
        values.put("code", "7");
        values.put("depart", "Montreal");
        values.put("destination", "New York");
        values.put("transporteur", "Air Canada");
        db.insert("voyages", null, values);

        values = new ContentValues();
        values.put("code", "8");
        values.put("depart", "Montreal");
        values.put("destination", "New York");
        values.put("transporteur", "Pan Am");
        db.insert("voyages", null, values);


        values = new ContentValues();
        values.put("code", "8");
        values.put("depart", "New York");
        values.put("destination", "Montreal");
        values.put("transporteur", "Voyageur");
        db.insert("voyages", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS voyages");
        onCreate(db);
    }

    public static Cursor obtenirToutLesVoyages(SQLiteDatabase db) {
        return db.rawQuery("select rowid AS _id, code, depart, destination, transporteur from voyages", null);
    }

    public static void insertVoyage(String code, String depart, String destination, String transporteur, SQLiteDatabase db) {

        //Initialisation de la table
        ContentValues values = new ContentValues();

        values.put("code", code);
        values.put("depart", depart);
        values.put("destination", destination);
        values.put("transporteur", transporteur);

        db.insert("voyages", null, values);


    }


    public static Cursor obtenirTransporteurParDepart(String depart, SQLiteDatabase db) {
        return db.rawQuery("SELECT rowid AS _id, depart, transporteur FROM voyages WHERE depart = ?", new String[]{depart});
    }


    public static Cursor obtenirListeTransporteur(SQLiteDatabase db) {
        return db.rawQuery("SELECT rowid AS _id, transporteur FROM voyages", null);
    }


    public static Cursor obtenirVolParTransporteur(String transporteur, SQLiteDatabase db) {
        return db.rawQuery("SELECT rowid AS _id, code, depart, destination transporteur FROM voyages WHERE transporteur = ?", new String[]{transporteur});
    }

    public static void effacerVol(String code, SQLiteDatabase db) {
        db.execSQL("DELETE FROM voyages WHERE code  = ?", new String[]{code});

    }


}
