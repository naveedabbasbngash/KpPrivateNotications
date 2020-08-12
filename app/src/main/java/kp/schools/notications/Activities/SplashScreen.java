package kp.schools.notications.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import kp.schools.notications.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        overridePendingTransition(0, 0);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d(key ,": " + value + "\n\n");
            }
        }

    }

    public void getStarted(View view) {
        startActivity(new Intent(SplashScreen.this,MainActivity.class));
    }
}