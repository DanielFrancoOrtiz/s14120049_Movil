package net.ivanvega.manejofragmentosb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActividadFragmentHardcoded extends AppCompatActivity implements Comunicador{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_hardcoded);



    }



    @Override
    public void responder(String datos){

        android.app.FragmentManager fragmentManager = getFragmentManager();

        FragmentoDos fragmentoDos = (FragmentoDos) fragmentManager.findFragmentById(R.id.fragDos);
        fragmentoDos.cambiarTexto(datos);

    }



}
