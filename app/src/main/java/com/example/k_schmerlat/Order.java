package com.example.k_schmerlat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private long id;
    private List<Food> foodList;
    private List<Drinks> drinkList;
    private double Preis;
    private boolean payed = false;






    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);


    public Order() {
        this.id = ID_GENERATOR.getAndIncrement();
        foodList = new ArrayList<>();
        drinkList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void addfood(Food food){
        foodList.add(food);
    }
    public void removeFood(int pos) {foodList.remove(pos);}

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public List<Drinks> getDrinkList() {
        return drinkList;
    }

    public void adddrink(Drinks drink){
        drinkList.add(drink);
    }
    public void removeDrink(int pos) {drinkList.remove(pos);}

    public void setDrinkList(List<Drinks> drinkList) {
        this.drinkList = drinkList;
    }

    public double getPreis() {
        return Preis;
    }

    public void setPreis(double preis) {
        Preis = preis;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }


}
