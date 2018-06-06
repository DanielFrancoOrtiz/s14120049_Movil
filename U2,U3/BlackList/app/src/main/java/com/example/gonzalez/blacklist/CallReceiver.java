package com.example.gonzalez.blacklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Gonzalez on 06/03/2018.
 */

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context contexto, Intent data) {
        // TODO Auto-generated method stub
        String estado =
                data.getStringExtra(TelephonyManager.EXTRA_STATE);
        String numero  =
                data.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if(estado.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            Toast.makeText(contexto, "SONANDO!!! "
                    + numero, Toast.LENGTH_SHORT).show();
        }

        if(estado.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
//			MARCANDO (DESCOLGADO)
            Toast.makeText(contexto,
                    "CUANDO YO ESTOY MARCANDO(DESCOLGADO)-OFFHOOK!!! "
                            + numero, Toast.LENGTH_SHORT).show();
        }

        if(estado.equals(TelephonyManager.EXTRA_STATE_IDLE)){
//			CUANDO CUELGO UNA LLAMADA RECIBIDA, O CUELGA EL QUE ME LLAMA
            Toast.makeText(contexto, "COLGAR-IDLE!!! "
                    + numero, Toast.LENGTH_SHORT).show();
        }
    }
}
