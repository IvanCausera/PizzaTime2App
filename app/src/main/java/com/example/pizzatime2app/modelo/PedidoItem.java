package com.example.pizzatime2app.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PedidoItem implements Parcelable {
    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("cantidad")
    @Expose
    private int cantidad;

    public PedidoItem (){
        super();
    }

    public PedidoItem(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    protected PedidoItem(Parcel in) {
        nombre = in.readString();
        cantidad = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(cantidad);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PedidoItem> CREATOR = new Creator<PedidoItem>() {
        @Override
        public PedidoItem createFromParcel(Parcel in) {
            return new PedidoItem(in);
        }

        @Override
        public PedidoItem[] newArray(int size) {
            return new PedidoItem[size];
        }
    };

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
