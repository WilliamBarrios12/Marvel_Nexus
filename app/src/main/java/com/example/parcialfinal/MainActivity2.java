package com.example.parcialfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity {

    ImageView imgComic;
    TextView txtTitulo, txtDescripcion, txtModified;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgComic = findViewById(R.id.img);
        txtTitulo = findViewById(R.id.txt_nombre);
        txtDescripcion = findViewById(R.id.txt_descripcion);
        txtModified = findViewById(R.id.txt_modified);

        Intent intent = getIntent();
        if (intent != null) {
            String nombre = intent.getStringExtra("nombre");
            String imagen = intent.getStringExtra("imagen");
            String descripcion = intent.getStringExtra("descripcion");
            String modified = intent.getStringExtra("modified");

            txtTitulo.setText(nombre);
            txtDescripcion.setText(descripcion);
            txtModified.setText(modified);
            Picasso.get().load(imagen).into(imgComic);
        }
    }
}