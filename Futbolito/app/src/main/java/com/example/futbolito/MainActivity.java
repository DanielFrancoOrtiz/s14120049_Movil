package com.example.futbolito;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager senManager;
    private Sensor sen;
    private SensorEventListener sensorEventListener;

    TextView txtEquipo1;
    TextView txtEquipo2;
    ImageView ball;
    int width, height;
    int equipo1 = 0, equipo2 = 0;
    float x = 0, y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ball = findViewById(R.id.ball);
        txtEquipo1 = findViewById(R.id.equipo1);
        txtEquipo2 = findViewById(R.id.equipo2);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        senManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sen = senManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        if(sen == null) {
            finish();
        }

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                if (x<-1){
                    if(ball.getX() < width - ball.getWidth() - 25)
                        ball.setX(ball.getX() + 10);
                }else if(x>1){
                    if(ball.getX() > 25)
                        ball.setX(ball.getX() - 10);
                }
                if (y<-1){
                    if(ball.getY() > 50){
                        ball.setY(ball.getY() - 10);
                    }else{
                        if(ball.getX() > 250 && ball.getX() < 350){
                            gol();
                            txtEquipo2.setText("Equipo2: "+(++equipo2));
                        }
                    }
                }else if(y>1){
                    if(ball.getY() < height - ball.getHeight() * 2.5 - 10){
                        ball.setY(ball.getY() + 10);
                    }else{
                        if(ball.getX() > 250 && ball.getX() < 350) {
                            gol();
                            txtEquipo1.setText("Equipo1: "+(++equipo1));
                        }
                    }
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }
    public void gol(){
        ball.setX(width / 2 - ball.getWidth() / 2);
        ball.setY(height / 2 - ball.getHeight() / 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gol();
        senManager.registerListener(sensorEventListener, sen, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        senManager.unregisterListener(sensorEventListener);
    }
}
