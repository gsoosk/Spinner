package yasaman.farzad.sadaf.spinnergame;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class GravityActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Ball ball;
    private long time;
    private boolean updated = false;
    private boolean start = false;



    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(start) {
                ball.gravityUpdate(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);

            }
            if(!updated)
            {
                BallView ballView = (BallView) findViewById(R.id.ball_view);
                ball.setWindow(ballView.getWidth(), ballView.getHeight());
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        time = Config.getTime();

        ball = new Ball(0, 0);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_FASTEST);


        BallView ballView = (BallView) findViewById(R.id.ball_view);
        ballView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = true;
                ball = new Ball(0, 0);
            }
        });

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                BallView ballView = (BallView) findViewById(R.id.ball_view);
                ballView.render(ball.getX(), ball.getY());
            }
        }, 0, 17);

    }

}
