package com.example.pokeapixmljava.model;

import com.google.gson.annotations.SerializedName;

public class TypeSlot {
    @SerializedName("slot")
    private int slot;

    @SerializedName("type")
    private Type type;

    // Constructors
    public TypeSlot() {}

    public TypeSlot(int slot, Type type) {
        this.slot = slot;
        this.type = type;
    }

    // Getters and Setters
    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    // Inner class for Type
    public static class Type {
        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private String url;

        // Constructors
        public Type() {}

        public Type(String name, String url) {
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
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
    }
} 