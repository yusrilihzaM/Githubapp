package com.yusril.submission2_a3322966.activity.settings

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.yusril.submission2_a3322966.R
import com.yusril.submission2_a3322966.activity.MainActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val TYPE_REPEATING = "Github App"
        const val EXTRA_MESSAGE = "message"

        private const val ID_REPEATING = 101

        private const val TIME_FORMAT = "HH:mm"
    }
    override fun onReceive(context: Context, intent: Intent) {
        showAlarmNotification(context)
    }

    private fun isDateInvalid(date: String): Boolean {
        return try {
            val df = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }
    fun setRepeatingAlarm(context: Context, time: String) {
        if (isDateInvalid(time)) return

        val alarmManager=context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent=Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, EXTRA_MESSAGE)
        // pecah format waktunya
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar=Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        // pending intent

        val pendingIntent= PendingIntent.getBroadcast(context, ID_REPEATING, intent,0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, R.string.alarm_activated, Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context) {
        val alarmManager=context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent=Intent(context,AlarmReceiver::class.java)

        val requestCode = ID_REPEATING
        //cansel pending intent
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, notificationIntent, 0)
        pendingIntent.cancel()
        //cancel alarm manager
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, R.string.alarm_deactivated, Toast.LENGTH_SHORT).show()
    }
    private fun showAlarmNotification(context: Context) {
        val channelId = "Channel_Alarm"
        val channelName = "AlarmManager channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        //pending intent
        val notificationIntent=Intent(context,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        val message ="It's time to look for relationships on the github application"
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentTitle(TYPE_REPEATING)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(ID_REPEATING, notification)
    }
}