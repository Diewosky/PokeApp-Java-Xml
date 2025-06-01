package com.example.pokeapixmljava.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonListResponse {
    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;

    @SerializedName("results")
    private List<PokemonListItem> results;

    // Constructors
    public PokemonListResponse() {}

    public PokemonListResponse(int count, String next, String previous, List<PokemonListItem> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    // Getters and Setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<PokemonListItem> getResults() {
        return results;
    }

    public void setResults(List<PokemonListItem> results) {
        this.results = results;
    }

    // Inner class for individual Pokemon list items
    public static class PokemonListItem {
        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private String url;

        // Constructors
        public PokemonListItem() {}

        public PokemonListItem(String name, String url) {
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

        // Helper method to extract Pokemon ID from URL
        public int getId() {
            if (url == null || url.isEmpty()) {
                return 0;
            }
            
            // URL format: https://pokeapi.co/api/v2/pokemon/1/
            String[] parts = url.split("/");
            try {
                return Integer.parseInt(parts[parts.length - 1]);
            } catch (NumberFormatException e) {
                // If the last part is empty (due to trailing slash), try the second to last
                try {
                    return Integer.parseInt(parts[parts.length - 2]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    return 0;
                }
            }
        }

        public String getCapitalizedName() {
            if (name == null || name.isEmpty()) {
                return "";
            }
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
    }
} 