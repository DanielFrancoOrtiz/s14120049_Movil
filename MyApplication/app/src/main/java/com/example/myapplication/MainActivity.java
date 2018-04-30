package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.utils.DB;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static final int PICK_CONTACT=1;
    private static final int SOLICITUD_PERMISO_CALL_PHONE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent service = new Intent(this,  DeleteService.class);
        startService(service);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
        }else {
            solicitarPermisoHacerLlamada();

        }
    }

    private void solicitarPermisoHacerLlamada() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},
                SOLICITUD_PERMISO_CALL_PHONE);
    }


    public void addBlockedNumber(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DB db = new DB(this);
        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME },
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        String name = c.getString(1);
                        db.addBlockedNumber(number, name);
                        showSelectedNumber(name, number);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }

    public void showSelectedNumber(String name, String number) {
        Toast.makeText(this, number + " Agregado a la lista", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == SOLICITUD_PERMISO_CALL_PHONE ){

            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else {
                Toast.makeText(this, "Permiso No Concedido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnAgregar_fn(View view) {
        addBlockedNumber();
    }

    public void btnLista_fn(View view) {
        Intent blockList = new Intent(getBaseContext(), ListActivity.class);
        startActivity(blockList);
    }
}
