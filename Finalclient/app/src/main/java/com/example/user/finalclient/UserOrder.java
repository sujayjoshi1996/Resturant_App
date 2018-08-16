package com.example.user.finalclient;

/**
 * Created by user on 2018/4/15.
 */
public class UserOrder {
    private String name ;
    private double total ;
    private String orderfood;

    public UserOrder() {
    }

    public UserOrder(String name,  double total, String orderfood) {
        this.name = name;
        this.total = total;
        this.orderfood =  orderfood;
    }

    public double gettotal() {
        return total;
    }

    public void settotal(double total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setorderfood(String orderfood) {
        this.orderfood = orderfood;
    }
    public String getorderfood() {
        return orderfood;
    }

    public void setName(String name) {
        this.name = name;
    }
}