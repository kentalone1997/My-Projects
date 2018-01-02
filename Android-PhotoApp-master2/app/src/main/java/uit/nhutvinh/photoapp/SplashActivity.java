package uit.nhutvinh.photoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Vin Vin on 30/12/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private TextView txtLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        addControls();
        addEvents();

    }

    private void addEvents() {
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.transition);

        txtLogo.startAnimation(anim);
        imgLogo.startAnimation(anim);

        final Intent i = new Intent(this,MainActivity.class);
        Thread timer = new Thread(){
            public void run ()
            {
                try{
                    sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }

        };
        timer.start();
    }

    private void addControls() {
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        txtLogo = (TextView) findViewById(R.id.txtLogo);
    }
}
