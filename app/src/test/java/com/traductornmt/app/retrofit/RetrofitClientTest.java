package com.traductornmt.app.retrofit;

import com.traductornmt.app.services.QuechuaApiService;
import org.junit.Test;
import static org.junit.Assert.*;

public class RetrofitClientTest {

    @Test
    public void testGetApiService_NotNull() {
        // When
        QuechuaApiService apiService = RetrofitClient.getApiService();

        // Then
        assertNotNull("El servicio API no debe ser null", apiService);
    }

    @Test
    public void testGetApiService_Singleton() {
        // When
        QuechuaApiService apiService1 = RetrofitClient.getApiService();
        QuechuaApiService apiService2 = RetrofitClient.getApiService();

        // Then
        assertSame("Debe retornar la misma instancia (patrón Singleton)",
                apiService1, apiService2);
    }
}