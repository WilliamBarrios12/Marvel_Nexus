package com.example.parcialfinal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parcialfinal.adaptador.SuperHeroeAdaptador;
import com.example.parcialfinal.superhero.SuperHeroe;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitarFragment# newInstance} factory method to
 * create an instance of this fragment.
 */

public class SolicitarFragment extends Fragment {

    TextView select;

    Button btn_solicitar;
    Spinner comics;
    TextView txtTitulo, txtDescripcion, txtFormato, txtNumPag;
    ImageView imgComic;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solicitar, container, false);

        select = view.findViewById(R.id.seleccion);
        comics = view.findViewById(R.id.comic_spinner);
        btn_solicitar = view.findViewById(R.id.btn_solicitar);
        imgComic = view.findViewById(R.id.img_comic);
        txtTitulo = view.findViewById(R.id.txt_titulo);
        txtDescripcion = view.findViewById(R.id.txt_descripcion);
        txtFormato = view.findViewById(R.id.txt_formato);
        txtNumPag = view.findViewById(R.id.txt_numpag);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.comics_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comics.setAdapter(adapter);

        btn_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedComic = comics.getSelectedItem().toString();
                fetchComicData(selectedComic);
            }
        });

        return view;
    }

    private void fetchComicData(String title) {
        String apiKey = "367e2ed94ccca4e5f05383be31b8e4b8";
        String hash = "77ef4109201726575c3eff2b4d1ef87e";
        String ts = "1";
        String url = "https://gateway.marvel.com:443/v1/public/comics?title=" + title + "&apikey=" + apiKey + "&hash=" + hash + "&ts=" + ts;

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleApiResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(stringRequest);
    }

    private void handleApiResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray results = jsonResponse.getJSONObject("data").getJSONArray("results");

            if (results.length() > 0) {
                JSONObject comicData = results.getJSONObject(0);
                String title = comicData.getString("title");
                String description = comicData.getString("description");
                String format = comicData.getString("format");
                int pageCount = comicData.getInt("pageCount");
                String thumbnailPath = comicData.getJSONObject("thumbnail").getString("path");
                String thumbnailExtension = comicData.getJSONObject("thumbnail").getString("extension");
                String imageUrl = thumbnailPath + "." + thumbnailExtension;

                txtTitulo.setText(title);
                txtDescripcion.setText(description);
                txtFormato.setText(format);
                txtNumPag.setText(String.valueOf(pageCount));

                Picasso.get().load(imageUrl).into(imgComic);
            } else {
                txtTitulo.setText("No results found.");
                txtDescripcion.setText("");
                txtFormato.setText("");
                txtNumPag.setText("");
                imgComic.setImageDrawable(null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
