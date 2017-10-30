package net.ivanvega.manejofragmentosb;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class FragmentoDos extends Fragment{

    EditText txt_1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.layout_fragment_dos,null);

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);

        txt_1 = (EditText) getActivity().findViewById(R.id.editText);

    }



    public void cambiarTexto(String texto){

        txt_1.setText(texto);

    }



}
