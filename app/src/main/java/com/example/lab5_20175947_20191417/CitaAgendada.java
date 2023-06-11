package com.example.lab5_20175947_20191417;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lab5_20175947_20191417.databinding.CitaAgendadaBinding;
import com.example.lab5_20175947_20191417.entity.firebase.DoctorDTO;

public class CitaAgendada extends AppCompatActivity {
    CitaAgendadaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= CitaAgendadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent in= getIntent();
        DoctorDTO d= (DoctorDTO) in.getSerializableExtra("doctor");
        binding.tvCita.setText("Se agendo su cita con el Dr. "+d.getFirst()+" "+d.getLast());
        binding.btnBack.setOnClickListener(v->{
            finish();
        });
    }
}