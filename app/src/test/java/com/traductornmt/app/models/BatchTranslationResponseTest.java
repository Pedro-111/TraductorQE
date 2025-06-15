package com.traductornmt.app.models;

import org.junit.Test;
import org.junit.Before;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BatchTranslationResponseTest {

    private BatchTranslationResponse response;

    @Before
    public void setUp() {
        response = new BatchTranslationResponse();
    }

    @Test
    public void testDefaultConstructor() {
        // Then
        assertNull("Las traducciones deben ser null por defecto", response.getTranslations());
        assertEquals("El tiempo total debe ser 0.0", 0.0, response.getTotal_processing_time(), 0.001);
    }

    @Test
    public void testSetTranslations() {
        // Given
        List<TranslationResponse> translations = new ArrayList<>();
        TranslationResponse tr1 = new TranslationResponse();
        tr1.setOriginal_text("Allillanchu");
        tr1.setTranslated_text("¿Cómo estás?");
        translations.add(tr1);

        // When
        response.setTranslations(translations);

        // Then
        assertEquals("Las traducciones deben establecerse correctamente",
                translations, response.getTranslations());
        assertEquals("Debe tener una traducción", 1, response.getTranslations().size());
    }

    @Test
    public void testSetTotalProcessingTime() {
        // Given
        double tiempoTotal = 5.75;

        // When
        response.setTotal_processing_time(tiempoTotal);

        // Then
        assertEquals("El tiempo total debe establecerse correctamente",
                tiempoTotal, response.getTotal_processing_time(), 0.001);
    }

    @Test
    public void testSetTranslations_EmptyList() {
        // Given
        List<TranslationResponse> listaVacia = new ArrayList<>();

        // When
        response.setTranslations(listaVacia);

        // Then
        assertNotNull("La lista no debe ser null", response.getTranslations());
        assertTrue("La lista debe estar vacía", response.getTranslations().isEmpty());
    }

    @Test
    public void testSetTotalProcessingTime_Zero() {
        // Given
        double tiempoCero = 0.0;

        // When
        response.setTotal_processing_time(tiempoCero);

        // Then
        assertEquals("Debe aceptar tiempo cero", 0.0, response.getTotal_processing_time(), 0.001);
    }
}