package com.example.k_schmerlat;

public class Food {
    private long id;
    private String foodname;
    private double preis;



    public Food (long id, String foodname, double preis){
        this.id = id;
        this.foodname = foodname;
        this.preis = preis;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String toString(){
        return foodname + " , " + preis;

    }
}


