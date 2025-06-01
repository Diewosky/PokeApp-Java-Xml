package com.example.pokeapixmljava;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.pokeapixmljava.model.AbilitySlot;
import com.example.pokeapixmljava.model.Pokemon;
import com.example.pokeapixmljava.model.StatSlot;
import com.example.pokeapixmljava.model.TypeSlot;
import com.example.pokeapixmljava.util.PokemonHolder;

import java.util.HashMap;
import java.util.Map;

public class PokemonDetailActivity extends AppCompatActivity {
    
    private ImageView ivPokemonDetailImage;
    private TextView tvPokemonDetailName;
    private TextView tvPokemonDetailId;
    private LinearLayout layoutTypes;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvBaseExperience;
    private LinearLayout layoutStats;
    private LinearLayout layoutAbilities;
    
    private Pokemon pokemon;
    
    // Type colors map
    private static final Map<String, String> TYPE_COLORS = new HashMap<>();
    static {
        TYPE_COLORS.put("normal", "#A8A878");
        TYPE_COLORS.put("fire", "#F08030");
        TYPE_COLORS.put("water", "#6890F0");
        TYPE_COLORS.put("electric", "#F8D030");
        TYPE_COLORS.put("grass", "#78C850");
        TYPE_COLORS.put("ice", "#98D8D8");
        TYPE_COLORS.put("fighting", "#C03028");
        TYPE_COLORS.put("poison", "#A040A0");
        TYPE_COLORS.put("ground", "#E0C068");
        TYPE_COLORS.put("flying", "#A890F0");
        TYPE_COLORS.put("psychic", "#F85888");
        TYPE_COLORS.put("bug", "#A8B820");
        TYPE_COLORS.put("rock", "#B8A038");
        TYPE_COLORS.put("ghost", "#705898");
        TYPE_COLORS.put("dragon", "#7038F8");
        TYPE_COLORS.put("dark", "#705848");
        TYPE_COLORS.put("steel", "#B8B8D0");
        TYPE_COLORS.put("fairy", "#EE99AC");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        
        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pokemon Details");
        }
        
        initializeViews();
        
        // Get Pokemon from holder
        pokemon = PokemonHolder.getInstance().getSelectedPokemon();
        if (pokemon != null) {
            displayPokemonDetails();
        } else {
            // If no Pokemon found, finish activity
            finish();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear the holder when leaving the activity
        PokemonHolder.getInstance().clear();
    }
    
    private void initializeViews() {
        ivPokemonDetailImage = findViewById(R.id.ivPokemonDetailImage);
        tvPokemonDetailName = findViewById(R.id.tvPokemonDetailName);
        tvPokemonDetailId = findViewById(R.id.tvPokemonDetailId);
        layoutTypes = findViewById(R.id.layoutTypes);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvBaseExperience = findViewById(R.id.tvBaseExperience);
        layoutStats = findViewById(R.id.layoutStats);
        layoutAbilities = findViewById(R.id.layoutAbilities);
    }
    
    private void displayPokemonDetails() {
        if (pokemon == null) return;
        
        // Set basic info
        tvPokemonDetailName.setText(pokemon.getCapitalizedName());
        tvPokemonDetailId.setText(String.format("#%03d", pokemon.getId()));
        tvHeight.setText(pokemon.getFormattedHeight());
        tvWeight.setText(pokemon.getFormattedWeight());
        tvBaseExperience.setText(String.valueOf(pokemon.getBaseExperience()));
        
        // Load image
        String imageUrl = pokemon.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivPokemonDetailImage);
        }
        
        // Display types
        displayTypes();
        
        // Display stats
        displayStats();
        
        // Display abilities
        displayAbilities();
    }
    
    private void displayTypes() {
        layoutTypes.removeAllViews();
        
        if (pokemon.getTypes() != null) {
            for (TypeSlot typeSlot : pokemon.getTypes()) {
                TextView typeView = createTypeView(typeSlot.getType().getCapitalizedName(), 
                                                 typeSlot.getType().getName());
                layoutTypes.addView(typeView);
            }
        }
    }
    
    private TextView createTypeView(String typeName, String typeKey) {
        TextView typeView = new TextView(this);
        typeView.setText(typeName);
        typeView.setTextColor(Color.WHITE);
        typeView.setTextSize(14);
        typeView.setPadding(24, 12, 24, 12);
        
        // Set background color based on type
        String colorHex = TYPE_COLORS.get(typeKey.toLowerCase());
        if (colorHex != null) {
            typeView.setBackgroundColor(Color.parseColor(colorHex));
        } else {
            typeView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        }
        
        // Set margins
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);
        typeView.setLayoutParams(params);
        
        // Round corners
        typeView.setBackground(ContextCompat.getDrawable(this, R.drawable.type_background));
        if (colorHex != null) {
            typeView.getBackground().setTint(Color.parseColor(colorHex));
        }
        
        return typeView;
    }
    
    private void displayStats() {
        layoutStats.removeAllViews();
        
        if (pokemon.getStats() != null) {
            for (StatSlot statSlot : pokemon.getStats()) {
                View statView = createStatView(statSlot.getStat().getDisplayName(), 
                                             statSlot.getBaseStat());
                layoutStats.addView(statView);
            }
        }
    }
    
    private View createStatView(String statName, int statValue) {
        View statView = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_2, null);
        
        TextView text1 = statView.findViewById(android.R.id.text1);
        TextView text2 = statView.findViewById(android.R.id.text2);
        
        text1.setText(statName);
        text1.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        text1.setTextSize(16);
        
        text2.setText(String.valueOf(statValue));
        text2.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        text2.setTextSize(14);
        
        // Add some padding
        statView.setPadding(0, 8, 0, 8);
        
        return statView;
    }
    
    private void displayAbilities() {
        layoutAbilities.removeAllViews();
        
        if (pokemon.getAbilities() != null) {
            for (AbilitySlot abilitySlot : pokemon.getAbilities()) {
                View abilityView = createAbilityView(abilitySlot.getAbility().getCapitalizedName(), 
                                                   abilitySlot.isHidden());
                layoutAbilities.addView(abilityView);
            }
        }
    }
    
    private View createAbilityView(String abilityName, boolean isHidden) {
        TextView abilityView = new TextView(this);
        
        String displayText = abilityName;
        if (isHidden) {
            displayText += " (Hidden)";
        }
        
        abilityView.setText(displayText);
        abilityView.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        abilityView.setTextSize(16);
        abilityView.setPadding(0, 8, 0, 8);
        
        if (isHidden) {
            abilityView.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
            abilityView.setTypeface(null, android.graphics.Typeface.ITALIC);
        }
        
        return abilityView;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 