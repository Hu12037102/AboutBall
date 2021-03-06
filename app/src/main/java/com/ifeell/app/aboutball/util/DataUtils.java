package com.ifeell.app.aboutball.util;

import android.content.Context;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.commonality.bean.ShareWebBean;
import com.ifeell.app.aboutball.home.bean.ResultRedPointInfoBean;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.other.SharedPreferencesHelp;
import com.ifeell.app.aboutball.other.TencentBuriedPoint;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.venue.bean.ResultOrderDetailsBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:36
 * 更新时间: 2019/3/4 13:36
 * 描述: 数据相关的工具类
 */
public class DataUtils {
    public static <T> T checkData(T t) {
        if (t == null) {
            throw new NullPointerException("data not is null !");
        }
        return t;
    }

    public static <T> boolean isEmptyList(List<T> data) {
        return (data == null || data.size() == 0);
    }

    public static int getTextLength(@NonNull TextView textView) {
        if (textView == null)
            return 0;
        return DataUtils.getTextTrimLength(textView.getText());
    }

    /**
     * 判断数据是存在
     *
     * @param data
     * @return
     */
    public static boolean isEmpty(String data) {
        if (data == null || TextUtils.isEmpty(data) || TextUtils.getTrimmedLength(data) == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(@NonNull CharSequence data) {
        if (TextUtils.isEmpty(data) || TextUtils.getTrimmedLength(data) == 0) {
            return true;
        }
        return false;
    }


    public static int getTextTrimLength(@NonNull CharSequence data) {
        if (!TextUtils.isEmpty(data)) {
            return TextUtils.getTrimmedLength(data);
        }
        return 0;
    }

    public static <T> int getListSize(@NonNull List<T> data) {
        if (isEmptyList(data)) {
            return 0;
        }
        return data.size();
    }

    /**
     * 判断是不是手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean
    isPhoneNumber(@NonNull String phoneNumber) {
        Pattern pattern = Pattern.compile(Contast.REGEX_PHONE_NUMBER);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isTellPhone(final String str) {
        Matcher m;
        boolean b;
        Pattern p1 = Pattern.compile(Contast.TELL_PHONE_ZONE); // 验证带区号的
        Pattern p2 = Pattern.compile(Contast.TELL_PHONE_NO_ZONE);     // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 延时倒计时
     */
    public static void countDownTime() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(60).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        if (compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    /**
     * 判断是不是密码
     *
     * @param password
     * @return
     */
    public static boolean isPassword(@NonNull String password) {
        return (!DataUtils.isEmpty(password) && DataUtils.getTextTrimLength(password) >= Contast.MIN_PASSWORD_LENGTH);
    }

    /**
     * 判断是不是验证码
     *
     * @param messageCode
     * @return
     */
    public static boolean isMessageCode(@NonNull String messageCode) {
        return DataUtils.getTextTrimLength(messageCode) == Contast.MESSAGE_CODE_LENGTH;
    }

    public static int splitImagePathCount(@NonNull String imagePath) {
        if (DataUtils.isEmpty(imagePath)) {
            return 0;
        }
        String[] imagePathArray = imagePath.split(",");
        return imagePathArray.length;
    }

    public static boolean isResultSure(@NonNull BaseBean baseBean) {
        if (baseBean == null) {
            return false;
        }
        return IApi.Code.SUCCEED == baseBean.code && baseBean.result != null;
    }

    public static boolean isResultSureResultNull(@NonNull BaseBean baseBean) {
        return IApi.Code.SUCCEED == baseBean.code;
    }

    public static String videoDuration(long videoDuration) {
        StringBuilder sb = new StringBuilder();
        if (videoDuration >= 1000) {
            int second = (int) (videoDuration / 1000);
            if (second / 60 >= 1) {
                int minute = second / 60;
                int remainderSecond = second % 60;
                sb.append(minute).append(remainderSecond >= 10 ? ":" + remainderSecond : ":0" + remainderSecond);
            } else {
                if (second >= 10) {
                    sb.append("0:").append(second);
                } else {
                    sb.append("0:0").append(second);
                }
            }

        } else {
            sb.append("0:01");
        }
        return sb.toString();

    }

    public static List<ResultRedPointInfoBean> jsonToListRedPoint(@NonNull String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<ResultRedPointInfoBean>>() {
        }.getType());
    }

    public static String getAssetsData(Context context, String name) {
        BufferedReader bis = null;
        StringBuilder sb;
        InputStreamReader isr = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(name);
            isr = new InputStreamReader(is);
            bis = new BufferedReader(isr);

            sb = new StringBuilder();
            String line;
            while ((line = bis.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return null;
    }

    public static String getEditDetails(EditText editText) {
        return DataUtils.checkData(editText.getText()).toString().trim();
    }

    public static <T> boolean baseDataBeanIsSucceed(BaseBean<BaseDataBean<T>> bean) {
        return bean.code == IApi.Code.SUCCEED && bean.result != null && bean.result.code == IApi.Code.SUCCEED;
    }

    /**
     * 是不是视频;
     *
     * @param url
     * @return
     */
    public static boolean isVideo(@NonNull String url) {
        return !DataUtils.isEmpty(url) && (url.endsWith(".mp4") || url.endsWith(".3gp") || url.endsWith(".avi"));
    }

    /**
     * 构造默认的球队分享bean
     *
     * @param teamId
     * @return
     */
    public static ShareWebBean resultShareBallTeam(long teamId) {
        ShareWebBean bean = new ShareWebBean();
        bean.webUrl = IApiService.H5.DOWNLOAD_APK + "?" + ARouterConfig.Key.SHARE_ID + "=" + teamId + "&" + ARouterConfig.Key.SHARE_TYPE + "=" + IApiService.TypeId.OPEN_BALL_INVITE;
        LogUtils.w("ShareWebBean--", bean.webUrl);
        bean.title = "加入球队";
        bean.description = "您的朋友邀请您一起组建球队，马上点击确认吧";
        return bean;
    }

    public static ShareWebBean resultShareGameVideo(long gameId, String hostName, String guestName) {
        ShareWebBean webBean = new ShareWebBean();
        webBean.title = UIUtils.getString(R.string.share_game_video_title);
        webBean.description = UIUtils.getString(R.string.share_game_video_content, hostName, guestName);
        webBean.webUrl = IApiService.H5.DOWNLOAD_APK + "?" + ARouterConfig.Key.SHARE_ID + "=" + gameId + "&" + ARouterConfig.Key.SHARE_TYPE + "=" + IApiService.TypeId.OPEN_GAME_DETAILS_VIDEO;
        return webBean;
    }

    /**
     * @param number 数据
     * @return 返回int整形数据
     */
    public static int getInt(String number) {
        if (DataUtils.isEmpty(number)) {
            return -1;
        }
        try {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setParseIntegerOnly(true);
            return nf.parse(number).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @param number 小数
     * @return 返回百分数
     */
    public static String getPercent(Number number) {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2); //最大保留两位小数百分数后两位
        return numberFormat.format(number);
    }

    public static float getPercent2Float(String numberParse) {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        try {
            Number number = numberFormat.parse(numberParse);
            return number.floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0f;
    }

    public static double getDoubleFormat(String number) {
        double result = 0;
        if (DataUtils.isEmpty(number)) {
            return result;
        }
        try {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setGroupingUsed(false);
            result = nf.parse(number).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    public static String getMoneyFormat(Number number) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        return numberFormat.format(number);
    }

    public static String getMoneyFormat(@NonNull String numberFormat) {
        String result = "";
        if (DataUtils.isEmpty(numberFormat)) {
            return result;
        }
        try {
            Double number = Double.valueOf(numberFormat);
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINA);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setGroupingUsed(false);
            result = nf.format(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getIntFormat(@NonNull String numberFormat) {
        int result = -1;
        if (DataUtils.isEmpty(numberFormat)) {
            return result;
        }
        try {
            result = Integer.valueOf(numberFormat);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
    public static int getIntFormat(@NonNull String numberFormat,int defaultInt) {
        int result = defaultInt;
        if (DataUtils.isEmpty(numberFormat)) {
            return result;
        }
        try {
            result = Integer.valueOf(numberFormat);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public static Double getMoneyFormats(Number number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        return Double.valueOf(numberFormat.format(number));
    }

    public static String getNotNullData(String content) {
        return content == null ? "" : content;
    }

    public static String getOrderSiteContent(List<ResultOrderDetailsBean.OrderPeopleCountBean> data) {
        if (data == null || data.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            ResultOrderDetailsBean.OrderPeopleCountBean bean = data.get(i);
            if (bean == null) {
                continue;
            }
            sb = sb.append(bean.areaName).append("  ").append(bean.calendar).append("  ").append("￥").append(bean.price).append(i == data.size() - 1 ? "" : "\n");
        }
        return sb.toString();
    }

    /**
     * 集合数据用逗号拼接
     *
     * @param data
     * @return
     */
    public static String getStringFormat(List<String> data) {
        if (data == null || data.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            sb = sb.append(data.get(i)).append(i == data.size() - 1 ? "" : ",");
        }
        return sb.toString();
    }

    public static String appendImagesPath(List<String> pathData) {
        if (pathData == null || pathData.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < pathData.size(); i++) {
            sb = sb.append(pathData.get(i)).append(i == pathData.size() - 1 ? "" : ",");
        }
        return sb.toString();
    }

    public static boolean isReadNews(long newId) {
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        return sp.isContainsKey("News" + newId);
    }

    public static void putNewsKey(long newId) {
        String key = "News" + newId;
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        if (!sp.isContainsKey(key)) {
            sp.putObject(key, true);
        }
    }

    public static String getTextViewContent(@NonNull TextView textView) {
        return DataUtils.isEmpty(textView.getText()) ? "" : textView.getText().toString().trim();
    }

    public static ArrayList<String> getOnePreviewData(@NonNull String imagePath) {
        ArrayList<String> data = new ArrayList<>();
        data.add(imagePath);
        return data;
    }

    public static boolean hasDigit(String content) {
        if (DataUtils.isEmpty(content)) {
            return false;
        }
        for (int i = 0; i < content.length(); i++) {
            char childChar = content.charAt(i);
            if (Character.isDigit(childChar)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getImageUrlData(String imageUrl) {
        List<String> imageUrlData = new ArrayList<>();
        if (!DataUtils.isEmpty(imageUrl)) {
            String[] urlArray = imageUrl.split(",");
            imageUrlData.addAll(Arrays.asList(urlArray));
            return imageUrlData;
        }
        return imageUrlData;
    }

    public static String getPath(@NonNull String path) {
        if (DataUtils.isEmpty(path)) {
            return System.currentTimeMillis() + "";
        }

        if (path.contains(Contast.SPLIT_IMAGE)) {
            return System.currentTimeMillis() + path.replaceAll(Contast.SPLIT_IMAGE, "");
        }
        return System.currentTimeMillis() + path;
    }

    public static boolean isImgae(@NonNull String imagePath) {
        if (DataUtils.isEmpty(imagePath)) {
            return false;
        }
        if (imagePath.endsWith(".jpg") || imagePath.endsWith(".jpeg") || imagePath.endsWith(".png") ||
                imagePath.endsWith(".bmp") || imagePath.endsWith(".webp")) {
            return true;
        }
        return false;
    }

    public static void addSellingPoint(Context context, String key) {
        TencentBuriedPoint.defaultBuriedPoint(context, key);
    }

    /**
     * 获取表情文字在文本中对应所有的下标
     *
     * @param content
     * @param key
     * @return
     */
    public static List<Integer> getContainsIndexs(String content, String key) {
        List<Integer> data = new ArrayList<>();
        StringBuffer sb = new StringBuffer(content);

        String replaceContent = "";
        for (int i = 0; i < key.length(); i++) {
            replaceContent = replaceContent.concat("-");
        }
        while (sb.toString().contains(key)) {
            int index = sb.indexOf(key);
            data.add(index);
            sb = sb.replace(index, index + key.length(), replaceContent);
        }
        return data;
    }

    /**
     * 判断字符是不是表情
     *
     * @param codePoint
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

    }

    /**
     * 字符串是否含有表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(@NonNull String source) {
        if (DataUtils.isEmpty(source)) {
            return false;
        }
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }


}
