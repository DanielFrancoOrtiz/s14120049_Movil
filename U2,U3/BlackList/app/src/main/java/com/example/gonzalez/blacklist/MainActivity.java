package com.example.gonzalez.blacklist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtN ;
    TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtN = (EditText)findViewById(R.id.txtNumber);
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        CallListener listener = new CallListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public void btnMarcar_click(View v) {
        Intent call = new  Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:" + txtN.getText().toString()));
        startActivity(call);
    }

    public void email(View v) {

        startActivity(new Intent(this, ActicityEnvioCorreo.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class CallListener extends PhoneStateListener {
        String TAG = "LOGGING PHONE CALL";
        private boolean phoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // TODO Auto-generated method stub
            if(state==TelephonyManager.CALL_STATE_RINGING){
                // phone ringing
                Log.i(TAG, "RECIBIENDO LLAMADA DE, number: " + incomingNumber);

            }
            //DESCOLGADO
            if(state==TelephonyManager.CALL_STATE_OFFHOOK){
                Log.d("TELEFUNK", "CONTESTADO!!!");
                phoneCalling =true;
            }
            //
            if(state==TelephonyManager.CALL_STATE_IDLE){
                Log.i(TAG, "COLGADO, FINALIZADO LLAMADA");

                if(phoneCalling){
                    Log.d("TELEFUNK", "REGRESAR A ACTIVITY PRINCIPAL");

                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());

                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    phoneCalling = false;

                }

            }

//			super.onCallStateChanged(state, incomingNumber);
        }

    }
}
