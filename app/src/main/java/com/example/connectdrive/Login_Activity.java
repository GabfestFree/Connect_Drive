package com.example.connectdrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Login_Activity extends AppCompatActivity {

   Button user_login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_);
        user_login_button=(Button) findViewById(R.id.userloginbutton);
       user_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonavigation=new Intent(getApplicationContext(),Navigation_Activity.class);
                startActivity(gotonavigation);
            }
        });

    }

}
