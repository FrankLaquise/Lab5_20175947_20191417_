package com.example.lab5_20175947_20191417;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.lab5_20175947_20191417.adapters.DoctorsAdapter;
import com.example.lab5_20175947_20191417.databinding.ActivityListadoBinding;
import com.example.lab5_20175947_20191417.entity.Doctor;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Listado extends AppCompatActivity {
    ActivityListadoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityListadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnPerfilmage.setImageResource(R.drawable.ic_launcher_background);

        List<Doctor> list= new ArrayList<>();
        list.add(new Doctor());list.add(new Doctor());list.add(new Doctor());list.add(new Doctor());

        DoctorsAdapter dA= new DoctorsAdapter(list,Listado.this);
        binding.rVDoctor.setAdapter(dA);
        binding.rVDoctor.setLayoutManager(new LinearLayoutManager(Listado.this));
    }
}