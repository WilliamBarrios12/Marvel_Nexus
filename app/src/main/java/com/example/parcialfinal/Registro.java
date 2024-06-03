package com.example.parcialfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;


public class Registro extends AppCompatActivity {

    TextInputEditText regis_nombre;
    TextInputEditText regis_correo;
    TextInputEditText regis_contraseña;
    TextInputEditText regis_fecha;
    Button regis_trate;
    TextView iniciar;



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
            registerUser(name,email,birthdate,password);
        }
    }

    public void registerUser(String nombre,String email, String birthdate,String password) {
        String url = "http://192.168.56.1:8888/android_mysql/insertar.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Registro.this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registro.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("email", email);
                params.put("birthdate", birthdate);
                params.put("pass", password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}