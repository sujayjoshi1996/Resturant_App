package com.example.user.serverexample;

import java.io.IOException;

class Read {
    static InventoryList dish1=new InventoryList();
    static InventoryList dish2=new InventoryList();
    static InventoryList dish3=new InventoryList();
    static InventoryList dish4=new InventoryList();
    static int quantity,q;
    static String name,n;



    public static void update()  throws IOException, InterruptedException{

        String lines = MainActivity.text;
        String str[] = lines.split(",");
        name = str[0];
        dish1.SetName(name);
        quantity = Integer.parseInt(str[1]);
        dish1.SetQuantity(quantity);
        n = dish1.GetName();
        q = dish1.GetQuantity();

        name = str[2];
        dish2.SetName(name);
        quantity = Integer.parseInt(str[3]);
        dish2.SetQuantity(quantity);
        n = dish2.GetName();
        q = dish2.GetQuantity();
        System.out.println(n+q);

        name = str[4];
        dish3.SetName(name);
        quantity = Integer.parseInt(str[5]);
        dish3.SetQuantity(quantity);
        n = dish3.GetName();
        q = dish3.GetQuantity();
        System.out.println(n+q);

        name = str[6];
        dish4.SetName(name);
        quantity = Integer.parseInt(str[7]);
        dish4.SetQuantity(quantity);
        n = dish4.GetName();
        q = dish4.GetQuantity();
        System.out.println(n+q);
    }
}

