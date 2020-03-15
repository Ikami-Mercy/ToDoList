package com.myToDoList.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.myToDoList.receiver.Alarm;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static String timeStampFormated(Timestamp timestamp){

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(timestamp.getTime());
        String formattedDate = sdf.format(date);

       // SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate2 = sdf2.format(date);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        Date n = new Date();
        long diffInMillies = Math.abs(date.getTime() - n.getTime());
        //long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        Calendar now = Calendar.getInstance();
        long diff = now.get(Calendar.DATE) - timestamp.getDate();
        String timeStampFormat = "";

        if(diff == 0){

            timeStampFormat = formattedDate;
        }
        else if(diff == 1 ){

            timeStampFormat = " Yesterday " + formattedDate;
        }
        else{

            timeStampFormat = formattedDate2;
        }

        return timeStampFormat;
       // return formattedDate;
    }

    //Set alarm
    public static void setAlarm(long time, Context context) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(context, Alarm.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(context, "Alarm is set", Toast.LENGTH_SHORT).show();
    }

}
