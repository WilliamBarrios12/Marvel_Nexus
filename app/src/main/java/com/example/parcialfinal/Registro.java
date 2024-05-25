package com.example.parcialfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {

    TextInputEditText regis_nombre;
    TextInputEditText regis_correo;
    TextInputEditText regis_contraseña;
    TextInputEditText regis_fecha;
    Button regis_trate;
    TextView iniciar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        regis_nombre = findViewById(R.id.regis_nombre);
        regis_correo = findViewById(R.id.regis_correo);
        regis_contraseña = findViewById(R.id.regis_contraseña);
        regis_fecha = findViewById(R.id.regis_fecha);
        regis_trate = findViewById(R.id.regis_trate);
        iniciar = findViewById(R.id.iniciar);

        mAuth = FirebaseAuth.getInstance();

        regis_trate.setOnClickListener(v -> validateRegister());
        iniciar.setOnClickListener(v ->{
            Intent intent = new Intent(Registro.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void validateRegister() {
        String name = regis_nombre.getText().toString().trim();
        String email = regis_correo.getText().toString().trim();
        String password = regis_contraseña.getText().toString().trim();
        String birthdate = regis_fecha.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || birthdate.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            registerUser(email, password);
        }
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Registro.this, "Registro fallido.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}