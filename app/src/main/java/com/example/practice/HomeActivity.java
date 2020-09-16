package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    BottomNavigationView bottomNavigationMenu;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth=FirebaseAuth.getInstance();
        bottomNavigationMenu=findViewById(R.id.bottom_navigation);
        bottomNavigationMenu.setOnNavigationItemSelectedListener(nav);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
    BottomNavigationView.OnNavigationItemSelectedListener nav=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.home_fragment:
                    navController= Navigation.findNavController(HomeActivity.this,R.id.home_nav_host);
                    navController.navigate(R.id.homeFragment);
                    break;
                case R.id.cart_fragment:
                    navController= Navigation.findNavController(HomeActivity.this,R.id.home_nav_host);
                    navController.navigate(R.id.cartFragment);
                    break;
                case R.id.profile_fragment:
                    navController= Navigation.findNavController(HomeActivity.this,R.id.home_nav_host);
                    navController.navigate(R.id.profileFragment);
                    break;
            }
            return true;
        }
    };

}