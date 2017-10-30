package net.ivanvega.manejofragmentosb;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentoUno extends Fragment{

    Button btn1;
    Button btn2;
    Button btn3;

    Comunicador comunicacion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.layout_fragment_uno, container);

    }



    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);

        comunicacion = (Comunicador) getActivity();


        btn1 = (Button) getActivity().findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                comunicacion.responder("TEXTO 1 BTN_1");

            }

        });

        btn2 = (Button) getActivity().findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                comunicacion.responder("TEXTO 2 BTN_2");

            }

        });

        btn3 = (Button) getActivity().findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                comunicacion.responder("TEXTO 3 BTN_3");

            }

        });



    }



}
