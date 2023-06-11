package com.example.lab5_20175947_20191417;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.lab5_20175947_20191417.databinding.InfoDoctorBinding;
import com.example.lab5_20175947_20191417.entity.firebase.DoctorDTO;
import com.squareup.picasso.Picasso;

public class InfoDoctor extends AppCompatActivity {
    InfoDoctorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= InfoDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent in= getIntent();
        DoctorDTO d= (DoctorDTO) in.getSerializableExtra("doctor");
        binding.btnBack.setOnClickListener(v->{
            finish();
        });
        binding.tVNombre.setText("Dr. "+d.getFirst()+" "+d.getLast());
        binding.tvCorreo.setText(d.getEmail());
        binding.tvCorreo.setPaintFlags(binding.tvCorreo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvCosto.setText("S/ "+(3*Integer.parseInt(d.getAge())));
        binding.tvNacionalidad.setText(d.getNat());
        binding.tvGenero.setText(d.getGender());
        binding.tvTelefono.setText(d.getCell());
        binding.tvEdad.setText(d.getAge()+" años");
        binding.tvUbicacion.setText(d.getCountry()+" - "+d.getState()+" - "+d.getCity());
        binding.tvUsername.setText(d.getUsername());
        Picasso.get().load(d.getFoto()).into(binding.imagen);
        binding.btnAgendar.setOnClickListener(v->{
            Intent in2= new Intent(InfoDoctor.this,CitaAgendada.class);
            in2.putExtra("doctor",d);
            startActivity(in2);
            finish();
        });
    }
}