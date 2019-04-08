package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.item.weight.TitleView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.AddressBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Address;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 15:46
 * 更新时间: 2019/4/8 15:46
 * 描述:收获地址dialog
 */
public class AddressDialog extends BaseDialog {
    private TitleView mTitleView;
    private WheelView mLeftWheelView;
    private WheelView mRightWheelView;
    private WheelView mCenterWheelView;
    private ArrayWheelAdapter<String> mCenterAdapter;
    private List<String> mCityData;
    private List<String> mAreaData;
    private ArrayWheelAdapter<String> mRightAdapter;
    private int mLeftIndex;
    private List<String> mAddressData;

    public void setOnResultAddressListener(OnResultAddressListener onResultAddressListener) {
        this.onResultAddressListener = onResultAddressListener;
    }

    private OnResultAddressListener onResultAddressListener;

    public AddressDialog(Context context) {

        super(context);

    }

    @Override
    protected void initEvent() {
        mTitleView.setOnTitleViewClickListener(new TitleView.OnTitleViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                dismiss();
            }

            @Override
            public void onSureClick(@NonNull View view) {
                dismiss();
                if (onResultAddressListener != null) {
                    onResultAddressListener.onResultAddress(mAddressData.get(mLeftWheelView.getCurrentItem())
                            .equals(mCityData.get(mCenterWheelView.getCurrentItem())) ? "" : mAddressData.get(mLeftWheelView.getCurrentItem())
                            .concat(mCityData.get(mCenterWheelView.getCurrentItem()))
                            .equals(mAreaData.get(mRightWheelView.getCurrentItem())) ? "" : mCityData.get(mCenterWheelView.getCurrentItem())
                            .concat(mAreaData.get(mRightWheelView.getCurrentItem())));
                }
            }
        });
    }

    @Override
    protected void initData() {
        String json = DataUtils.getAssetsData(getContext(), "address.txt");
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();
        Gson gson = new Gson();
        List<AddressBean> data = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            data.add(gson.fromJson(element, AddressBean.class));
        }
        if (data.size() > 0) {
            initDataView(data);
        }


    }


    private void initDataView(List<AddressBean> data) {
        initLeftViewData(data);
        List<AddressBean.City> cityData = data.get(0).cityList;
        if (cityData != null && cityData.size() > 0) {
            initCenterViewData(data, cityData);
            List<AddressBean.Area> areaData = cityData.get(0).areaList;
            if (areaData != null && areaData.size() > 0) {
                initRightViewData(areaData);
            }
        }


    }


    private void initLeftViewData(List<AddressBean> data) {
        LogUtils.w("initLeftViewData--", Thread.currentThread().getName() + "");
        mAddressData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            mAddressData.add(data.get(i).name);
        }
        ArrayWheelAdapter<String> leftAdapter = new ArrayWheelAdapter<>(mAddressData);
        mLeftWheelView.setAdapter(leftAdapter);
        mLeftWheelView.setOnItemSelectedListener(index -> {
            mLeftIndex = index;
            notifyCenterViewData(data, index);
            notifyRightViewData(data.get(index).cityList, 0);
        });
    }

    private void notifyCenterViewData(List<AddressBean> data, int index) {
        if (data != null && data.size() > index && mCityData != null && mCenterAdapter != null) {
            AddressBean bean = data.get(index);
            mCityData.clear();
            for (int i = 0; i < bean.cityList.size(); i++) {
                mCityData.add(bean.cityList.get(i).name);
            }
            mCenterWheelView.setCurrentItem(0);
            mCenterWheelView.setAdapter(mCenterAdapter);
        }
    }

    private void notifyRightViewData(List<AddressBean.City> data, int index) {
        if (data != null && data.size() > index && mAreaData != null && mRightAdapter != null) {
            AddressBean.City city = data.get(index);
            mAreaData.clear();
            for (int i = 0; i < city.areaList.size(); i++) {
                mAreaData.add(city.areaList.get(i).name);
            }
            mRightWheelView.setCurrentItem(0);
            mRightWheelView.setAdapter(mRightAdapter);
        }
    }

    private void initCenterViewData(List<AddressBean> dataAddress, List<AddressBean.City> data) {
        mCityData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            mCityData.add(data.get(i).name);
        }
        mCenterAdapter = new ArrayWheelAdapter<>(mCityData);
        mCenterWheelView.setAdapter(mCenterAdapter);
        mCenterWheelView.setOnItemSelectedListener(index -> {
            notifyRightViewData(dataAddress.get(mLeftIndex).cityList, index);

        });
    }

    private void initRightViewData(List<AddressBean.Area> data) {
        mAreaData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            mAreaData.add(data.get(i).name);
        }
        mRightAdapter = new ArrayWheelAdapter<>(mAreaData);
        mRightWheelView.setAdapter(mRightAdapter);
    }

    @Override
    protected void initView(Context context) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.dialog_address_view, null);
        setContentView(inflateView);
        mTitleView = inflateView.findViewById(R.id.title_view);
        mLeftWheelView = inflateView.findViewById(R.id.wv_left);
        mRightWheelView = inflateView.findViewById(R.id.wv_right);
        mCenterWheelView = inflateView.findViewById(R.id.wv_center);
        initWheelView(mLeftWheelView);
        initWheelView(mRightWheelView);
        initWheelView(mCenterWheelView);

        this.setCanceledOnTouchOutside(true);
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        //layoutParams.height = ScreenUtils.dp2px(getContext(), 270);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);
    }

    private void initWheelView(WheelView wheelView) {
        wheelView.setCyclic(false);
        wheelView.setLineSpacingMultiplier(2.0f);
        wheelView.setTextColorCenter(ContextCompat.getColor(getContext(), R.color.colorFF333333));
        wheelView.setGravity(Gravity.CENTER);
    }

    public interface OnResultAddressListener {
        void onResultAddress(String address);
    }
}
