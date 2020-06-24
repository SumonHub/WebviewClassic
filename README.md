# WebViewClassic
an easy-to-use tool for using YouTube SearchApi

# Gradle Dependency
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

      allprojects {
          repositories {
            ...
            maven { url 'https://jitpack.io' }
          }
        }
Step 2. Add the dependency
      
      dependencies {
                implementation 'com.github.SumonHub:WebViewClassic:1.0.1'
        }
        
# How to use
Step 1. create an empty activity in android studio (File>New>Acivity>Empty Ativity)
              
Step 2. in xml file paste bellow code
<?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          android:layout_width="fill_parent"
          android:layout_height="fill_parent"
          android:orientation="vertical" >

          <WebView
              android:id="@+id/webView1"
              android:layout_width="fill_parent"
              android:layout_height="fill_parent"
              />
      </LinearLayout>

Step 3. In onCreate() method in .java file paste bellow code

       webView = findViewById(R.id.webView1);
       webUrl = "YOUR_WEB_URL";
       WebViewClassic webViewClassic = new WebViewClassic(this,webView,webUrl);
       webViewClassic.startWebView();
              
              
Step 3. if you want back capability override onBackPressed() like bellow

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
