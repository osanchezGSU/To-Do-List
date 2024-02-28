package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Memo> memos;
    ToDoAdapter memoAdapter;
    RecyclerView memoList;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = memos.get(position).getId();
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            intent.putExtra("Id", id);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);
        initButtonAddMemo();
        initDeleteSwitch();
        initNavBar();

    }

    private void initButtonAddMemo () {
        Button newContact = findViewById(R.id.buttonAddMemo);
        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,
                        MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initDeleteSwitch () {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Boolean status = compoundButton.isChecked();
                memoAdapter.setDelete(status);
                memoAdapter.notifyDataSetChanged(); // redraws the list
            }
        });
    }

    private void initNavBar() {
        BottomNavigationView navBar = findViewById(R.id.navigation_bar);
        navBar.setSelectedItemId(R.id.list_activity);
        navBar.setOnItemSelectedListener(item -> {

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

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MyToDoListPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "subjectinput");
        String sortOrder = getSharedPreferences("MyToDoListPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        // Enter Listing 6.3 Simple List Activation Code
        ToDoDataSource ds = new ToDoDataSource(this);

        try {

            ds.open();
            memos = ds.getMemos(sortBy, sortOrder);
            ds.close();

            if (memos.size() > 0) {
                memoList = findViewById(R.id.rvMemos);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                memoList.setLayoutManager(layoutManager);
                memoAdapter = new ToDoAdapter(memos, this);
                memoAdapter.setOnItemClickListener(onItemClickListener);
                memoList.setAdapter(memoAdapter);
            } else {
                Intent intent = new Intent(ListActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving memos",
                    Toast.LENGTH_LONG).show();

        }
    }

}