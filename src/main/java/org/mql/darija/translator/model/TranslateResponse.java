package org.mql.darija.translator.model;

public class TranslateResponse {
    private String translation;
    public TranslateResponse(String translation) {
        this.translation = translation;
    }
    // Getters and Setters
    public String getTranslation() {
        return translation;
    }
    public void setTranslation(String translation) {
        this.translation = translation; 
    }
}
