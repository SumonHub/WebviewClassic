package com.sumon.webview_classic;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by SumOn on 7/30/2019.
 */

public class WebViewClassic {
    private static final String TAG = "WebViewClassic";

    private Context context;
    private WebView webView;
    private String webUrl;
    private ProgressDialog progressDialog;
    private long dialogDuration = 2000;
    private boolean setDialogDuration = false;
    private boolean clearCache = false;

    public WebViewClassic(Context context, WebView webView, String webUrl) {
        this.context = context;
        this.webView = webView;
        this.webUrl = webUrl;
    }

    public void setCustomDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public void setDialogDuration(long dialogDuration) {
        this.dialogDuration = dialogDuration;
        this.setDialogDuration = true;
    }

    public void setClearCache(boolean clearCache) {
        this.clearCache = clearCache;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void startWebView() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressNumberFormat(null);
            progressDialog.setProgressPercentFormat(null);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();

        } else {
            progressDialog.show();
        }


        if (setDialogDuration) {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }
                    , dialogDuration);
        }

        webView.loadUrl(webUrl);
        webView.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            //If url has "tel:245678" , on clicking the number it will directly call to inbuilt calling feature of phone
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    context.startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished");
                try {
                    if (!setDialogDuration && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        if (clearCache) {
            // Clear all the Application Cache, Web SQL Database and the HTML5 Web Storage
            WebStorage.getInstance().deleteAllData();
        }

    }
}