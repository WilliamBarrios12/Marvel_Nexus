package com.example.parcialfinal;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracionFragment# newInstance} factory method to
 * create an instance of this fragment.
 */

public class ConfiguracionFragment extends Fragment {

    TextView textViewUsuario;
    Button btn_cerrar;
    public static final String dataUser = "dataUser";
    private static final int modo_private = Context.MODE_PRIVATE;
    String dato;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);

        textViewUsuario = view.findViewById(R.id.textViewUsuario);
        btn_cerrar = view.findViewById(R.id.btn_cerrar);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.dataUser, Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("user", "Usuario no encontrado");

        // Mostrar los datos del usuario en el TextView
        textViewUsuario.setText(usuario);

        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences configuracion = getContext().getSharedPreferences(dataUser, modo_private);
                configuracion.edit().clear().commit();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }
}