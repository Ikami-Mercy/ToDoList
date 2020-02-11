package com.myToDoList.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myToDoList.R;
import com.myToDoList.constants.Constants;
import com.myToDoList.data.DbHandler;
import com.myToDoList.model.Task;
import com.myToDoList.utils.UtilsKt;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class SingleTaskActivity extends AppCompatActivity {
    private ImageView back;
    private DbHandler dbHandler;
    private CircleImageView userPic;
    private FloatingActionButton edit_task;
    private Intent intent;
    private String profilePic;
    private String taskID;
    private EditText et_taskTittle, et_taskContent;
    private SharedPreferences sharedPreferences;
    private Task myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);


        this.sharedPreferences = this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.profilePic = sharedPreferences.getString("profileImage", null);
        userPic = findViewById(R.id.userPic);
        edit_task = findViewById(R.id.edit_task);
        userPic.setImageBitmap(decodeBase64(profilePic));
        taskID = getIntent().getStringExtra("taskID");

        userPic.setOnClickListener(v -> {
            intent = new Intent(this, SetProfileActivity.class);
            startActivity(intent);
        });

        dbHandler = DbHandler.getInstance(getApplicationContext());
        myTask = dbHandler.getTaskByID(taskID);
        Log.e("Task id is",""+ taskID);
        Log.e("Task is:", ""+ myTask.toString());
        back = findViewById(R.id.back);
        et_taskContent = findViewById(R.id.et_taskContent);
        et_taskTittle = findViewById(R.id.et_taskTittle);
        et_taskTittle.setText(myTask.getTaskTittle());
        et_taskContent.setText(myTask.getTaskContent());
        back.setOnClickListener(v ->
        {
            onBackPressed();
        });
//edit_task.setEnabled(false);
        edit_task.setOnClickListener(v->{
            Task mTask =new Task();
            mTask.setTaskTittle(et_taskTittle.getText().toString());
            mTask.setTaskContent(et_taskContent.getText().toString());
            mTask.setTimestamp(String.valueOf(System.currentTimeMillis()));
            mTask.setTaskType(1);
            mTask.setTaskID(taskID);
            dbHandler.updateTask(mTask);
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Saved")
                    .setContentText("Your task has been succesfully edited.")
                    .show();
        });


    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
