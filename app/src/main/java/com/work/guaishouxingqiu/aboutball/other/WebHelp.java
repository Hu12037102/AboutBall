package com.work.guaishouxingqiu.aboutball.other;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.NetWorkUtils;

import java.io.File;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 16:48
 * 更新时间: 2019/3/21 16:48
 * 描述:WebView相关的帮助类
 */
public class WebHelp {
    @SuppressLint("SetJavaScriptEnabled")
    public static void initSetting(@NonNull WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
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
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能


    }
}