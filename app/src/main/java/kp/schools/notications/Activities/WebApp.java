package kp.schools.notications.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.victor.loading.rotate.RotateLoading;

import kp.schools.notications.Helpers.PrefManager;
import kp.schools.notications.R;

public class WebApp extends AppCompatActivity {

    private WebView wv1;
    private RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_app);

        String url= "https://psra.gkp.pk/schoolReg/user/login";

        wv1=(WebView)findViewById(R.id.webView);
        rotateLoading=findViewById(R.id.rotation);

        wv1.setWebViewClient(new MyBrowser());
        rotateLoading.start();

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.setWebChromeClient(new WebChromeClient());
        wv1.loadUrl(url);
        wv1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // TODO show you progress image
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // TODO hide your progress image
                super.onPageFinished(view, url);
                rotateLoading.stop();
            }
        });
    }

    public void inbox(View view) {
        startActivity(new Intent(this,NotificationActivity.class));
        finish();

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void logout(View view) {
        PrefManager prefManager=new PrefManager(this);
        prefManager.saveLoginStatus(this,false);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}