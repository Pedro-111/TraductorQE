package com.traductornmt.app.models.diccionario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diccionario_quechua.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla para Quechua -> Español
    public static final String TABLE_ENTRADAS_QE = "entradas_qe";
    // Tabla para Español -> Quechua
    public static final String TABLE_ENTRADAS_EQ = "entradas_eq";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PALABRA = "palabra";
    public static final String COLUMN_ORIGEN = "origen";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_DEFINICION = "definicion";
    public static final String COLUMN_EJEMPLO = "ejemplo";
    public static final String COLUMN_REFERENCIAS = "referencias";

    private static final String CREATE_TABLE_QE =
            "CREATE TABLE " + TABLE_ENTRADAS_QE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PALABRA + " TEXT NOT NULL, " +
                    COLUMN_ORIGEN + " TEXT, " +
                    COLUMN_CATEGORIA + " TEXT, " +
                    COLUMN_DEFINICION + " TEXT NOT NULL, " +
                    COLUMN_EJEMPLO + " TEXT, " +
                    COLUMN_REFERENCIAS + " TEXT" +
                    ");";

    private static final String CREATE_TABLE_EQ =
            "CREATE TABLE " + TABLE_ENTRADAS_EQ + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PALABRA + " TEXT NOT NULL, " +
                    COLUMN_ORIGEN + " TEXT, " +
                    COLUMN_CATEGORIA + " TEXT, " +
                    COLUMN_DEFINICION + " TEXT NOT NULL, " +
                    COLUMN_EJEMPLO + " TEXT, " +
                    COLUMN_REFERENCIAS + " TEXT" +
                    ");";

    private static final String CREATE_INDEX_PALABRA_QE =
            "CREATE INDEX idx_palabra_qe ON " + TABLE_ENTRADAS_QE + "(" + COLUMN_PALABRA + ");";

    private static final String CREATE_INDEX_PALABRA_EQ =
            "CREATE INDEX idx_palabra_eq ON " + TABLE_ENTRADAS_EQ + "(" + COLUMN_PALABRA + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QE);
        db.execSQL(CREATE_TABLE_EQ);
        db.execSQL(CREATE_INDEX_PALABRA_QE);
        db.execSQL(CREATE_INDEX_PALABRA_EQ);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRADAS_QE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRADAS_EQ);
        onCreate(db);
    }
}