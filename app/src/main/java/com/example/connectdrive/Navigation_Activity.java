package com.example.connectdrive;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;

public class Navigation_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Gmail_Fragment.OnFragmentInteractionListener,Yahoo_Fragment.OnFragmentInteractionListener {

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5, floatingActionButton6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_);
        Gmail_Fragment fragment = new Gmail_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_facebook);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_twitter);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_linkdin);
        floatingActionButton4 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_google_plus);
        floatingActionButton6 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_youtube);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent facebookIntent = getOpenFacebookIntent(Navigation_Activity.this);
                startActivity(facebookIntent);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent twitterIntent = getOpenTwitterIntent(Navigation_Activity.this);
                startActivity(twitterIntent);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Intent linkdinIntent = getOpenLinkdinIntent(Navigation_Activity.this);
                startActivity(linkdinIntent);
            }
        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent googleplusIntent = getOpenGPlusIntent(Navigation_Activity.this);
                startActivity(googleplusIntent);
            }
        });

        floatingActionButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Intent youtubeIntent = getOpenYouTubeIntent(Navigation_Activity.this);
                startActivity(youtubeIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Login_Activity.prefs = PreferenceManager.getDefaultSharedPreferences(Navigation_Activity.this);
            Login_Activity.prefs.edit().clear().commit();
            Intent gotologin=new Intent(getApplicationContext(),Login_Activity.class);
            startActivity(gotologin);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = getString(R.string.app_name);

      //Items are added
        if (id == R.id.gab_Gmail) {
            Gmail_Fragment fragment = new Gmail_Fragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

            title = "Gmail";


        } else if (id == R.id.gab_Ymail) {
            Yahoo_Fragment fragment = new Yahoo_Fragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

            title = "Ymail";

        } else if (id == R.id.gab_Hotmail) {

            title = "Hotmail";

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenTwitterIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.twitter.android", 0); //Checks if Twitter is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com")); //Trys to make intent with Twitter's's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenLinkdinIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.linkedin.android", 0); //Checks if Linkdin is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com")); //Trys to make intent with Linkdin's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenGPlusIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.google.android.apps.plus", 0); //Checks if G+ is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://plus.google.com")); //Trys to make intent with G+'s URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://plus.google.com")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenInstagramIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.instagram.android", 0); //Checks if Instagram is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/accounts/login/")); //Trys to make intent with Instagram's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/accounts/login/")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenYouTubeIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.google.android.whatsapp", 0); //Checks if YT is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.whatsapp.com")); //Trys to make intent with YT's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.whatsapp.com")); //catches and opens a url to the desired page
        }
    }




}
