package com.traductornmt.app.activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.traductornmt.app.MainActivity;
import com.traductornmt.app.activity.diccionario.DictionaryActivity;
import com.traductornmt.app.databinding.ActivityTranslationResultBinding;

public class TranslationResultActivity extends AppCompatActivity {

    private ActivityTranslationResultBinding binding;
    private String originalText;
    private String translatedText;
    private String sourceLanguage;
    private String targetLanguage;
    private double processingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTranslationResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentData();
        setupViews();
        setupClickListeners();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        originalText = intent.getStringExtra("original_text");
        translatedText = intent.getStringExtra("translated_text");
        sourceLanguage = intent.getStringExtra("source_language");
        targetLanguage = intent.getStringExtra("target_language");
        processingTime = intent.getDoubleExtra("processing_time", 0.0);
    }

    private void setupViews() {
        // Mostrar el texto original en el header
        if (originalText != null) {
            binding.tvOriginalWord.setText(originalText);
        }

        // Mostrar la traducción
        if (translatedText != null) {
            binding.tvTranslationResult.setText(translatedText);
        }

        // Mostrar información adicional si tienes TextViews para ello
        if (sourceLanguage != null && targetLanguage != null) {
            // Si tienes un TextView para mostrar los idiomas:
            // binding.tvLanguageInfo.setText(sourceLanguage + " → " + targetLanguage);
        }

        // Mostrar tiempo de procesamiento si tienes un TextView para ello
        if (processingTime > 0) {
            // Si tienes un TextView para mostrar el tiempo:
            // binding.tvProcessingTime.setText(String.format("Procesado en %.2f segundos", processingTime));
        }
    }

    private void setupClickListeners() {
        // Botón de retroceso
        binding.ivBack.setOnClickListener(v -> finish());

        // Botón copiar
        binding.ivCopy.setOnClickListener(v -> copyToClipboard());

        // Botón compartir
        binding.ivShare.setOnClickListener(v -> shareTranslation());

        // Navegación inferior
        binding.ivHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.ivTranslateNav.setOnClickListener(v -> {
            Intent intent = new Intent(this, TranslateActivity.class);
            startActivity(intent);
        });
        /*
        binding.ivFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        });
        */
        binding.ivDictionary.setOnClickListener(v -> {
            Intent intent = new Intent(this, DictionaryActivity.class);
            startActivity(intent);
        });
    }

    private void copyToClipboard() {
        if (translatedText != null && !translatedText.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Traducción", translatedText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay texto para copiar", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareTranslation() {
        if (originalText != null && translatedText != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            StringBuilder shareText = new StringBuilder();
            shareText.append("Traducción Quechua-Español:\n");
            shareText.append(originalText).append(" → ").append(translatedText);

            if (processingTime > 0) {
                shareText.append(String.format("\n(Procesado en %.2f segundos)", processingTime));
            }

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Traducción Quechua");

            Intent chooser = Intent.createChooser(shareIntent, "Compartir traducción");
            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            } else {
                Toast.makeText(this, "No hay aplicaciones para compartir", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No hay traducción para compartir", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}