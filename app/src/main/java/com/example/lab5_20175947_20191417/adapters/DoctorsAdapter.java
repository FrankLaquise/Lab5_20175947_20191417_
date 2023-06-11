package com.example.lab5_20175947_20191417.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_20175947_20191417.R;
import com.example.lab5_20175947_20191417.entity.Doctor;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>{
    private List<Doctor> listaDoctors;
    private Context context;
    public DoctorsAdapter(List<Doctor> dl,Context c){
        this.listaDoctors=dl;
        this.context=c;
    }
    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.doctor_rv,parent,false);
        return new DoctorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor d= listaDoctors.get(position);
        holder.doctor=d;
        ImageView imgV=holder.itemView.findViewById(R.id.img);
        String url="https://img.freepik.com/fotos-premium/doctor-sonriente-joven-negro-encuentra-fondo-blanco-foto-alta-calidad_209729-1428.jpg?w=360";
        Picasso.get().load(url).into(imgV);
    }

    @Override
    public int getItemCount() {
        return listaDoctors.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder{
        Doctor doctor;
        public DoctorViewHolder(@NonNull View v){
            super(v);
        }
    }
}
