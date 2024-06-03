package com.example.parcialfinal.comic;

public class Comic {
    String titulo, descripcion, format, img;
    int numPag;

    public Comic(String titulo, String descripcion, String format, String img, int numPag) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.format = format;
        this.numPag = numPag;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getNumPag() {
        return numPag;
    }

    public void setNumPag(int numPag) {
        this.numPag = numPag;
    }
}
