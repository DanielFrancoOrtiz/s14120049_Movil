package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.annotation.Nullable;

import com.example.myapplication.utils.DB;


public class DeleteService extends Service {

    @Override
    public void onCreate() {

    }

    hilo h;

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess) {

        try {
            if (h == null) {
                h = new hilo();
                h.start();
            }
        } catch (Exception err) {

        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            if (h.isAlive()) {
                h.stop();
            }
        } catch (Exception err) {

        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public class hilo extends Thread {
        @SuppressLint("MissingPermission")
        @Override
        public void run() {
            while (true) {
                try {

                    DB dao = new DB(getBaseContext());
                    String[] lista = dao.listBlockedNumbers();

                    for (int i = 0; i < lista.length; i++) {
                        String[] arrayString = dao.listBlockedNumbers()[i].split(";");
                        String blockName = arrayString[1];
                        String blockNumber = arrayString[0];
                        blockNumber = blockNumber.replace(" ", "");
                        Uri CALLLOG_URI = Uri.parse("content://call_log/calls");
                        getApplicationContext().getContentResolver().delete(CALLLOG_URI, CallLog.Calls.NUMBER + "=?", new String[]{blockNumber});

                    }

                } catch (Exception e) {

                }
            }
        }

        public void deleteSMS(String number) {
            try {

                Uri uriSms = Uri.parse("content://sms/inbox");
                Cursor c = getContentResolver().query(uriSms,
                        new String[]{"_id", "thread_id", "address",
                                "person", "date", "body"}, null, null, null);

                if (c != null && c.moveToFirst()) {
                    do {
                        long id = c.getLong(0);
                        long threadId = c.getLong(1);
                        String address = c.getString(2);
                        String body = c.getString(5);

                        if (address.equals(number)) {
                            getContentResolver().delete(
                                    Uri.parse("content://sms/" + id), null, null);
                        }
                    } while (c.moveToNext());
                }
            } catch (Exception e) {

            }

        }
    }

}
