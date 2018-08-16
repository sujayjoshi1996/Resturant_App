package com.example.user.serverexample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.Test;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    private ArrayList<Integer> item1 = new ArrayList<>();
    private ArrayList<Integer> item2 = new ArrayList<Integer>();
    private ArrayList<Integer> item3 = new ArrayList<>();
    private ArrayList<Integer> item4 = new ArrayList<>();
    private static ArrayList<String> Rstatus = new ArrayList<>();
    public static String text;
    final static String FILENAME = "1.txt";

    private Timer timer;
    private TimerTask task;

    static TextView msg1;


    static String result;
    String result2;

    static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String data = (String) msg.obj;
                    msg1.setText(data);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //read file at the first time
        try{
            InputStream is = getAssets().open(FILENAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text= new String(buffer);
            Log.e("text",text);
            Read.update();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        task = new TimerTask() {
            @Override
            public void run() {
                try{
                    InputStream is = getAssets().open(FILENAME);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    text= new String(buffer);
                    Log.e("text",text);
                    Read.update();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };




        timer = new Timer();
        timer.schedule(task,1000,3600000);

        //firebase test

        msg1 = (TextView) findViewById(R.id.msg);




        final ListView listView = (ListView) findViewById(R.id.list);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1);
        listView.setAdapter(adapter);
        adapter.clear();
        //Dym Order read from firebase dymOrder
        DatabaseReference Dref = FirebaseDatabase.getInstance().getReference("dymOrder");
        Dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //adapter.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    item1.add(Integer.valueOf(ds.child("item1").getValue().toString()));
                    item2.add(Integer.valueOf(ds.child("item2").getValue().toString()));
                    item3.add(Integer.valueOf(ds.child("item3").getValue().toString()));
                    item4.add(Integer.valueOf(ds.child("item4").getValue().toString()));

                    //listview
                    adapter.add(ds.getValue().toString());


                }

                ArrayList<Order> OrderList = new ArrayList<>();

                int size=item1.size();
                for (int i = 0; i < size; i++) {
                    Order order = new Order(item1.get(i), item2.get(i), item3.get(i), item4.get(i));
                    OrderList.add(order);
                }

                int a1, a2, a3, a4;

                for(int i = 0; i<OrderList.size();i++){
                    a1=OrderList.get(i).getBurger();
                    a2=OrderList.get(i).getFries();
                    a3=OrderList.get(i).getChicken();
                    a4=OrderList.get(i).getRings();
                    System.out.println(a1+" "+a2+" "+a3+" "+a4);
                    KitchenThread k = new KitchenThread(a1,a2,a3,a4,i);
                    k.start();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    static class Process {

        static synchronized public void submitting(int i) {

            System.out.println("Order "+i+" "+"has been submitted");
            handler.sendEmptyMessage(0);
            Message msg =new Message();
            msg.obj = "Order "+i+" has been submittied";//
            handler.sendMessage(msg);
        }

        static synchronized public boolean check(int a1, int a2, int a3, int a4, int i) throws InterruptedException {

            int c1 = 0, c2 = 0;
            int a, b, c, d;
            int inv1 = Read.dish1.GetQuantity();
            int inv2 = Read.dish2.GetQuantity();
            int inv3 = Read.dish3.GetQuantity();
            int inv4 = Read.dish4.GetQuantity();

            if (a1 > 0) c1++;
            if (a2 > 0) c1++;
            if (a3 > 0) c1++;
            if (a4 > 0) c1++;

            if ((inv1 - a1) > 0 && a1 > 0) {
                c2++;
                a = inv1 - a1;
                Read.dish1.SetQuantity(a);
            }
            if ((inv2 - a2) > 0 && a2 > 0) {
                c2++;
                b = inv2 - a2;
                Read.dish2.SetQuantity(b);
            }
            if ((inv3 - a3) > 0 && a3 > 0) {
                c2++;
                c = inv3 - a3;
                Read.dish3.SetQuantity(c);
            }
            if ((inv4 - a4) > 0 && a4 > 0) {
                c2++;
                d = inv4 - a4;
                Read.dish4.SetQuantity(d);
            }
            System.out.println("1:" + Read.dish1.GetQuantity() + " " + Read.dish2.GetQuantity() + " " + Read.dish3.GetQuantity() + " " + Read.dish4.GetQuantity());
            if (c1 == c2) {//order is completely available;
                return true;
            } else if (c1 > c2 && c2 > 0) {//order is partially available

                //if client wants partial order
                KitchenThread.sleep(10000);
                System.out.println("----id" + i);
                //result2 = getStatus(id);
                if (getStatus(i).equals("ok")) {

                    if (a1 > 0 && a1 > inv1) {
                        a1 = inv1;
                        Read.dish1.SetQuantity(0);
                    }
                    if (a2 > 0 && a2 > inv2) {
                        a2 = inv2;
                        Read.dish1.SetQuantity(0);
                    }
                    if (a3 > 0 && a3 > inv3) {
                        a3 = inv3;
                        Read.dish1.SetQuantity(0);
                    }
                    if (a4 > 0 && a4 > inv4) {
                        a4 = inv4;
                        Read.dish1.SetQuantity(0);
                    }
                    return true;
                } else if (getStatus(i).equals("fail")) {
                    return false;
                } else {
                    //if client wants to cancel, set the value back
                    Read.dish1.SetQuantity(inv1);
                    Read.dish2.SetQuantity(inv2);
                    Read.dish3.SetQuantity(inv3);
                    Read.dish4.SetQuantity(inv4);
                    return false;

                }
            } else { //order is not available
                //cancel the order
                return false;
            }

        }

        static synchronized public void receiving(int i) {
            System.out.println("Order "+i+" has been received");
            //msg.setText("Your order "+id+" has been received");
            handler.sendEmptyMessage(0);
            Message msg =new Message();
            msg.obj = "Order "+i+" has been received";//
            handler.sendMessage(msg);

        }

        static synchronized public void preparing(int i) {
            Random random = new Random();
            int s1 = random.nextInt(300) % (300 - 180 + 1) + 180;
            System.out.println("Order "+i+": "+"We need " + s1 + " seconds to prepare your order"+i+".");
            handler.sendEmptyMessage(0);
            Message msg =new Message();

            msg.obj="Order "+i+": "+"We need " + s1 + " seconds to prepare your order"+i+".";

            handler.sendMessage(msg);
            System.out.println("Preparing order "+i+"...");
            s1 = s1 * 1000;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Order "+i+"has been prepared");
            }

        static synchronized public void packaging(int i) {

            Random random = new Random();
            int s2 = random.nextInt(30) % (30 - 20 + 1) + 20;
            System.out.println("Order "+i+": "+"We need " + s2 + " seconds to package your order"+i+".");
            handler.sendEmptyMessage(0);
            Message msg =new Message();

            msg.obj="Order "+i+": "+"We need " + s2 + " seconds to package your order"+i+".";

            handler.sendMessage(msg);
            System.out.println("Packaging order "+i+"...");
            s2 = s2 * 1000;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
//            Test.setText("Pack");
            System.out.println("Order"+i+" has been packaged.");
        }

        static synchronized public void delivering(int i) {
            System.out.println(" order"+i+" has been delivered");
            handler.sendEmptyMessage(0);
            Message msg =new Message();
            msg.obj=" order"+i+" has been delivered";
            handler.sendMessage(msg);
            System.out.println(Read.dish1.GetQuantity() + " " + Read.dish2.GetQuantity() + " " + Read.dish3.GetQuantity() + " " + Read.dish4.GetQuantity());
            updateTrue(i);
        }
        static synchronized public void cancel(int i){
            System.out.println("order"+i+" has been cancelled.....");

            handler.sendEmptyMessage(0);
            Message msg =new Message();
            msg.obj=" order"+i+" has been cancelled....";
            handler.sendMessage(msg);

            System.out.println(Read.dish1.GetQuantity() + " " + Read.dish2.GetQuantity() + " " + Read.dish3.GetQuantity() + " " + Read.dish4.GetQuantity());
            updateFalse(i);
        }

    }


    static class KitchenThread extends Thread{
        boolean checkid;
        int a1,a2,a3,a4,i;
        public KitchenThread(int a1,int a2,int a3,int a4,int i) {
            this.a1=a1;
            this.a2=a2;
            this.a3=a3;
            this.a4=a4;
            this.i=i;
        }
        public void run() {
            Process.submitting(i);
            try {
                checkid = Process.check(a1,a2,a3,a4,i);
                System.out.println(checkid);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(checkid==true) {
                Process.receiving(i);
                synchronized (this) {
                    Process.preparing(i);
                }
                Process.packaging(i);
                Process.delivering(i);
            }
            else{
                Process.cancel(i);
            }


        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }





    public void getStatus(){

        DatabaseReference SRef = FirebaseDatabase.getInstance().getReference("status");
        SRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Rstatus.add((String)ds.child("status").getValue());
                }

                System.out.println(Rstatus);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public static String getStatus(final int index){
        final String num1;
        num1 = String.valueOf(index);

        DatabaseReference SRef = FirebaseDatabase.getInstance().getReference("status");
        SRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Rstatus.add((String)ds.child("status").getValue());
                }
                result = (String)Rstatus.get(index);
                //System.out.println(Rstatus.toString());

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return result;
    }

    public static void updateFalse(int index){
        final String num2;
        num2 = String.valueOf(index);
        DatabaseReference SRef = FirebaseDatabase.getInstance().getReference("status");
        Map<String,Object> tmap = new HashMap<>();
        tmap.put("status","fail");
        SRef.child(num2).updateChildren(tmap);
    }
    public static void updateTrue(int index){
        final String num3;
        num3 = String.valueOf(index);
        DatabaseReference SRef = FirebaseDatabase.getInstance().getReference("status");
        Map<String,Object> tmap = new HashMap<>();
        tmap.put("status","ok");
        SRef.child(num3).updateChildren(tmap);
    }


}
class Order{
    int burger;
    int fries;
    int chicken;
    int rings;
    public Order(int burger,int fries,int chicken,int rings){
        this.burger=burger;
        this.fries = fries;
        this.chicken = chicken;
        this.rings = rings;
    }
    public int getBurger(){
        return burger;
    }
    public int getFries(){
        return fries;
    }
    public int getChicken(){
        return chicken;
    }
    public int getRings(){
        return rings;
    }

}