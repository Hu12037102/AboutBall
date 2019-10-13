package com.ifeell.app.aboutball.base;

import android.content.Intent;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.View;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.other.SharedPreferencesHelp;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.util.PhoneUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseDialog;
import com.ifeell.app.aboutball.weight.HintDialog;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/5 14:55
 * 更新时间: 2019/6/5 14:55
 * 描述:定位fragment
 */
public class LocationFragment extends Fragment implements TencentLocationListener {
    private TencentLocationRequest mRequestLocation;
    private TencentLocationManager mManager;
    private HintDialog mOpenGPSDialog;

    /**
     * 获取经度
     *
     * @return
     */
    public double getSPLongitude() {
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        if (sp.isContainsKey(SharedPreferencesHelp.KEY.LOCATION_LONGITUDE)) {
            return Double.valueOf(sp.getString(SharedPreferencesHelp.KEY.LOCATION_LONGITUDE, String.valueOf(Contast.DEFAULT_LONGITUDE)));
        }
        return Contast.DEFAULT_LONGITUDE;
    }

    /**
     * 获取纬度
     *
     * @return
     */
    public double getSPLatitude() {
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        if (sp.isContainsKey(SharedPreferencesHelp.KEY.LOCATION_LATITUDE)) {
            return Double.valueOf(sp.getString(SharedPreferencesHelp.KEY.LOCATION_LATITUDE, String.valueOf(Contast.DEFAULT_LATITUDE)));
        }
        return Contast.DEFAULT_LATITUDE;
    }

    /**
     * 获取定位城市
     *
     * @return
     */
    public String getSPCityName() {
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        if (sp.isContainsKey(SharedPreferencesHelp.KEY.LOCATION_CITY)) {
            return sp.getString(SharedPreferencesHelp.KEY.LOCATION_CITY, UIUtils.getString(R.string.unknown));
        }
        return UIUtils.getString(R.string.unknown);
    }

    /**
     * 位置改变
     *
     * @param tencentLocation 新的位置
     * @param i               错误码
     * @param s               错误描述
     */
    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if (i == 0) {
            if (mManager != null) {
                mManager.removeUpdates(LocationFragment.this);
            }
            SharedPreferencesHelp sp = new SharedPreferencesHelp();
            sp.putObject(SharedPreferencesHelp.KEY.LOCATION_LONGITUDE, String.valueOf(tencentLocation.getLongitude()));
            sp.putObject(SharedPreferencesHelp.KEY.LOCATION_LATITUDE, String.valueOf(tencentLocation.getLatitude()));
            sp.putObject(SharedPreferencesHelp.KEY.LOCATION_CITY, tencentLocation.getCity());
        }
        locationResult(getSPLongitude(), getSPLatitude(), getSPCityName());

        LogUtils.w("onLocationChanged--", tencentLocation.getCity() + "--" + i + "--"
                + tencentLocation.getLatitude() + "--" + tencentLocation.getLongitude());
    }


    /**
     * 手机状态改变
     *
     * @param s  GPS，Wi-Fi等
     * @param i  新的状态, 启用或禁用
     * @param s1 状态描述
     */
    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        LogUtils.w("onStatusUpdate--", s + "--" + i + "--" + s1);
    }

    public void requestLocation() {
        if (PhoneUtils.isOpenGPS(DataUtils.checkData(getContext()))) {
            if (mRequestLocation == null) {
                mRequestLocation = TencentLocationRequest.create();
                mRequestLocation.setAllowCache(true);
                mRequestLocation.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
                mRequestLocation.setQQ("1203747102");
            }
            if (mManager == null) {
                mManager = TencentLocationManager.getInstance(UIUtils.getContext());
            }
            int code = mManager.requestLocationUpdates(mRequestLocation, this);
            LogUtils.w("requestLocation--", code + "--");
        } else {
            showOpenGPSDialog();
        }
    }

    public void locationResult(double longitude, double latitude, String city) {

    }


    private void showOpenGPSDialog() {
        if (mOpenGPSDialog == null) {
            mOpenGPSDialog = new HintDialog.Builder(DataUtils.checkData(getContext()))
                    .setTitle(R.string.hint)
                    .setBody(R.string.gprs_not_open)
                    .setShowSingButton(false)
                    .setSures(R.string.go_open)
                    .builder();
        }
        mOpenGPSDialog.setCanceledOnTouchOutside(false);
        mOpenGPSDialog.setCancelable(false);
        if (!mOpenGPSDialog.isShowing()) {
            mOpenGPSDialog.show();
        }
        mOpenGPSDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, Contast.REQUEST_CODE);
            }

            @Override
            public void onClickCancel(@NonNull View view) {
                mOpenGPSDialog.dismiss();
                locationResult(getSPLongitude(), getSPLatitude(), getSPCityName());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //返回定位
        if (requestCode == Contast.REQUEST_CODE && resultCode == Contast.REQUEST_CODE) {
            requestLocation();
        }
    }

}
