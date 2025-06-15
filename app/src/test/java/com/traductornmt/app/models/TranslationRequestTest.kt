package com.traductornmt.app.models

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TranslationRequestTest {
    private var translationRequest: TranslationRequest? = null

    @Before
    fun setUp() {
        translationRequest = TranslationRequest("Hola mundo", true)
    }

    @Test
    fun testConstructor_ValidInput() {
        // Given
        val text = "Allillanchu"
        val fixUnks = true

        // When
        val request = TranslationRequest(text, fixUnks)

        // Then
        Assert.assertEquals("El texto debe ser igual al proporcionado", text, request.text)
        Assert.assertTrue("fix_unks debe ser true", request.isFix_unks)
    }

    @Test
    fun testSetText() {
        // Given
        val nuevoTexto = "Imaynallan kashanki"

        // When
        translationRequest!!.text = nuevoTexto

        // Then
        Assert.assertEquals(
            "El texto debe actualizarse correctamente",
            nuevoTexto,
            translationRequest!!.text
        )
    }

    @Test
    fun testSetFixUnks() {
        // Given
        val nuevoFixUnks = false

        // When
        translationRequest!!.isFix_unks = nuevoFixUnks

        // Then
        Assert.assertFalse("fix_unks debe ser false", translationRequest!!.isFix_unks)
    }

    @Test
    fun testConstructor_EmptyText() {
        // Given
        val textoVacio = ""
        val fixUnks = false

        // When
        val request = TranslationRequest(textoVacio, fixUnks)

        // Then
        Assert.assertEquals("Debe aceptar texto vacío", textoVacio, request.text)
        Assert.assertFalse("fix_unks debe ser false", request.isFix_unks)
    }

    @Test
    fun testConstructor_NullText() {
        // Given
        val textoNull: String? = null
        val fixUnks = true

        // When
        val request = TranslationRequest(textoNull, fixUnks)

        // Then
        Assert.assertNull("Debe aceptar texto null", request.text)
        Assert.assertTrue("fix_unks debe ser true", request.isFix_unks)
    }
}