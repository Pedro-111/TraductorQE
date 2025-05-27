package com.traductornmt.app.services;

import com.traductornmt.app.models.TranslationRequest;
import com.traductornmt.app.models.TranslationResponse;
import com.traductornmt.app.models.BatchTranslationRequest;
import com.traductornmt.app.models.BatchTranslationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface QuechuaApiService {

    @GET("health")
    Call<Void> healthCheck();

    @POST("translate")
    Call<TranslationResponse> translateText(@Body TranslationRequest request);

    @POST("translate/batch")
    Call<BatchTranslationResponse> translateBatch(@Body BatchTranslationRequest request);
}