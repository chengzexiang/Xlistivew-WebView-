package com.bwei.czx.chengzexiang1507d0918;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * webview界面
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        WebView web = (WebView) findViewById(R.id.web);
        Intent intent = getIntent();
        //Webview展示
        String url = intent.getStringExtra("url");
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(url);
    }
}
