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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    public Database_Service mService;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog process;
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
        login_user_name = (EditText) findViewById(R.id.logi_user_name);
        login_pass_word = (EditText) findViewById(R.id.login_pass_word);
        firebaseAuth = FirebaseAuth.getInstance();
        process = new ProgressDialog(this);

        user_login_button = (Button) findViewById(R.id.userloginbutton);
        user_login_button.setOnClickListener(this);


    }

    private void loginValidation() {
        login_username=login_user_name.getText().toString().trim();
        login_password=login_pass_word.getText().toString().trim();
        if (TextUtils.isEmpty(login_username)) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(login_password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        process.setMessage("Signing Up...");
        process.show();
        firebaseAuth.signInWithEmailAndPassword(login_username, login_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    process.dismiss();
                    Intent gotonavigation = new Intent(getApplicationContext(), Navigation_Activity.class);
                    startActivity(gotonavigation);
                    finish();


                }
                else
                {
                    process.dismiss();
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        loginValidation();
    }
}


