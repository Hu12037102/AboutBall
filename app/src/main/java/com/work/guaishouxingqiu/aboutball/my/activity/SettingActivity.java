package com.work.guaishouxingqiu.aboutball.my.activity;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.contract.SettingContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.SettingPresenter;
import com.work.guaishouxingqiu.aboutball.other.DownloadApkHelp;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/1 15:05
 * 更新时间: 2019/4/1 15:05
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SETTING)
public class SettingActivity extends BaseActivity<SettingPresenter>
        implements SettingContract.View {
    @BindView(R.id.item_feedback)
    ItemView mItemFeedback;
    @BindView(R.id.item_cache)
    ItemView mItemCache;
    @BindView(R.id.item_agreement)
    ItemView mItemAgreement;
    @BindView(R.id.item_login_out)
    TextView mItemLoginOut;
    @BindView(R.id.rl_about_we)
    RelativeLayout mRlAboutWe;
    @BindView(R.id.tv_versions)
    TextView mTvVersions;
    private ResultUpdateApkBean mUpdateBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mItemLoginOut.setVisibility(UserManger.get().isLogin() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initData() {
        mPresenter.getFileSize();
        mPresenter.updateApkInfo(DownloadApkHelp.getVersionName(this));
    }

    @Override
    protected void initEvent() {
        mItemFeedback.setOnItemClickListener(view -> {
            clickFeedback();
        });
        mItemLoginOut.setOnClickListener(v -> {
            clickLoginOut();
        });
        mItemCache.setOnItemClickListener(view -> {
            clickClearCache();
        });

        mRlAboutWe.setOnClickListener(view -> {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ABOUT_WE);
        });
        mItemAgreement.setOnItemClickListener(view -> clickAgreement());
    }

    private void clickFeedback() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_FEEDBACK);
    }

    private void clickAgreement() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_USER_AGREEMENT, ARouterConfig.Key.PARCELABLE, mUpdateBean);
    }


    private void clickClearCache() {
        FileUtils.removeFileCache();
        mPresenter.getFileSize();
        Toasts.with().showToast(R.string.clear_cache_complete);
    }

    private void clickLoginOut() {
        HintDialog hintDialog = new HintDialog.Builder(this)
                .setTitle(R.string.hint)
                .setBody(R.string.your_sure_login_out)
                .setSure(R.string.sure).builder();
        hintDialog.show();
        hintDialog.setOnItemClickListener(view -> {
            hintDialog.dismiss();
            if (UserManger.get().isWeiChatId()) {
                getBaseApplication().closeWeiChat();
            }
            UserManger.get().loginOut();

            finish();
        });

    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }


    @Override
    public void resultFileSize(String fileSize) {
        mItemCache.mTvRight.setText(fileSize);
    }

    @Override
    public void resultApkInfo(ResultUpdateApkBean bean) {
        mUpdateBean = bean;
        //版本信息回调
        if (!bean.version.equals(DownloadApkHelp.getVersionName(this))) {
            mTvVersions.setVisibility(View.VISIBLE);
        }
    }
}
