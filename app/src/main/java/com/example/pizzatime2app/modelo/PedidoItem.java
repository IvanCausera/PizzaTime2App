package com.example.pizzatime2app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PedidoItem {
    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("cantidad")
    @Expose
    private int cantidad;

    public PedidoItem (){
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
