package com.example.pokeapixmljava.model;

import com.google.gson.annotations.SerializedName;

public class Sprites {
    @SerializedName("front_default")
    private String frontDefault;

    @SerializedName("front_shiny")
    private String frontShiny;

    @SerializedName("back_default")
    private String backDefault;

    @SerializedName("back_shiny")
    private String backShiny;

    // Constructors
    public Sprites() {}

    public Sprites(String frontDefault, String frontShiny, String backDefault, String backShiny) {
        this.frontDefault = frontDefault;
        this.frontShiny = frontShiny;
        this.backDefault = backDefault;
        this.backShiny = backShiny;
    }

    // Getters and Setters
    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
    }
} 