package com.zq.webviewerrordemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView webview;
    LinearLayout linearLayout;
    ImageView imageView;
    LinearLayout webviewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        webview=(WebView)findViewById(R.id.webview);
        linearLayout= (LinearLayout) findViewById(R.id.web_started);
        imageView= (ImageView) findViewById(R.id.started_gif);
        webviewError=(LinearLayout)findViewById(R.id.webview_error);
        findViewById(R.id.webview_onclick).setOnClickListener(this);
        initData();

    }

    private void initData(){
        Glide.with(MainActivity.this).load(R.mipmap.loading_circle).asGif().into(imageView);
        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        webview.setVerticalScrollBarEnabled(false); //垂直不显示
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setAppCacheMaxSize(1024 * 1024 * 25);//设置缓冲大小，我设的是8M
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setSavePassword(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        if (!NetUtils.isNetworkAvailable(MainActivity.this)) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        webview.loadUrl("http://stsc.liaidicn.com/app.php?platform=android&appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1&c=posts");
        showWebViewLoad();
    }

    private void showWebViewLoad(){
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                return true;
            }
            //当开始载入页面的时候调用
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
            /*初始加载完成*/
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                linearLayout.setVisibility(View.GONE);
            }
            /*加载失败时*/
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                webviewError.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (!NetUtils.isNetworkAvailable(MainActivity.this)) {//网络是否连接
            Toast.makeText(MainActivity.this,"请检查网络连接",Toast.LENGTH_LONG).show();
        }else{
            webview.loadUrl("http://stsc.liaidicn.com/app.php?platform=android&appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1&c=posts");
            webviewError.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);

        }
    }
}
