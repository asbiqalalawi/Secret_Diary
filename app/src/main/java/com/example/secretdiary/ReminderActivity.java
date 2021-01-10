package com.example.secretdiary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.secretdiary.reminder.DatePickerFragment;
import com.example.secretdiary.reminder.ReminderReceiver;
import com.example.secretdiary.reminder.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private ReminderReceiver alarmReceiver;

    private TextView tvRepeatingTime;
    private EditText edtRepeatingMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Daily Reminder");

        ImageButton btnRepeatingTime = findViewById(R.id.btn_repeating_time);
        Button btnSetRepeating = findViewById(R.id.btn_set_repeating_alarm);
        Button btnCancelRepeating = findViewById(R.id.btn_cancel_repeating_alarm);
        tvRepeatingTime = findViewById(R.id.tv_repeating_time);
        edtRepeatingMessage = findViewById(R.id.edt_repeating_message);

        btnRepeatingTime.setOnClickListener(this);
        btnSetRepeating.setOnClickListener(this);
        btnCancelRepeating.setOnClickListener(this);

        alarmReceiver = new ReminderReceiver();
    }

    private final static String DATE_PICKER_TAG = "DatePicker";
    private final static String TIME_PICKER_ONCE_TAG = "TimePickerOnce";
    private final static String TIME_PICKER_REPEAT_TAG = "TimePickerRepeat";

    @Override
    public void onClick(View v) {
     if (v.getId() == R.id.btn_repeating_time) {
            TimePickerFragment timePickerFragmentRepeat = new TimePickerFragment();
            timePickerFragmentRepeat.show(getSupportFragmentManager(), TIME_PICKER_REPEAT_TAG);
        } else if (v.getId() == R.id.btn_set_repeating_alarm) {
            String repeatTime = tvRepeatingTime.getText().toString();
            String repeatMessage = edtRepeatingMessage.getText().toString();
            alarmReceiver.setRepeatingAlarm(this, ReminderReceiver.TYPE_REPEATING,
                    repeatTime, repeatMessage);
        } else if (v.getId() == R.id.btn_cancel_repeating_alarm) {
            alarmReceiver.cancelAlarm(this, ReminderReceiver.TYPE_REPEATING);
        }
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        TIME_PICKER_REPEAT_TAG:
        tvRepeatingTime.setText(dateFormat.format(calendar.getTime()));

    }
}