package com.example.parcialfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracionFragment# newInstance} factory method to
 * create an instance of this fragment.
 */

public class ConfiguracionFragment extends Fragment {

    TextView textViewNombre, textViewCorreo, textViewdate, textViewContraseña;
    Button btn_cerrar;
    public static final String dataUser = "dataUser";
    private static final int modo_private = Context.MODE_PRIVATE;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);

        textViewNombre = view.findViewById(R.id.textViewNombre);
        textViewCorreo = view.findViewById(R.id.textViewCorreo);
        textViewdate = view.findViewById(R.id.textViewdate);
        textViewContraseña = view.findViewById(R.id.textViewContraseña);
        btn_cerrar = view.findViewById(R.id.btn_cerrar);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.dataUser, Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("user", "Usuario no encontrado");

        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences configuracion = getContext().getSharedPreferences(MainActivity.dataUser, Context.MODE_PRIVATE);
                configuracion.edit().clear().commit();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        // Llamar a la API para obtener los datos del usuario
        String url = "http://192.168.56.1:8888/android_mysql/credenciales.php?email=" + usuario;

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");

                            if (status.equals("success")) {
                                JSONObject data = jsonResponse.getJSONObject("data");
                                textViewNombre.setText(data.getString("nombre"));
                                textViewCorreo.setText(data.getString("email"));
                                textViewdate.setText(data.getString("birthdate"));
                                textViewContraseña.setText(data.getString("pass"));
                            } else {
                                // Manejar el caso de usuario no encontrado
                                textViewNombre.setText("Usuario no encontrado");
                                textViewCorreo.setText("");
                                textViewdate.setText("");
                                textViewContraseña.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(stringRequest);

        return view;
    }

}