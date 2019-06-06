package com.work.guaishouxingqiu.aboutball.map.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.MyLocationStyle;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

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
    private double mLongitude;
    private double mLatitude;
    private String mMapName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void initPermission() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.not_find_longitude_and_latitude);
            finish();
            return;
        }
        mLongitude = bundle.getDouble(ARouterConfig.Key.LONGITUDE, -1);
        mLatitude = bundle.getDouble(ARouterConfig.Key.LATITUDE, -1);
        mMapName = bundle.getString(ARouterConfig.Key.POSITION_NAME, "");
        if (mLongitude == -1 || mLatitude == -1) {
            UIUtils.showToast(R.string.not_find_longitude_and_latitude);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {

//获取TencentMap实例
        TencentMap tencentMap = mMapView.getMap();
        //获取UiSettings实例
        UiSettings uiSettings = tencentMap.getUiSettings();
//启用缩放手势(更多的手势控制请参考开发手册)
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

//设置卫星底图
        tencentMap.setSatelliteEnabled(false);
//设置实时路况开启
        tencentMap.setTrafficEnabled(false);


        LatLng latLng = new LatLng(mLatitude, mLongitude);
        tencentMap.setMyLocationEnabled(true);
        MyLocationStyle locationStyle = new MyLocationStyle()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_rule_6))
                .myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
                .anchor(0.5f, 0.5f);
        locationStyle.strokeColor(R.color.color_2);
        tencentMap.setMyLocationStyle(locationStyle);

//设置缩放级别
        // tencentMap.setMaxZoomLevel(18);
        MarkerOptions markerOptions = new MarkerOptions(latLng);
        markerOptions = markerOptions.position(latLng).title(mMapName).anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker())
                .draggable(true);
        markerOptions.fastLoad(true);
        Marker marker = tencentMap.addMarker(markerOptions);
        marker.showInfoWindow();


        CameraUpdate cameraSigma =
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(mLatitude, mLongitude), //新的中心点坐标
                        8,  //新的缩放级别
                        0, //俯仰角 0~45° (垂直地图时为0)
                        0)); //偏航角 0~360° (正北方为0)
//移动地图
        tencentMap.moveCamera(cameraSigma);
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
