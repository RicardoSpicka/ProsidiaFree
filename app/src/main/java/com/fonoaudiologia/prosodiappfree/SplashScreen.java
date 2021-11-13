package com.fonoaudiologia.prosodiappfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.LinkMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {


    TextView textView, clique;
    private CountDownTimer count;
    private long timeLeftInMilliseconds = 20000;
    boolean time = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        clique = (TextView) findViewById(R.id.clique);
        clique.setMovementMethod(LinkMovementMethod.getInstance());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView = (TextView) findViewById(R.id.count);

        count = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                int seconds = (int) (timeLeftInMilliseconds / 1000);
                textView.setText(String.valueOf(seconds));
                if (seconds == 0) {
                    fechaSplashScreen();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();




    }

    private void fechaSplashScreen(){

            super.finish();
            Intent intent = new Intent(this, MenuActivity.class);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
            ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());


    }

}