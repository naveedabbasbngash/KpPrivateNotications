package kp.schools.notications.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import kp.schools.notications.R;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
    }

    public void Notifications(View view) {
        startActivity(new Intent(Dashboard.this,MainActivity.class));
        finish();
    }

    public void WebApp(View view) {
        if (isNetworkConnected()) {
            startActivity(new Intent(Dashboard.this, WebApp.class));
            finish();
        }
        else {
            Toast.makeText(this, "Check Your Internat", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}