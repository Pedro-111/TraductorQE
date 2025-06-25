package com.traductornmt.app.models.diccionario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DiccionarioDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DiccionarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void abrir() {
        database = dbHelper.getWritableDatabase();
    }

    public void cerrar() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Insertar una entrada
    public long insertarEntrada(Entrada entrada) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PALABRA, entrada.getPalabra());
        values.put(DatabaseHelper.COLUMN_ORIGEN, entrada.getOrigen());
        values.put(DatabaseHelper.COLUMN_CATEGORIA, entrada.getCategoria());
        values.put(DatabaseHelper.COLUMN_DEFINICION, entrada.getDefinicion());
        values.put(DatabaseHelper.COLUMN_EJEMPLO, entrada.getEjemplo());
        values.put(DatabaseHelper.COLUMN_REFERENCIAS, entrada.getReferencias());

        return database.insert(DatabaseHelper.TABLE_ENTRADAS, null, values);
    }

    // Insertar múltiples entradas (más eficiente)
    public int insertarEntradas(List<Entrada> entradas) {
        int insertadas = 0;
        database.beginTransaction();
        try {
            for (Entrada entrada : entradas) {
                long resultado = insertarEntrada(entrada);
                if (resultado != -1) {
                    insertadas++;
                }
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        return insertadas;
    }

    // Buscar por palabra
    public List<Entrada> buscarPorPalabra(String palabra) {
        List<Entrada> entradas = new ArrayList<>();
        String selection = DatabaseHelper.COLUMN_PALABRA + " LIKE ?";
        String[] selectionArgs = {"%" + palabra + "%"};

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_ENTRADAS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                DatabaseHelper.COLUMN_PALABRA + " ASC"
        );

        entradas = cursorToEntradas(cursor);
        cursor.close();
        return entradas;
    }

    private List<Entrada> cursorToEntradas(Cursor cursor) {
        List<Entrada> entradas = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Entrada entrada = new Entrada();
                entrada.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                entrada.setPalabra(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PALABRA)));
                entrada.setOrigen(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORIGEN)));
                entrada.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORIA)));
                entrada.setDefinicion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DEFINICION)));
                entrada.setEjemplo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EJEMPLO)));
                entrada.setReferencias(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REFERENCIAS)));

                entradas.add(entrada);
            } while (cursor.moveToNext());
        }

        return entradas;
    }
    public List<Entrada> buscarEnDefinicion(String texto) {
        List<Entrada> entradas = new ArrayList<>();
        String selection = DatabaseHelper.COLUMN_DEFINICION + " LIKE ?";
        String[] selectionArgs = {"%" + texto + "%"};

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_ENTRADAS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                DatabaseHelper.COLUMN_PALABRA + " ASC"
        );

        entradas = cursorToEntradas(cursor);
        cursor.close();
        return entradas;
    }

    public List<Entrada> obtenerTodas() {
        List<Entrada> entradas = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_ENTRADAS,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_PALABRA + " ASC"
        );

        entradas = cursorToEntradas(cursor);
        cursor.close();
        return entradas;
    }
}