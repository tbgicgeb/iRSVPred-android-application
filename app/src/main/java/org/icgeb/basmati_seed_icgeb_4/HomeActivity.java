package org.icgeb.basmati_seed_icgeb_3;



import android.content.Intent;


import android.os.Bundle;


import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class HomeActivity extends YouTubeBaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private YouTubePlayerView youTubePlayerView1, youTubePlayerView2;
    private YouTubePlayer.OnInitializedListener onInitializedListener;


    private static final String TAG = "HomeActivity";
boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        bottomNav.setSelectedItemId(R.id.nav_home);

        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);


        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // Youtube Activity

        youTubePlayerView1 = findViewById(R.id.youtubeView1);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("CiS2gY_9l-o");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        Button btplay1, btplay2;

        btplay1 = findViewById(R.id.playButton1);

        btplay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                youTubePlayerView1.initialize("Your Youtube API Key", onInitializedListener);
            }
        });


        //Button Upload

        Button upload = findViewById(R.id.home_bt_3);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ServerConnectionActivity.class);
                startActivity(intent);
            }
        });




    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_rice_variety:
                startActivity(new Intent(getApplicationContext(), RiceVarietyActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_stat:
                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_developers:
                startActivity(new Intent(getApplicationContext(), DevelopersActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_contact_us:
                startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_help:
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=org.icgeb.basmati_seed_icgeb_3";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "BasPred - Basmati Seed Predictor");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_home:
                    return true;

                case R.id.nav_about:
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.nav_algo:
                    startActivity(new Intent(getApplicationContext(), AlgoActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return false;

        }
    };


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}






