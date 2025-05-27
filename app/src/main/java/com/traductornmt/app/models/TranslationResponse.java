package com.traductornmt.app.models;

public class TranslationResponse {
    private String original_text;
    private String translated_text;
    private double processing_time;
    private String status;

    // Constructor vacío
    public TranslationResponse() {}

    // Getters y setters
    public String getOriginal_text() {
        return original_text;
    }

    public void setOriginal_text(String original_text) {
        this.original_text = original_text;
    }

    public String getTranslated_text() {
        return translated_text;
    }

    public void setTranslated_text(String translated_text) {
        this.translated_text = translated_text;
    }

    public double getProcessing_time() {
        return processing_time;
    }

    public void setProcessing_time(double processing_time) {
        this.processing_time = processing_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Método de conveniencia para verificar si fue exitoso
    public boolean isSuccess() {
        return "éxito".equals(status);
    }

    // Para compatibilidad con el código anterior
    public String getSource_language() {
        return "Quechua Cajamarquino";
    }

    public String getTarget_language() {
        return "Español";
    }

    public String getMessage() {
        return status != null ? status : "Error desconocido";
    }
}
