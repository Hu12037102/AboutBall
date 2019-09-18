package com.work.guaishouxingqiu.aboutball.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.bean.EmojiBean;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityDataAdapter;
import com.work.guaishouxingqiu.aboutball.other.EmojiManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
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

    public static String getString(@StringRes int res) {
        return UIUtils.getContext().getString(res);
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


    public static void showToast(String content) {
        Toasts.with().showToast(content);
    }

    public static void showToast(int resContent) {
        Toasts.with().showToast(resContent);
    }

    public static void showCallPhoneDialog(Context context, String phoneNumber) {
        if (!DataUtils.isEmpty(phoneNumber) && (DataUtils.isPhoneNumber(phoneNumber) || DataUtils.isTellPhone(phoneNumber))) {
            String phoneContent = UIUtils.getString(R.string.you_sure_call_this_phone, phoneNumber);
            SpannableString spannableString = null;
            if (phoneContent.length() >= 11) {
                spannableString = SpanUtils.getTextColor(R.color.color_2, phoneContent.length() - phoneNumber.length(), phoneContent.length(), phoneContent);
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

    public static void setBaseCustomTabLayout(TabLayout tabLayout, String content, boolean isSelector, int tabHeight, int textSize) {
        View inflateView = LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.item_base_tab_view, tabLayout, false);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TextView tvTitle = inflateView.findViewById(R.id.tv_title);
        tvTitle.setText(content);
        if (isSelector) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize - 1);
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

    public static void setText(@NonNull TextView textView, CharSequence content) {
        textView.setText(DataUtils.isEmpty(content) ? "" : content);
    }

    public static void setText(@NonNull TextView textView, String content) {
        textView.setText(DataUtils.isEmpty(content) ? "" : content);
    }

    public static void setTextColor(@NonNull TextView textView, @ColorRes int colorRes) {
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), colorRes));
    }

    public static void setText(@NonNull TextView textView, @StringRes int resContent) {
        textView.setText(resContent);
    }

    public static void setText(@NonNull TextView textView, String content, @NonNull String defaultContent) {
        textView.setText(DataUtils.isEmpty(content) ? defaultContent : content);
    }

    public static void setText(@NonNull TextView textView, String content, @StringRes int resContent) {
        textView.setText(DataUtils.isEmpty(content) ? UIUtils.getString(resContent) : content);
    }

    public static void setTextShirtColor(@NonNull TextView textView, String content, @StringRes int resContent) {
        textView.setText(DataUtils.isEmpty(content) ? UIUtils.getString(resContent) : content.contains("色") ? content : content + "色");
    }

    public static void setOrderDetailsItemSpan(@NonNull TextView textView, String host, String body) {
        if (DataUtils.isEmpty(host)) {
            return;
        }
        String content = UIUtils.getString(R.string.s_s, DataUtils.getNotNullData(host), DataUtils.getNotNullData(body));
        if (DataUtils.isEmpty(content)) {
            return;
        }
        textView.setText(SpanUtils.getTextColor(R.color.colorFFA6A6A6, 0, host.length(), content));
    }

    public static void setOrderDetailsItemSpan(@NonNull TextView textView, String host, String body, @ColorRes int colorRes) {
        if (DataUtils.isEmpty(host)) {
            return;
        }
        String content = UIUtils.getString(R.string.s_s, DataUtils.getNotNullData(host), DataUtils.getNotNullData(body));
        if (DataUtils.isEmpty(content)) {
            return;
        }
        textView.setText(SpanUtils.getTextColor(colorRes, 0, host.length(), content));
    }

    public static View getInputEvaluationView(@NonNull ViewGroup viewGroup, @NonNull String imagePaths) {
        viewGroup = DataUtils.checkData(viewGroup);
        Context context = viewGroup.getContext();
        imagePaths = DataUtils.checkData(imagePaths);
        String[] imagePathArray = imagePaths.split(",");
        View inflateView = null;
        switch (imagePathArray.length) {
            case 1:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_1, viewGroup, false);
                break;
            case 2:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_2, viewGroup, false);
                break;
            case 3:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_3, viewGroup, false);
                break;
            case 4:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_4, viewGroup, false);
                break;
            case 5:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_5, viewGroup, false);
                break;
            case 6:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_6, viewGroup, false);
                break;
            case 7:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_7, viewGroup, false);
                break;
            case 8:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_8, viewGroup, false);
                break;
            case 9:
                inflateView = LayoutInflater.from(context).inflate(R.layout.item_evaluate_image_9, viewGroup, false);
                break;
            default:
                break;

        }
        return inflateView;
    }

    public static void setCommunityCount(TextView textView, int count) {
        UIUtils.setText(textView, count > CommunityDataAdapter.MAX_COMMUNITY_COUNT ? CommunityDataAdapter.MAX_COMMUNITY_CONTENT :
                count > 0 ? String.valueOf(count) : "");
    }

    public static void editTextFouse(@NonNull EditText editText) {
        EditText et = DataUtils.checkData(editText);
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        et.requestLayout();
    }

    public static void addEmojiText(@NonNull TextView text, @DrawableRes int drawableRes, @NonNull String key) {
        if (DataUtils.isEmpty(key)) {
            key = "  ";
        }
        // UIUtils.setText(text, SpanUtils.getTextDrawable(drawableRes, DataUtils.getTextLength(text), content.length() + 1, content));
        TextPaint textPaint = text.getPaint();
        Rect rect = new Rect();
        textPaint.getTextBounds(key, 0, key.length(), rect);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int textHeight = Math.abs(fontMetricsInt.leading) + Math.abs(fontMetricsInt.ascent) + Math.abs(fontMetricsInt.descent);
        //  int textHeight = rect.height();
        text.append(SpanUtils.getTextDrawable(drawableRes, 0, key.length(), key, textHeight, textHeight));
    }

    public static int getTextWidth(@NonNull TextView textView) {
        TextPaint textPaint = textView.getPaint();
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        return Math.abs(fontMetricsInt.leading) + Math.abs(fontMetricsInt.ascent) + Math.abs(fontMetricsInt.descent);
    }

    /**
     * 表情图文显示
     *
     * @param textView
     * @param content
     */
    public static void setEmojiText(@NonNull TextView textView, String content) {
        List<String> data = EmojiManger.get().getEmojiKeyData();
        SpannableString spannableString = new SpannableString(content);
        for (int i = 0; i < data.size(); i++) {
            String dataContent = data.get(i);
            List<Integer> emojiIndexData = DataUtils.getContainsIndexs(content, dataContent);
            for (int j = 0; j < emojiIndexData.size(); j++) {
                EmojiBean bean = EmojiManger.get().getEmoji(dataContent);
                spannableString = SpanUtils.getTextDrawable(bean.drawableResId, emojiIndexData.get(j), emojiIndexData.get(j) + dataContent.length(), spannableString, getTextWidth(textView), getTextWidth(textView));
            }
        }
        UIUtils.setText(textView, spannableString);
    }

    public static SpannableString getEmojiSpannable(@NonNull TextView textView, String content) {
        List<String> data = EmojiManger.get().getEmojiKeyData();
        SpannableString spannableString = new SpannableString(content);
        for (int i = 0; i < data.size(); i++) {
            String dataContent = data.get(i);
            List<Integer> emojiIndexData = DataUtils.getContainsIndexs(content, dataContent);
            for (int j = 0; j < emojiIndexData.size(); j++) {
                EmojiBean bean = EmojiManger.get().getEmoji(dataContent);
                spannableString = SpanUtils.getTextDrawable(bean.drawableResId, emojiIndexData.get(j), emojiIndexData.get(j) + dataContent.length(), spannableString, getTextWidth(textView), getTextWidth(textView));
            }
        }
        return spannableString;
    }

    /**
     * 删除字符串（包含表情）
     *
     * @param editText
     */
    public static void deleteEdit(@NonNull EditText editText) {
        if (editText == null) {
            return;
        }
        String editContent = DataUtils.getEditDetails(editText);
        if (DataUtils.isEmpty(editContent)) {
            return;
        }
        Editable editable = editText.getText();
        List<String> data = EmojiManger.get().getEmojiKeyData();
        for (String dataContent : data) {
            if (editContent.contains(dataContent) && editContent.endsWith(dataContent)) {
                editable.delete(editable.length() - dataContent.length(), editable.length());
                return;
            }
        }
        editable.delete(editable.length() - 1, editable.length());
    }


}
