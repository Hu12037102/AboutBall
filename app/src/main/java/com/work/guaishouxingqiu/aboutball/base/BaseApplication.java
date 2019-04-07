package com.work.guaishouxingqiu.aboutball.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alivc.player.AliVcMediaPlayer;
import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.work.guaishouxingqiu.aboutball.BuildConfig;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

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

    private IWXAPI mWeiChatApi;

    public IWXAPI getWeiChatApi() {
        return DataUtils.checkData(mWeiChatApi);
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
        Bugtags.start(Contast.SECRET_KEY.Bugtag_ID, this, Bugtags.BTGInvocationEventBubble, options);
        initARouter();
        initALi();
        initWeiChat();

    }

    protected void initWeiChat() {
        mWeiChatApi = WXAPIFactory.createWXAPI(this, Contast.SECRET_KEY.WEICHAT_APP_ID,false);
        mWeiChatApi.registerApp(Contast.SECRET_KEY.WEICHAT_APP_ID);
        LogUtils.w("initWeiChat--",Contast.SECRET_KEY.WEICHAT_APP_ID);
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
