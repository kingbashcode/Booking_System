package com.example.k_schmerlat;
public class Drinks {

    private long id;
    private String drinkname;
    private double preis;


    public Drinks(long id, String drinkname, double preis) {
        this.id = id;
        this.drinkname = drinkname;
        this.preis = preis;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDrinkname() {
        return drinkname;
    }

    public void setDrinkname(String drinkname) {
        this.drinkname = drinkname;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String toString(){
        return drinkname + " , " + preis;

    };

}
