package com.traductornmt.app.activity.diccionario;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.traductornmt.app.R;
import com.traductornmt.app.adapters.EntradaAdapter;
import com.traductornmt.app.databinding.ActivityDictionaryBinding;
import com.traductornmt.app.models.diccionario.DiccionarioDAO;
import com.traductornmt.app.models.diccionario.Entrada;
import com.traductornmt.app.models.diccionario.JSONManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {

    private ActivityDictionaryBinding binding;
    private DiccionarioDAO diccionarioDAO;
    private EntradaAdapter adapter;
    private List<Entrada> entradasCompletas;
    private boolean isQuechuaToSpanish = true; // true = Quechua->Español, false = Español->Quechua

    private static final String JSON_QUECHUA_ESPANOL = "entradas_quechua_espanol.json";
    private static final String JSON_ESPANOL_QUECHUA = "entradas_espanol_quechua.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDictionaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeComponents();
        setupClickListeners();
        setupSearch();
        loadInitialData();
    }

    private void initializeComponents() {
        diccionarioDAO = new DiccionarioDAO(this);
        diccionarioDAO.abrir();

        // Configurar RecyclerView
        adapter = new EntradaAdapter(new ArrayList<>());
        binding.rvEntradas.setLayoutManager(new LinearLayoutManager(this));
        binding.rvEntradas.setAdapter(adapter);

        // Configurar dirección inicial
        updateDirectionUI();
    }

    private void setupClickListeners() {
        // Botón para cambiar dirección
        binding.btnChangeDirection.setOnClickListener(v -> {
            isQuechuaToSpanish = !isQuechuaToSpanish;
            updateDirectionUI();

            // Limpiar búsqueda actual
            binding.etSearch.setText("");

            // Cargar datos del archivo correspondiente
            loadDataForCurrentDirection();
        });

        // Botón limpiar búsqueda
        binding.ivClearSearch.setOnClickListener(v -> {
            binding.etSearch.setText("");
        });

        // Navegación inferior
        binding.ivHome.setOnClickListener(v -> finish());

        binding.ivTranslateNav.setOnClickListener(v -> {
            // Implementar navegación a TranslateActivity si es necesario
            finish();
        });

        binding.ivDictionary.setOnClickListener(v -> {
            // Ya estamos en diccionario, no hacer nada
        });
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().trim();
                if (searchText.isEmpty()) {
                    binding.ivClearSearch.setVisibility(View.GONE);
                    showAllEntries();
                } else {
                    binding.ivClearSearch.setVisibility(View.VISIBLE);
                    performSearch(searchText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void loadInitialData() {
        loadDataForCurrentDirection();
    }
    private void loadDataForCurrentDirection() {
        String archivoJSON = isQuechuaToSpanish ? JSON_QUECHUA_ESPANOL : JSON_ESPANOL_QUECHUA;
        String tablaDestino = isQuechuaToSpanish ? "entradas_qe" : "entradas_eq";

        // Verificar si ya hay datos para esta dirección
        List<Entrada> entradasExistentes = diccionarioDAO.obtenerTodasDeTabla(tablaDestino);

        if (entradasExistentes.isEmpty()) {
            loadDataFromJSON(archivoJSON, tablaDestino);
        } else {
            entradasCompletas = entradasExistentes;
            showAllEntries();
        }
    }
    private void loadDataFromJSON(String nombreArchivo, String tabla) {
        try {
            binding.progressBar.setVisibility(View.VISIBLE);

            // Leer JSON desde assets
            String json = readJSONFromAssets(nombreArchivo);

            if (json == null || json.isEmpty()) {
                Toast.makeText(this, "No se encontró el archivo " + nombreArchivo,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Convertir JSON a objetos
            JSONManager jsonManager = new JSONManager();
            List<Entrada> entradas = jsonManager.jsonToEntradas(json);

            // Insertar en la tabla correspondiente
            int insertadas = diccionarioDAO.insertarEntradasEnTabla(entradas, tabla);

            if (insertadas > 0) {
                entradasCompletas = entradas;
                showAllEntries();
                String direccion = isQuechuaToSpanish ? "Quechua-Español" : "Español-Quechua";
                Toast.makeText(this, "Diccionario " + direccion + " cargado: " + insertadas + " entradas",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Error al cargar diccionario: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } finally {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private String readJSONFromAssets(String fileName) throws IOException {
        InputStream is = getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

    private void performSearch(String searchText) {
        if (searchText.isEmpty()) {
            showAllEntries();
            return;
        }

        String tablaDestino = isQuechuaToSpanish ? "entradas_qe" : "entradas_eq";
        List<Entrada> resultados = diccionarioDAO.buscarPorPalabraEnTabla(searchText, tablaDestino);

        adapter.updateData(resultados);
        updateResultsCount(resultados.size());
    }
    private void showAllEntries() {
        if (entradasCompletas == null) {
            String tablaDestino = isQuechuaToSpanish ? "entradas_qe" : "entradas_eq";
            entradasCompletas = diccionarioDAO.obtenerTodasDeTabla(tablaDestino);
        }
        adapter.updateData(entradasCompletas);
        updateResultsCount(entradasCompletas.size());
    }


    private void updateDirectionUI() {
        if (isQuechuaToSpanish) {
            binding.tvFromLanguage.setText("Quechua");
            binding.tvToLanguage.setText("Español");
            binding.etSearch.setHint("Buscar en quechua...");
        } else {
            binding.tvFromLanguage.setText("Español");
            binding.tvToLanguage.setText("Quechua");
            binding.etSearch.setHint("Buscar en español...");
        }
    }

    private void updateResultsCount(int count) {
        if (count == 0) {
            binding.tvResultsCount.setText("Sin resultados");
        } else {
            binding.tvResultsCount.setText(count + " resultado" + (count == 1 ? "" : "s"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (diccionarioDAO != null) {
            diccionarioDAO.cerrar();
        }
        binding = null;
    }
}