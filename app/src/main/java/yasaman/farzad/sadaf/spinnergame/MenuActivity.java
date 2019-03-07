package yasaman.farzad.sadaf.spinnergame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button gravityBtn = (Button) findViewById(R.id.gravity_btn);
        Button gyroBtn = (Button) findViewById(R.id.gyro_btn);

        gravityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainActivity);

            }
        });

        gyroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gyroActivity = new Intent(getBaseContext(), GyroActivity.class);
                startActivity(gyroActivity);
            }
        });
    }
}
