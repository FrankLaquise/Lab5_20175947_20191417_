package com.example.lab5_20175947_20191417;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lab5_20175947_20191417.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}