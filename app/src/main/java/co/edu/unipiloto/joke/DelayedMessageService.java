package co.edu.unipiloto.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DelayedMessageService extends IntentService {
    public static final String EXTRA_MESSAGE = "message";
    public static final int NOTIFICATION_ID = 5453;

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try{
                wait(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("notification","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        showText(text);
    }

    private void showText(final String text){
        //Log.v("DelayedMessageService","The message is: "+text);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"notification");
                /*.setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.question))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0,1000})
                .setAutoCancel(true);*/
        builder.setContentTitle("What is the secret of comedy?")
                .setContentText("timing")
                .setSmallIcon(R.drawable.ic_message)
                .setAutoCancel(true);

        Intent actionIntent=new Intent(this,MainActivity.class);
        PendingIntent actionPendingIntent=PendingIntent.getActivity(
                this,
                0,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(actionPendingIntent);

        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(DelayedMessageService.this);
        //NotificationManagerCompat managerCompat= (NotificationManagerCompat)getSystemService(NOTIFICATION_SERVICE);
        managerCompat.notify(NOTIFICATION_ID,builder.build());

        /*
        Intent actionIntent=new Intent(this,MainActivity.class);
        PendingIntent actionPendingIntent=PendingIntent.getActivity(
                this,
                0,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(actionPendingIntent);
        NotificationManager notificationManager=
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());*/
    }

}