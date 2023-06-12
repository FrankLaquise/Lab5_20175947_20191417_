package com.example.lab5_20175947_20191417;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab5_20175947_20191417.databinding.ActivityListadoBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView txtnombre;
    Button btnCerrarSesion,btnPerfilmage;



    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    ActivityListadoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        binding= ActivityListadoBinding.inflate(getLayoutInflater());



        //boton cerrar sesion
        btnCerrarSesion = findViewById(R.id.btnLogout);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent loginActivity = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(loginActivity);
                            PerfilActivity.this.finish();
                        }else{
                            Toast.makeText(PerfilActivity.this, "No se pudo cerrar sesion con google", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        btnPerfilmage=findViewById(R.id.btnPerfilmage2);

        binding.btnPerfilmage.setImageResource(R.mipmap.aitel);


        //nombre perfil
        txtnombre = findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        txtnombre.setText(currentUser.getDisplayName());

    }
}