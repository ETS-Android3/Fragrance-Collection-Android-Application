package com.example.fragrancecollection.activitiesandfragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fragrancecollection.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    public static String currentLoggedUserEmail;
    public static String currentLoggedUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        String intentSourceActivity = getIntent().getExtras().getString("source");
        currentLoggedUserEmail = getIntent().getExtras().getString("email");
        currentLoggedUserPassword = getIntent().getExtras().getString("password");

        if (intentSourceActivity.equals("editUserActivity")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new AccountFragment()).commit();
        }

        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragranceListFragment()).commit();
        }

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
                    case R.id.navigation_discover:
                        selectedFragment = new UserListFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

                return true;
            }
        });
    }
}