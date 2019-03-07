package yasaman.farzad.sadaf.spinnergame;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GyroActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private Ball ball;
    private long time;
    private boolean updated = false;


    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            ball.gyroUpdate(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            updateTextViews(sensorEvent);



        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);

        time = Config.getTime();

        ball = new Ball(0, 0);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_FASTEST);


        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                BallView ballView = (BallView) findViewById(R.id.ball_view_g);
                ballView.render(ball.getX(), ball.getY());
            }
        }, 0, 17);

    }
    public void updateTextViews(SensorEvent sensorEvent)
    {
        if(!updated)
        {
            BallView ballView = (BallView) findViewById(R.id.ball_view_g);
            ball.setWindow(ballView.getWidth(), ballView.getHeight());
        }
        ((TextView) findViewById(R.id.g_x_text)).setText("X Gravity : " + Float.toString(sensorEvent.values[0]));
        ((TextView) findViewById(R.id.g_y_text)).setText("Y Gravity : " +Float.toString(sensorEvent.values[1]));
        ((TextView) findViewById(R.id.g_z_text)).setText("Z Gravity : " +Float.toString(sensorEvent.values[2]));
        ((TextView) findViewById(R.id.g_vx_text)).setText("Vx : " +Float.toString(ball.getVx0()));
        ((TextView) findViewById(R.id.g_vy_text)).setText("Vy : " +Float.toString(ball.getVy0()));
    }
}
