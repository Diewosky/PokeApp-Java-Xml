package com.example.pokeapixmljava.model;

import com.google.gson.annotations.SerializedName;

public class AbilitySlot {
    @SerializedName("is_hidden")
    private boolean isHidden;

    @SerializedName("slot")
    private int slot;

    @SerializedName("ability")
    private Ability ability;

    // Constructors
    public AbilitySlot() {}

    public AbilitySlot(boolean isHidden, int slot, Ability ability) {
        this.isHidden = isHidden;
        this.slot = slot;
        this.ability = ability;
    }

    // Getters and Setters
    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    // Inner class for Ability
    public static class Ability {
        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private String url;

        // Constructors
        public Ability() {}

        public Ability(String name, String url) {
            this.name = name;
            this.url = url;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCapitalizedName() {
            if (name == null || name.isEmpty()) {
                return "";
            }
            return name.replace("-", " ")
                      .replace("_", " ")
                      .substring(0, 1).toUpperCase() + 
                   name.replace("-", " ")
                      .replace("_", " ")
                      .substring(1);
        }
    }
} 