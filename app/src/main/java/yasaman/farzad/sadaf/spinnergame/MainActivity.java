package yasaman.farzad.sadaf.spinnergame;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private Ball ball;
    private long time;
    private boolean updated = false;


    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            ball.gravityUpdate(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            updateTextViews(sensorEvent);



        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = Config.getTime();

        ball = new Ball(0, 0);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);





        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                BallView ballView = (BallView) findViewById(R.id.ball_view);
                ballView.render(ball.getX(), ball.getY());
            }
        }, 0, 16);



    }
    public void updateTextViews(SensorEvent sensorEvent)
    {
        if(!updated)
        {
            BallView ballView = (BallView) findViewById(R.id.ball_view);
            ball.setWindow(ballView.getWidth(), ballView.getHeight());
        }
        ((TextView) findViewById(R.id.x_text)).setText("X Gravity : " + Float.toString(sensorEvent.values[0]));
        ((TextView) findViewById(R.id.y_text)).setText("Y Gravity : " +Float.toString(sensorEvent.values[1]));
        ((TextView) findViewById(R.id.z_text)).setText("Z Gravity : " +Float.toString(ball.getVy0()));
    }
}
