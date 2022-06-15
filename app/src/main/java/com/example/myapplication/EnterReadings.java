package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterReadings extends AppCompatActivity {

    //Popup builder Variables
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newtemppopup_temperature, newbppopup_sysPressure, newbppopup_diaPressure,  newheartpopup_heartrate;
    private TextView tempOutput, sysOutput, diaOutput, heartOutput;
    private Button newpopup_cancel, newpopup_save, newpopup_cels;
    private boolean cels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_readings);

        //Button Variables
        final Button BackToMainMenuButton = (Button)findViewById(R.id.backButton1);
        final Button tempReadingButton = (Button)findViewById(R.id.temperatureReading);
        final Button bpReadingButton = (Button)findViewById(R.id.bloodPressureReading);
        final Button heartReadingButton = (Button)findViewById(R.id.heartrateReading);
        final Button showRecords = (Button)findViewById(R.id.records);
        final Button calculateReadingButton = (Button)findViewById(R.id.CalculateReadings);

        //Reading Variables
        TextView tempOutput = (TextView) findViewById(R.id.tempOutput);
        TextView heartOutput = (TextView) findViewById(R.id.heartOutput);
        TextView sysOutput = (TextView) findViewById(R.id.sysOutput);
        TextView diaOutput = (TextView) findViewById(R.id.diaOutput);

        //Cels is set to true by default
        cels = true;

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(BackToMainMenuButton))
                {
                    startActivity(new Intent(EnterReadings.this, Mainmenu.class));
                }
                else if (v.equals(tempReadingButton))
                {
                    createTemperatureReading();
                    cels = true;
                }
                else if (v.equals(bpReadingButton))
                {
                    createBloodPressureReading();
                }
                else if (v.equals(heartReadingButton))
                {
                    createHeartrateReading();
                }
                else if (v.equals(showRecords))
                {
                   startActivity(new Intent(EnterReadings.this, Records.class));
                }
                else if (v.equals(calculateReadingButton))
                {
                    //If no readings have been entered by the user calculateReadings() will not run
                    if (tempOutput.getText().toString().equals("N/A") && sysOutput.getText().toString().equals("N/A") && diaOutput.getText().toString().equals("N/A") && heartOutput.getText().toString().equals("N/A"))
                    {
                        Toast.makeText(EnterReadings.this, "Please enter one of the readings", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        calculateReadings();
                    }
                }
            }
        };
        BackToMainMenuButton.setOnClickListener(listener);
        tempReadingButton.setOnClickListener(listener);
        bpReadingButton.setOnClickListener(listener);
        heartReadingButton.setOnClickListener(listener);
        showRecords.setOnClickListener(listener);
        calculateReadingButton.setOnClickListener(listener);
    }

    public void createTemperatureReading()
    {
        //Builds Dialog box for temperature reading
        dialogBuilder = new AlertDialog.Builder(this);
        final View tempPopupView = getLayoutInflater().inflate(R.layout.enter_temperature_reading, null);
        newtemppopup_temperature = (EditText) tempPopupView.findViewById(R.id.newtemppopup_temperature);

        newpopup_save = (Button) tempPopupView.findViewById(R.id.saveButton);
        newpopup_cancel = (Button) tempPopupView.findViewById(R.id.cancelButton);
        newpopup_cels = (Button) tempPopupView.findViewById(R.id.celsButton);

        dialogBuilder.setView(tempPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        View.OnClickListener saveCancel = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(newpopup_save))
                {
                    //Gets and sets the temperature to the TextView on the enter_readings.xml
                    tempOutput = (TextView) findViewById(R.id.tempOutput);
                    String enteredTemp = newtemppopup_temperature.getText().toString();

                    //Stops the user from saving an empty reading
                    if(enteredTemp.matches(""))
                    {
                        Toast.makeText(EnterReadings.this, "Please enter a reading", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Converts readings into Integers
                        float tempNumber = Float.parseFloat(enteredTemp);
                        //If celsius
                        if(cels)
                        {
                            //Lowest possible temperature couldn't be less than 15C
                            if (tempNumber < 15 || tempNumber > 42)
                            {
                                Toast.makeText(EnterReadings.this, "Your temperature cannot be under 15C or over 42C", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                dialog.dismiss();
                                tempOutput.setText(newtemppopup_temperature.getText().toString());
                            }
                        }
                        //If fahrenheit
                        else
                        {
                            //Lowest possible temperature couldn't be less than 59F
                            if (tempNumber < 59 || tempNumber > 107.6)
                            {
                                Toast.makeText(EnterReadings.this, "Your temperature cannot be under 59F or over 107.6F", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                dialog.dismiss();
                                tempOutput.setText(newtemppopup_temperature.getText().toString());
                            }
                        }
                    }
                }
                else if (v.equals(newpopup_cancel))
                {
                    dialog.dismiss();
                    cels = true;
                }
                else if (v.equals(newpopup_cels))
                {
                    if(cels)
                    {
                        cels = false;
                        newpopup_cels.setText("Fahrenheit");
                    }
                    else
                    {
                        cels = true;
                        newpopup_cels.setText("Celsius");
                    }
                }
            }
        };
        newpopup_save.setOnClickListener(saveCancel);
        newpopup_cancel.setOnClickListener(saveCancel);
        newpopup_cels.setOnClickListener(saveCancel);
    }
    public void createBloodPressureReading()
    {
        //Builds dialog box for blood pressure reading
        dialogBuilder = new AlertDialog.Builder(this);
        final View bpPopupView = getLayoutInflater().inflate(R.layout.enter_bloodpressure_reading, null);
        newbppopup_sysPressure = (EditText) bpPopupView.findViewById(R.id.newbppopup_bloodpressure);
        newbppopup_diaPressure = (EditText) bpPopupView.findViewById(R.id.newbppopup_bloodpressure1);

        newpopup_save = (Button) bpPopupView.findViewById(R.id.saveButton);
        newpopup_cancel = (Button) bpPopupView.findViewById(R.id.cancelButton);

        dialogBuilder.setView(bpPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        View.OnClickListener saveCancel = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(newpopup_save))
                {
                    //Gets and sets the blood pressure to the TextView on the enter_readings.xml
                    sysOutput = (TextView) findViewById(R.id.sysOutput);
                    diaOutput = (TextView) findViewById(R.id.diaOutput);
                    //Converts readings into Strings
                    String enteredSys = newbppopup_sysPressure.getText().toString();
                    String enteredDia = newbppopup_diaPressure.getText().toString();

                    //Stops users from entering null values
                    if (enteredSys.matches("") || enteredDia.matches(""))
                    {
                        Toast.makeText(EnterReadings.this, "Please enter a reading", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Converts readings into Integers
                        float sysNumber = Float.parseFloat(enteredSys);
                        float diaNumber = Float.parseFloat(enteredDia);
                        //Systolic Pressure cannot be under 70 or over 190 and Diastolic Pressure cannot be under 40 or over 100
                        if (sysNumber < 70 || diaNumber < 40 || sysNumber > 190 || diaNumber > 100)
                        {
                            Toast.makeText(EnterReadings.this, "Your blood pressure cannot be under 70 / 40 or over 190 / 100", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            dialog.dismiss();
                            sysOutput.setText(newbppopup_sysPressure.getText().toString());
                            diaOutput.setText(newbppopup_diaPressure.getText().toString());
                        }
                    }
                }
                else if (v.equals(newpopup_cancel))
                {
                    dialog.dismiss();
                }
            }
        };
        newpopup_save.setOnClickListener(saveCancel);
        newpopup_cancel.setOnClickListener(saveCancel);
    }

    public void createHeartrateReading()
    {
        //Builds dialog box for blood pressure reading
        dialogBuilder = new AlertDialog.Builder(this);
        final View heartPopup = getLayoutInflater().inflate(R.layout.enter_heartrate_reading, null);
        newheartpopup_heartrate = (EditText) heartPopup.findViewById(R.id.newheartpopup_heartrate);

        newpopup_save = (Button) heartPopup.findViewById(R.id.saveButton);
        newpopup_cancel = (Button) heartPopup.findViewById(R.id.cancelButton);

        dialogBuilder.setView(heartPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        View.OnClickListener saveCancel = new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                if (v.equals(newpopup_save))
                {
                    //Gets and sets the heartrate to the TextView on the enter_readings.xml
                    heartOutput = (TextView) findViewById(R.id.heartOutput);
                    String enteredHeart = newheartpopup_heartrate.getText().toString();
                    //Stops users from entering null values
                    if (enteredHeart.matches(""))
                    {
                        Toast.makeText(EnterReadings.this, "Please enter a reading", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                    //Converts readings into Integers
                    float heartNumber = Float.parseFloat(enteredHeart);
                    //Heartrate cannot be under 30 or over 200
                    if (heartNumber < 10 || heartNumber > 500)
                        {
                            Toast.makeText(EnterReadings.this, "Your heartrate cannot be under 10BPM or over 500BPM.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            dialog.dismiss();
                            heartOutput.setText(newheartpopup_heartrate.getText().toString());
                        }
                    }
                }
                else if (v.equals(newpopup_cancel))
                {
                    dialog.dismiss();
                }
            }
        };
        newpopup_save.setOnClickListener(saveCancel);
        newpopup_cancel.setOnClickListener(saveCancel);
    }

    public void calculateReadings() {

        Intent myIntent = new Intent(EnterReadings.this, ReadingResults.class);

        //Temperature Variables
        tempOutput = (TextView) findViewById(R.id.tempOutput);
        String tempStr = tempOutput.getText().toString();
        String tempLabel;

        //Blood Pressure Variables
        sysOutput = (TextView) findViewById(R.id.sysOutput);
        String sysStr = sysOutput.getText().toString();
        diaOutput = (TextView) findViewById(R.id.diaOutput);
        String diaStr = diaOutput.getText().toString();
        String bpLabel;
        String bloodPressureStr = sysStr + " / " + diaStr;

        //Heartrate Variables
        heartOutput = (TextView) findViewById(R.id.heartOutput);
        String heartStr = heartOutput.getText().toString();
        String heartLabel;

        //Temperature Calculations
            if (tempStr != null && !"N/A".equals(tempStr)) {
                float tempValue = Float.parseFloat(tempStr);
                float temp = tempValue;

                //If user has cels to true
                if (cels)
                {
                    //Low temperature
                    if (Float.compare(temp, 35f) <= 0)
                    {
                        tempLabel = ("Hypothermia / Low temperature");
                    }
                    //High temperature
                    else if (Float.compare(temp, 38f) >= 0)
                    {
                        tempLabel = ("Fever / High temperature");
                    }
                    //Normal
                    else
                    {
                        tempLabel = ("Normal temperature");
                    }
                    tempStr = tempStr + " C";
                }
                //If user has cels to false
                else
                {
                    //Low temperature
                    if (Float.compare(temp, 95f) <= 0)
                    {
                        tempLabel = ("Hypothermia / Low temperature");
                    }
                    //High temperature
                    else if (Float.compare(temp, 100.4f) >= 0)
                    {
                        tempLabel = ("Fever / High temperature");
                    }
                    //Normal temperature
                    else
                    {
                        tempLabel = ("Normal temperature");
                    }
                    tempStr = tempStr + " F";
                }
            }
            else
            {
                tempLabel = "No User Reading Given";
            }
        //Sets the temperature number and label into the Intent
        myIntent.putExtra("tempNum", tempStr);
        myIntent.putExtra("tempReading", tempLabel);

        //Blood Pressure Calculations
        if (sysStr != null && !"N/A".equals(sysStr) || diaStr != null && !"N/A".equals(diaStr))
        {
            float sysValue = Float.parseFloat(sysStr);
            float sys = sysValue;

            float diaValue = Float.parseFloat(diaStr);
            float dia = diaValue;

            //Low blood pressure
            if (Float.compare(sys, 89.99f) <= 0 && Float.compare(dia, 59.99f) <= 0 )
            {
                bpLabel = ("Hypotension / Low blood pressure");
            }
            //High blood pressure
            else if (Float.compare(sys, 120.01f) >= 0 || Float.compare(dia, 80.01f) >= 0)
            {
                bpLabel = ("Hypertension / High blood pressure");
            }
            //Normal blood pressure
            else
            {
                bpLabel = ("Normal blood pressure");
            }
        }
        else bpLabel = "No User Reading Given";
        //Sets the blood pressure number and label into the Intent
        myIntent.putExtra("bpNum", bloodPressureStr);
        myIntent.putExtra("bpReading", bpLabel);

        //Heartrate Calculations
        if (heartStr != null && !"N/A".equals(heartStr))
        {
            float heartValue = Float.parseFloat(heartStr);
            float heartrate = heartValue;

            //Low heartrate
            if (Float.compare(heartrate, 59.99f) <= 0)
            {
                heartLabel = ("Low heartrate");
            }
            //High heartrate
            else if (Float.compare(heartrate, 100.01f) >= 0)
            {
                heartLabel = ("High heartrate");
            }
            //Normal heartrate
            else
                {
                heartLabel = ("Normal heartrate");
            }
            heartStr = heartStr + "bpm";
        }
        else heartLabel = "No User Reading Given";
        //Sets the heartrate number and label into the Intent
        myIntent.putExtra("heartNum", heartStr);
        myIntent.putExtra("heartReading", heartLabel);

        //Runs the Intent was calculations are given
        startActivity(myIntent);
    }
}
