package com.example.c.xwalkviewservice1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebResourceResponse;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;
import org.chromium.mojom.mojo.HttpConnection;
import org.chromium.mojom.mojo.HttpRequest;
import org.chromium.mojom.mojo.HttpResponse;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private Logger logger = Logger.getLogger("com.tr");
    //private CordovaWebViewEngine view1;
    private XWalkView view;
    private static String REFERER_URL = "http://www.youtube.com/";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, MyService.class));
        bindService(new Intent(this, MyService.class), this, BIND_AUTO_CREATE);
    }

    @Override
    public void setContentView(View view) {
        final ViewParent parent = view.getParent();
        if (parent != null) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
        super.setContentView(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean bound;

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(this);
            bound = false;
        }
    }


    public void onServiceConnected(ComponentName name, IBinder s) {
        bound = true;
        MyService.MyBinder binder = (MyService.MyBinder) s;
        if (binder.getView() != null) {
            view = binder.getView();

            ((MutableContextWrapper) view.getContext()).setBaseContext(this);
            view.onShow();
        } else {
           // view1 = new CordovaWebView() {

            view = new XWalkView(new MutableContextWrapper(this), this) {
                @Override
                public void onDestroy() {
                    // super.onDestroy();
                    //disable this method to keep an insatce in memory
                }
            };


         //   Map<String, String> extraHeaders = new HashMap<String, String>();
          //  extraHeaders.put("html","http://www.youtube.com/");
          //  extraHeaders.put("text/html","http://www.youtube.com/");
           // extraHeaders.put("utf-8","http://www.youtube.com/");
        //    extraHeaders.put("Referer", "http://www.youtube.com");
         //   extraHeaders.put("referrer", "https://www.youtube.com/?");
         //   extraHeaders.put("referrer", "https://www.youtube.com/?");
         //   extraHeaders.put("referrer", "https://m.youtube.com");
         //   extraHeaders.put("Referer", "http://www.google.com");
         //   extraHeaders.put("referrer", "2");
         //   extraHeaders.put("Accept-Encoding", "utf-8");
         //   extraHeaders.put("Accept-Language", "zh-cn");
         //   extraHeaders.put("Referer","2");

            Map<String,String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Accept-Encoding", "utf-8");
            extraHeaders.put("Accept-Language", "zh-cn");
            extraHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
            extraHeaders.put("Referer", "http://www.google.com");

            view.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");


            view.setResourceClient(new XWalkResourceClient(view)
            {

                @Override
                public XWalkWebResourceResponse shouldInterceptLoadRequest(XWalkView view, XWalkWebResourceRequest request) {

                    try{



                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.setHeader("MY-CUSTOM-HEADER", "header value");
                    httpGet.setHeader(HttpHeaders.USER_AGENT, "custom user-agent");
                    HttpResponse httpReponse = client.execute(httpGet);

                    PreferenceActivity.Header contentType = httpReponse.getEntity().getContentType();
                    PreferenceActivity.Header encoding = httpReponse.getEntity().getContentEncoding();
                    InputStream responseInputStream = httpReponse.getEntity().getContent();

                    String contentTypeValue = null;
                    String encodingValue = null;
                    if (contentType != null) {
                        contentTypeValue = contentType.getValue();
                    }
                    if (encoding != null) {
                        encodingValue = encoding.getValue();
                    }
                    return new WebResourceResponse(contentTypeValue, encodingValue, responseInputStream);
                } catch (ClientProtocolException e) {
                //return null to tell WebView we failed to fetch it WebView should try again.
                return null;
            } catch (IOException e) {
                //return null to tell WebView we failed to fetch it WebView should try again.








                    return super.shouldInterceptLoadRequest(view, request);
                }
            }


            );
           // view.setResourceClient();



          //  view.setResourceClient((new XWalkWebResourceRequest() {
           //       }));
          //  view.setResourceClient(new XWalkResourceClient(view){

           //     @Override
            //    public XWalkWebResourceResponse shouldInterceptLoadRequest(XWalkView view, XWalkWebResourceRequest request) {

                   // final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36";
              //      URL url = null;
                //    try {
                //        url = new URL("http://www.youtube.com");
                //    } catch (MalformedURLException e) {
                //        e.printStackTrace();
                //    }
                    //  final String REFERER_URL = "http://www.w3.org/hypertext/DataSources/Overview.html";

                  //  URLConnection connection = new URLConnection(url) {
                //        @Override
                  //      public void connect() throws IOException {
                 //           setRequestProperty("html","http://www.youtube.com/");
                 //           setRequestProperty("referrer","http://www.youtube.com/");

                   //     }
                 //   };
                   // HttpsURLConnection httpURLConnection = (HttpsURLConnection)
                   // HttpRequest request = new HttpRequest();


                    // add request header
                   // request.addHeader("User-Agent", USER_AGENT);
                   // request.addHeader("Referer", REFERER_URL);

                  //  try {
                   //     HttpResponse response = httpclient.execute(request);
                  //  } catch (Exception e) {
                        // ...
                  //  }




                  //  return super.shouldInterceptLoadRequest(view, request);
               // };

            //    @Override
            //    public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
                   // URLConnection



              //      return super.shouldOverrideUrlLoading(view, url);
                    //return true;
              //  }
           // }//);

            view.load("file:///android_asset/youtubehtml.html", null,extraHeaders);


            binder.setView(view);

        }
        setContentView(view);
    }

    public void onServiceDisconnected(ComponentName name) {

    }



}



