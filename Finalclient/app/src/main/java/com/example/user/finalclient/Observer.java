package com.example.user.finalclient;

/**
 * Created by user on 2018/4/17.
 */

public abstract class Observer {
    protected UserOrder userorder;
    public abstract void update();
}