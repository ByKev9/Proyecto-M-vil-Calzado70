package com.cdp.prueba2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cdp.prueba2.databinding.ActivityMainBinding;
import androidx.appcompat.widget.Toolbar;

public class DrawerLayoout extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.WelcomtextView.);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle.DelegateProvider(this.binding.logoImageView,binding.WelcomtextView.toolbar);
    }
}