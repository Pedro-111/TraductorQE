package com.traductornmt.app.utils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestUtils {

    /**
     * Espera hasta que un elemento aparezca en pantalla
     * @param viewId ID del elemento a esperar
     * @param timeoutSeconds Tiempo máximo de espera en segundos
     */
    public static void waitForElement(int viewId, int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);

        while (System.currentTimeMillis() < endTime) {
            try {
                onView(withId(viewId)).check(matches(isDisplayed()));
                return; // Elemento encontrado
            } catch (Exception e) {
                // Elemento no encontrado aún, seguir esperando
                try {
                    Thread.sleep(1000); // Esperar 1 segundo antes de volver a intentar
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        // Último intento después del timeout
        onView(withId(viewId)).check(matches(isDisplayed()));
    }

    /**
     * Espera un tiempo específico
     * @param seconds Segundos a esperar
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}