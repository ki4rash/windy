package com.windy.windyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class sp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isConnected(sp.this))
            buildDialog(sp.this).show();
        else {

            setContentView(R.layout.activity_sp);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(sp.this, "Connecting..", Toast.LENGTH_SHORT).show();
                    Toast.makeText(sp.this, "Please wait!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(sp.this, "You're Welcome", Toast.LENGTH_SHORT).show();
                }
            }, 800);

//          Toast.makeText(SplashSpeedActivity.this, "Connecting..Please wait!", Toast.LENGTH_LONG).show();
//          Toast.makeText(SplashSpeedActivity.this, "Connecting..", Toast.LENGTH_SHORT).show();
//          Toast.makeText(SplashSpeedActivity.this, "Please wait!", Toast.LENGTH_SHORT).show();
//          Toast.makeText(SplashSpeedActivity.this, "You're Welcome", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent splashSpeedToMainActivity = new Intent(sp.this, MainActivity.class);
                    startActivity(splashSpeedToMainActivity);
                    finish();
                }
            }, 7000);

        }

    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please make sure you are connected to the internet." + "\n" + "Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
}
