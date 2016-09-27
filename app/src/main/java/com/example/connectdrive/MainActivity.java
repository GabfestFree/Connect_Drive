package com.example.connectdrive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//main Activity
    TextView login_button;
    TextView register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button= (TextView) findViewById(R.id.login_button);
        register_button=(TextView)findViewById(R.id.register_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin=new Intent(MainActivity.this,Login_Activity.class);
                startActivity(gotologin);
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregister=new Intent(MainActivity.this,Register_Activity.class);
                startActivity(gotoregister);
            }
        });
    }
}
