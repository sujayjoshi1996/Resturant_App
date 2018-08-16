package com.example.user.finalclient;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2018/4/15.
 */

public class Cart extends AppCompatActivity {
    EditText t1, t2, t3, t4, t5;
    TextView tv1, tv2;
    CheckBox cb1, cb2, cb3, cb4;
    double price1;
    double price2;
    double price3;
    double price4;
    Switch s1;
    RatingBar rb;
    Button bttn, bts;
    double sum, total;
    private ArrayList<String> food = new ArrayList<String>();
    private ArrayList<String> fooddetail = new ArrayList<String>();
    private ArrayList<String> status = new ArrayList<String>();
    private ArrayList<Double> cost = new ArrayList<Double>();
    private ArrayList<String> update = new ArrayList<String>();
    private ArrayList<Integer> foodcount = new ArrayList<Integer>();
    int peoplecount=0;
    JSONObject json_test = new JSONObject();
    //String response = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);
        t3 = (EditText) findViewById(R.id.t3);
        t4 = (EditText) findViewById(R.id.t4);
        t5 = (EditText) findViewById(R.id.t5);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        s1 = (Switch) findViewById(R.id.s1);
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        rb = (RatingBar) findViewById(R.id.rb);
        bttn = (Button) findViewById(R.id.bttn);
        bts=(Button) findViewById(R.id.bts);
        final int count =0;



        rate r=new rate();
        rb.setOnRatingBarChangeListener(r);
//firebase test
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("order");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    food.add(ds.child("name").getValue().toString());
                    cost.add(Double.parseDouble(ds.child("cost").getValue().toString()));
                    }
                price1=cost.get(0)*1.25;
                price2=cost.get(1)*1.25;
                price3=cost.get(2)*1.25;
                price4=cost.get(3)*1.25;
                cb1.setText(food.get(0)+"  USD:"+price1);
                cb2.setText(food.get(1)+"  USD:"+price2);
                cb3.setText(food.get(2)+"  USD:"+price3);
                cb4.setText(food.get(3)+"  USD:"+price4);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //end
        bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                status.clear();
                //

                DatabaseReference reference_status=FirebaseDatabase.getInstance().getReference("status");
                reference_status.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot2) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (DataSnapshot ds1 : dataSnapshot2.getChildren()) {
                            status.add(ds1.child("status").getValue().toString());
                            }

                        if (status.get(0).equals("ok")) {
                            new AlertDialog.Builder(Cart.this).setTitle("Order Status").setIcon(R.mipmap.ic_launcher).setMessage("Your order is OK").setPositiveButton("close",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    }
                            }).show();//呈現對話視窗
                        }else if(status.get(0).equals("fail")) {
                            //
                            DatabaseReference reference_change = FirebaseDatabase.getInstance().getReference("orderreply");
                            reference_change.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {

                                    String updatedetail=(String)dataSnapshot1.child( String.valueOf(peoplecount)).child("orderfood").getValue();
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(Cart.this);
                                    dialog.setTitle("Order Fail");
                                    dialog.setMessage("Your order is fail , but we will change food for you! \r"+"and your new order will be "+updatedetail);
                                    dialog.setNegativeButton("I want to cancel the order",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            // TODO Auto-generated method stub

                                            Toast.makeText(Cart.this, "We are sorry about that!",Toast.LENGTH_SHORT).show();
                                        }

                                    });
                                    dialog.setPositiveButton("It's fine",new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(Cart.this, "Thank you",Toast.LENGTH_SHORT).show();
                                        }

                                    });

                                    dialog.show();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                            //


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        }
                });

            }
        });

        bttn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // On login button click, storing our username into singleton class.
                try {
                    if (!s1.isChecked()) {
                        setTitle("Store Closed");
                    }
                    double x=0;
                    double y=0;
                    double z=0;
                    double m=0;
                    int a =Utilities.convertToInteger(t2);
                    int b = Utilities.convertToInteger(t3);
                    int c = Utilities.convertToInteger(t4);
                    int d = Utilities.convertToInteger(t5);
                    foodcount.add(0);
                    foodcount.add(0);
                    foodcount.add(0);
                    foodcount.add(0);
                    FirebaseDatabase database4 = FirebaseDatabase.getInstance();
                    DatabaseReference userRef2 = database4.getReference("status").child("0");
                    Map<String, Object> childUpdates2 = new HashMap<>();
                    childUpdates2.put("status","ok");
                    userRef2.updateChildren(childUpdates2);

                    if (s1.isChecked()) {
                        {
                            setTitle("Store OPEN");
                        }
                        if (cb1.isChecked()) {
                            x = price1 * a;
                            fooddetail.add(food.get(0));
                            fooddetail.add(t2.getText().toString());
                            foodcount.set(0,Integer.parseInt(t2.getText().toString()));
                            } if (cb2.isChecked()) {
                            y = price2 * b;
                            fooddetail.add(food.get(1));
                            fooddetail.add(t3.getText().toString());
                            foodcount.set(1,Integer.parseInt(t3.getText().toString()));
                           } if (cb3.isChecked()) {
                            z = price3 * c;
                            fooddetail.add(food.get(2));
                            fooddetail.add(t4.getText().toString());
                            foodcount.set(2,Integer.parseInt(t4.getText().toString()));
                        }  if (cb4.isChecked()) {
                            m = price4 * d;
                            fooddetail.add(food.get(3));
                            fooddetail.add(t5.getText().toString());
                            foodcount.set(3,Integer.parseInt(t5.getText().toString()));
                        }
                        sum = (x + y + z + m) * 1.0;
                        total=sum+0.05*sum;
                        tv1.setText("Thank You for Shopping. Your BILL +5% Tax =" + total);
                        //
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        String pc=String.valueOf(peoplecount);
                        DatabaseReference myRef = database.getReference("orderreply").child(pc);
                        String listString = "";
                        for (String s : fooddetail)
                        {
                            listString += s + ",";
                        }
                        UserOrder user = new UserOrder(t1.getText().toString(), total,listString);
                        myRef.setValue(user);
                        //
                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                        DatabaseReference myRef2 = database2.getReference("dymOrder").child(pc);
                        Fooditem fooditem= new Fooditem(foodcount.get(0),foodcount.get(1),foodcount.get(2),foodcount.get(3));
                        myRef2.setValue(fooditem);
                        //
                        peoplecount=peoplecount+1;
                        fooddetail.clear();
                        //


                    }
                }

                catch (Exception ex){
                    System.out.println(ex);
                    Log.e("error", String.valueOf(ex));
                    tv1.setText(ex.getMessage());
                }
            }
        });
        Log.e("msg","i am here");


    }


    class rate implements RatingBar.OnRatingBarChangeListener{
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            if(v>=4){
                tv2.setText("Wow! Your Rating is " + v + " .Glad you like it");
            }
            else
            if(v>=2){
                tv2.setText("Your rating is " + v + " .Please shop again to check the updation");
            }
            else
            if(v>=0){
                tv2.setText("Your rating is " + v + " .Sorry for the inconvenience" );
            }
        }
    }
}
