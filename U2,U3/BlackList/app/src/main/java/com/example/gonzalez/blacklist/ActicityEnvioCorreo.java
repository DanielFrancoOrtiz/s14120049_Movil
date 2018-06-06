package com.example.gonzalez.blacklist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Gonzalez on 06/03/2018.
 */

public class ActicityEnvioCorreo extends Activity {
    EditText txtD, txtA, txtM;
    CheckBox chkAdjunto;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acticity_envio_correo);

        txtD = (EditText)findViewById(R.id.txtDestinatario);
        txtA = (EditText)findViewById(R.id.txtAsunto);
        txtM = (EditText)findViewById(R.id.txtMensaje);

        chkAdjunto = (CheckBox)findViewById(R.id.chk);

    }

    public void btnEnviar_click(View v) {
        Intent email= new  Intent(Intent.ACTION_SEND);

        email.setType("plain/text");

        email.putExtra(Intent.EXTRA_EMAIL, txtD.getText().toString().split(","));
        email.putExtra(Intent.EXTRA_SUBJECT, txtA.getText().toString());
        email.putExtra(Intent.EXTRA_TEXT, txtM.getText().toString());

        if (chkAdjunto.isChecked()){
            email.putExtra(Intent.EXTRA_STREAM, Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_launcher));
            email.setType("image/png");
        }

        startActivity(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acticity_envio_correo, menu);
        return true;
    }
}
