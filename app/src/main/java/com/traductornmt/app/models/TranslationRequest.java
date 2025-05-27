package com.traductornmt.app.models;

public class TranslationRequest {
    private String text;
    private boolean fix_unks;

    public TranslationRequest(String text, boolean fix_unks) {
        this.text = text;
        this.fix_unks = fix_unks;
    }

    // Getters y setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFix_unks() {
        return fix_unks;
    }

    public void setFix_unks(boolean fix_unks) {
        this.fix_unks = fix_unks;
    }
}
