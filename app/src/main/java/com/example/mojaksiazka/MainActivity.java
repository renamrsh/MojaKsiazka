package com.example.mojaksiazka;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    Button zobaczOpis, chcePrzeczytac, przypomniejOKsiazce;
    TextView messageSuccess;

    private static final  String CHANNEL_ID = "default_channel";
    private static final String CHANNEL_NAME = "Kanał Powiadomień";
    boolean chcePrzeczytacBoolean = true;
    private static int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        zobaczOpis = findViewById(R.id.zobaczOpis);
        chcePrzeczytac = findViewById(R.id.chcePrzeczytac);
        przypomniejOKsiazce = findViewById(R.id.przypomniejOKsiazce);
        messageSuccess = findViewById(R.id.messageSuccess);

        zobaczOpis.setOnClickListener(view -> {
            sendNotification(ID, this, "Moja Książka", "Krótki opis: Ekscytująca historia pełna zwrotów akcji.");
            ID++;
        });
        chcePrzeczytac.setOnClickListener(view -> {
            if(chcePrzeczytacBoolean){
                chcePrzeczytac.setText("Usuń z chcę przeczytać");
                messageSuccess.setVisibility(TextView.VISIBLE);
                chcePrzeczytacBoolean = false;
            }else{
                chcePrzeczytac.setText("Dodaj do chcę przeczytać");
                messageSuccess.setVisibility(TextView.GONE);
                chcePrzeczytacBoolean = true;
            }
        });
        przypomniejOKsiazce.setOnClickListener(view -> {
            sendNotification(ID, this, "Moja Książka", "Pamiętaj, aby znaleźć czas na lekturę!");
            ID++;
        });
    }

    public static void sendNotification(int NOTIFICATION_ID, AppCompatActivity activity, String title, String message) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.book)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME , NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}