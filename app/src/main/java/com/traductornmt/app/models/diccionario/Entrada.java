package com.traductornmt.app.models.diccionario;
public class Entrada {
    private int id;
    private String palabra;
    private String origen;
    private String categoria;
    private String definicion;
    private String ejemplo;
    private String referencias;

    // Constructor vacío para Gson
    public Entrada() {}

    // Constructor completo
    public Entrada(String palabra, String origen, String categoria,
                   String definicion, String ejemplo, String referencias) {
        this.palabra = palabra;
        this.origen = origen;
        this.categoria = categoria;
        this.definicion = definicion;
        this.ejemplo = ejemplo;
        this.referencias = referencias;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPalabra() { return palabra; }
    public void setPalabra(String palabra) { this.palabra = palabra; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDefinicion() { return definicion; }
    public void setDefinicion(String definicion) { this.definicion = definicion; }

    public String getEjemplo() { return ejemplo; }
    public void setEjemplo(String ejemplo) { this.ejemplo = ejemplo; }

    public String getReferencias() { return referencias; }
    public void setReferencias(String referencias) { this.referencias = referencias; }
}