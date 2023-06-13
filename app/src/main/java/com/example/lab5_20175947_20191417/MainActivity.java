package com.example.lab5_20175947_20191417;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab5_20175947_20191417.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;
import java.util.Objects;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    ActivityMainBinding binding;
    private GoogleSignInClient mGoogleSignInClient;

//////
    TextView lblCrearCuenta;
    EditText txtInputEmail, txtInputPassword;
    Button btnLogin, btnGoogle;
    int RC_SIGN_IN =1;
    String TAG ="GoogleSignInLoginActivity";
    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        binding= ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        binding.ingresar.setOnClickListener(v->{
//            Intent in= new Intent(MainActivity.this,Listado.class);
//            startActivity(in);
//        });

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);

        txtInputEmail = findViewById(R.id.inputEmail);
        txtInputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnlogin);
        lblCrearCuenta = findViewById(R.id.txtNotieneCuenta);
        btnGoogle = findViewById(R.id.btnGoogle);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername()|!validatePass()){

                }else {
                    checkUser();
                }
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        TextView textView =findViewById(R.id.txtNotieneCuenta);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



        mProgressBar = new ProgressDialog(MainActivity.this);
// Configurar Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.

        mGoogleSignInClient  = GoogleSignIn.getClient(this, gso);
        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Resultado devuelto al iniciar el Intent de GoogleSignInApi.getSignInIntent (...);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In fallido, actualizar GUI
                    Log.w(TAG, "Google sign in failed", e);
                }
            }else{
                Log.d(TAG, "Error, login no exitoso:" + task.getException().toString());
                Toast.makeText(this, "Ocurrio un error. "+task.getException().getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
//Iniciar DASHBOARD u otra actividad luego del SigIn Exitoso
                            Intent dashboardActivity = new Intent(MainActivity.this, Listado.class);
                            startActivity(dashboardActivity);
                            MainActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }





    public void verificarCredenciales(){
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();
        if(email.isEmpty() || !email.contains("@")){
            showError(txtInputEmail, "Email no valido");
        }else if(password.isEmpty()|| password.length()<7){
            showError(txtInputPassword, "Password invalida");
        }else{
            //Mostrar ProgressBar
            mProgressBar.setTitle("Login");
            mProgressBar.setMessage("Iniciando sesión, espere un momento..");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();
            //Registrar usuario
            //Exitoso -> Mostrar toast
            //redireccionar - intent a login
            Intent intent = new Intent(MainActivity.this, Listado.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //ocultar progressBar
            mProgressBar.dismiss();
        }
    }

    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
    @Override
    protected void onStart() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){ //si no es null el usuario ya esta logueado
            //mover al usuario al dashboard
            Intent dashboardActivity = new Intent(MainActivity.this, Listado.class);
            startActivity(dashboardActivity);
        }
        super.onStart();
    }


    ///realtime
    public Boolean validateUsername(){
        String val = txtInputEmail.getText().toString();
        if(val.isEmpty()){
            txtInputEmail.setError("Usuario vacio");
            return false   ;

        }else{
            txtInputEmail.setError(null);
            return true;
        }
    }
    public Boolean validatePass(){
        String val = txtInputPassword.getText().toString();
        if(val.isEmpty()){
            txtInputPassword.setError("Contraseña vacia");
            return false   ;

        }else{
            txtInputPassword.setError(null);
            return true;
        }
    }


    public void checkUser() {
        String userCorreo = txtInputEmail.getText().toString().trim();
        String userPass = txtInputPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("correo").equalTo(userCorreo);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    txtInputEmail.setError(null);
                    Log.d(TAG, ""+snapshot.getChildren());
                    HelperClass user=null;
                    for(DataSnapshot data:snapshot.getChildren()){
                         user= data.getValue(HelperClass.class);
                    }
                    if (user!=null) {
                        txtInputEmail.setError(null);
                        Intent intent = new Intent(MainActivity.this, Listado.class);
                        intent.putExtra("nombre",user.getNombre());
                        startActivity(intent);
                        finish(); // Terminar la ejecución del método después de iniciar sesión exitosamente
                    }

                    txtInputPassword.requestFocus();
                } else {
                    txtInputEmail.setError("Usuario no existe");
                    txtInputEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error de cancelación de la consulta si es necesario
            }
        });
    }

}