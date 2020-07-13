package com.myToDoList.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.exoplayer2.Player;
import com.myToDoList.data.DbHandler;
import com.myToDoList.model.Task;
import com.myToDoList.utils.NotificationUtils;

public class Alarm extends BroadcastReceiver implements Player.EventListener {
    private MediaPlayer mp;
    private String taskId;
    private DbHandler dbHandler;

    @Override
    public void onReceive(Context context, Intent intent) {

        dbHandler = DbHandler.getInstance(context);
        taskId = intent.getStringExtra("TaskId");
        Task task = new Task();
        task=dbHandler.getTaskByID(taskId);
        Toast.makeText(context, "Alarm is set!!", Toast.LENGTH_SHORT).show();
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/notification");
        //Notification
        NotificationUtils.taskReminderNotification("A Task: " + task.getTaskTittle() + " needs to be done!",(Integer.parseInt(taskId)) ,context, task.getTimestamp(), task.getTaskDone(),taskId);

//        Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
//        r.play();
//        r.stop();
        mp = MediaPlayer.create(context, alarmSound);
        if(mp!=null){
            mp.start();
        }






    }
}
