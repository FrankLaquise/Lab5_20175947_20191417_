package com.example.lab5_20175947_20191417;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lab5_20175947_20191417.databinding.ActivityListadoBinding;

import de.hdodenhof.circleimageview.CircleImageView;

public class Listado extends AppCompatActivity {
    ActivityListadoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityListadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnPerfilmage.setImageResource(R.drawable.ic_launcher_background);
    }
}