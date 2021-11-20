package com.example.pizzatime2app.modelo;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
    private ArrayList<PedidosRealizados> pedidoRealizado;

    public PedidosUsuario() {super();}

    public PedidosUsuario(String nombre, ArrayList<PedidosRealizados> pedidoRealizado) {
        this.nombre = nombre;
        this.pedidoRealizado = pedidoRealizado;
    }

    protected PedidosUsuario(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        pedidoRealizado = in.createTypedArrayList(PedidosRealizados.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeTypedList(pedidoRealizado);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public ArrayList<PedidosRealizados> getPedidoRealizado() {
        return pedidoRealizado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addPedido(PedidosRealizados pedidoRealizados){
        this.pedidoRealizado.add(pedidoRealizados);
    }
}
