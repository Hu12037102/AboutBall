package com.work.guaishouxingqiu.aboutball.map.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.map.activity
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/6/5
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MAP)
public class MapActivity extends PermissionActivity {

    @BindView(R.id.map_view)
    MapView mMapView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView() {

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

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mMapView.onStop();
        super.onStop();
    }
}
