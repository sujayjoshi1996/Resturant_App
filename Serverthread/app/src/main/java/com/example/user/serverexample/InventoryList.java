package com.example.user.serverexample;

public class InventoryList {
    String name;
    Integer quantity;


    public void SetName(String st1) {
        name=st1;
    }

    public void SetQuantity(Integer i)
    {
        quantity=i;
    }

    public String GetName() {
        return name;
    }

    public Integer GetQuantity() {
        return quantity;
    }
}
