# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系

-dontoptimize
-verbose
# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-ignorewarnings # 抑制警告
#-dontwarn java.awt.**"
# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
 # 因为这些子类都有可能被外部调用
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Appliction
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class * extends android.view.View
 -keep class android.support.v4.app.** { *; }
 -keep interface android.support.v4.app.** { *; }
 -keep public class com.android.vending.licensing.ILicensingService

#保留在Activity中的方法参数是view的方法，
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}


 # 保留support下的所有类及其内部类
 -keep class android.support.** {*;}

 # 保留继承的
 -keep public class * extends android.support.v4.**
 -keep public class * extends android.support.v7.**
 -keep public class * extends android.support.annotation.**

 # 保留R下面的资源
 -keep class **.R$* {*;}
 # 保留本地native方法不被混淆
 -keepclasseswithmembernames class * {
     native <methods>;
 }

 # 保留在Activity中的方法参数是view的方法，
 # 这样以来我们在layout中写的onClick就不会被影响
 -keepclassmembers class * extends android.app.Activity{ public void *(android.view.View); }

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
*** get*();
 void set*(***);
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.util.AttributeSet);
 public <init>(android.content.Context, android.util.AttributeSet, int); }

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
static final long serialVersionUID;
 private static final java.io.ObjectStreamField[] serialPersistentFields;
 !static !transient <fields>; !private <fields>;
 !private <methods>; private void writeObject(java.io.ObjectOutputStream);
 private void readObject(java.io.ObjectInputStream);
 java.lang.Object writeReplace();
 java.lang.Object readResolve(); }

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}
# webView处理，项目中没有使用到webView忽略即可
 -keepclassmembers class fqcn.of.javascript.interface.for.webview { public *; }
 -keepclassmembers class * extends android.webkit.webViewClient {
 public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
  public boolean *(android.webkit.WebView, java.lang.String); }
  -keepclassmembers class * extends android.webkit.webViewClient {
  public void *(android.webkit.webView, jav.lang.String); }

#====================第三方代码混淆，有就添加==============
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-keep class com.trello.rxlifecycle2.**{*;}
-keep class io.reactivex.**{*;}
-keep class io.reactivex.android**{*;}
#===============Bugtags代码混淆==================
-keepattributes LineNumberTable,SourceFile
-keep class com.bugtags.library.** {*;}
-dontwarn com.bugtags.library.**
-keep class io.bugtags.** {*;}
-dontwarn io.bugtags.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-keep class sun.misc.Unsafe { *; }
-keep class com.idea.fifaalarmclock.entity.***
-keep class com.google.gson.** { *; }

-keep class com.tencent.** { *; }
-keep class com.work.guaishouxingqiu.aboutball.other.**{*;}
-keep class com.work.guaishouxingqiu.aboutball.home.activity.MainActivity{*;}
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
#保留所有的bean
-keep public class com.work.guaishouxingqiu.aboutball.base.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.commonality.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.community.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.game.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.good.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.home.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.login.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.media.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.my.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.venue.bean.**{*;}
-keep public class com.work.guaishouxingqiu.aboutball.OnItemClickListener.{*;}


#路由器混淆规则
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider


-dontwarn javax.annotation.**
-dontwarn javax.inject.**
#OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn sun.misc.**
-dontwarn sorg.codehaus.mojo.animal_sniffer.**
-dontwarn org.codehaus.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.ArrayQueueField* {
long producerIndex;
long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

#EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep public class * extends java.lang.annotation.Annotation {}
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# 保持测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**
#AutoSize
-keep class me.jessyan.autosize.** { *; }
-keep interface me.jessyan.autosize.** { *; }

#Ali直播的代码混淆
-keep class com.alivc.player.**{*;}
-keep class com.aliyun.clientinforeport.**{*;}
-dontwarn com.alivc.player.**
#OSS代码混淆
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#微信登录分享混淆
-keep class com.tencent.mm.opensdk.** {
    *;
}

-keep class com.tencent.wxop.** {
    *;
}

-keep class com.tencent.mm.sdk.** {
    *;
}

-keep class com.growingio.** {
    *;
}

#growingio代码混淆
-dontwarn com.growingio.**
-keepnames class * extends android.view.View
-keepnames class * extends android.app.Fragment
-keepnames class * extends android.support.v4.app.Fragment
-keepnames class * extends androidx.fragment.app.Fragment
-keep class android.support.v4.view.ViewPager{
  *;
}
-keep class android.support.v4.view.ViewPager$**{
  *;
}
-keep class androidx.viewpager.widget.ViewPager{
  *;
}
-keep class androidx.viewpager.widget.ViewPager$**{
  *;
}
#腾讯地图代码混淆
-keepclassmembers class ** {
    public void on*Event(...);
}
-keep class c.t.**{*;}
-keep class com.tencent.map.geolocation.**{*;}
-keep class com.tencent.tencentmap.lbssdk.service.**{*;}
-dontwarn  org.eclipse.jdt.annotation.**
-dontwarn  c.t.**
#腾讯地图 2D sdk
-keep class com.tencent.mapsdk.**{*;}
-keep class com.tencent.tencentmap.**{*;}
#腾讯地图 3D sdk
-keep class com.tencent.tencentmap.**{*;}
-keep class com.tencent.map.**{*;}
-keep class com.tencent.beacontmap.**{*;}
-keep class navsns.**{*;}
-dontwarn com.qq.**
-dontwarn com.tencent.beacon.**
#腾讯地图检索sdk
-keep class com.tencent.lbssearch.**{*;}
-keepattributes Signature
-dontwarn com.tencent.lbssearch.**
#友盟统计代码混淆
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class com.work.guaishouxingqiu.aboutball.R$*{
public static final int *;
}
-dontwarn io.microshow.rxffmpeg.**
-keep class io.microshow.rxffmpeg.**{*;}

#腾讯数据统计
-keep class com.tencent.stat.*{*;}
-keep class com.tencent.mid.*{*;}



