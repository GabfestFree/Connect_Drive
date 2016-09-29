package com.example.connectdrive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register_Activity extends AppCompatActivity {

    public Database_Service mService;
    boolean mBound = false;
    Intent gotoserviceintent;
    Button register_register_button;
    EditText firstnametext;
    EditText lastnametext;
    EditText emailtext;
    EditText phonetext;
    EditText passwordtext;
    String firstname;
    String lastname;
    String emailid;
    String phonenum;
    String passwordd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstnametext=(EditText)findViewById(R.id.firstnametext);
        lastnametext=(EditText)findViewById(R.id.lastnametext);
        emailtext=(EditText)findViewById(R.id.emailtext);
        phonetext=(EditText)findViewById(R.id.phonetext);
        passwordtext=(EditText)findViewById(R.id.passwordtext);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        register_register_button=(Button) findViewById(R.id.registe_register_button);
        register_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname=firstnametext.getText().toString();
                lastname=lastnametext.getText().toString();
                emailid=emailtext.getText().toString();
                phonenum=phonetext.getText().toString();
                passwordd=passwordtext.getText().toString();
                String result = mService.storedb(firstname,lastname,emailid,phonenum,passwordd);
                if(result.equals("Success")) {
                    Intent gotonavigation = new Intent(getApplicationContext(), Navigation_Activity.class);
                    startActivity(gotonavigation);
                    finish();
                }
                else
                {
                    Log.i("Login Status",result);
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        gotoserviceintent= new Intent(this, Database_Service.class);
        bindService(gotoserviceintent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Database_Service.LocalBinder binder = (Database_Service.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}


