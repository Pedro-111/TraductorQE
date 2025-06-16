package com.traductornmt.app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.traductornmt.app.activity.TranslationResultActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TranslationResultActivityAcceptanceTest {

    private Intent createIntentWithTranslationData() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, TranslationResultActivity.class);
        intent.putExtra("original_text", "kuchi");
        intent.putExtra("translated_text", "chancho");
        intent.putExtra("source_language", "Quechua Cajamarquino");
        intent.putExtra("target_language", "Español");
        return intent;
    }

    @Rule
    public ActivityScenarioRule<TranslationResultActivity> activityRule =
            new ActivityScenarioRule<>(createIntentWithTranslationData());

    @Test
    public void testUserCanSeeTranslationResult() {
        // Escenario: Usuario puede ver el resultado de la traducción

        // Verificar que se muestra la palabra original en el header
        onView(withId(R.id.tv_original_word))
                .check(matches(isDisplayed()))
                .check(matches(withText("kuchi")));

        // Verificar que se muestra la traducción
        onView(withId(R.id.tv_translation_result))
                .check(matches(isDisplayed()))
                .check(matches(withText("chancho")));

        // Verificar que el card de resultado está visible
        onView(withId(R.id.cv_translation_result))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUserCanAccessActionButtons() {
        // Escenario: Usuario puede ver los botones de acción

        // Verificar que el botón copiar está visible
        onView(withId(R.id.iv_copy))
                .check(matches(isDisplayed()));

        // Verificar que el botón compartir está visible
        onView(withId(R.id.iv_share))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUserCanCopyTranslation() {
        // Escenario: Usuario puede copiar la traducción

        // Hacer clic en el botón copiar
        onView(withId(R.id.iv_copy))
                .perform(click());

        // El botón debe ser clickeable
        onView(withId(R.id.iv_copy))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUserCanShareTranslation() {
        // Escenario: Usuario puede compartir la traducción

        // Hacer clic en el botón compartir
        onView(withId(R.id.iv_share))
                .perform(click());

        // El botón debe ser clickeable
        onView(withId(R.id.iv_share))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUserCanNavigateBack() {
        // Escenario: Usuario puede regresar usando el botón back

        // Verificar que el botón back está visible
        onView(withId(R.id.iv_back))
                .check(matches(isDisplayed()));

        // Hacer clic en el botón back
        onView(withId(R.id.iv_back))
                .perform(click());
    }

    @Test
    public void testBottomNavigationIsDisplayed() {
        // Escenario: Usuario puede ver la navegación inferior

        // Verificar que la navegación inferior está visible
        onView(withId(R.id.bottom_navigation))
                .check(matches(isDisplayed()));

        // Verificar botones de navegación
        onView(withId(R.id.iv_home))
                .check(matches(isDisplayed()));

        onView(withId(R.id.iv_translate_nav))
                .check(matches(isDisplayed()));

        onView(withId(R.id.iv_favorites))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUserCanNavigateToHome() {
        // Escenario: Usuario puede navegar al inicio

        onView(withId(R.id.iv_home))
                .perform(click());
    }

    @Test
    public void testUserCanNavigateToTranslate() {
        // Escenario: Usuario puede navegar a traducir

        onView(withId(R.id.iv_translate_nav))
                .perform(click());
    }

    @Test
    public void testUserCanNavigateToFavorites() {
        // Escenario: Usuario puede navegar a favoritos

        onView(withId(R.id.iv_favorites))
                .perform(click());
    }
}