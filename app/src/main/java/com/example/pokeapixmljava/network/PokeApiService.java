package com.example.pokeapixmljava.network;

import com.example.pokeapixmljava.model.Pokemon;
import com.example.pokeapixmljava.model.PokemonListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {
    
    /**
     * Get a list of Pokemon with pagination
     * @param limit Number of Pokemon to fetch (default: 20)
     * @param offset Starting point for pagination (default: 0)
     * @return Call object containing PokemonListResponse
     */
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    /**
     * Get detailed information about a specific Pokemon by ID
     * @param id Pokemon ID
     * @return Call object containing Pokemon details
     */
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemonById(@Path("id") int id);

    /**
     * Get detailed information about a specific Pokemon by name
     * @param name Pokemon name
     * @return Call object containing Pokemon details
     */
    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonByName(@Path("name") String name);
} 