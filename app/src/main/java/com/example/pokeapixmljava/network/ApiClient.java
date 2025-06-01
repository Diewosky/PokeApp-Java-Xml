package com.example.pokeapixmljava.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ApiClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private static final int TIMEOUT_SECONDS = 30;
    
    private static Retrofit retrofit = null;
    private static PokeApiService pokeApiService = null;

    /**
     * Creates and configures the Retrofit instance
     * @return Configured Retrofit instance
     */
    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Create HTTP logging interceptor for debugging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Configure OkHttpClient with timeouts and logging
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Build Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Provides a singleton instance of PokeApiService
     * @return PokeApiService instance
     */
    public static PokeApiService getPokeApiService() {
        if (pokeApiService == null) {
            pokeApiService = getRetrofitInstance().create(PokeApiService.class);
        }
        return pokeApiService;
    }

    /**
     * Clears the singleton instances (useful for testing)
     */
    public static void clearInstances() {
        retrofit = null;
        pokeApiService = null;
    }
} 