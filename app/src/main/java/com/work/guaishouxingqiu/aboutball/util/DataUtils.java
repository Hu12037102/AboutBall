package com.work.guaishouxingqiu.aboutball.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ShareWebBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
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


    public static int getTextTrimLength(@NonNull String data) {
        if (!TextUtils.isEmpty(data)) {
            return TextUtils.getTrimmedLength(data);
        }
        return 0;
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
        return IApi.Code.SUCCEED == baseBean.code && baseBean.result != null;
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
     * 构造默认的球队分享bean
     *
     * @param webUrl
     * @return
     */
    public static ShareWebBean resultShareBallTeam(@NonNull String webUrl) {
        ShareWebBean bean = new ShareWebBean();
        bean.webUrl = webUrl;
        bean.title = "加入球队";
        bean.description = "您的朋友邀请您一起组建球队，马上点击确认吧";
        return bean;
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

    public static String getMoneyFormat(Number number) {

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        return numberFormat.format(number);
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
            sb = sb.append(bean.areaName).append("  ").append(bean.calendar).append("  ").append("￥").append(bean.price).append(i == data.size() - 1 ? "" : "\n");
        }
        return sb.toString();
    }

    /**
     * 集合数据用逗号拼接
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


}
