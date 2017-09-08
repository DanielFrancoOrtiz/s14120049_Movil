package com.example.danie.contactos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by danie on 06/09/2017.
 */

public class Ventana2 extends AppCompatActivity {

    EditText txtUsuario;
    EditText txtNumero;
    EditText txtEmail;
    EditText txtTwiter;
    EditText txtFecha;
    Button btnAceptar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana2);
        setTitle("Registrar Contacto");
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtNumero = (EditText) findViewById(R.id.txtNumero);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtTwiter = (EditText) findViewById(R.id.txtTwiter);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contacto c = new Contacto(
                        txtUsuario.getText().toString(),
                        txtNumero.getText().toString(),
                        txtEmail.getText().toString(),
                        txtTwiter.getText().toString(),
                        txtFecha.getText().toString());
                Intent i = new Intent(getApplication(),Ventana2.class);
                i.putExtra("usr",c);

                setResult(RESULT_OK,i);
                finish();

            }
        });

    }
}
