package com.example.pizzatime2app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PedidosUsuario {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("pizzas")
    @Expose
    private ArrayList<PedidosRealizados> pizzas;

    public PedidosUsuario() {super();}

    public PedidosUsuario(String nombre, ArrayList<PedidosRealizados> pizzas) {
        this.nombre = nombre;
        this.pizzas = pizzas;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<PedidosRealizados> getPizzas() {
        return pizzas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPizzas(ArrayList<PedidosRealizados> pizzas) {
        this.pizzas = pizzas;
    }
}
