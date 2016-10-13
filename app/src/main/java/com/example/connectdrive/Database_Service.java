package com.example.connectdrive;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;



public class Database_Service extends Service {
    private final IBinder mBinder = new LocalBinder();
    String username;
    String password;

    String result=null;

    public class LocalBinder extends Binder {
       Database_Service getService() {
            return Database_Service.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        username=intent.getStringExtra("username");
        return mBinder;
    }

    public String loginvalid(String user,String pass) {



        return result;
    }


    public String storedb(String firstname, String lastname, String email, String phone,String pass) {

            final String first_name = firstname;
            final String last_name=lastname;
            final String user_email=email;
          final String user_phone=phone;
            final String password = pass;


        return result;
    }




}