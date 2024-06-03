package com.example.parcialfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    TextInputEditText correo;
    TextInputEditText contraseña;
    Button iniciar_sesion;
    TextView registrate;

    SharedPreferences sharedPreferennces;
    SharedPreferences.Editor editor;
    public static final String dataUser = "dataUser";
    private static final int modo_private = Context.MODE_PRIVATE;
    String dato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.correo);
        contraseña = findViewById(R.id.contraseña);
        iniciar_sesion = findViewById(R.id.iniciar_sesion);
        registrate = findViewById(R.id.registrate);


        sharedPreferennces = getSharedPreferences(dataUser, modo_private);
        editor = sharedPreferennces.edit();
        dato = getApplicationContext().getSharedPreferences(dataUser,modo_private).getString("user","0");

        if (!dato.equalsIgnoreCase("0")){
            Intent i = new Intent(MainActivity.this, Fragments.class);
            startActivity(i);
            finish();
        }

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

    private void loginUser(String email, String pass) {

        String url = "http://192.168.1.9:8888/android_mysql/registro.php?email=" + email + "&pass=" + pass;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("success")) {
                            Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                            editor.putString("user",correo.getText().toString());
                            editor.commit();
                            Intent intent = new Intent(MainActivity.this, Fragments.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Error en el servidor: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}