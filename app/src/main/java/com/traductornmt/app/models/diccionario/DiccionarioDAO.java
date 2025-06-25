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
    public int insertarEntradasEnTabla(List<Entrada> entradas, String tabla) {
        int insertadas = 0;
        database.beginTransaction();
        try {
            for (Entrada entrada : entradas) {
                long resultado = insertarEntradaEnTabla(entrada, tabla);
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
    public long insertarEntradaEnTabla(Entrada entrada, String tabla) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PALABRA, entrada.getPalabra());
        values.put(DatabaseHelper.COLUMN_ORIGEN, entrada.getOrigen());
        values.put(DatabaseHelper.COLUMN_CATEGORIA, entrada.getCategoria());
        values.put(DatabaseHelper.COLUMN_DEFINICION, entrada.getDefinicion());
        values.put(DatabaseHelper.COLUMN_EJEMPLO, entrada.getEjemplo());
        values.put(DatabaseHelper.COLUMN_REFERENCIAS, entrada.getReferencias());

        return database.insert(tabla, null, values);
    }
    public void abrir() {
        database = dbHelper.getWritableDatabase();
    }

    public void cerrar() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
    public List<Entrada> buscarPorPalabraEnTabla(String palabra, String tabla) {
        List<Entrada> entradas = new ArrayList<>();
        String selection = DatabaseHelper.COLUMN_PALABRA + " LIKE ?";
        String[] selectionArgs = {"%" + palabra + "%"};

        Cursor cursor = database.query(
                tabla,
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
    public List<Entrada> obtenerTodasDeTabla(String tabla) {
        List<Entrada> entradas = new ArrayList<>();

        Cursor cursor = database.query(
                tabla,
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
    // Insertar una entrada



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

}