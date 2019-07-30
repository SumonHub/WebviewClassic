package com.sumon.webview_classic;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by SumOn on 7/30/2019.
 */

public class WebViewClassic {
    private static final String TAG = WebViewClassic.class.getSimpleName();

    private Context context;
    private WebView webView;
    private String webUrl;

    public WebViewClassic(Context context, WebView webView, String webUrl) {
        this.context = context;
        this.webView = webView;
        this.webUrl = webUrl;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void startWebView() {
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

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

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    if (progressDialog != null &&progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);

    }

}