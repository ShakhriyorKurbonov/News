package com.k.shakhriyor.news.service

//noinspection SuspiciousImport
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.k.shakhriyor.news.data.common.Constants
import com.k.shakhriyor.news.data.store.NotificationImportanceStore
import com.k.shakhriyor.news.presentation.main_activity.MainActivity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class MyFireBaseMessagingService @Inject constructor(
    private val notificationImportanceStore: NotificationImportanceStore
) :FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("QAZ", "onNewToken: ${token}")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notificationTitle=message.notification?.title?:"News"
        val notificationBody=message.notification?.body?:"Message body"
        val data=message.data
        val title=data[Constants.TITLE]?:""
        val description=data[Constants.DESCRIPTION]?:""
        val image=data[Constants.IMAGE]?:""
        val postedDate=data[Constants.POSTEDDATE]?:""
        val author=data[Constants.AUTHOR]?:""
//        val like=data[Constants.LIKE]?:false
        showNotification(notificationTitle,notificationBody,title,description,image,postedDate,author)
    }

    private fun showNotification(
        notificationTitle: String,
        notificationBody: String,
        title: String,
        description: String,
        image:String,
        postedDate: String,
        author: String
    ){


            createNotificationChannel()


        Log.d("QAZ", "showNotification: ${title}")


        val intent= Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(Constants.TITLE,title)
            putExtra(Constants.DESCRIPTION,description)
            putExtra(Constants.IMAGE,image)
            putExtra(Constants.POSTEDDATE,postedDate)
            putExtra(Constants.AUTHOR,author)
          //  putExtra(Constants.LIKE,like)
        }

        val pendingIntent= PendingIntent.getActivity(
            this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val isVoiced= runBlocking { notificationImportanceStore.getNotificationImportance().firstOrNull()?:true }

        val channelId=if (isVoiced) "news_viced" else "news_silent"
         NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.drawable.btn_star_big_on)
            .setContentTitle(notificationTitle)
            .setContentText(notificationBody)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)





    }


    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val audioAttr= AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            val sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val channelVoiced = NotificationChannel(
                "news_viced",
                "News Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            channelVoiced.setSound(sound, audioAttr)
            val managerVoiced = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            managerVoiced.createNotificationChannel(channelVoiced)

            val channelSilent = NotificationChannel(
                "news_silent",
                "News Notification",
                NotificationManager.IMPORTANCE_LOW
            )
            channelSilent.setSound(null, null)
            val managerSilent = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            managerSilent.createNotificationChannel(channelSilent)
        }
    }




    }

