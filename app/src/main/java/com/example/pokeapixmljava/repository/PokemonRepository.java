package com.example.pokeapixmljava.repository;

import android.util.Log;

import com.example.pokeapixmljava.model.Pokemon;
import com.example.pokeapixmljava.model.PokemonListResponse;
import com.example.pokeapixmljava.network.ApiClient;
import com.example.pokeapixmljava.network.PokeApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonRepository {
    private static final String TAG = "PokemonRepository";
    private static final int DEFAULT_LIMIT = 20;
    
    private final PokeApiService apiService;

    public PokemonRepository() {
        this.apiService = ApiClient.getPokeApiService();
    }

    /**
     * Interface for Pokemon list callbacks
     */
    public interface PokemonListCallback {
        void onSuccess(PokemonListResponse response);
        void onError(String errorMessage);
    }

    /**
     * Interface for individual Pokemon callbacks
     */
    public interface PokemonCallback {
        void onSuccess(Pokemon pokemon);
        void onError(String errorMessage);
    }

    /**
     * Fetches a list of Pokemon with pagination
     * @param offset Starting point for pagination
     * @param callback Callback to handle the response
     */
    public void getPokemonList(int offset, PokemonListCallback callback) {
        getPokemonList(DEFAULT_LIMIT, offset, callback);
    }

    /**
     * Fetches a list of Pokemon with custom limit and offset
     * @param limit Number of Pokemon to fetch
     * @param offset Starting point for pagination
     * @param callback Callback to handle the response
     */
    public void getPokemonList(int limit, int offset, PokemonListCallback callback) {
        Call<PokemonListResponse> call = apiService.getPokemonList(limit, offset);
        
        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Successfully fetched Pokemon list");
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Failed to fetch Pokemon list. Code: " + response.code();
                    Log.e(TAG, errorMsg);
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                String errorMsg = "Network error: " + t.getMessage();
                Log.e(TAG, errorMsg, t);
                callback.onError(errorMsg);
            }
        });
    }

    /**
     * Fetches detailed information about a specific Pokemon by ID
     * @param pokemonId Pokemon ID
     * @param callback Callback to handle the response
     */
    public void getPokemonById(int pokemonId, PokemonCallback callback) {
        Call<Pokemon> call = apiService.getPokemonById(pokemonId);
        
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Successfully fetched Pokemon: " + response.body().getName());
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Failed to fetch Pokemon details. Code: " + response.code();
                    Log.e(TAG, errorMsg);
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                String errorMsg = "Network error: " + t.getMessage();
                Log.e(TAG, errorMsg, t);
                callback.onError(errorMsg);
            }
        });
    }

    /**
     * Fetches detailed information about a specific Pokemon by name
     * @param pokemonName Pokemon name
     * @param callback Callback to handle the response
     */
    public void getPokemonByName(String pokemonName, PokemonCallback callback) {
        Call<Pokemon> call = apiService.getPokemonByName(pokemonName.toLowerCase());
        
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Successfully fetched Pokemon: " + response.body().getName());
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Failed to fetch Pokemon details. Code: " + response.code();
                    Log.e(TAG, errorMsg);
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                String errorMsg = "Network error: " + t.getMessage();
                Log.e(TAG, errorMsg, t);
                callback.onError(errorMsg);
            }
        });
    }
} 