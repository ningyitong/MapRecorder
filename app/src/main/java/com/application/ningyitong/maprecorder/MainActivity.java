package com.application.ningyitong.maprecorder;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    // user session
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // session class instance
        session = new UserSessionManager(getApplicationContext());
        // Check user login status
        if (session.checkLogin()) {
            finish();
        }
        setContentView(R.layout.activity_main);

        // Check and set permission
        permissionState();

        // Setup bottom nav-bar
        setupBottomNavbar();
    }

    private void setupBottomNavbar() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }

    private void permissionState() {
        // Check SDK version
        if (Build.VERSION.SDK_INT < 23)
            return;

        // Check device permissions
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION }, 0);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    break;
                case R.id.navigation_map:
                    Intent intent_map = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent_map);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.navigation_edit:
                    Intent intent_edit = new Intent(MainActivity.this, EditActivity.class);
                    startActivity(intent_edit);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.navigation_account:
                    Intent intent_account = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(intent_account);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
            }
            return false;
        }
    };



}
