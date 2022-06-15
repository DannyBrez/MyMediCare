package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReadingResults extends AppCompatActivity {

    //Variables
    private DBHelper DB;
    private boolean recordsSaved = false, smsSent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_results);

        //Database Variable
        DB = new DBHelper(this);

        //Displays Current Date
        TextView dateTimeDisplay = findViewById(R.id.subTextDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        dateTimeDisplay.setText(date);

        //Database Variables
        TextView dateTxt = (TextView) findViewById(R.id.subTextDate);
        TextView tempTxt = (TextView) findViewById(R.id.subTextTempNum);
        TextView bpTxt = (TextView) findViewById(R.id.subTextBpNum);
        TextView heartTxt = (TextView) findViewById(R.id.subTextHeartNum);

        //Outputs the messages to the TextViews from the EnterReadings Intent.
        Intent outPutIntent = getIntent();

        //Temp Number Message
        String tempNumber = outPutIntent.getStringExtra("tempNum");
        TextView tempNumTextView = (TextView) findViewById(R.id.subTextTempNum);
        tempNumTextView.setText(tempNumber);
        //Temp Reading Message
        String tempReading = outPutIntent.getStringExtra("tempReading");
        TextView tempReadingTextView = (TextView) findViewById(R.id.subTextTempReading);
        tempReadingTextView.setText(tempReading);

        //Blood Pressure Number Message
        String bpNumber = outPutIntent.getStringExtra("bpNum");
        TextView bpNumberTextView = (TextView) findViewById(R.id.subTextBpNum);
        bpNumberTextView.setText(bpNumber);
        //Blood Pressure Reading Message
        String bpReading = outPutIntent.getStringExtra("bpReading");
        TextView bpReadingTextView = (TextView) findViewById(R.id.subTextBpReading);
        bpReadingTextView.setText(bpReading);

        //Heartrate Number Message
        String heartNumber = outPutIntent.getStringExtra("heartNum");
        TextView heartNumberTextView = (TextView) findViewById(R.id.subTextHeartNum);
        heartNumberTextView.setText(heartNumber);
        //Heartrate Reading Message
        String heartReading = outPutIntent.getStringExtra("heartReading");
        TextView heartReadingTextView = (TextView) findViewById(R.id.subTextHeartReading);
        heartReadingTextView.setText(heartReading);

        //Button Variables
        final Button BacktoMainMenuButton = (Button) findViewById(R.id.backButton3);
        final Button sendSMS = (Button) findViewById(R.id.sendSMS);
        final Button insertRecords = (Button) findViewById(R.id.saveRecords);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (v.equals(BacktoMainMenuButton))
                {
                    startActivity(new Intent(ReadingResults.this, Mainmenu.class));
                }
                // Send the SMS Message to GP if High Risk
                else if (v.equals(sendSMS))
                {
                    if (tempReadingTextView.getText().toString().equals("Hypothermia / Low temperature") || bpReadingTextView.getText().toString().equals("Hypotension / Low blood pressure") || heartReadingTextView.getText().toString().equals("Low heartrate") ||
                        tempReadingTextView.getText().toString().equals("Fever / High temperature") || bpReadingTextView.getText().toString().equals("Hypertension / High blood pressure") || heartReadingTextView.getText().toString().equals("High heartrate"))
                    {
                        if(!smsSent)
                        {
                            //checks if the permission is not granted
                            if (ContextCompat.checkSelfPermission(ReadingResults.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                            {
                                //if permission is not granted then check if the user has denied the permission
                                if (ActivityCompat.shouldShowRequestPermissionRationale(ReadingResults.this, Manifest.permission.SEND_SMS)) {
                                    Toast.makeText(getApplicationContext(), "Please grant access to sending SMS", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Requests Permission for sending SMS
                                    ActivityCompat.requestPermissions(ReadingResults.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
                                    Toast.makeText(getApplicationContext(), "Please click send SMS Text to GP button again if you accept permissions.", Toast.LENGTH_LONG).show();

                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
                                onSMSSend();
                                smsSent = true;
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Message already sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "All your given results returned Normal", Toast.LENGTH_SHORT).show();
                    }
                }
                //Inserts Records into the records table. The boolean stops users entering records twice.
                else if (v.equals(insertRecords))
                {
                    String date = dateTxt.getText().toString();
                    String temp = tempTxt.getText().toString();
                    String bp = bpTxt.getText().toString();
                    String heart = heartTxt.getText().toString();

                    if(!recordsSaved)
                    {
                        Boolean insert = DB.insertDataReadings(date, temp, bp, heart, Login.globalUsername);
                        recordsSaved = true;
                        if(insert)
                        {
                            Toast.makeText(ReadingResults.this, "Records inserted", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ReadingResults.this, "Records failed to insert", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ReadingResults.this, "Records already saved!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        BacktoMainMenuButton.setOnClickListener(listener);
        sendSMS.setOnClickListener(listener);
        insertRecords.setOnClickListener(listener);

    }

    public void onSMSSend() {
        //DB Variable
        DB = new DBHelper(this);

        String no = "";
        String mess = "This is an automatic message in responds to the user being at Risk.";

        //Gets the Users GP Number
        Cursor cursor = DB.fetchUserData(Login.globalUsername);
        if (cursor.getCount() == 0) {
            no = "NO NUMBER";
        }
        else
        {
            //Gets the users GP's phone number from the database
            while (cursor.moveToNext())
            {
                no = (cursor.getString(6));
            }
        }
        //Sends Message to GP
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(no, null, mess, null, null);
    }

    @Override
    //Stops potential errors
    public void onBackPressed()
    {
        Toast.makeText(getApplicationContext(), "Please Use Back to Main Menu", Toast.LENGTH_SHORT).show();
        return;
    }
}
