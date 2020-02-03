package com.myToDoList.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun timeStampFormated(timestamp: Timestamp): String {

    val sdf = SimpleDateFormat("HH:mm")
    val date = Date(timestamp.time)
    val formattedDate = sdf.format(date)

    val sdf2 = SimpleDateFormat("dd/MM/yyyy")
    val formattedDate2 = sdf2.format(date)

    val cal = Calendar.getInstance()
    cal.timeInMillis = date.time
    val n = Date()
    val diffInMillies = Math.abs(date.time - n.time)
    val now = Calendar.getInstance()
    val diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
    val diffBackup = (now.get(Calendar.DATE) - timestamp.date).toLong()

    var timeStampFormat = ""

    if (diffBackup == 0L) {
        timeStampFormat = formattedDate
    } else if (diffBackup == 1L) {

        timeStampFormat = " Yesterday "
    } else {

        timeStampFormat = formattedDate2
    }

    return timeStampFormat

}


