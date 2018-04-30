package com.example.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    TextView lblHeight;
    TextView lblWidth;
    SeekBar skbrWidth;
    SeekBar skbrHeight;
    ImageView imageView;
    Switch swBlackAndWhite;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblHeight = findViewById(R.id.textView);
        lblWidth = findViewById(R.id.textView2);

        skbrHeight=(SeekBar) findViewById(R.id.skbrHeight);
        skbrWidth=(SeekBar) findViewById(R.id.skbrWidth);
        imageView=(ImageView) findViewById(R.id.imageView);
        swBlackAndWhite=(Switch) findViewById(R.id.swBlackAndWhite);

        skbrHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                lblHeight.setText("Height: "+i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skbrWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                lblWidth.setText("Width: "+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        swBlackAndWhite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cargar();
                Toast.makeText(MainActivity.this,"Blanco y Negro "+(b?"Activado":"Desactivado"),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void btnAceptar_onClick(View v){
        cargar();
    }

    public void cargar(){
        String url = "https://loremflickr.com/";
        if(swBlackAndWhite.isChecked())
            url += "g/";
        url += skbrWidth.getProgress() + "/";
        url += skbrHeight.getProgress() + "/";
        url += "paris";
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageRequest peticion = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "No se encontro", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        queue.add(peticion);
    }
}
