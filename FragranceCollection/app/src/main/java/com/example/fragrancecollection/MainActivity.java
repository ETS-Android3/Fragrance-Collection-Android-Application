package com.example.fragrancecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    static Context context;
    static String currentLoggedUserEmail;
    static String currentLoggedUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        currentLoggedUserEmail = getIntent().getExtras().getString("email");
        currentLoggedUserPassword = getIntent().getExtras().getString("password");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragranceListFragment()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch(item.getItemId()) {
                    case R.id.navigation_fragrance_list:
                        selectedFragment = new FragranceListFragment();
                        break;
                    case R.id.navigation_add:
                        selectedFragment = new AddFragranceFragment();
                        break;
                    case R.id.navigation_account:
                        selectedFragment = new AccountFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

                return true;
            }
        });
    }
}