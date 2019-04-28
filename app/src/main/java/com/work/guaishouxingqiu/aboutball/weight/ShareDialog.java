package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/28 11:43
 * 更新时间: 2019/4/28 11:43
 * 描述:分享dialog
 */
public class ShareDialog extends BaseDialog {

    private TextView mTvWeichat;
    private TextView mTvWeichatFriend;
    private TextView mTvQQ;
    private TextView mTvSinWeibo;
    private LinearLayout mLlRoot;

    public ShareDialog(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_share_view, null);
        setContentView(contentView);
        mTvWeichat = contentView.findViewById(R.id.tv_weichat);
        mTvWeichatFriend = contentView.findViewById(R.id.tv_weichat_friend);
        mTvQQ = contentView.findViewById(R.id.tv_qq);
        mTvSinWeibo = contentView.findViewById(R.id.tv_sin);
        mLlRoot = contentView.findViewById(R.id.ll_root);
        //FrameLayout.LayoutParams linearParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) mLlRoot.getLayoutParams();
        linearParams.setMargins(ScreenUtils.dp2px(getContext(), 10), ScreenUtils.dp2px(getContext(), 10),
                ScreenUtils.dp2px(getContext(), 10), ScreenUtils.dp2px(getContext(), 10));
        mLlRoot.setLayoutParams(linearParams);
    }

    public void setWeichatClicklistener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            mTvWeichat.setOnClickListener(onClickListener);
        }
    }

    public void setWeichatFriendClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            mTvWeichatFriend.setOnClickListener(onClickListener);
        }
    }

    public void setQQClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            mTvQQ.setOnClickListener(onClickListener);
        }
    }

    public void setSinWeiboClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            mTvSinWeibo.setOnClickListener(onClickListener);
        }
    }
}
