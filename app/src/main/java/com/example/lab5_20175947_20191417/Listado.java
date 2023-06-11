package com.example.lab5_20175947_20191417;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.lab5_20175947_20191417.adapters.DoctorsAdapter;
import com.example.lab5_20175947_20191417.databinding.ActivityListadoBinding;
import com.example.lab5_20175947_20191417.entity.firebase.DoctorDTO;
import com.example.lab5_20175947_20191417.entity.randoentities.Doctor;
import com.example.lab5_20175947_20191417.retrofit.RandoMuser;
import com.example.lab5_20175947_20191417.retrofit.services.RandoMuserServices;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Listado extends AppCompatActivity {
    ActivityListadoBinding binding;
    private final String TAG="msg-info";
    FirebaseDatabase firebaseDatabase;
    DoctorsAdapter adapter;
    private FirebaseUser mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityListadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference= firebaseDatabase.getReference("doctores");

        binding.btnPerfilmage.setImageResource(R.mipmap.aitel);
        binding.btnAddDoctor.setOnClickListener(v->{
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.overlayView.setVisibility(View.VISIBLE);
            v.setEnabled(false);
            RandoMuserServices service= new Retrofit.Builder()
                    .baseUrl("https://randomuser.me")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RandoMuserServices.class);
            service.getRandonMuser().enqueue(new Callback<RandoMuser>() {
                @Override
                public void onResponse(Call<RandoMuser> call, Response<RandoMuser> response) {
                    v.setEnabled(true);
                    if(response.isSuccessful()){
                        RandoMuser r= response.body();
                        Doctor d=r.getResults().get(0);
                        DoctorDTO doctor= new DoctorDTO(d.getName().getTitle(),d.getName().getFirst(),d.getName().getLast(),
                                d.getLocation().getCountry(),d.getLocation().getState(),d.getLocation().getCity(),
                                d.getDob().getAge(),d.getEmail(),d.getCell(),d.getPicture().getLarge(),d.getNat(),d.getGender()
                        ,d.getLogin().getUsername());
                        databaseReference.push().setValue(doctor)
                                .addOnSuccessListener(aVoid->{
                                    Toast.makeText(getApplicationContext(), "Se almacenó exitosamente!", Toast.LENGTH_SHORT).show();
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.overlayView.setVisibility(View.GONE);
                                })
                                .addOnFailureListener(e->{
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.overlayView.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Error al guardar en firebase!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG,e.getMessage());
                                });
                    }else{
                        Log.d(TAG,"Error de la respuesta del servicio.");
                    }
                }
                @Override
                public void onFailure(Call<RandoMuser> call, Throwable t) {
                    binding.overlayView.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    v.setEnabled(true);
                    Log.d(TAG,"OnFailure, salio mal!!");
                    t.printStackTrace();
                }
            });
        });
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.progressBar.setProgress(50);
        binding.progressBar.setIndeterminate(true);
        binding.overlayView.setVisibility(View.VISIBLE);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.progressBar.setVisibility(View.GONE);
                binding.overlayView.setVisibility(View.GONE);
                List<DoctorDTO> doctores= new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    DoctorDTO doctorDTO= data.getValue(DoctorDTO.class);
                    doctores.add(doctorDTO);
                }
                adapter=new DoctorsAdapter(doctores,Listado.this);
                binding.rVDoctor.setAdapter(adapter);
                binding.rVDoctor.setLayoutManager(new LinearLayoutManager(Listado.this));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.overlayView.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                Log.d(TAG,"onCancelled Error Listener");
            }
        });
        databaseReference.addValueEventListener(doctorsListener);
        binding.inputFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String searchQuery= s.toString().trim();
                adapter.filterDoctorsByName(searchQuery);
            }
        });

        // FirebaseUser currentUser = mAuth.get;
    }
    ValueEventListener doctorsListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<DoctorDTO> doctores= new ArrayList<>();
            for(DataSnapshot data: snapshot.getChildren()){
                DoctorDTO doctorDTO= data.getValue(DoctorDTO.class);
                doctores.add(doctorDTO);
            }
            Integer startPosition= adapter.getListaDoctors().size();
            adapter.setListaDoctors(doctores);
            adapter.notifyItemRangeInserted(startPosition,1);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.d(TAG,"onCancelled Error Listener");
        }
    };
}