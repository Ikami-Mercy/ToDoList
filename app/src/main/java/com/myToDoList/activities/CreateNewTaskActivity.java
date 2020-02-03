package com.myToDoList.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myToDoList.R;
import com.myToDoList.data.DbHandler;
import com.myToDoList.model.Task;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateNewTaskActivity extends AppCompatActivity {

    private CircleImageView userPic;
    private Button save_task;
    private Intent intent;
    private DbHandler dbHandler;
    private EditText et_addTask, et_taskTittle;
    private long randomTaskId = (long) ((Math.random() * 1000000));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        userPic = findViewById(R.id.userPic);
        save_task = findViewById(R.id.save_task);
        et_addTask = findViewById(R.id.et_addTask);
        et_taskTittle = findViewById(R.id.et_taskTittle);

        userPic.setOnClickListener(v -> {
            intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        dbHandler = DbHandler.getInstance(getApplicationContext());
        save_task.setEnabled(false);

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
/*
        if (et_addTask.getText().toString().isEmpty() || et_taskTittle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Task or task tittle is empty!", Toast.LENGTH_LONG).show();
            save_task.setEnabled(false);
        } else {
            save_task.setEnabled(true);
        }*/

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

        });


}}
