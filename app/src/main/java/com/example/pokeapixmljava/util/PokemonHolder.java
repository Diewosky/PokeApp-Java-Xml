package com.example.pokeapixmljava.util;

import com.example.pokeapixmljava.model.Pokemon;

/**
 * Simple singleton to hold Pokemon data for passing between activities
 * In a production app, you would implement Parcelable or use a more robust solution
 */
public class PokemonHolder {
    private static PokemonHolder instance;
    private Pokemon selectedPokemon;
    
    private PokemonHolder() {}
    
    public static PokemonHolder getInstance() {
        if (instance == null) {
            instance = new PokemonHolder();
        }
        return instance;
    }
    
    public void setSelectedPokemon(Pokemon pokemon) {
        this.selectedPokemon = pokemon;
    }
    
    public Pokemon getSelectedPokemon() {
        return selectedPokemon;
    }
    
    public void clear() {
        selectedPokemon = null;
    }
} 