package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button Variables
        final Button loginButton = (Button)findViewById(R.id.goToLoginButton);
        final Button registerButton = (Button)findViewById(R.id.goToRegisterButton);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(loginButton))
                {
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
                else if (v.equals(registerButton))
                {
                    startActivity(new Intent(MainActivity.this, Register.class));
                }
            }
        };
        loginButton.setOnClickListener(listener);
        registerButton.setOnClickListener(listener);

    }
}