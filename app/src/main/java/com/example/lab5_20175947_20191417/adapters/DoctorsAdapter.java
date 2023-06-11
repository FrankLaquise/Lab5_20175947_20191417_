package com.example.lab5_20175947_20191417.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_20175947_20191417.InfoDoctor;
import com.example.lab5_20175947_20191417.R;
import com.example.lab5_20175947_20191417.entity.firebase.DoctorDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>{
    private List<DoctorDTO> listaDoctors;
    private Context context;
    private List<DoctorDTO> listaDoctorsFiltered;
    public DoctorsAdapter(List<DoctorDTO> dl,Context c){
        this.listaDoctors=dl;
        this.context=c;
    }

    public List<DoctorDTO> getListaDoctors() {
        return listaDoctors;
    }

    public void setListaDoctors(List<DoctorDTO> listaDoctors) {
        this.listaDoctors = listaDoctors;
        this.listaDoctorsFiltered= new ArrayList<>(listaDoctors);
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.doctor_rv,parent,false);
        return new DoctorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        DoctorDTO d= listaDoctorsFiltered.get(position);
        holder.doctor=d;
        ImageView imgV=holder.itemView.findViewById(R.id.img);
        Picasso.get().load(d.getFoto()).into(imgV);
        TextView tVName= holder.itemView.findViewById(R.id.tvName);
        tVName.setText("Dr. "+d.getFirst());
        TextView tVLocation= holder.itemView.findViewById(R.id.tvLocation);
        tVLocation.setText(d.getCountry()+" - "+d.getState()+" - "+d.getCity());

        Button btnInfo= holder.itemView.findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(v->{
            Intent in= new Intent(context, InfoDoctor.class);
            in.putExtra("doctor", d);
            context.startActivity(in);
        });
    }

    @Override
    public int getItemCount() {
        return listaDoctorsFiltered.size();
    }

    public void filterDoctorsByName(String searchQuery){
        listaDoctorsFiltered= new ArrayList<>();
        if(TextUtils.isEmpty(searchQuery)){
            listaDoctorsFiltered.addAll(listaDoctors);
        }else{
            for(DoctorDTO d: listaDoctors){
                if(d.getFirst().toLowerCase().contains(searchQuery.toLowerCase())){
                    listaDoctorsFiltered.add(d);
                }
            }
        }
        notifyDataSetChanged();
    }
    public class DoctorViewHolder extends RecyclerView.ViewHolder{
        DoctorDTO doctor;
        public DoctorViewHolder(@NonNull View v){
            super(v);
        }
    }
}
