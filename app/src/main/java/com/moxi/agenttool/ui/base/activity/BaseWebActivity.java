package com.moxi.agenttool.ui.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.moxi.agenttool.R;
import com.moxi.agenttool.wdiget.NormalTitleBar;

/**
 * 详情界面
 */
public class BaseWebActivity extends Activity{
    private ProgressBar progressBar;
    private WebView webview;
    private String url;
    private boolean showTitle;
    private TextView tvCard;
    private View tvSave;
    private View llBottom;
    private String title;
    private NormalTitleBar ntb;




    public static void actionStart(Context context, String url, String title) {
        Intent intent = new Intent(context, BaseWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        BarUtils.transparentStatusBar(this);
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        setContentView(R.layout.activity_base_web);
        ntb = findViewById(R.id.ntb);
        webview = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_bar);
        Log.e("urlurl",url);
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
        }
        ntb.setNewTitleText(title);
        //配置WebSettings
        WebSettings settings = webview.getSettings();
        //设置自适应屏幕，两者合用
//        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        // 让WebView能够执行javaScript
        settings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        settings.setAppCacheEnabled(true);
//        // 设置缓存模式,一共有四种模式
//        settings.setCacheMode(settings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
        settings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        settings.setDisplayZoomControls(true);
        // 设置默认字体大小
        settings.setDefaultFontSize(12);
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他操作
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片

        //配置WebViewClient，使用WebView加载
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//表示等待证书相应
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //只要调用一下此方法就可以顺利执行
            }
        });

        //配置WebChromeClient类
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
    // 设置回退监听
    // 5、覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
            if (webview.canGoBack())
            {
                webview.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            }else
            {
                finish();
                return true;
            }

        }
        return false;
    }


}