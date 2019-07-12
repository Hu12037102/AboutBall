package com.work.guaishouxingqiu.aboutball.other;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.NonNull;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.NetWorkUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 16:48
 * 更新时间: 2019/3/21 16:48
 * 描述:WebView相关的帮助类
 */
public class WebHelp {
    @SuppressLint("SetJavaScriptEnabled")
    public static void initSetting(@NonNull WebView webView) {
        initSetting(webView, false);
    }

    public static void initSetting(@NonNull WebView webView, boolean javaScriptEnabled) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(javaScriptEnabled);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        webSettings.setAppCachePath(FileUtils.getNetCacheFile().getAbsolutePath());
        if (NetWorkUtils.isNetCanUse()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能

        //硬件加速
      // webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(false); // 关键点
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


    }
}
