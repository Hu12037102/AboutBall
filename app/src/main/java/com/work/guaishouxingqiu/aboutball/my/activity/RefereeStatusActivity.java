package com.work.guaishouxingqiu.aboutball.my.activity;

import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.RefereeStatusContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.RefereeStatusPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 13:42
 * 更新时间: 2019/5/7 13:42
 * 描述:裁判状态Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_REFEREE_STATUS)
public class RefereeStatusActivity extends BaseActivity<RefereeStatusPresenter> implements RefereeStatusContract.View {
    @BindView(R.id.iv_content)
    ImageView mIvContent;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_hint)
    TextView mTvHint;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private int mRefereeStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_referee_status;
    }

    /**
     * 审核裁判状态
     * 0：审核中;1：已通过（用户确认);2：未通过;3：已通过（后台审核）
     */
    @Override
    protected void initView() {
        mRefereeStatus = mIntent.getIntExtra(ARouterConfig.Key.REFEREE_STATUS, -1);
        if (mRefereeStatus == -1) {
            UIUtils.showToast(R.string.audit_result_error);
            finish();
            return;
        }
        switch (mRefereeStatus) {
            case 0:
                mIvContent.setImageResource(R.mipmap.icon_sleep_audit_result);
                mTvStatus.setText(R.string.commit_succeed_please_sleep);
                mTvHint.setVisibility(View.GONE);
                mTvContent.setVisibility(View.GONE);
                break;
            case 2:
                mIvContent.setImageResource(R.mipmap.icon_audit_failed);
                mTvStatus.setVisibility(View.VISIBLE);
                mTvStatus.setText(R.string.audit_failure);
                mTvHint.setVisibility(View.VISIBLE);
                mTvHint.setText(R.string.audit_failure_hint);
                mTvContent.setVisibility(View.VISIBLE);
                mTvContent.setText(R.string.register_update);
                break;
            case 3:
                mIvContent.setImageResource(R.mipmap.icon_audit_succeed);
                mTvStatus.setVisibility(View.VISIBLE);
                mTvStatus.setText(R.string.get_approved);
                mTvHint.setVisibility(View.VISIBLE);
                mTvHint.setText(R.string.get_approved_hint);
                mTvContent.setVisibility(View.VISIBLE);
                mTvContent.setText(R.string.into_my_record_center);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected RefereeStatusPresenter createPresenter() {
        return new RefereeStatusPresenter(this);
    }


    @OnClick(R.id.tv_content)
    public void onViewClicked() {
        if (mRefereeStatus == 2) {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_APPLY_BECOME_REFEREE);
            finish();
        } else if (mRefereeStatus == 3) {
            mPresenter.start();
        }
    }

    @Override
    public void resultSureRefereeStatus() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_REFEREE_RECORD);
        finish();
    }
}
