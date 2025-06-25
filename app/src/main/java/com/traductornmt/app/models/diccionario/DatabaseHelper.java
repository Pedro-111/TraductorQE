package com.traductornmt.app.models.diccionario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diccionario_quechua.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla entradas
    public static final String TABLE_ENTRADAS = "entradas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PALABRA = "palabra";
    public static final String COLUMN_ORIGEN = "origen";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_DEFINICION = "definicion";
    public static final String COLUMN_EJEMPLO = "ejemplo";
    public static final String COLUMN_REFERENCIAS = "referencias";

    private static final String CREATE_TABLE_ENTRADAS =
            "CREATE TABLE " + TABLE_ENTRADAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PALABRA + " TEXT NOT NULL, " +
                    COLUMN_ORIGEN + " TEXT, " +
                    COLUMN_CATEGORIA + " TEXT, " +
                    COLUMN_DEFINICION + " TEXT NOT NULL, " +
                    COLUMN_EJEMPLO + " TEXT, " +
                    COLUMN_REFERENCIAS + " TEXT" +
                    ");";

    private static final String CREATE_INDEX_PALABRA =
            "CREATE INDEX idx_palabra ON " + TABLE_ENTRADAS + "(" + COLUMN_PALABRA + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRADAS);
        db.execSQL(CREATE_INDEX_PALABRA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRADAS);
        onCreate(db);
    }
}