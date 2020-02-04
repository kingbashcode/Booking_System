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
    double Preis;
    Map<Food, Long> resultMapfood = new HashMap<>();
    Map<Drinks, Long> resultMapdrink = new HashMap<>();
    Map items = new HashMap<>();





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

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public List<Drinks> getDrinkList() {
        return drinkList;
    }

    public void adddrink(Drinks drink){
        drinkList.add(drink);
    }

    public void setDrinkList(List<Drinks> drinkList) {
        this.drinkList = drinkList;
    }

    public double getPreis() {
        return Preis;
    }

    public void setPreis(double preis) {
        Preis = preis;
    }

    public void countfood(List<Food> inputList) {
        for (Food element : inputList) {
            if (resultMapfood.containsKey(element)) {
                resultMapfood.put(element, resultMapfood.get(element) + 1L);
            } else {
                resultMapfood.put(element, 1L);
            }
        }

    }

    public void countdrinks(List<Drinks> inputList) {
        for (Drinks element : inputList) {
            if (resultMapdrink.containsKey(element)) {
                resultMapdrink.put(element, resultMapdrink.get(element) + 1L);
            } else {
                resultMapdrink.put(element, 1L);
            }
        }

    }

    public Map getitems(){

        items.putAll(resultMapdrink);
        items.putAll(resultMapfood);

        return items;

    };

}
