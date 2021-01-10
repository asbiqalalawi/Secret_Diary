package com.example.secretdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Setelan");

        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new PreferenceFragment()).commit();
    }
}