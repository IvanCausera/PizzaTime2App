package com.example.pizzatime2app;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pizzatime2app.modelo.PedidosUsuario;

import java.util.Calendar;

public class Client implements Parcelable {
    private String name;
    private double cost;

    private String cardType;
    private String card;
    private String expirationDate;

    private PedidosUsuario pedidoUsuario;

    public Client(String name, double cost) {
        this.name = name;
        this.cost = Math.round(cost * 100) / 100.0;
    }


    protected Client(Parcel in) {
        name = in.readString();
        cost = in.readDouble();
        cardType = in.readString();
        card = in.readString();
        expirationDate = in.readString();
        pedidoUsuario = in.readParcelable(PedidosUsuario.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(cost);
        dest.writeString(cardType);
        dest.writeString(card);
        dest.writeString(expirationDate);
        dest.writeParcelable(pedidoUsuario, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    /**
     * Check between two calendar if one is expired.
     * @param calActual calendar with the actual date
     * @param calExpirationDate calendar with the expiration date
     * @return true if is expired
     */
    public static boolean isExpired(Calendar calExpirationDate, Calendar calActual){
        return calActual.compareTo(calExpirationDate) > 0;
    }

    /**
     * Check if a date is valid
     * @return true if the date is valid.
     */
    public static boolean isValidDate(int day, int month, int year){
        boolean isValid;
        if (day <=0 || day > 31 || month <= 0 || month > 12 || year <= 0) {
            isValid = false;
        } else {
            isValid = day <= daysOfMonth(month, year);
        }
        return isValid;
    }

    /**
     * Gives the days of the month.
     * @return days of the month.
     */
    private static int daysOfMonth(int month, int year){
        int days;
        switch (month) {
            case 2:
                if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0) {
                    days = 29;
                } else days = 28;
                break;
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                days = 31;
                break;
            default:
                days = 30;
        }
        return days;
    }

    public void setCard(String cardType, String card, String expirationDate) {
        this.cardType = cardType;
        this.card = card;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCard() {
        return card;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setCost(double cost) {
        this.cost = Math.round(cost * 100) / 100.0;
    }

    public PedidosUsuario getPedidoUsuario() {
        return pedidoUsuario;
    }

    public void setPedidoUsuario(PedidosUsuario pedidoUsuario) {
        this.pedidoUsuario = pedidoUsuario;
    }
}
