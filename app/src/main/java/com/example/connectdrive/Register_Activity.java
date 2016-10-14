package com.example.connectdrive;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener{

    public Database_Service mService;
    private ProgressDialog process;
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
    private FirebaseAuth firebaseAuth;


    String uid;

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
        firebaseAuth=FirebaseAuth.getInstance();
        process=new ProgressDialog(this);


        register_register_button=(Button) findViewById(R.id.registe_register_button);
        register_register_button.setOnClickListener(this);
    }

    private void register() {
        firstname=firstnametext.getText().toString().trim();
        lastname=lastnametext.getText().toString().trim();
        emailid=emailtext.getText().toString().trim();
        phonenum=phonetext.getText().toString().trim();
        passwordd=passwordtext.getText().toString().trim();
        if (TextUtils.isEmpty(emailid)) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(passwordd)&& passwordd.length()< 8) {
            Toast.makeText(this, "Please enter password or Enter atleast 8 charecters", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(phonenum)) {
            Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_LONG).show();
            return;
        }
        process.setMessage("Registering...");
        process.show();
        firebaseAuth.createUserWithEmailAndPassword(emailid,passwordd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    process.dismiss();
                    uid=firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference  mchild= FirebaseDatabase.getInstance().getReference().child("Users");
                    DatabaseReference nextchild=mchild.child(uid);
                    nextchild.child("email_id").setValue(emailid);
                   nextchild.child("Password").setValue(passwordd);
                    nextchild.child("first_name").setValue(firstname);
                    nextchild.child("last_name").setValue(lastname);
                  nextchild.child("phone").setValue(phonenum);
                    process.dismiss();
                    Intent gotonavigation = new Intent(getApplicationContext(), Navigation_Activity.class);
                    startActivity(gotonavigation);
                    process.dismiss();
                    finish();


                }
                else
                {
                    process.dismiss();
                    Toast.makeText(getApplicationContext(),"Registering Failed", Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        register();
    }
}


