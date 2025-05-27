package com.traductornmt.app.models;

import java.util.List;

public class BatchTranslationRequest {
    private List<String> texts;
    private boolean fix_unks;

    public BatchTranslationRequest(List<String> texts, boolean fix_unks) {
        this.texts = texts;
        this.fix_unks = fix_unks;
    }

    // Getters y setters
    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public boolean isFix_unks() {
        return fix_unks;
    }

    public void setFix_unks(boolean fix_unks) {
        this.fix_unks = fix_unks;
    }
}
