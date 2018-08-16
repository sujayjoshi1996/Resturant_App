package com.example.user.finalclient;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import org.json.JSONObject;
import android.util.Log;




/**
 * Created by user on 2018/3/31.
 */

public class WelcomeActivity extends AppCompatActivity {


    TextView textResponse;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button start = (Button) findViewById(R.id.startbutton);
        textResponse = (TextView) findViewById(R.id.response);
        TextView txtWelcome  = (TextView) findViewById(R.id.txtWelcome);
        txtWelcome.setText("Welcome\n" + SingletonSession.Instance().getUsername());
        //thread=new Thread(Connection);                //賦予執行緒工作
        //thread.start();                    //讓執行緒開始執行
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // On login button click, storing our username into singleton class.
                Intent cart = new Intent(WelcomeActivity.this, Cart.class);
                startActivity(cart);
            }
        });
    }

    @Override
    protected void onDestroy() {            //當銷毀該app時
        super.onDestroy();

    }
}