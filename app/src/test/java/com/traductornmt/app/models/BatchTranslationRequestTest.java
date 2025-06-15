package com.traductornmt.app.models;

import org.junit.Test;
import org.junit.Before;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BatchTranslationRequestTest {

    private List<String> textosEjemplo;

    @Before
    public void setUp() {
        textosEjemplo = Arrays.asList("Allillanchu", "Imaynallan", "Tupananchiskama");
    }

    @Test
    public void testConstructor_ValidInput() {
        // Given
        boolean fixUnks = true;

        // When
        BatchTranslationRequest request = new BatchTranslationRequest(textosEjemplo, fixUnks);

        // Then
        assertEquals("La lista de textos debe ser igual", textosEjemplo, request.getTexts());
        assertTrue("fix_unks debe ser true", request.isFix_unks());
    }

    @Test
    public void testSetTexts() {
        // Given
        BatchTranslationRequest request = new BatchTranslationRequest(new ArrayList<>(), false);
        List<String> nuevosTextos = Arrays.asList("Nuevo", "Texto", "Lista");

        // When
        request.setTexts(nuevosTextos);

        // Then
        assertEquals("La lista debe actualizarse", nuevosTextos, request.getTexts());
    }

    @Test
    public void testSetFixUnks() {
        // Given
        BatchTranslationRequest request = new BatchTranslationRequest(textosEjemplo, false);

        // When
        request.setFix_unks(true);

        // Then
        assertTrue("fix_unks debe cambiar a true", request.isFix_unks());
    }

    @Test
    public void testConstructor_EmptyList() {
        // Given
        List<String> listaVacia = new ArrayList<>();
        boolean fixUnks = false;

        // When
        BatchTranslationRequest request = new BatchTranslationRequest(listaVacia, fixUnks);

        // Then
        assertTrue("Debe aceptar lista vacía", request.getTexts().isEmpty());
        assertFalse("fix_unks debe ser false", request.isFix_unks());
    }

    @Test
    public void testConstructor_NullList() {
        // Given
        List<String> listaNula = null;
        boolean fixUnks = true;

        // When
        BatchTranslationRequest request = new BatchTranslationRequest(listaNula, fixUnks);

        // Then
        assertNull("Debe aceptar lista null", request.getTexts());
        assertTrue("fix_unks debe ser true", request.isFix_unks());
    }
}