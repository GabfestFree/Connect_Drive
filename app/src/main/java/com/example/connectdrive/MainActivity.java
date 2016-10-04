package com.example.connectdrive;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//main Activity
    TextView login_button;
    TextView register_button;
    int Userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       Login_Activity.prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Userid= Login_Activity.prefs.getInt("userid",0);
        if(Userid > 0)
        {
          Intent gotonaviactivity=new Intent(getApplicationContext(),Navigation_Activity.class);
            startActivity(gotonaviactivity);
            finish();
        }
        else {
            login_button = (TextView) findViewById(R.id.login_button);
            register_button = (TextView) findViewById(R.id.register_button);
            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotologin = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(gotologin);
                    finish();
                }
            });
            register_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoregister = new Intent(MainActivity.this, Register_Activity.class);
                    startActivity(gotoregister);
                    finish();
                }
            });
        }



    }


}
