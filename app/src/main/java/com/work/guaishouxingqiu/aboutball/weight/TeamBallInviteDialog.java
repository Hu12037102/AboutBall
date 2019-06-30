package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/30 14:17
 * 更新时间: 2019/5/30 14:17
 * 描述:球队邀请dialog
 */
public class TeamBallInviteDialog extends AppCompatDialog {
    private ResultBallDetailsBean mBean;
    private TextView mTvName;
    private TextView mTvTeamType, mTvBottomLeft, mTvBottomRight;
    private ItemView mItemCaptain;
    private ItemView mItemMember;
    private CircleImageView mCivHead;
    private TitleView mTitleView;

    public void setOnItemClickSureAndCancelListener(BaseDialog.OnItemClickSureAndCancelListener onItemClickSureAndCancelListener) {
        this.onItemClickSureAndCancelListener = onItemClickSureAndCancelListener;
    }

    private BaseDialog.OnItemClickSureAndCancelListener onItemClickSureAndCancelListener;

    public TeamBallInviteDialog(Context context, @NonNull ResultBallDetailsBean bean) {
        super(context,R.style.DefaultDialogStyle);
        this.mBean = bean;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView(getContext());
        initData();
        initEvent();
    }




    private void initView(Context context) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_team_ball_invite_view, null);
        setContentView(view);
        mTitleView = findViewById(R.id.title_view);
        mCivHead = findViewById(R.id.civ_head);
        mTvName = findViewById(R.id.tv_name);
        mTvBottomLeft = findViewById(R.id.tv_bottom_left);
        mTvBottomRight = findViewById(R.id.tv_bottom_right);
        mTvTeamType = findViewById(R.id.tv_team_type);
        mItemCaptain = findViewById(R.id.item_captain);
        mItemMember = findViewById(R.id.item_member);
    }

    private void initData() {
        UIUtils.setText(mTvName, mBean.teamName);
        GlideManger.get().loadLogoImage(getContext(), mBean.teamLogo, mCivHead);
        mTvTeamType.setText(mBean.teamType);
        UIUtils.setText(mItemCaptain.mTvRight,mBean.leaderName);
        UIUtils.setText(mItemMember.mTvRight, mBean.playerCount);
    }

    private void initEvent() {
        mTitleView.setOnBackViewClickListener(view -> dismiss());
        mTvBottomLeft.setOnClickListener(v -> {
            if (onItemClickSureAndCancelListener != null) {
                onItemClickSureAndCancelListener.onClickCancel(v);
            }
        });
        mTvBottomRight.setOnClickListener(v -> {
            if (onItemClickSureAndCancelListener != null) {
                onItemClickSureAndCancelListener.onClickSure(v);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
    }

    private void initWindows() {
        Window window = getWindow();
        if (window == null) {
            dismiss();
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        WindowManager windowManager = window.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);

        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);
    }
}
