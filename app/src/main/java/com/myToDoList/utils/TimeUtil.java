package com.holla.holla.mqttchat.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeAgoUtil {

    public static String timeStampFormated(Timestamp timestamp){

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(timestamp.getTime());
        String formattedDate = sdf.format(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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

}
