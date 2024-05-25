package com.example.parcialfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextInputEditText correo;
    TextInputEditText contraseña;
    Button iniciar_sesion;
    TextView registrate;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.correo);
        contraseña = findViewById(R.id.contraseña);
        iniciar_sesion = findViewById(R.id.iniciar_sesion);
        registrate = findViewById(R.id.registrate);

        mAuth = FirebaseAuth.getInstance();

        iniciar_sesion.setOnClickListener(v -> validateLogin());
        registrate.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, Registro.class);
            startActivity(intent);
        });
    }

    private void validateLogin() {
        String email = correo.getText().toString().trim();
        String password = contraseña.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            loginUser(email, password);
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUserToPreferences(user.getEmail());

                        Intent intent = new Intent(MainActivity.this, Fragments.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Autenticación fallida.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToPreferences(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }
}