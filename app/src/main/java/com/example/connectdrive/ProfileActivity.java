package com.example.connectdrive;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mdatabase;
    TextView profileemailtextview,profilephoneview;
    AppCompatImageView imgView;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";

    private FirebaseAuth firebaseAuth;
    FloatingActionButton btnSelectImage;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        String proftitle=intent.getStringExtra("Title");
        setTitle(proftitle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profileemailtextview=(TextView)findViewById(R.id.profileemailtextview);
        profilephoneview=(TextView)findViewById(R.id.profilephoneview);
        firebaseAuth = FirebaseAuth.getInstance();
        mdatabase= FirebaseDatabase.getInstance().getReference();

       imgView = (AppCompatImageView) findViewById(R.id.profilimageview);
        btnSelectImage = (FloatingActionButton) findViewById(R.id.fabinprofile);
        btnSelectImage.setOnClickListener(this);
        mdatabase.child("Users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String emailid=snapshot.child("email_id").getValue(String.class);
                String phone=snapshot.child("phone").getValue(String.class);
                profileemailtextview.setText(emailid);
               profilephoneview.setText(phone);
                //prints "Do you have data? You'll love Firebase."
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    imgView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    @Override
    public void onClick(View v) {
        openImageChooser();
    }
}
