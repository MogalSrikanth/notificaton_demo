package com.example.notification_overview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.os.IResultReceiver;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Give the view id here with what type of view
        Button notifyBtn = findViewById(R.id.notification);

        //create the Notification Channel if your version is equal or greater than Oreo version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "Mughal", NotificationManager.IMPORTANCE_HIGH);
            // give , channel_ID, channel name , Importance
            NotificationManager manager = getSystemService(NotificationManager.class);
            // Take the manager from System service
            manager.createNotificationChannel( channel);
            // this manager will create notification Channel
        }


        notifyBtn.setOnClickListener(new View.OnClickListener() {
            // set a click Listener to that view : Button
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_icons8_apple_logo);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification");
                //Building the Notification NotificationCompact.Builder
                builder.setContentTitle("Hello User");
                builder.setContentText("Hello user this is mughal, welcome to our application");
                builder.setSmallIcon(R.drawable.ic_baseline_add_alert_24);
                builder.setAutoCancel(true);
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                // notification to allow even at Screen lock


                //Open Activity when clicked on Notification
                Intent intent = new Intent(MainActivity.this, Target_Activity.class);
                // Open the Activity when we click on Notification

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 123, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                // pending intent is given so that, unless the customer react to notification it is not gone

                builder.setContentIntent(pendingIntent);


                //Notification Big Text Style --> we can see it when we expand notification
                NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                bigTextStyle.setBigContentTitle("This is Big Content Title");
                bigTextStyle.bigText("This is Big text , we see it when we expand it");
                builder.setStyle(bigTextStyle); //Pass the Notification Builder

                //Notification Big Picture --> when the notification is expanded we can see the image
                NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
                bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.fruits_banner));
                builder.setStyle(bigPictureStyle);


                //setup Notification Action
                NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(R.drawable.ic_music, "Action 1", pendingIntent);
                NotificationCompat.Action action = actionBuilder.build();
                builder.addAction(action);
                builder.addAction(R.drawable.ic_music, "Action 2", pendingIntent);


                // Manager compact will notify the user with NotificationManagerCompact
                NotificationManagerCompat managerCompat =  NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(45, builder.build());
            }
        });

    }
}