package com.example.pokeapixmljava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pokeapixmljava.adapter.PokemonAdapter;
import com.example.pokeapixmljava.model.Pokemon;
import com.example.pokeapixmljava.model.PokemonListResponse;
import com.example.pokeapixmljava.repository.PokemonRepository;
import com.example.pokeapixmljava.util.PokemonHolder;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";
    private static final int ITEMS_PER_PAGE = 20;
    
    // UI Components
    private RecyclerView recyclerViewPokemon;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBarMain;
    private LinearLayout layoutError;
    private TextView tvErrorMessage;
    private Button btnRetry;
    
    // Data and Logic
    private PokemonAdapter pokemonAdapter;
    private PokemonRepository pokemonRepository;
    private int currentOffset = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        initializeViews();
        setupRecyclerView();
        setupSwipeRefresh();
        setupErrorHandling();
        
        // Initialize repository and load first page
        pokemonRepository = new PokemonRepository();
        loadPokemonList(true);
    }
    
    private void initializeViews() {
        recyclerViewPokemon = findViewById(R.id.recyclerViewPokemon);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progressBarMain = findViewById(R.id.progressBarMain);
        layoutError = findViewById(R.id.layoutError);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        btnRetry = findViewById(R.id.btnRetry);
    }
    
    private void setupRecyclerView() {
        pokemonAdapter = new PokemonAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPokemon.setLayoutManager(layoutManager);
        recyclerViewPokemon.setAdapter(pokemonAdapter);
        
        // Set up pagination
        recyclerViewPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && !isLastPage) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= ITEMS_PER_PAGE) {
                        loadPokemonList(false);
                    }
                }
            }
        });
        
        // Set up click listener for Pokemon details
        pokemonAdapter.setOnPokemonClickListener(pokemon -> {
            // Store Pokemon in holder and launch detail activity
            PokemonHolder.getInstance().setSelectedPokemon(pokemon);
            Intent intent = new Intent(MainActivity.this, PokemonDetailActivity.class);
            startActivity(intent);
        });
    }
    
    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            currentOffset = 0;
            isLastPage = false;
            loadPokemonList(true);
        });
        
        // Set refresh colors
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }
    
    private void setupErrorHandling() {
        btnRetry.setOnClickListener(v -> {
            hideError();
            loadPokemonList(currentOffset == 0);
        });
    }
    
    private void loadPokemonList(boolean isRefresh) {
        if (isLoading) return;
        
        isLoading = true;
        
        if (isRefresh) {
            currentOffset = 0;
            showMainLoading();
        }
        
        Log.d(TAG, "Loading Pokemon list with offset: " + currentOffset);
        
        pokemonRepository.getPokemonList(ITEMS_PER_PAGE, currentOffset, new PokemonRepository.PokemonListCallback() {
            @Override
            public void onSuccess(PokemonListResponse response) {
                isLoading = false;
                hideMainLoading();
                hideError();
                
                if (response.getResults() != null && !response.getResults().isEmpty()) {
                    if (isRefresh) {
                        pokemonAdapter.setPokemonList(response.getResults());
                    } else {
                        pokemonAdapter.addPokemonList(response.getResults());
                    }
                    
                    currentOffset += ITEMS_PER_PAGE;
                    
                    // Check if this is the last page
                    if (response.getNext() == null || response.getResults().size() < ITEMS_PER_PAGE) {
                        isLastPage = true;
                    }
                    
                    Log.d(TAG, "Successfully loaded " + response.getResults().size() + " Pokemon");
                } else {
                    if (currentOffset == 0) {
                        showError("No Pokemon found");
                    }
                }
            }
            
            @Override
            public void onError(String errorMessage) {
                isLoading = false;
                hideMainLoading();
                
                Log.e(TAG, "Error loading Pokemon list: " + errorMessage);
                
                if (currentOffset == 0) {
                    showError(errorMessage);
                } else {
                    // Show toast for pagination errors
                    Toast.makeText(MainActivity.this, "Error loading more Pokemon: " + errorMessage, 
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void showMainLoading() {
        progressBarMain.setVisibility(View.VISIBLE);
        recyclerViewPokemon.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }
    
    private void hideMainLoading() {
        progressBarMain.setVisibility(View.GONE);
        recyclerViewPokemon.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }
    
    private void showError(String errorMessage) {
        layoutError.setVisibility(View.VISIBLE);
        recyclerViewPokemon.setVisibility(View.GONE);
        progressBarMain.setVisibility(View.GONE);
        tvErrorMessage.setText(errorMessage);
        swipeRefreshLayout.setRefreshing(false);
    }
    
    private void hideError() {
        layoutError.setVisibility(View.GONE);
        recyclerViewPokemon.setVisibility(View.VISIBLE);
    }
}