package com.example.permisos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission;

public class MainActivity extends AppCompatActivity {
    private static final int PERMIT_CALL = 1;
    private static final int PERMIT_READ_CONTATS=1;
    private Intent iLlamada;
    private Intent IContacts;
    private Button btnLlamada;
    private Button btnContactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLlamada = (Button) findViewById(R.id.btnLlamadas);
        btnContactos=(Button) findViewById(R.id.btnContactos);
        permisos();

    }


        private void permisos() {
            btnLlamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    iLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1234"));
                    if (ActivityCompat.checkSelfPermission(getApplication(), permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(iLlamada);
                        Toast.makeText(MainActivity.this, "Permiso de llamada concendido", Toast.LENGTH_SHORT).show();
                    } else {
                        explicarusopermiso();
                        solicitarpermisohacerllamada();
                    }

                }
            });

            btnContactos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ActivityCompat.checkSelfPermission(getApplication(), permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(iLlamada);
                        Toast.makeText(MainActivity.this, "concendido", Toast.LENGTH_SHORT).show();
                    } else {
                        explicarusopermisoContactos();
                        solicitarpermisocontactos();
                    }

                }

            });
        }

    private void solicitarpermisohacerllamada() {
        ActivityCompat.requestPermissions(this, new String[]{permission.CALL_PHONE}, PERMIT_CALL);
        Toast.makeText(this, "Peticion del permiso de llamada", Toast.LENGTH_SHORT).show();
    }
    private void explicarusopermiso() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.CALL_PHONE)) {
            Toast.makeText(this, "explicacion del permiso de llamada", Toast.LENGTH_SHORT).show();
        }
    }
    private void explicarusopermisoContactos() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.CALL_PHONE)) {
            Toast.makeText(this, "explicacion del permiso de llamada", Toast.LENGTH_SHORT).show();
        }
    }
    private void solicitarpermisocontactos() {
        ActivityCompat.requestPermissions(this, new String[]{permission.CALL_PHONE}, PERMIT_READ_CONTATS);
        Toast.makeText(this, "Peticion del permiso de leer contactos", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMIT_CALL) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(iLlamada);
                Toast.makeText(this, "Permiso Concedido", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Permiso No Concedido", Toast.LENGTH_SHORT).show();
            }

        if (requestCode == PERMIT_READ_CONTATS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(IContacts);
                Toast.makeText(this, "Permiso Concedido", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Permiso No Concedido", Toast.LENGTH_SHORT).show();
        }
        }

    }





