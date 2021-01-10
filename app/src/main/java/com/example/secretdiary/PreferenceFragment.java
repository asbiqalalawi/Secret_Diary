package com.example.secretdiary;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String DEFAULT_VALUE = "Tidak Ada";
    private String NAME;
    private String EMAIL;

    private EditTextPreference namePreference;
    private EditTextPreference emailPreference;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummatrise();
    }

    private void init() {
        NAME = getResources().getString(R.string.key_name);
        EMAIL = getResources().getString(R.string.key_email);

        namePreference = (EditTextPreference) findPreference(NAME);
        emailPreference = (EditTextPreference) findPreference(EMAIL);
    }

    private void setSummatrise() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        namePreference.setSummary(sh.getString(NAME, DEFAULT_VALUE));
        emailPreference.setSummary(sh.getString(EMAIL, DEFAULT_VALUE));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(NAME)) {
            namePreference.setSummary(sharedPreferences.getString(NAME, DEFAULT_VALUE));
        }
        if (key.equals(EMAIL)) {
            emailPreference.setSummary(sharedPreferences.getString(EMAIL, DEFAULT_VALUE));
        }
    }

}
