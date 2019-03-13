package com.work.guaishouxingqiu.aboutball.home.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.home.contract.RecommendedContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.RecommendedPresenter;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestRegisterBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:37
 * 更新时间: 2019/3/12 17:37
 * 描述: 推荐Fragment
 */
public class RecommendedFragment extends BaseFragment<RecommendedPresenter> implements RecommendedContract.View {
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    @BindView(R.id.srl_recommend)
    SmartRefreshLayout mSrlRecommend;

    public static RecommendedFragment newInstance() {
        return new RecommendedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommended;
    }

    @Override
    protected void initView() {
        mSrlRecommend.autoRefresh();
    }

    @Override
    protected void initData() {
        initLocation();
    }
    private void loadRefreshData(){
        mPresenter.loadData(new RequestRecommendDataBean());
    }

    @Override
    protected void initEvent() {
        mSrlRecommend.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadRefreshData();
            }
        });
    }

    private void initLocation() {
        PhoneUtils.checkoutGPS(this);
        LocationManager locationManager = (LocationManager) DataUtils.checkData(getContext()).getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        List<String> providers = locationManager.getProviders(true);
        String provider = null;
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }
        if (provider == null) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 3000, 2, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LogUtils.w("requestLocation", "onLocationChanged--" + location.getLongitude() + "--" +
                        location.getLatitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LogUtils.w("requestLocation", "onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String provider) {
                LogUtils.w("requestLocation", "onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                LogUtils.w("requestLocation", "onProviderDisabled");
            }
        });


    }


    @Override
    protected RecommendedPresenter createPresenter() {
        return new RecommendedPresenter(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contast.REQUEST_CODE && resultCode == Contast.REQUEST_CODE) {
            PhoneUtils.checkoutGPS(this);
        }
    }

}
