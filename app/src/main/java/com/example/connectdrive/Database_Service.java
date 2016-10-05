package com.example.connectdrive;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class Database_Service extends Service {
    private final IBinder mBinder = new LocalBinder();
    String username;
    String password;
   private FirebaseAuth firebaseAuth;
    private DatabaseReference mchild;
    String result=null;

    public class LocalBinder extends Binder {
       Database_Service getService() {
            return Database_Service.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        firebaseAuth = FirebaseAuth.getInstance();
        mchild= FirebaseDatabase.getInstance().getReference().child("Users");
        username=intent.getStringExtra("username");
        return mBinder;
    }

    public String loginvalid(String user,String pass) {

        firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                             result="success";

                        }
                    }

                });

        return result;
    }


    public String storedb(String firstname, String lastname, String email, String phone,String pass) {

            final String first_name = firstname;
            final String last_name=lastname;
            final String user_email=email;
          final String user_phone=phone;
            final String password = pass;
        firebaseAuth.createUserWithEmailAndPassword(user_email,password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //start the profile activity
                    //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    //Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
                    String uid=firebaseAuth.getCurrentUser().getUid();
                   DatabaseReference nextchild=  mchild.child(uid);
                    nextchild.child("email_id").setValue(user_email);
                    nextchild.child("Password").setValue(password);
                    nextchild.child("first_name").setValue(first_name);
                    nextchild.child("last_name").setValue(last_name);
                    nextchild.child("phone").setValue(user_phone);
                    result="success";

                }

            }

        });

        return result;
    }




}