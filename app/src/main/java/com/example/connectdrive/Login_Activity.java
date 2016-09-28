package com.example.connectdrive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_Activity extends AppCompatActivity {

    public Database_Service mService;
    boolean mBound = false;
    Intent gotoserviceintent;

   Button user_login_button;
    EditText login_user_name;
    EditText login_pass_word;
    String login_username;
    String login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        login_user_name=(EditText)findViewById(R.id.logi_user_name);
        login_pass_word=(EditText)findViewById(R.id.login_pass_word);
        login_username=login_user_name.getText().toString();
        login_password=login_pass_word.getText().toString();
        user_login_button=(Button) findViewById(R.id.userloginbutton);
       user_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = mService.getRandomNumber("manikandan", "7639965633");
                Log.i("Login Status",result);
                if(result.equals("success")) {
                    Intent gotonavigation = new Intent(getApplicationContext(), Navigation_Activity.class);
                    startActivity(gotonavigation);
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
        gotoserviceintent.putExtra("username",login_username);
        gotoserviceintent.putExtra("password",login_password);
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


