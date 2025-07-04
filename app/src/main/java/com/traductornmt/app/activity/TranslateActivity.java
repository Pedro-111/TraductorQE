package com.traductornmt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.traductornmt.app.MainActivity;
import com.traductornmt.app.databinding.ActivityTranslateBinding;
import com.traductornmt.app.models.TranslationRequest;
import com.traductornmt.app.models.TranslationResponse;
import com.traductornmt.app.retrofit.RetrofitClient;
import com.traductornmt.app.services.QuechuaApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslateActivity extends AppCompatActivity {

    private ActivityTranslateBinding binding;
    private QuechuaApiService apiService;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTranslateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = RetrofitClient.getApiService();
        setupViews();
        setupClickListeners();
    }

    private void setupViews() {
        // Configurar hint del EditText
        if (binding != null && binding.etInputText != null) {
            binding.etInputText.setHint("Ingresa texto a traducir");
        }
    }

    private void setupClickListeners() {
        if (binding == null) return;

        // Cuando el usuario presiona Enter en el EditText
        if (binding.etInputText != null) {
            binding.etInputText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_GO ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    translateText();
                    return true;
                }
                return false;
            });
        }

        // Click en el botón de traducir
        if (binding.btnTranslate != null) {
            binding.btnTranslate.setOnClickListener(v -> translateText());
        }

        // Navegación
        if (binding.ivBack != null) {
            binding.ivBack.setOnClickListener(v -> finish());
        }

        if (binding.ivHome != null) {
            binding.ivHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }

        if (binding.ivTranslateNav != null) {
            binding.ivTranslateNav.setOnClickListener(v -> {
                // Ya estamos en translate, no hacer nada o mostrar mensaje
            });
        }

        if (binding.ivFavorites != null) {
            binding.ivFavorites.setOnClickListener(v -> {
                // Agregar navegación a favoritos cuando esté implementado
                Toast.makeText(this, "Funcionalidad de favoritos próximamente", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void translateText() {
        // Verificar que binding y componentes no sean null
        if (binding == null || binding.etInputText == null) {
            return;
        }

        String inputText = binding.etInputText.getText().toString().trim();

        if (TextUtils.isEmpty(inputText)) {
            binding.etInputText.setError("Ingresa texto para traducir");
            binding.etInputText.requestFocus();
            return;
        }

        if (isLoading) {
            return; // Evitar múltiples llamadas simultáneas
        }

        setLoadingState(true);

        TranslationRequest request = new TranslationRequest(inputText, true);

        Call<TranslationResponse> call = apiService.translateText(request);
        call.enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                // Verificar que la Activity y el binding aún existen
                if (isFinishing() || isDestroyed() || binding == null) {
                    return;
                }

                setLoadingState(false);

                if (response.isSuccessful() && response.body() != null) {
                    TranslationResponse translationResponse = response.body();

                    if (translationResponse.isSuccess()) {
                        // Navegar a la pantalla de resultados
                        Intent intent = new Intent(TranslateActivity.this, TranslationResultActivity.class);
                        intent.putExtra("original_text", translationResponse.getOriginal_text());
                        intent.putExtra("translated_text", translationResponse.getTranslated_text());
                        intent.putExtra("source_language", translationResponse.getSource_language());
                        intent.putExtra("target_language", translationResponse.getTarget_language());
                        intent.putExtra("processing_time", translationResponse.getProcessing_time());
                        startActivity(intent);

                        // Limpiar el campo de texto para nueva traducción
                        if (binding != null && binding.etInputText != null) {
                            binding.etInputText.setText("");
                        }
                    } else {
                        showError("Error en la traducción: " + translationResponse.getStatus());
                    }
                } else {
                    if (response.code() == 404) {
                        showError("Endpoint no encontrado. Verifica la URL de la API.");
                    } else if (response.code() == 500) {
                        showError("Error interno del servidor. Intenta nuevamente.");
                    } else {
                        showError("Error del servidor. Código: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable t) {
                // Verificar que la Activity y el binding aún existen
                if (isFinishing() || isDestroyed() || binding == null) {
                    return;
                }

                setLoadingState(false);

                if (t instanceof java.net.SocketTimeoutException) {
                    showError("Tiempo de espera agotado. La traducción está tomando mucho tiempo.");
                } else if (t instanceof java.net.ConnectException) {
                    showError("No se puede conectar al servidor. Verifica tu conexión a internet.");
                } else {
                    showError("Error de conexión: " + t.getMessage());
                }
            }
        });
    }

    private void setLoadingState(boolean loading) {
        // Verificar que la Activity no se haya destruido y que binding no sea null
        if (isFinishing() || isDestroyed() || binding == null) {
            return;
        }

        isLoading = loading;

        // Verificar cada componente individualmente antes de usarlo
        if (binding.etInputText != null) {
            binding.etInputText.setEnabled(!loading);
            if (loading) {
                binding.etInputText.setHint("Traduciendo...");
            } else {
                binding.etInputText.setHint("Ingresa texto a traducir");
            }
        }

        if (binding.btnTranslate != null) {
            binding.btnTranslate.setEnabled(!loading);
            if (loading) {
                binding.btnTranslate.setText("Traduciendo...");
            } else {
                binding.btnTranslate.setText("Traducir");
            }
        }

        if (loading) {
            Toast.makeText(this, "Traduciendo...", Toast.LENGTH_SHORT).show();
        }
    }

    private void showError(String message) {
        // Verificar que la Activity no se haya destruido antes de mostrar el Toast
        if (isFinishing() || isDestroyed()) {
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar referencia al binding
        binding = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Opcional: cancelar llamadas en progreso si es necesario
        // call.cancel();
    }
}