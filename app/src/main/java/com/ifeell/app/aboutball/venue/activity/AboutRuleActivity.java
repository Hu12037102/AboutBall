package com.ifeell.app.aboutball.venue.activity;

import androidx.core.text.HtmlCompat;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.UIUtils;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 9:22
 * 更新时间: 2019/5/27 9:22
 * 描述:约球规则页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ABOUT_RULE)
public class AboutRuleActivity extends BaseActivity {
    @BindView(R.id.tv_title_1)
    TextView mTvTitle1;
    @BindView(R.id.tv_title_2)
    TextView mTvTitle2;
    @BindView(R.id.tv_title_3)
    TextView mTvTitle3;
    @BindView(R.id.tv_content_1)
    TextView mTvContent1;
    @BindView(R.id.tv_content_2)
    TextView mTvContent2;
    @BindView(R.id.tv_content_3)
    TextView mTvContent3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_rule;
    }

    @Override
    protected void initView() {
        mTvTitle1.setText(HtmlCompat.fromHtml(UIUtils.getString(R.string.about_ball_rule_1), HtmlCompat.FROM_HTML_MODE_LEGACY));
        mTvTitle2.setText(HtmlCompat.fromHtml(UIUtils.getString(R.string.about_ball_rule_2), HtmlCompat.FROM_HTML_MODE_LEGACY));
        mTvTitle3.setText(HtmlCompat.fromHtml(UIUtils.getString(R.string.about_ball_rule_3), HtmlCompat.FROM_HTML_MODE_LEGACY));
        mTvContent1.setText(HtmlCompat.fromHtml(UIUtils.getString(R.string.about_ball_rule_4), HtmlCompat.FROM_HTML_MODE_LEGACY));
        mTvContent2.setText(HtmlCompat.fromHtml(UIUtils.getString(R.string.about_ball_rule_5), HtmlCompat.FROM_HTML_MODE_LEGACY));
        mTvContent3.setText(HtmlCompat.fromHtml(UIUtils.getString(R.string.about_ball_rule_6), HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


}
