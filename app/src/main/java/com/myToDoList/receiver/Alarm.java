package com.myToDoList.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.exoplayer2.Player;
import com.myToDoList.utils.NotificationUtils;

public class Alarm extends BroadcastReceiver implements Player.EventListener {
    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm is set!!", Toast.LENGTH_SHORT).show();
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/notification");
        //Notification
        NotificationUtils.newMessageNotification("A Task needs to be done!",123,context);

//        Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
//        r.play();
//        r.stop();
        mp = MediaPlayer.create(context, alarmSound);
        if(mp!=null){
            mp.start();
        }






    }
}
