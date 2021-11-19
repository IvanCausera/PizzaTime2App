package com.example.pizzatime2app.modelo;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PedidosRealizados implements Parcelable {
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

    protected PedidosRealizados(Parcel in) {
        id = in.readInt();
        precioTotal = in.readDouble();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelableList(pizzas, flags);
        dest.writeParcelableList(bebidas, flags);
        dest.writeDouble(precioTotal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PedidosRealizados> CREATOR = new Creator<PedidosRealizados>() {
        @Override
        public PedidosRealizados createFromParcel(Parcel in) {
            return new PedidosRealizados(in);
        }

        @Override
        public PedidosRealizados[] newArray(int size) {
            return new PedidosRealizados[size];
        }
    };

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
