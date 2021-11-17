package com.example.pizzatime2app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Client implements Parcelable {
    private String name;
    private double cost;

    private String cardType;
    private String card;
    private String expirationDate;

    public Client(String name, double cost) {
        this.name = name;
        this.cost = Math.round(cost * 100) / 100.0;
    }

    protected Client(Parcel in) {
        this.name = in.readString();
        this.cost = in.readDouble();
        this.cardType = in.readString();
        this.card = in.readString();
        this.expirationDate = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(cost);

        parcel.writeString(cardType);
        parcel.writeString(card);
        parcel.writeString(expirationDate);
    }

    /**
     * Check between two calendar if one is expired.
     * @param calActual calendar with the actual date
     * @param calExpirationDate calendar with the expiration date
     * @return true if is expired
     */
    public static boolean isExpired(Calendar calExpirationDate, Calendar calActual){
        return calActual.compareTo(calExpirationDate) == 1;
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
}
