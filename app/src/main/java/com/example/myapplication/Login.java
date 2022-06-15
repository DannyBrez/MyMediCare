package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    public static String globalUsername;
    private EditText username, password;
    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //EditText Variables
        username = (EditText) findViewById(R.id.loginUsername);
        password = (EditText) findViewById(R.id.loginPassword);

        //DB Variable
        DB = new DBHelper(this);

        //Button Variables
        final Button backToWelcomeButton = (Button)findViewById(R.id.backButton7);
        final Button login = (Button)findViewById(R.id.confirmLogin);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(backToWelcomeButton))
                {
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
                else if (v.equals(login))
                {
                    String user = username.getText().toString();
                    String pass = password.getText().toString();

                    //If username or password is not entered, display message
                    if(user.equals("")||pass.equals(""))
                    {
                        Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Checks if the username and password match an account in the database
                         Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                        if(checkuserpass==true)
                        {
                            Toast.makeText(Login.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            globalUsername = user;
                            Intent intent = new Intent(getApplicationContext(), Mainmenu.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };
        backToWelcomeButton.setOnClickListener(listener);
        login.setOnClickListener(listener);

    }
}