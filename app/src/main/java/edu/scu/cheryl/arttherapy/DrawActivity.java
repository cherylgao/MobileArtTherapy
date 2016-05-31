package edu.scu.cheryl.arttherapy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DrawActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    TouchDrawView view;
    String song= "android.resource://edu.scu.cheryl.arttherapy/" +R.raw.eraser;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        view = (TouchDrawView) findViewById(R.id.myview);
        if (view == null) {
            Log.e("cheryl", "we have a problem");
        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        mediaPlayer= MediaPlayer.create(this, R.raw.eraser);

//        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mediaPlayer.start();
//                view.clear();
//            }
//        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Photo Notes");
        actionBar.setIcon(R.drawable.ic_draw);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    public void onSensorChanged(SensorEvent se) {

        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta * 0.1f; // perform low-cut filter
        float accel= Math.abs(mAccel);
        if(accel>1.0f){
            new AsyncTask<Void, Void, String>(){

                @Override
                protected String doInBackground(Void... params) {
                    mediaPlayer.start();
                    return "s";
                }
            }.execute();

            view.clear();
        }
//        displayAcceleration();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_delete:
                Intent intent3= new Intent(Intent.ACTION_DELETE);
                intent3.setData(Uri.parse("package:edu.scu.cheryl.arttherapy"));
                startActivity(intent3);
                break;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                toast("unknown action ...");
        }

        return true;
    }
    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
