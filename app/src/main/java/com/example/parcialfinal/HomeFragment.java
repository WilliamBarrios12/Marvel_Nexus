package com.example.parcialfinal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parcialfinal.adaptador.SuperHeroeAdaptador;
import com.example.parcialfinal.superhero.SuperHeroe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView rcv_personaje;
    List<SuperHeroe> listaPersonaje = new ArrayList<>();

    Button btn_buscar;
    EditText edt_buscar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rcv_personaje = view.findViewById(R.id.rcv_personaje);
        btn_buscar = view.findViewById(R.id.btn_buscar);
        edt_buscar = view.findViewById(R.id.edt_buscar);

        rcv_personaje.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_personaje.setAdapter(new SuperHeroeAdaptador(listaPersonaje));

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heroe = edt_buscar.getText().toString();
                if (heroe.isEmpty()) {
                    listaPersonaje.clear();
                    cargarInformacion(0);
                } else {
                    listaPersonaje.clear();
                    cargarInformacionHeroe(heroe);
                }
            }
        });

        rcv_personaje.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listaPersonaje.size() - 1) {
                    cargarInformacion(listaPersonaje.size());
                }
            }
        });

        cargarInformacion(0);

        return view;
    }

    public void cargarInformacion(int offset){
        String url = "https://gateway.marvel.com/v1/public/characters?offset=" + offset + "&apikey=367e2ed94ccca4e5f05383be31b8e4b8&hash=77ef4109201726575c3eff2b4d1ef87e&ts=1";

        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recibirRespuesta(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error en el servidor 1", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error en el servidor 2", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(myRequest);
    }

    public void recibirRespuesta(JSONObject respuesta) {
        try {
            JSONObject data = respuesta.getJSONObject("data");
            JSONArray results = data.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject personaje = results.getJSONObject(i);

                if (personaje.has("name") && personaje.has("description") && personaje.has("modified") && personaje.has("thumbnail")) {
                    String nombre = personaje.getString("name");
                    String descripcion = personaje.getString("description");
                    String modified = personaje.getString("modified");
                    JSONObject thumbnail = personaje.getJSONObject("thumbnail");
                    String imagen = thumbnail.getString("path") + "." + thumbnail.getString("extension");

                    SuperHeroe superheroe = new SuperHeroe(nombre, imagen, descripcion, modified);
                    listaPersonaje.add(superheroe);
                } else {
                    Toast.makeText(getContext(), "Faltan algunos campos en la respuesta JSON", Toast.LENGTH_LONG).show();
                }
            }

            rcv_personaje.getAdapter().notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error en el servidor 3: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cargarInformacionHeroe(String heroe){
        String url = "https://gateway.marvel.com/v1/public/characters?name="+heroe+"&apikey=367e2ed94ccca4e5f05383be31b8e4b8&hash=77ef4109201726575c3eff2b4d1ef87e&ts=1";

        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recibirRespuestaHeroe(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error en el servidor 1", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error en el servidor 2", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(myRequest);
    }

    public void recibirRespuestaHeroe(JSONObject respuesta) {

        try {

            JSONObject data = respuesta.getJSONObject("data");
            JSONArray results = data.getJSONArray("results");

            JSONObject personaje = results.getJSONObject(0);

            if (personaje.has("name") && personaje.has("description") && personaje.has("modified") && personaje.has("thumbnail")) {
                String nombre = personaje.getString("name");
                String descripcion = personaje.getString("description");
                String modified = personaje.getString("modified");
                JSONObject thumbnail = personaje.getJSONObject("thumbnail");
                String imagen = thumbnail.getString("path") + "." + thumbnail.getString("extension");

                SuperHeroe superheroe = new SuperHeroe(nombre, imagen, descripcion, modified);
                listaPersonaje.clear();
                listaPersonaje.add(superheroe);
            } else {
                Toast.makeText(getContext(), "Faltan algunos campos en la respuesta JSON", Toast.LENGTH_LONG).show();
            }

            rcv_personaje.setAdapter(new SuperHeroeAdaptador(listaPersonaje));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Nombre mal escrito " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}