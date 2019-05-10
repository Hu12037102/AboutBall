package com.work.guaishouxingqiu.aboutball.my.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.contract.AboutWeContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.AboutWePresenter;
import com.work.guaishouxingqiu.aboutball.other.DownloadApkHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.my.activity
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/4
 * 描  述 :  ${TODO}关于我们
 *
 * @author ：
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ABOUT_WE)
public class AboutWeActivity extends BaseActivity<AboutWePresenter> implements AboutWeContract.View {
    @BindView(R.id.item_versions)
    ItemView mItemVersions;
    @BindView(R.id.item_grade)
    ItemView mItemGrade;
    @BindView(R.id.item_check_updates)
    ItemView mItemCheckUpdates;
    private ResultUpdateApkBean mUpdateBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_we;
    }

    @Override
    protected void initView() {
        mItemVersions.setContentText(DownloadApkHelp.getVersionName(this));

    }

    @Override
    protected void initData() {
        mUpdateBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
    }

    @Override
    protected void initEvent() {
        mItemGrade.setOnItemClickListener(view -> {
            try {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(new Intent(Intent.ACTION_VIEW, uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception ex) {
                UIUtils.showToast("您的手机还没有安装任何安装安装应用市场");
            }
        });
        mItemCheckUpdates.setOnItemClickListener(view -> {
            if (mUpdateBean == null) {
                mPresenter.updateApkInfo(DownloadApkHelp.getVersionName(this));
            } else {
              //  showUpdateDialog(mUpdateBean);
                UIUtils.showUpdateDialog(this,mUpdateBean);
            }
        });
    }

   /* private void showUpdateDialog(ResultUpdateApkBean updateBean) {
        HintDialog hintDialog = new HintDialog.Builder(this)
                .setTitle(R.string.update_apk)
                .setBody(updateBean.content)
                .setSure(R.string.sure).builder();
        hintDialog.show();
        hintDialog.setOnItemClickListener(view -> {
            hintDialog.dismiss();
            DownloadApkHelp.loadApk(AboutWeActivity.this, updateBean.updateUrl);


        });
    }*/

    @Override
    protected AboutWePresenter createPresenter() {
        return new AboutWePresenter(this);
    }


    @Override
    public void resultApkInfo(ResultUpdateApkBean bean) {
       // showUpdateDialog(bean);
        UIUtils.showUpdateDialog(this,bean);
    }
}
