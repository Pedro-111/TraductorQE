package com.traductornmt.app.retrofit;

import com.traductornmt.app.services.QuechuaApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static final String BASE_URL = "https://quechua-espaniol.duckdns.org/";
    private static Retrofit retrofit = null;
    private static QuechuaApiService apiService = null;

    public static QuechuaApiService getApiService() {
        if (apiService == null) {
            // Logging interceptor para debug
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(3, TimeUnit.MINUTES)    // Timeout de conexión: 3 minutos
                    .readTimeout(3, TimeUnit.MINUTES)       // Timeout de lectura: 3 minutos
                    .writeTimeout(3, TimeUnit.MINUTES)      // Timeout de escritura: 3 minutos
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(QuechuaApiService.class);
        }
        return apiService;
    }
}