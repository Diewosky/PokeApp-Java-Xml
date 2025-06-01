package com.example.pokeapixmljava.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pokemon {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("height")
    private int height;

    @SerializedName("weight")
    private int weight;

    @SerializedName("base_experience")
    private int baseExperience;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("types")
    private List<TypeSlot> types;

    @SerializedName("stats")
    private List<StatSlot> stats;

    @SerializedName("abilities")
    private List<AbilitySlot> abilities;

    // Constructors
    public Pokemon() {}

    public Pokemon(int id, String name, int height, int weight, int baseExperience, 
                   Sprites sprites, List<TypeSlot> types, List<StatSlot> stats, 
                   List<AbilitySlot> abilities) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.baseExperience = baseExperience;
        this.sprites = sprites;
        this.types = types;
        this.stats = stats;
        this.abilities = abilities;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<TypeSlot> getTypes() {
        return types;
    }

    public void setTypes(List<TypeSlot> types) {
        this.types = types;
    }

    public List<StatSlot> getStats() {
        return stats;
    }

    public void setStats(List<StatSlot> stats) {
        this.stats = stats;
    }

    public List<AbilitySlot> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilitySlot> abilities) {
        this.abilities = abilities;
    }

    // Helper methods
    public String getCapitalizedName() {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getFormattedHeight() {
        return String.format("%.1f m", height / 10.0);
    }

    public String getFormattedWeight() {
        return String.format("%.1f kg", weight / 10.0);
    }

    public String getImageUrl() {
        return sprites != null ? sprites.getFrontDefault() : null;
    }

    public String getPrimaryType() {
        if (types != null && !types.isEmpty()) {
            return types.get(0).getType().getName();
        }
        return "unknown";
    }
} 