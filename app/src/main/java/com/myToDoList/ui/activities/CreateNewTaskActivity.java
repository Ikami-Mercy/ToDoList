package com.myToDoList.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.myToDoList.R;
import com.myToDoList.constants.Constants;
import com.myToDoList.data.DbHandler;
import com.myToDoList.model.Task;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateNewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private CircleImageView userPic;
    private Button save_task;
    private Intent intent;
    private String profilePic,profileName ;
    private ImageView back;
    private RadioButton radioFamily;
    private DbHandler dbHandler;
    private EditText et_addTask, et_taskTittle,et_reminder;
    private long randomTaskId ;
private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        userPic = findViewById(R.id.userPic);
        save_task = findViewById(R.id.save_task);
        et_addTask = findViewById(R.id.et_addTask);
        et_taskTittle = findViewById(R.id.et_taskTittle);
        radioFamily = findViewById(R.id.radioFamily);
        et_reminder = findViewById(R.id.et_reminder);

        back = findViewById(R.id.back);
        back.setOnClickListener(v->{
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
        if(radioFamily.isChecked())
        {
            // is checked
        }

        et_reminder.setOnClickListener(v->{
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    CreateNewTaskActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );

            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
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
            newTask.setTaskTittle(et_taskTittle.getText().toString());
            newTask.setTaskContent(et_addTask.getText().toString());
            newTask.setTimestamp(String.valueOf(System.currentTimeMillis()));
            newTask.setTaskType(1);
            newTask.setTaskID(String.valueOf(randomTaskId));

            dbHandler.saveTask(newTask);

            new SweetAlertDialog(CreateNewTaskActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Saved")
                    .setContentText("Your task has been succesfully saved.")
                    .show();

            et_addTask.setText("");
            et_taskTittle.setText("");
            save_task.setEnabled(false);

        });


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
        String date = "Date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        et_reminder.setText(date);
    }
}
