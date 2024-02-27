package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initNavBar();
        initSettings();
        initSortByClick();
        initSortOrderClick();

    }

    private void initNavBar() {
        BottomNavigationView navBar = findViewById(R.id.navigation_bar);
        navBar.setSelectedItemId(R.id.settings_activity);
        navBar.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.list_activity) {
                replaceActivity(ListActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            if (item.getItemId() == R.id.calendar_activity) {
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


    private void replaceActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void initSettings() {
        SharedPreferences preferences = getSharedPreferences("MyToDoListPreferences", MODE_PRIVATE);
        String sortBy = preferences.getString("sortfield", "subject");
        String sortOrder = preferences.getString("sortorder", "ASC");

        RadioButton rbSubject = findViewById(R.id.radioSubject);
        RadioButton rbDate = findViewById(R.id.radioDate);
        RadioButton rbCriticality = findViewById(R.id.radioCriticality);

        if (sortBy.equalsIgnoreCase("subject")) {
            rbSubject.setChecked(true);
        } else if (sortBy.equalsIgnoreCase("date")) {
            rbDate.setChecked(true);
        } else {
            rbCriticality.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioAscending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);

        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        } else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSort);
        rgSortBy.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences preferences = getSharedPreferences("MyToDoListPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            RadioButton rbSubject = findViewById(R.id.radioSubject);
            RadioButton rbDate = findViewById(R.id.radioDate);
            RadioButton rbCriticality = findViewById(R.id.radioCriticality);

            if (rbSubject.isChecked()) {
                editor.putString("sortfield", "subject");
            } else if (rbDate.isChecked()) {
                editor.putString("sortfield", "date");
            } else if (rbCriticality.isChecked()) {
                editor.putString("sortfield", "criticality");
            }

            editor.apply();
        });
    }

    @SuppressLint("WrongViewCast")
    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupOrder);
        rgSortOrder.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences preferences = getSharedPreferences("MyToDoListPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            RadioButton rbAscending = findViewById(R.id.radioAscending);

            if (rbAscending.isChecked()) {
                editor.putString("sortorder", "ASC");
            } else {
                editor.putString("sortorder", "DESC");
            }

            editor.apply();
        });
    }
}