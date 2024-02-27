package com.example.to_dolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.SaveDateListener {

    private Memo currentMemo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavBar();
        initToggleButton();
        initSaveButton();
        setForEditing(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            initMemo(extras.getInt("Id"));
        }
        else {

            currentMemo = new Memo();
        }
        initChangeDateButton();
        Button lowPriority = findViewById(R.id.lowPriorityButton);
        Button mediumPriority = findViewById(R.id.mediumPriorityButton);
        Button highPriority = findViewById(R.id.highPriorityButton);


        lowPriority.setOnClickListener(this);

        mediumPriority.setOnClickListener(this);

        highPriority.setOnClickListener(this);
        initTextChangeEvents();

    }

    private void initMemo(int id) {
            ToDoDataSource ds = new ToDoDataSource(MainActivity.this);
        try {
            ds.open();
            currentMemo = ds.getSpecificMemo(id);
            ds.close();
        }
        catch (Exception e) {
            Toast.makeText(this, "Load Contact Failed", Toast.LENGTH_LONG).show();
        }
        Integer criticality = currentMemo.getCriticality();
        Calendar unformattedDate = currentMemo.getDate();

        LinearLayout linearLayout = findViewById(R.id.criticality_layout);
        ImageView firstDot = findViewById(R.id.firstDot);
        ImageView secondDot = findViewById(R.id.secondDot);
        ImageView thirdDot = findViewById(R.id.thirdDot);
        EditText editSubject = findViewById(R.id.subjectInput);
        EditText editMemo = findViewById(R.id.memoInput);
        TextView date = findViewById(R.id.dateTextView);

        if (criticality == 1){
            firstDot.setImageResource(R.drawable.low_priority);
            secondDot.setImageResource(R.drawable.circle_outline);
            thirdDot.setImageResource(R.drawable.circle_outline);
            linearLayout.setVisibility(View.VISIBLE);
        }
        else if (criticality == 2){
            firstDot.setImageResource(R.drawable.medium_priority);
            secondDot.setImageResource(R.drawable.medium_priority);
            thirdDot.setImageResource(R.drawable.circle_outline);
            linearLayout.setVisibility(View.VISIBLE);
        }
        else if (criticality == 3){
            firstDot.setImageResource(R.drawable.high_priority);
            secondDot.setImageResource(R.drawable.high_priority);
            thirdDot.setImageResource(R.drawable.high_priority);
            linearLayout.setVisibility(View.VISIBLE);

        }


        date.setText(DateFormat.format("MM/dd/yyyy", unformattedDate));
        editSubject.setText(currentMemo.getSubjectInput());
        editMemo.setText(currentMemo.getMemoInput());

    }

    private void initNavBar() {
        BottomNavigationView navBar = findViewById(R.id.navigation_bar);
        navBar.setSelectedItemId(0);
        navBar.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.list_activity){
                replaceActivity(ListActivity.class);
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
            System.out.println("Low Priority Button Pressed");
            currentMemo.setCriticality(1);
        }
        else if (v.getId() == R.id.mediumPriorityButton){
            firstDot.setImageResource(R.drawable.medium_priority);
            secondDot.setImageResource(R.drawable.medium_priority);
            thirdDot.setImageResource(R.drawable.circle_outline);
            linearLayout.setVisibility(View.VISIBLE);
            currentMemo.setCriticality(2);
        }
        else if (v.getId() == R.id.highPriorityButton){
            firstDot.setImageResource(R.drawable.high_priority);
            secondDot.setImageResource(R.drawable.high_priority);
            thirdDot.setImageResource(R.drawable.high_priority);
            linearLayout.setVisibility(View.VISIBLE);
            currentMemo.setCriticality(3);
        }

    }
    private void initToggleButton() {
        final ToggleButton editToggle = findViewById(R.id.editToggleButton);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setForEditing(editToggle.isChecked());
            }
        });
    }
    private void setForEditing(boolean isEnabled) {
        EditText editSubject = findViewById(R.id.subjectInput);
        EditText editMemo = findViewById(R.id.memoInput);
        Button buttonDateDialog = findViewById(R.id.dateButton);
        Button buttonSave = findViewById(R.id.saveButton);
        Button lowPriority = findViewById(R.id.lowPriorityButton);
        Button mediumPriority = findViewById(R.id.mediumPriorityButton);
        Button highPriority = findViewById(R.id.highPriorityButton);

        editSubject.setEnabled(isEnabled);
        editMemo.setEnabled(isEnabled);
        buttonDateDialog.setEnabled(isEnabled);
        lowPriority.setEnabled(isEnabled);
        mediumPriority.setEnabled(isEnabled);
        highPriority.setEnabled(isEnabled);
        buttonSave.setEnabled(isEnabled);


        if (isEnabled) {
            editSubject.requestFocus();
        }
    }

    private void initChangeDateButton() {
        Button changeDate = findViewById(R.id.dateButton);
        changeDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();

                datePickerDialog.show(fm, "DatePick");
            }
        });
    }

    @Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView birthday = findViewById(R.id.dateTextView);
        birthday.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        currentMemo.setDate(selectedTime);

    }
    public void initTextChangeEvents(){
      final EditText editSubject = findViewById(R.id.subjectInput);

      editSubject.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {
              currentMemo.setSubjectInput(editSubject.getText().toString());

          }
      });

      final EditText editMemo = findViewById(R.id.memoInput);
      editMemo.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {
            currentMemo.setMemoInput(editMemo.getText().toString());
          }
      });

    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button Pressed", Toast.LENGTH_LONG).show();
                boolean wasSuccessful;
                ToDoDataSource ds = new ToDoDataSource(MainActivity.this);
                try {
                    ds.open();
                    if (currentMemo.getId() == -1) {
                        wasSuccessful = ds.insertMemo(currentMemo);
                    }
                    else {
                        wasSuccessful = ds.updateMemo(currentMemo);
                    }
                    ds.close();
                }
                catch (Exception e) {
                    wasSuccessful = false;
                }
                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.editToggleButton);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }
}