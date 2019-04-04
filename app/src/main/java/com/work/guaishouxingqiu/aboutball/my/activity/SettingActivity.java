package com.work.guaishouxingqiu.aboutball.my.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.contract.SettingContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.SettingPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.item_about)
    ItemView mItemAbout;
    @BindView(R.id.item_cache)
    ItemView mItemCache;
    @BindView(R.id.item_agreement)
    ItemView mItemAgreement;
    @BindView(R.id.item_login_out)
    TextView mItemLoginOut;
    private long mFileSize;

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
    }

    @Override
    protected void initEvent() {
        mItemFeedback.setOnItemClickListener(view -> {

        });
        mItemLoginOut.setOnClickListener(v -> {
            clickLoginOut();
        });
        mItemCache.setOnItemClickListener(view -> {
            clickClearCache();
        });
        mItemAbout.setOnItemClickListener(view -> {
            clickAboutWe();
        });
    }

    private void clickAboutWe() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            mPresenter.updateApkInfo( "v-1.0");
            LogUtils.w("clickAboutWe--",versionName+"--"+versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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
        //版本信息回调
    }
}
