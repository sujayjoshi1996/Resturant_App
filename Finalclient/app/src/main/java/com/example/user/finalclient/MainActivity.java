package com.example.user.finalclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        final EditText editUsername  = (EditText) findViewById(R.id.editUsername);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // On login button click, storing our username into singleton class.
                SingletonSession.Instance().setUsername(editUsername.getText().toString());
                Intent welcomeActivity = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(welcomeActivity);
            }
        });

    }
}
