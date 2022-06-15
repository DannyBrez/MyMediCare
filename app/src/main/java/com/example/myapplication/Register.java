package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    //Variables
    private EditText username, password, rePassword, firstName, age, postcode, phoneNumber, gpPhoneNumber;
    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //EditText Variables
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.rePassword);
        firstName = (EditText) findViewById(R.id.firstName);
        age = (EditText) findViewById(R.id.age);
        postcode = (EditText) findViewById(R.id.postcode);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        gpPhoneNumber = (EditText) findViewById(R.id.gpPhoneNumber);

        //Button Variables
        final Button backToWelcomeButton = (Button)findViewById(R.id.backButton5);
        final Button registerButton = (Button)findViewById(R.id.registerButton);

        //DB Variable
        DB = new DBHelper(this);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(backToWelcomeButton))
                {
                    startActivity(new Intent(Register.this, MainActivity.class));
                }
                else if (v.equals(registerButton))
                {
                    String user = username.getText().toString();
                    String pass = password.getText().toString();
                    String rePass = rePassword.getText().toString();
                    String userFirstName = firstName.getText().toString();
                    String userAge = age.getText().toString();
                    String userPostcode = postcode.getText().toString();
                    String userPhone = phoneNumber.getText().toString();
                    String gpPhone = gpPhoneNumber.getText().toString();

                    //The user must enter all the readings in order to register an account
                    if(user.equals("")||pass.equals("")||rePass.equals("")||userFirstName.equals("")||userAge.equals("")||userPostcode.equals("")||userPhone.equals("")||gpPhone.equals(""))
                    {
                        Toast.makeText(Register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            //If the password and reenter password don't match, display not matching message
                            if(pass.equals(rePass))
                        {
                            //Checks to see if the username entered is in the database, if it is then choose different name
                            Boolean checkuser = DB.checkusername(user);
                            if(checkuser==false)
                            {
                                //Inserts the registered information into the database
                                Boolean insert = DB.insertData(user, pass, userFirstName, userAge, userPostcode, userPhone, gpPhone);
                                if(insert==true)
                                {
                                    Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Register.this, "User already exists! Please sign in ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        };
        backToWelcomeButton.setOnClickListener(listener);
        registerButton.setOnClickListener(listener);
    }
}