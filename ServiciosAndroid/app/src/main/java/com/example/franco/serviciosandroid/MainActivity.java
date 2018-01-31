package com.example.franco.serviciosandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btnIP_Click (View v){
        Toast.makeText(this,"INTERACTUANDO HILO PRINCIPAL",
                Toast.LENGTH_SHORT).show();
    }
    public void btnS_Click(View v){
        Toast.makeText(this,"arrancando servicio",
                Toast.LENGTH_SHORT).show();

        Intent is = new Intent(this, MiServicio.class);

        startService(is);
    }
    public void btnIS_Click(View v){
        Toast.makeText(this,"arrancando intent servicio",
                Toast.LENGTH_SHORT).show();

        Intent is = new Intent(this,MiIntentService.class);

        startService(is);
    }
    public void btnSE_Click(View v){

    }
}
