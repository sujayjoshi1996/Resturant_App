package com.example.user.finalclient;

/**
 * Created by user on 2018/4/17.
 */

public class Fooditem {
    private int item1 ;
    private int item2 ;
    private int item3 ;
    private int item4 ;


    public Fooditem() {
    }

    public Fooditem(int item1,int item2,int item3,int item4) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

    public double getitem1() {
        return item1;
    }

    public void setitem1(int item1) {
        this.item1 = item1;
    }
    public double getitem2() {
        return item2;
    }

    public void setitem2(int item2) {
        this.item2 = item2;
    }
    public double getitem3() {
        return item3;
    }

    public void setitem3(int item3) {
        this.item3 = item3;
    }
    public double getitem4() {
        return item4;
    }

    public void setitem4(int item4) {
        this.item4 = item4;
    }
}
