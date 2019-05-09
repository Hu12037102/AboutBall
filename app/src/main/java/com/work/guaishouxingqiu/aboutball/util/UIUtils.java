package com.work.guaishouxingqiu.aboutball.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.venue.activity.VenueDetailsActivity;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;
import com.yalantis.ucrop.UCrop;

import org.jsoup.helper.DataUtil;

import java.io.File;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:36
 * 更新时间: 2019/3/4 13:36
 * 描述: 界面相关的工具类
 */
public class UIUtils {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static void init(@NonNull Context context) {
        UIUtils.mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 判断清除按钮是否要不要显示
     *
     * @param editText
     * @param imageView
     */
    public static void checkClearImageStatus(@NonNull EditText editText, @NonNull ImageView imageView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageView.setVisibility(DataUtils.isEmpty(editText.getText().toString()) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 点击清除按钮
     *
     * @param view
     * @param editText
     */
    public static void clickClearEditData(@NonNull View view, @NonNull EditText editText) {
        view.setOnClickListener(v -> {
            editText.setText(null);
            view.setVisibility(View.GONE);
        });
    }

    public static void resultBaseData(@NonNull BaseBean baseBean, @NonNull Activity activity) {
        switch (baseBean.code) {
            case IApi.Code.USER_NO_EXIST:
                HintDialog hintDialog = new HintDialog.Builder(activity)
                        .setTitle(R.string.hint)
                        .setBody(R.string.this_phone_not_register)
                        .setSure(R.string.go_register)
                        .builder();
                hintDialog.show();
                hintDialog.setOnItemClickListener(view -> {
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_REGISTER);
                    hintDialog.dismiss();
                });
                Toasts.with().showToast(baseBean.title);
                break;
            case IApi.Code.SERVICE_ERROR:
                Toasts.with().showToast(baseBean.message);
                break;
            case IApi.Code.USER_EXIST:
                final HintDialog loginDialog = new HintDialog.Builder(activity)
                        .setTitle(R.string.hint)
                        .setBody(R.string.this_phone_is_register)
                        .setSure(R.string.login_immediately)
                        .builder();
                loginDialog.show();
                loginDialog.setOnItemClickListener(view -> {
                    activity.finish();
                    loginDialog.dismiss();
                });
                break;
            case IApi.Code.USER_NOT_LOGIN:
                final HintDialog notLoginDialog = new HintDialog.Builder(activity)
                        .setTitle(R.string.hint)
                        .setBody(R.string.is_go_to_login)
                        .setSure(R.string.login_immediately)
                        .builder();
                notLoginDialog.show();
                notLoginDialog.setOnItemClickListener(view -> {
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_LOGIN);
                    notLoginDialog.dismiss();
                });
                break;
            default:
                break;
        }
    }

