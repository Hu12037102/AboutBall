package com.work.guaishouxingqiu.aboutball.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alivc.player.AliVcMediaPlayer;
import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.work.guaishouxingqiu.aboutball.BuildConfig;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 12:17
 * 更新时间: 2019/3/4 12:17
 * 描述:
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 对Application的一个进行初始化
     */
    private void init() {
        UIUtils.init(this);
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置，默认 true
                trackingCrashLog(false).//是否收集crash，默认 true
                trackingConsoleLog(false).//是否收集console log，默认 true
                trackingUserSteps(false).//是否收集用户操作步骤，默认 true
                trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 url 规则，默认 null
                build();
        //初始化Bugtags采集
        Bugtags.start("96481d2c6099fa3e827b8c04d036d566", this, Bugtags.BTGInvocationEventBubble,options);
        initARouter();
        initALi();
    }

    /**
     * 初始化Ali视频
     */
    private void initALi() {
        AliVcMediaPlayer.init(this);
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            return new MaterialHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

}
