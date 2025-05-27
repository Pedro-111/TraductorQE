package com.traductornmt.app.models;

import java.util.List;

public class BatchTranslationResponse {
    private List<TranslationResponse> translations;
    private double total_processing_time;

    // Constructor vacío
    public BatchTranslationResponse() {}

    // Getters y setters
    public List<TranslationResponse> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationResponse> translations) {
        this.translations = translations;
    }

    public double getTotal_processing_time() {
        return total_processing_time;
    }

    public void setTotal_processing_time(double total_processing_time) {
        this.total_processing_time = total_processing_time;
    }
}