package com.example.pizzatime2app.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PedidosRealizados {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("pizzas")
    @Expose
    private ArrayList<PedidoItem> pizzas;

    @SerializedName("bebida")
    @Expose
    private ArrayList<PedidoItem> bebidas;

    @SerializedName("precioTotal")
    @Expose
    private double precioTotal;

    public PedidosRealizados (){
        super();
    }

    public int getId() {
        return id;
    }

    public ArrayList<PedidoItem> getPizzas() {
        return pizzas;
    }

    public ArrayList<PedidoItem> getBebidas() {
        return bebidas;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPizzas(ArrayList<PedidoItem> pizzas) {
        this.pizzas = pizzas;
    }

    public void setBebidas(ArrayList<PedidoItem> bebidas) {
        this.bebidas = bebidas;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
