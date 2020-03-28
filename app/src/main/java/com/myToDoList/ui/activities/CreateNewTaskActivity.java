package com.myToDoList.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.myToDoList.R;
import com.myToDoList.constants.Constants;
import com.myToDoList.data.DbHandler;
import com.myToDoList.model.Task;
import com.myToDoList.utils.TimeUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateNewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private CircleImageView userPic;
    private Button save_task;
    private ArrayList<Task> list = new ArrayList<>();
    private Intent intent;
    private String profilePic;
    private ImageView back;
    private RadioButton radioFamily, radioOther, radioStudy, radioPersonal, radioWork;
    private DbHandler dbHandler;
    private RadioGroup radioGroup;
    private RadioGroup radioGroupDynamic;
    private EditText et_addTask, et_taskTittle, et_reminder, et_reminder_time;
    private long randomTaskId;
    private long timestamp=0;
    private SharedPreferences sharedPreferences;
    private int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        userPic = findViewById(R.id.userPic);
        save_task = findViewById(R.id.save_task);
        et_addTask = findViewById(R.id.et_addTask);
        et_taskTittle = findViewById(R.id.et_taskTittle);
        radioFamily = findViewById(R.id.radioFamily);
        radioOther = findViewById(R.id.radioOther);
        radioWork = findViewById(R.id.radioWork);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroupDynamic = findViewById(R.id.radioGroupDynamic);
        radioPersonal = findViewById(R.id.radioPersonal);
        radioStudy = findViewById(R.id.radioStudy);
        et_reminder = findViewById(R.id.et_reminder);
        et_reminder_time = findViewById(R.id.et_reminder_time);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            onBackPressed();
        });

        this.sharedPreferences = this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.profilePic = sharedPreferences.getString("profileImage", null).toString();
        //  this.profileName = sharedPreferences.getString("profileName", null).toString();

        userPic.setImageBitmap(decodeBase64(profilePic));

        userPic.setOnClickListener(v -> {
            intent = new Intent(this, SetProfileActivity.class);
            startActivity(intent);
        });

        dbHandler = DbHandler.getInstance(getApplicationContext());
        save_task.setEnabled(false);




        list = dbHandler.getTasks();



        if (radioFamily.isChecked()) {
            save_task.setEnabled(true);
            // is checked
        }

        et_reminder.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    CreateNewTaskActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );

            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

        et_reminder_time.setOnClickListener(v -> {
            openTimeDialog();
        });

        et_addTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                save_task.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {

                }
            }
        });

        et_taskTittle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                save_task.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {

                }
            }
        });

        save_task.setOnClickListener(v -> {
            randomTaskId = (long) ((Math.random() * 1000000));
            Task newTask = new Task();
            if (radioPersonal.isChecked()) {
                newTask.setTaskType(1);
            }
            if (radioWork.isChecked()) {
                newTask.setTaskType(2);
            }
            if (radioFamily.isChecked()) {
                newTask.setTaskType(3);
            }
            if (radioStudy.isChecked()) {
                newTask.setTaskType(4);
            }
            if (radioOther.isChecked()) {
                newTask.setTaskType(5);
            }

            newTask.setTaskTittle(et_taskTittle.getText().toString());
            newTask.setTaskContent(et_addTask.getText().toString());
            newTask.setReminder(et_reminder.getText().toString());
            newTask.setTimestamp(System.currentTimeMillis());

            newTask.setTaskID(String.valueOf(randomTaskId));

            dbHandler.saveTask(newTask);
            if(timestamp!=0){
                TimeUtil.setAlarm(timestamp, CreateNewTaskActivity.this);
            }


            new SweetAlertDialog(CreateNewTaskActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Saved")
                    .setContentText("Your task has been succesfully saved.")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent intent = new Intent(CreateNewTaskActivity.this, DashboardActivity.class);
                            CreateNewTaskActivity.this.startActivity(intent);
                            CreateNewTaskActivity.this.finish();
                        }
                    })
                    .show();

            et_addTask.setText("");
            et_reminder.setText("");
            et_reminder_time.setText("");
            et_taskTittle.setText("");
            radioGroup.clearCheck();
            save_task.setEnabled(false);
            Intent intent = new Intent(CreateNewTaskActivity.this, DashboardActivity.class);
            CreateNewTaskActivity.this.startActivity(intent);
            CreateNewTaskActivity.this.finish();

        });


    }
    private void openTimeDialog(){

        // Get Current Time
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        et_reminder_time.setText("" + hourOfDay + ":" + minute);
                        timestamp =c.getTimeInMillis();
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * @param view        The view associated with this listener.
     * @param year        The year that was set.
     * @param monthOfYear The month that was set (0-11) for compatibility
     *                    with {@link Calendar}.
     * @param dayOfMonth  The day of the month that was set.
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = ""+ dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        et_reminder.setText(date);
    }
}
