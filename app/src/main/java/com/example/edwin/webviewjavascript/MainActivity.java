package com.example.edwin.webviewjavascript;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.annotation.SuppressLint;
import android.app.Activity;

import java.io.IOException;

public class MainActivity extends Activity implements OnClickListener {

    private static final String URL = "file:///android_asset/index.html";
//    private static final String URL = "C:\\Users\\Edwin\\AndroidStudioProjects\\WebViewJavascript\\app\\src\\main\\res\\assets";
    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAssetFiles("");
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                String user = ((EditText) findViewById(R.id.edit_text)).getText().toString();
                if (user.isEmpty()) {
                    user = "Mundo";
                }
                String javascript="javascript: document.getElementById('msg').innerHTML='Hola "+user+"!';";
                view.loadUrl(javascript);
            }
        });
        refreshWebView();
        findViewById(R.id.button).setOnClickListener(this);
    }

    private void refreshWebView() {
        mWebView.loadUrl(URL);
    }

    @Override
    public void onClick(View v) {
        refreshWebView();
    }

    private boolean listAssetFiles(String path) {

        String [] list;
        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    Log.d("a", file);
                    if (!listAssetFiles(path + "/" + file))
                        return false;
                }
            } else {
                Log.d("a", list.toString());
                for (String l: list) {
                    Log.d("a", l);
                }
                // This is a file
                // TODO: add file name to an array list
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

}