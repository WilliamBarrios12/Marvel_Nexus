package com.example.parcialfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Fragments extends AppCompatActivity {

    FrameLayout home;
    BottomNavigationView btn_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmet_home);

        HomeFragment fragmentHome = new HomeFragment();
        SolicitarFragment fragmentSolicitar = new SolicitarFragment();
        ConfiguracionFragment fragmentConfiguracion = new ConfiguracionFragment();

        loadFragment(fragmentHome);
        home =findViewById(R.id.home);
        btn_navigation = findViewById(R.id.btn_navigation);

        btn_navigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_nav){
                    loadFragment(fragmentHome);
                    return true;
                } else if (item.getItemId() == R.id.solicitar_nav) {
                    loadFragment(fragmentSolicitar);
                    return true;
                } else if (item.getItemId() == R.id.configuracion_nav) {
                    loadFragment(fragmentConfiguracion);
                    return true;
                }
                return false;
            }
        });

    }
    public void loadFragment(Fragment fr){
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.replace(R.id.home, fr);
        tr.commit();
    }
}