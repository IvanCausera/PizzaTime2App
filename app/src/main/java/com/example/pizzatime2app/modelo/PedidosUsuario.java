package com.example.pizzatime2app.modelo;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PedidosUsuario implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("pedidosRealizados")
    @Expose
    private ArrayList<PedidosRealizados> pizzas;

    public PedidosUsuario() {super();}

    public PedidosUsuario(String nombre, ArrayList<PedidosRealizados> pizzas) {
        this.nombre = nombre;
        this.pizzas = pizzas;
    }

    protected PedidosUsuario(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
    }

    public static final Creator<PedidosUsuario> CREATOR = new Creator<PedidosUsuario>() {
        @Override
        public PedidosUsuario createFromParcel(Parcel in) {
            return new PedidosUsuario(in);
        }

        @Override
        public PedidosUsuario[] newArray(int size) {
            return new PedidosUsuario[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeParcelableList(pizzas, i);
    }
}
