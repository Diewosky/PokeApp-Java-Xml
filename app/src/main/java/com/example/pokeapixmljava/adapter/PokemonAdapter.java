package com.example.pokeapixmljava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.pokeapixmljava.R;
import com.example.pokeapixmljava.model.Pokemon;
import com.example.pokeapixmljava.model.PokemonListResponse;
import com.example.pokeapixmljava.repository.PokemonRepository;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    
    private final Context context;
    private final List<PokemonListResponse.PokemonListItem> pokemonList;
    private final List<Pokemon> pokemonDetails;
    private final PokemonRepository repository;
    private OnPokemonClickListener onPokemonClickListener;

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon);
    }

    public PokemonAdapter(Context context) {
        this.context = context;
        this.pokemonList = new ArrayList<>();
        this.pokemonDetails = new ArrayList<>();
        this.repository = new PokemonRepository();
    }

    public void setPokemonList(List<PokemonListResponse.PokemonListItem> newPokemonList) {
        pokemonList.clear();
        pokemonDetails.clear();
        pokemonList.addAll(newPokemonList);
        
        // Initialize pokemonDetails list with nulls
        for (int i = 0; i < newPokemonList.size(); i++) {
            pokemonDetails.add(null);
        }
        
        notifyDataSetChanged();
    }

    public void addPokemonList(List<PokemonListResponse.PokemonListItem> newPokemonList) {
        int startPosition = pokemonList.size();
        pokemonList.addAll(newPokemonList);
        
        // Add nulls for new items
        for (int i = 0; i < newPokemonList.size(); i++) {
            pokemonDetails.add(null);
        }
        
        notifyItemRangeInserted(startPosition, newPokemonList.size());
    }

    public void setOnPokemonClickListener(OnPokemonClickListener listener) {
        this.onPokemonClickListener = listener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonListResponse.PokemonListItem pokemonItem = pokemonList.get(position);
        Pokemon pokemonDetail = pokemonDetails.get(position);

        // Set basic info from list item
        holder.tvPokemonName.setText(pokemonItem.getCapitalizedName());
        holder.tvPokemonId.setText(String.format("#%03d", pokemonItem.getId()));

        if (pokemonDetail == null) {
            // Show loading and fetch details
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.tvPokemonType.setVisibility(View.GONE);
            holder.ivPokemonImage.setImageResource(R.drawable.ic_launcher_foreground);

            // Fetch Pokemon details
            repository.getPokemonById(pokemonItem.getId(), new PokemonRepository.PokemonCallback() {
                @Override
                public void onSuccess(Pokemon pokemon) {
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION && currentPosition < pokemonDetails.size()) {
                        // Update the details list
                        pokemonDetails.set(currentPosition, pokemon);
                        
                        // Update UI on main thread
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tvPokemonType.setVisibility(View.VISIBLE);
                        
                        // Set type
                        if (!pokemon.getTypes().isEmpty()) {
                            holder.tvPokemonType.setText(pokemon.getTypes().get(0).getType().getCapitalizedName());
                        }
                        
                        // Load image
                        String imageUrl = pokemon.getImageUrl();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(context)
                                    .load(imageUrl)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .error(R.drawable.ic_launcher_foreground)
                                    .into(holder.ivPokemonImage);
                        }
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tvPokemonType.setVisibility(View.VISIBLE);
                        holder.tvPokemonType.setText("Unknown");
                    }
                }
            });
        } else {
            // Details already loaded
            holder.progressBar.setVisibility(View.GONE);
            holder.tvPokemonType.setVisibility(View.VISIBLE);
            
            // Set type
            if (!pokemonDetail.getTypes().isEmpty()) {
                holder.tvPokemonType.setText(pokemonDetail.getTypes().get(0).getType().getCapitalizedName());
            }
            
            // Load image
            String imageUrl = pokemonDetail.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(holder.ivPokemonImage);
            }
        }

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (pokemonDetail != null && onPokemonClickListener != null) {
                onPokemonClickListener.onPokemonClick(pokemonDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPokemonImage;
        TextView tvPokemonName;
        TextView tvPokemonId;
        TextView tvPokemonType;
        ProgressBar progressBar;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPokemonImage = itemView.findViewById(R.id.ivPokemonImage);
            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);
            tvPokemonId = itemView.findViewById(R.id.tvPokemonId);
            tvPokemonType = itemView.findViewById(R.id.tvPokemonType);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
} 