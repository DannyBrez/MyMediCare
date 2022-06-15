package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.Set;

public class Mainmenu extends AppCompatActivity {

    private TextView helloName;
    private DBHelper DB;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private SharedPreferences sharedPreferences = null;
    private Settings s = new Settings();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Sets the users Default settings once logging in.
        sharedPreferences = getSharedPreferences(Login.globalUsername, 0);
        s.booleanValue = sharedPreferences.getBoolean("night_mode", s.booleanValue);
        if(s.booleanValue)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_mainmenu);

        //TextView Variable
        helloName = (TextView)findViewById(R.id.helloName);
        //DB Variable
        DB = new DBHelper(this);

        //Sets the "Hello" + firstName message onto the Main menu
        Cursor cursor = DB.fetchUserData(Login.globalUsername);
        if(cursor.getCount()==0) {
            helloName.setText("NO NAME");
        }
        else{
            while(cursor.moveToNext()) {
                helloName.setText("Hello " + cursor.getString(2));
            }
        }

        //Button Variables
        final Button enterReadingsButton = (Button)findViewById(R.id.enterReadingsButton);
        final Button modifyDetailsButton = (Button)findViewById(R.id.modifyDetailsButton);
        final Button userSettingsButton = (Button)findViewById(R.id.userSettingsButton);
        final Button logoutButton = (Button)findViewById(R.id.logoutButton);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(enterReadingsButton))
                {
                    startActivity(new Intent(Mainmenu.this, EnterReadings.class));
                }
                else if (v.equals(modifyDetailsButton))
                {
                    startActivity(new Intent(Mainmenu.this, UserDetails.class));
                }
                else if (v.equals(userSettingsButton))
                {
                    startActivity(new Intent(Mainmenu.this, Settings.class));
                }
                else if (v.equals(logoutButton))
                {
                    logout();
                }
            }
        };
        enterReadingsButton.setOnClickListener(listener);
        modifyDetailsButton.setOnClickListener(listener);
        userSettingsButton.setOnClickListener(listener);
        logoutButton.setOnClickListener(listener);

    }

    public void logout()
    {
        //Builds Dialog box for Logout
        dialogBuilder = new AlertDialog.Builder(this);
        final View logoutView = getLayoutInflater().inflate(R.layout.logout, null);

        final Button yesButton = (Button) logoutView.findViewById(R.id.yesButton);
        final Button noButton = (Button) logoutView.findViewById(R.id.noButton);

        dialogBuilder.setView(logoutView);
        dialog = dialogBuilder.create();
        dialog.show();

        View.OnClickListener saveCancel = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(yesButton)) {
                    startActivity(new Intent(Mainmenu.this, MainActivity.class));
                    dialog.dismiss();
                }
                else if (v.equals(noButton))
                {
                    dialog.dismiss();
                }
            }
        };
        yesButton.setOnClickListener(saveCancel);
        noButton.setOnClickListener(saveCancel);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please Use Logout", Toast.LENGTH_SHORT).show();
        return;
    }
}