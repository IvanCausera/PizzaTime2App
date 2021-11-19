package com.example.pizzatime2app;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pizzatime2app.modelo.PizzaBebida;

public class Pedido implements Parcelable {
    private String name;
    private Double price;
    private int quantity;
    private String image;

    public Pedido() { super();}

    public Pedido(String name, Double price, String image) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
        this.image = image;
    }

    protected Pedido(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        quantity = in.readInt();
        image = in.readString();
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeInt(quantity);
        parcel.writeString(image);
    }

    public void setPizzaBebida(PizzaBebida pb){
        name = pb.getNombre();
        price = pb.getPrecio();
    }
}
