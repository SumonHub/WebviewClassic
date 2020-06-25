package com.sumon.webviewclassic;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.sumon.webview_classic.WebViewClassic;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        String webUrl = "https://www.google.com/";
        webView = findViewById(R.id.webView);

        WebViewClassic webViewClassic = new WebViewClassic(this, webView, webUrl);
        //webViewClassic.setCustomDialog(progressDialog);
        //webViewClassic.setDialogDuration(10*1000);
        //webViewClassic.setClearCache(true);
        webViewClassic.startWebView();

    }

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
}