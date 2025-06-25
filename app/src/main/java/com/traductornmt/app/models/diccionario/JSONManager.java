package com.traductornmt.app.models.diccionario;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class JSONManager {
    private Gson gson;

    public JSONManager() {
        this.gson = new Gson();
    }

    // Convertir JSON string a lista de entradas
    public List<Entrada> jsonToEntradas(String json) {
        Type listType = new TypeToken<List<Entrada>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    // Convertir una entrada a JSON
    public String entradaToJson(Entrada entrada) {
        return gson.toJson(entrada);
    }

    // Convertir lista de entradas a JSON
    public String entradasToJson(List<Entrada> entradas) {
        return gson.toJson(entradas);
    }
}
