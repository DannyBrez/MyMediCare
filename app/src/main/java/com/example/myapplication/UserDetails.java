package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserDetails extends AppCompatActivity {

    //Variables
    private EditText age, postcode, phoneNumber, gpPhoneNumber;
    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        //Database Variable
        DB = new DBHelper(this);

        //Button Variables
        final Button BacktoMainMenuButton = (Button) findViewById(R.id.backButton2);
        final Button ConfirmUserDetails = (Button) findViewById(R.id.confirmUserDetails);

        //EditText Variables
        age = findViewById(R.id.changeAge);
        postcode = findViewById(R.id.changePostcode);
        phoneNumber = findViewById(R.id.changePhone);
        gpPhoneNumber = findViewById(R.id.changeGPPhone);

        //Sets the EditText to the Users current details
        Cursor cursor = DB.fetchUserData(Login.globalUsername);
        if(cursor.getCount()==0) {
            return;
        }
        else{
            while(cursor.moveToNext()) {
                age.setText(cursor.getString(3));
                postcode.setText(cursor.getString(4));
                phoneNumber.setText(cursor.getString(5));
                gpPhoneNumber.setText(cursor.getString(6));
            }
        }
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(BacktoMainMenuButton))
                {
                    startActivity(new Intent(UserDetails.this, Mainmenu.class));
                }
                else if (v.equals(ConfirmUserDetails))
                {
                    String username = Login.globalUsername;
                    String ageTxt = age.getText().toString();
                    String postcodeTxt = postcode.getText().toString();
                    String phoneNumberTxt = phoneNumber.getText().toString();
                    String gpPhoneNumberTxt = gpPhoneNumber.getText().toString();

                    if (ageTxt.equals("") || postcodeTxt.equals("") || phoneNumberTxt.equals("") || gpPhoneNumberTxt.equals(""))
                    {
                        Toast.makeText(UserDetails.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Updates the users information with the new details (unchanged data will be overwritten)
                        Boolean checkInsertData = DB.updateData(username, ageTxt, postcodeTxt, phoneNumberTxt, gpPhoneNumberTxt);
                        if (checkInsertData)
                        {
                            Toast.makeText(UserDetails.this, "User Details Updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(UserDetails.this, "User Details Not Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };
        BacktoMainMenuButton.setOnClickListener(listener);
        ConfirmUserDetails.setOnClickListener(listener);
    }
}