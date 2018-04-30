package com.example.myapplication.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import com.android.internal.telephony.ITelephony;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.DB;

import java.lang.reflect.Method;

public class ICReceiver extends BroadcastReceiver{
private Context context;
private static int CALLSHIELD_ID = 1982;
	private static final String TAG = "Phone call";
	private ITelephony telephonyService;
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		Bundle bundle = intent.getExtras();
		String incomingNumber= bundle.getString("incoming_number");
		String state = bundle.getString(TelephonyManager.EXTRA_STATE);
		if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
		if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
		DB db = new DB(context);
		String[] blocklist = db.listBlockedNumbers();
		for( int i = 0; i < blocklist.length; i++)
		{
		    String numberString = blocklist[i];
		    String[] bNumberArray = numberString.split(";");
		    String bNumber = bNumberArray[0];

		    if (settings.getBoolean("exact_match",false)== false){
		    	bNumber = bNumber.replace(" ", "");
		    	bNumber = bNumber.replace("-", "");
		    	incomingNumber = incomingNumber.replace(" ", "");
		    	incomingNumber = incomingNumber.replace("-", "");
		    	if (incomingNumber.contains(bNumber)){
					TelephonyManager telephony = (TelephonyManager)
							context.getSystemService(Context.TELEPHONY_SERVICE);
					try {
						Class c = Class.forName(telephony.getClass().getName());
						Method m = c.getDeclaredMethod("getITelephony");
						m.setAccessible(true);
						telephonyService = (ITelephony) m.invoke(telephony);
						telephonyService.endCall();
					} catch (Exception e) {
						e.printStackTrace();
					}

					showNotification(incomingNumber, "name");
		    	}

			}else{

		}

	    }}}
	}


	public void showNotification(String number, String name){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(context.getString(R.string.statusbar_call_blocked_title))
		        .setContentText(context.getString(R.string.statusbar_call_blocked_title));

		Intent resultIntent = new Intent(context, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(CALLSHIELD_ID, mBuilder.build());
	}
}