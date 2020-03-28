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
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
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
    private Intent intent;
    private String profilePic;
    private String taskID;
    private EditText et_taskTittle, et_taskContent, et_reminder;
    private TextView tv_type;
    private SharedPreferences sharedPreferences;
    private Task myTask;
    private Boolean textChanged= false;
    private String mTaskType;
    private FloatingActionMenu floatingActionMenu;
    private com.github.clans.fab.FloatingActionButton edit_task, floatDeleteTask, markDoneTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);


        this.sharedPreferences = this.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.profilePic = sharedPreferences.getString("profileImage", null);
        taskID = getIntent().getStringExtra("taskID");
        dbHandler = DbHandler.getInstance(getApplicationContext());
        myTask = dbHandler.getTaskByID(taskID);

        back = findViewById(R.id.back);
        int taskType = myTask.getTaskType();
        if (taskType == 1) {
            mTaskType = "Personal";
        }
        if (taskType == 2) {
            mTaskType = "Work";
        }
        if (taskType == 3) {
            mTaskType = "Family";
        }
        if (taskType == 4) {
            mTaskType = "Study";
        }
        if (taskType == 5) {
            mTaskType = "Other";
        }
        userPic = findViewById(R.id.userPic);
        edit_task = findViewById(R.id.edit_task);
        floatDeleteTask = findViewById(R.id.floatDeleteTask);
        markDoneTask = findViewById(R.id.markDoneTask);
        et_reminder = findViewById(R.id.et_reminder);
        userPic.setImageBitmap(decodeBase64(profilePic));
        et_taskContent = findViewById(R.id.et_taskContent);
        et_taskTittle = findViewById(R.id.et_taskTittle);
        tv_type = findViewById(R.id.task_tyype);
        et_taskTittle.setText(myTask.getTaskTittle());
        et_taskContent.setText(myTask.getTaskContent());
        tv_type.setText(mTaskType);
        et_reminder.setText(myTask.getReminder());
        floatDeleteTask.setColorNormal(getResources().getColor(R.color.colorGreen));
        floatDeleteTask.setColorPressed(getResources().getColor(R.color.colorBlue));
        edit_task.setColorNormal(getResources().getColor(R.color.colorYellow));
        edit_task.setColorPressed(getResources().getColor(R.color.colorBlue));
        markDoneTask.setColorNormal(getResources().getColor(R.color.colorAccent));
        markDoneTask.setColorPressed(getResources().getColor(R.color.colorPink));
        floatingActionMenu = findViewById(R.id.floatingActionMenu);
        floatingActionMenu.setMenuButtonColorNormal(getResources().getColor(R.color.colorBlue));
        floatingActionMenu.setMenuButtonColorPressed(getResources().getColor(R.color.colorGreen));


        userPic.setOnClickListener(v -> {
            intent = new Intent(this, SetProfileActivity.class);
            startActivity(intent);
        });


        et_taskContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textChanged=true;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {

                }
            }
        });





        back.setOnClickListener(v ->
        {
            onBackPressed();
        });

        edit_task.setOnClickListener(v -> {
            if(textChanged){
                Task mTask = new Task();
                mTask.setTaskTittle(et_taskTittle.getText().toString());
                mTask.setTaskContent(et_taskContent.getText().toString());
                mTask.setTimestamp(System.currentTimeMillis());
                mTask.setTaskType(1);
                mTask.setTaskID(taskID);
                dbHandler.updateTask(mTask);
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Saved")
                        .setContentText("Your task has been succesfully edited.")
                        .show();
            }
          else if(!textChanged){
                Toast.makeText(this, "No edited changes to update!", Toast.LENGTH_SHORT).show();
            }
        });
        markDoneTask.setOnClickListener(v->{
            Toast.makeText(this, "Working on it.", Toast.LENGTH_SHORT).show();
        });

        floatDeleteTask.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("Delete")
                    .setContentText("Are you sure you want to delete this task?")
                    .setConfirmText("OK")
                    .setCustomImage(R.drawable.ic_warning_black_24dp)

                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            dbHandler.deleteTask(taskID);
                            Intent i = new Intent(SingleTaskActivity.this, DashboardActivity.class);
                            startActivity(i);
                            finishAffinity();
                            Toast.makeText(SingleTaskActivity.this,
                                    "Task deleted!!", Toast.LENGTH_LONG)
                                    .show();


                        }
                    })
                    .show();
        });


    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
