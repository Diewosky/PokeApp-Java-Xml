package com.example.pokeapixmljava.model;

import com.google.gson.annotations.SerializedName;

public class StatSlot {
    @SerializedName("base_stat")
    private int baseStat;

    @SerializedName("effort")
    private int effort;

    @SerializedName("stat")
    private Stat stat;

    // Constructors
    public StatSlot() {}

    public StatSlot(int baseStat, int effort, Stat stat) {
        this.baseStat = baseStat;
        this.effort = effort;
        this.stat = stat;
    }

    // Getters and Setters
    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    // Inner class for Stat
    public static class Stat {
        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private String url;

        // Constructors
        public Stat() {}

        public Stat(String name, String url) {
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

        public String getDisplayName() {
            if (name == null) return "";
            
            switch (name) {
                case "hp":
                    return "HP";
                case "attack":
                    return "Attack";
                case "defense":
                    return "Defense";
                case "special-attack":
                    return "Sp. Attack";
                case "special-defense":
                    return "Sp. Defense";
                case "speed":
                    return "Speed";
                default:
                    return name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        }
    }
} 