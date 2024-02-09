package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.to_dolist.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavBar();
        Button lowPriority = findViewById(R.id.lowPriorityButton);
        Button mediumPriority = findViewById(R.id.mediumPriorityButton);
        Button highPriority = findViewById(R.id.highPriorityButton);

        lowPriority.setOnClickListener(this);

        mediumPriority.setOnClickListener(this);

        highPriority.setOnClickListener(this);
    }
    private void initNavBar() {
        BottomNavigationView navBar = findViewById(R.id.navigation_bar);
        navBar.setSelectedItemId(0);
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
        Intent intent = new Intent(MainActivity.this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    @Override
    public void onClick(View v) {
        ImageView firstDot = findViewById(R.id.firstDot);
        ImageView secondDot = findViewById(R.id.secondDot);
        ImageView thirdDot = findViewById(R.id.thirdDot);
        LinearLayout linearLayout = findViewById(R.id.criticality_layout);

        if (v.getId() == R.id.lowPriorityButton){
            firstDot.setImageResource(R.drawable.low_priority);
            secondDot.setImageResource(R.drawable.circle_outline);
            thirdDot.setImageResource(R.drawable.circle_outline);
            linearLayout.setVisibility(View.VISIBLE);
        }
        else if (v.getId() == R.id.mediumPriorityButton){
            firstDot.setImageResource(R.drawable.medium_priority);
            secondDot.setImageResource(R.drawable.medium_priority);
            thirdDot.setImageResource(R.drawable.circle_outline);
            linearLayout.setVisibility(View.VISIBLE);

        }
        else if (v.getId() == R.id.highPriorityButton){
            firstDot.setImageResource(R.drawable.high_priority);
            secondDot.setImageResource(R.drawable.high_priority);
            thirdDot.setImageResource(R.drawable.high_priority);
            linearLayout.setVisibility(View.VISIBLE);

        }
    }
}