    public static void setGameIconStatus(int stateId, TextView textView) {
        switch (stateId) {
            case Contast.GAME_STATUS_STARTING:
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_recommend_live_status, 0);
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.colorFFDB2F23));
                break;
            case Contast.GAME_STATUS_FINISH:
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_recommend_no_start, 0);
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.colorFFA6A6A6));
                break;
            case Contast.GAME_STATUS_NO_START:
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.colorFFA6A6A6));
                break;
            default:
                break;
        }
    }

    public static void uCropImage(Activity activity, File firstFile, File lastFile, int scaleX, int scaleY,
                                  int cropWidth, int cropHeight) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(100);
        options.setToolbarColor(ContextCompat.getColor(activity, R.color.color_4));
        options.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_4));
        options.setLogoColor(ContextCompat.getColor(activity, R.color.color_4));
        options.setActiveWidgetColor(ContextCompat.getColor(activity, R.color.color_2));
        UCrop.of(Uri.fromFile(firstFile), Uri.fromFile(lastFile))
                .withAspectRatio(scaleX, scaleY)
                .withMaxResultSize(cropWidth, cropHeight)
                .withOptions(options)
                .start(activity);

    }

    public static String getString(@StringRes int res, Object... obj) {
        return UIUtils.getContext().getString(res, obj);
    }

    public static View loadNotMoreView(ViewGroup viewGroup) {
        return LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_not_more, viewGroup, false);
    }

    public static void parseScanCode(String result) {
        String[] splits = result.split(",");
        if (splits.length >= 2) {
            Bundle bundle = new Bundle();
            bundle.putString(ARouterConfig.Key.ACTION_ID, splits[0]);
            bundle.putString(ARouterConfig.Key.URL, splits[1]);
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WEB_DATA, bundle);
        }
    }

    public static void showLoginDialog(Context context) {
        HintDialog notLoginDialog = new HintDialog.Builder(context)
                .setTitle(R.string.hint)
                .setBody(R.string.is_go_to_login)
                .setSure(R.string.login_immediately)
                .builder();
        notLoginDialog.show();
        notLoginDialog.setOnItemClickListener(view -> {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_LOGIN);
            notLoginDialog.dismiss();
        });
    }

    public static void showToast(String content) {
        Toasts.with().showToast(content);
    }

    public static void showToast(int resContent) {
        Toasts.with().showToast(resContent);
    }

    public static void showCallPhoneDialog(Context context, String phoneNumber) {
        if (phoneNumber != null && DataUtils.isPhoneNumber(phoneNumber)) {
            String phoneContent = UIUtils.getString(R.string.you_sure_call_this_phone, phoneNumber);
            SpannableString spannableString = null;
            if (phoneContent.length() >= 11) {
                spannableString = SpanUtils.getTextColor(R.color.color_2, phoneContent.length() - 11, phoneContent.length(), phoneContent);
            }

            HintDialog hintDialog = new HintDialog.Builder(context)
                    .setTitle(R.string.hint)
                    .setBody(spannableString == null ? phoneContent : spannableString)
                    .setSures(R.string.sure)
                    .setCancel(R.string.cancel)
                    .setShowSingButton(false)
                    .builder();
            hintDialog.show();
            hintDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
                @Override
                public void onClickSure(@NonNull View view) {
                    PhoneUtils.callPhone(context, phoneNumber);
                    hintDialog.dismiss();
                }

                @Override
                public void onClickCancel(@NonNull View view) {
                    hintDialog.dismiss();
                }
            });
        } else {
            UIUtils.showToast(R.string.this_venue_not_phone);
        }
    }

    public static void setBaseCustomTabLayout(TabLayout tabLayout, String content, boolean isSelector, int tabHeight) {
        View inflateView = LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.item_base_tab_view, tabLayout, false);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TextView tvTitle = inflateView.findViewById(R.id.tv_title);
        tvTitle.setText(content);
        if (isSelector) {
            tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        View viewSelector = inflateView.findViewById(R.id.view_selector);
        Rect rect = new Rect();
        tvTitle.getPaint().getTextBounds(content, 0, content.length(), rect);
        ViewGroup.LayoutParams lineLayoutParams = viewSelector.getLayoutParams();
        lineLayoutParams.width = rect.width();
        lineLayoutParams.height = ScreenUtils.dp2px(tabLayout.getContext(), 4);
        viewSelector.setLayoutParams(lineLayoutParams);
        TabLayout.Tab tab = tabLayout.newTab().setCustomView(inflateView);
        tabLayout.addTab(tab);
        if (isSelector) {
            viewSelector.setVisibility(View.VISIBLE);
            tab.select();
        } else {
            viewSelector.setVisibility(View.GONE);
        }
        if (tab.getCustomView() != null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ScreenUtils.dp2px(mContext, tabHeight));

            tab.getCustomView().setLayoutParams(layoutParams);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                notifySelectorBaseTab(tabLayout);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static void setBaseCustomTabLayout(TabLayout tabLayout, String content, boolean isSelector, int tabHeight, boolean isAddListener) {
        View inflateView = LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.item_base_tab_view, tabLayout, false);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TextView tvTitle = inflateView.findViewById(R.id.tv_title);
        tvTitle.setText(content);
        if (isSelector) {
            tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        View viewSelector = inflateView.findViewById(R.id.view_selector);
        Rect rect = new Rect();
        tvTitle.getPaint().getTextBounds(content, 0, content.length(), rect);
        ViewGroup.LayoutParams lineLayoutParams = viewSelector.getLayoutParams();
        lineLayoutParams.width = rect.width();
        lineLayoutParams.height = ScreenUtils.dp2px(tabLayout.getContext(), 4);
        viewSelector.setLayoutParams(lineLayoutParams);


        TabLayout.Tab tab = tabLayout.newTab().setCustomView(inflateView);
        tabLayout.addTab(tab);
        if (isSelector) {
            viewSelector.setVisibility(View.VISIBLE);
            tab.select();
        } else {
            viewSelector.setVisibility(View.GONE);
        }
        if (tab.getCustomView() != null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ScreenUtils.dp2px(mContext, tabHeight));

            tab.getCustomView().setLayoutParams(layoutParams);
        }
        if (isAddListener) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    notifySelectorBaseTab(tabLayout);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }


    public static void notifySelectorBaseTab(TabLayout tabLayout) {
        int tabCount = tabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab == null) {
                continue;
            }
            View inflateView = tab.getCustomView();
            if (inflateView == null) {
                continue;
            }
            TextView tvTitle = inflateView.findViewById(R.id.tv_title);
            View viewSelector = inflateView.findViewById(R.id.view_selector);
            if (tab.isSelected()) {
                viewSelector.setVisibility(View.VISIBLE);
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                Rect rect = new Rect();
                tvTitle.getPaint().getTextBounds(tvTitle.getText().toString(), 0, tvTitle.getText().length(), rect);
                ViewGroup.LayoutParams lineLayoutParams = viewSelector.getLayoutParams();
                lineLayoutParams.width = rect.width();
                lineLayoutParams.height = ScreenUtils.dp2px(tabLayout.getContext(), 4);
                viewSelector.setLayoutParams(lineLayoutParams);

            } else {
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                viewSelector.setVisibility(View.GONE);
            }

        }

    }

    public static void setText(@NonNull TextView textView, String content) {
        textView.setText(DataUtils.isEmpty(content) ? "" : content);
    }
}
