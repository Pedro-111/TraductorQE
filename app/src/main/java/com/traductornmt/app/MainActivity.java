package com.traductornmt.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.traductornmt.app.activity.TranslateActivity;
import com.traductornmt.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupClickListeners();
    }

    private void setupClickListeners() {
        // Botón principal de traducir
        binding.cvTranslateButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TranslateActivity.class);
            startActivity(intent);
        });

        // Navegación inferior
        binding.ivHome.setOnClickListener(v -> {
            // Ya estamos en home, no hacer nada
        });

        binding.ivTranslateNav.setOnClickListener(v -> {
            Intent intent = new Intent(this, TranslateActivity.class);
            startActivity(intent);
        });
        /*
        binding.ivFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}