package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    //Variables
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor;
    public boolean booleanValue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //SharePrefs
        sharedPreferences = getSharedPreferences(Login.globalUsername, MODE_PRIVATE);
        booleanValue = sharedPreferences.getBoolean("night_mode", booleanValue);
        Boolean finalBooleanValue = booleanValue;

        //Button Variables
        final Button BacktoMainMenuButton = (Button) findViewById(R.id.backButton);
        final Button helpButton = (Button) findViewById(R.id.helpButton);
        final Button themeButton = (Button) findViewById(R.id.themeButton);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(BacktoMainMenuButton)) {
                    startActivity(new Intent(Settings.this, Mainmenu.class));
                }
                else if (v.equals(helpButton))
                {
                    startActivity(new Intent(Settings.this, Help.class));
                }
                else if (v.equals(themeButton))
                {
                    if(!finalBooleanValue)
                    {
                        //Sets the application theme to Dark Theme and updates the users SharedPrefs
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor = sharedPreferences.edit();
                        editor.putBoolean("night_mode", true);
                        editor.commit();
                    }
                    else
                    {
                        //Sets the application theme to Default Theme and updates the users SharedPrefs
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor = sharedPreferences.edit();
                        editor.putBoolean("night_mode", false);
                        editor.commit();
                    }
                }
            }
        };
        BacktoMainMenuButton.setOnClickListener(listener);
        helpButton.setOnClickListener(listener);
        themeButton.setOnClickListener(listener);
    }
}