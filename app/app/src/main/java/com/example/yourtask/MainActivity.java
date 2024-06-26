package com.example.yourtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.example.yourtask.model.ApiRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ApiRequest.setup();
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        //sharedPreferences.edit().clear().commit();

        if (sharedPreferences.getInt("id", 0) == 0)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }

        changeFragment(new HomepageFragment());

        bottomNavigationMenu = (BottomNavigationView)findViewById(R.id.bottom_navigation_bar);
        bottomNavigationMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);

                if (id == R.id.navigation_home)
                    changeFragment(new HomepageFragment());
                else if (id == R.id.navigation_profile)
                    changeFragment(new ProfileFragment());
                return false;
            }
        });
    }

    public boolean changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return true;
    }
}
