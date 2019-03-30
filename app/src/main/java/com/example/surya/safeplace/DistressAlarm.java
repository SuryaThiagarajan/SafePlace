package com.example.surya.safeplace;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DistressAlarm extends AppCompatActivity {
    Button b1, b2;
    String message = "HELP ME!!!";
    private static final String FILE_NAME = "EmergencyContacts.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distress_alarm);
        b1 = (Button) findViewById(R.id.yes);
        b2 = (Button) findViewById(R.id.no);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DistressAlarm.this,DistressMode.class);
                startActivity(it);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DistressAlarm.this,"Nothing here",Toast.LENGTH_LONG).show();
                FileOutputStream fis = null;
                try {
                    fis = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    String s1 = "";
                    fis.write(s1.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}