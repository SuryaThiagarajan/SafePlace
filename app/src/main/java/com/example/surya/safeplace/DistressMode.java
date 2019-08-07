package com.example.surya.safeplace;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class DistressMode extends AppCompatActivity implements LocationListener {
    static String message = "Help Me!!!";
    private static final String FILE_NAME = "EmergencyContacts.txt";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_SMS = 99;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distress_mode);

        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            while ((text = br.readLine()) != null) {
                int i = 0;
                text = text.replaceAll("-", "");
                while (text.charAt(i) != ' ')
                    text = text.replaceFirst(String.valueOf(text.charAt(i)), "");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkAllPermissions();
    }

    public void checkAllPermissions()   {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
            ||ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(DistressMode.this,"Please grant permissions",Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_SMS);
            checkAllPermissions();

        }   else    {
            Toast.makeText(this, "Tracking location", Toast.LENGTH_LONG).show();
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 20 * 1000, 0, this);
        }
    }

    private void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
            return;
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override public void onLocationChanged(Location loc) {
        Toast.makeText(getBaseContext(),"on location changed",Toast.LENGTH_LONG).show();
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            while ((text = br.readLine()) != null) {
                int i = 0;
                text = text.replaceAll("-", "");
                while (text.charAt(i) != ' ')
                    text = text.replaceFirst(String.valueOf(text.charAt(i)), "");
                //text = text.replaceFirst(String.valueOf(text.charAt(i)), "");
                Toast.makeText(DistressMode.this, text, Toast.LENGTH_LONG).show();
                StringBuilder sb = new StringBuilder();
                sb.append("Help me, I'm at ").append("http://www.google.com/maps/place/").append(loc.getLatitude()).append(",").append(loc.getLongitude());
                String message = sb.toString();
                sendSMS(text, message);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
     }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(),"Your GPS is disabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(),"on provider enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(getBaseContext(),"on status changed",Toast.LENGTH_LONG).show();
    }
}



