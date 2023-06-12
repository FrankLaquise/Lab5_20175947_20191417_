package com.example.lab5_20175947_20191417;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText nombre , contraseña  , correo , telefono ;

    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombre =findViewById(R.id.inputNameR);
        contraseña=findViewById(R.id.inputPasswordR);
        correo = findViewById(R.id.inputEmailR);
        telefono=findViewById(R.id.inputTelefonoR);
        signupButton=findViewById(R.id.btnregisterR);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String nameS = nombre.getText().toString();
                String contraseñaS = contraseña.getText().toString();
                String correoS = correo.getText().toString();
                String telefonoS = telefono.getText().toString();

                HelperClass helperClass = new HelperClass(nameS,contraseñaS,correoS,telefonoS);
                reference.push().setValue(helperClass)
                        .addOnSuccessListener(aVoid->{
                            Toast.makeText(getApplicationContext(), "Se almacenó exitosamente!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e->{
                            Toast.makeText(getApplicationContext(), "Error al guardar en firebase!", Toast.LENGTH_SHORT).show();

                        });

                Toast.makeText(RegisterActivity.this, "Registrado exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}