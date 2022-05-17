package com.courier.manager.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.courier.manager.R;
import com.courier.manager.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

//activitatea principala
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    //controler-ul de navigare
    private NavController navController;

    //Il folosim la legatura dintre activitate si layout/view
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setNavBar();
    }

    //initializam componentele necesare pentru navigation
    private void setNavBar() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_host);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomnavigationview, navController);
    }
}