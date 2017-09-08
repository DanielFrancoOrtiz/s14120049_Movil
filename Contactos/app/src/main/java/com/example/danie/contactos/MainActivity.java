package com.example.danie.contactos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> lista = new ArrayList<>();
    private Button btnAgregar;
    private ListView lstView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        lstView = (ListView) findViewById(R.id.lsView);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(),Ventana2.class);
                startActivityForResult(i,1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000 && resultCode==RESULT_OK)
        {

            Contacto u =(Contacto) data.getExtras().getSerializable("usr");
            lista.add(u.getUsuario() + "  "+ u.getNumero());
            ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista);
            lstView.setAdapter(adp);

        }
    }
}
