package com.example.parcialfinal.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcialfinal.R;
import com.example.parcialfinal.superhero.SuperHeroe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SuperHeroeAdaptador extends RecyclerView.Adapter<SuperHeroeAdaptador.ViewHolder> {
    private List<SuperHeroe> datos;

    public SuperHeroeAdaptador(List<SuperHeroe> datos){
        this.datos = datos;
    }

    @NonNull
    @Override
    public SuperHeroeAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_superhero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperHeroeAdaptador.ViewHolder holder, int position) {
        SuperHeroe dato = datos.get(position);
        holder.bind(dato);
    }

    @Override
    public int getItemCount() {return datos.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_nombre, txt_modified, txt_description;

        ImageView img_personaje;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.txt_nombre);
            txt_modified = itemView.findViewById(R.id.txt_modified);
            txt_description = itemView.findViewById(R.id.txt_description);
            img_personaje = itemView.findViewById(R.id.img_personaje);
        }

        public void bind(SuperHeroe dato) {
            txt_nombre.setText(dato.getNombre());
            txt_description.setText(dato.getModified());
            txt_modified.setText(dato.getModified());
            Picasso.get().load(dato.getImg()).into(img_personaje);
        }
    }
}
