package com.example.parcialfinal.superhero;

public class SuperHeroe {
    String nombre, img, descripcion, modified;

    public SuperHeroe(String nombre, String img, String descripcion, String modified) {
        this.nombre = nombre;
        this.img = img;
        this.descripcion = descripcion;
        this.modified = modified;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
