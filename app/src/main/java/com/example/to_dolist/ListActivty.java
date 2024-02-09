package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.to_dolist.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activty);
        initNavBar();

    }
    private void initNavBar() {
        BottomNavigationView navBar = findViewById(R.id.navigation_bar);
        navBar.setSelectedItemId(R.id.list_activity);
        navBar.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.list_activity){
                replaceActivity(ListActivty.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            if (item.getItemId() == R.id.calendar_activity){
                replaceActivity(CalendarActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            if (item.getItemId() == R.id.settings_activity) {
                replaceActivity(SettingsActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            return true;
        });
    }


    private void replaceActivity(Class activity){
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}