package com.work.guaishouxingqiu.aboutball.my.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.CameraActivity;
import com.work.guaishouxingqiu.aboutball.media.MediaSelector;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;
import com.work.guaishouxingqiu.aboutball.my.adapter.AddImageAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.AddImageBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestOrderEvaluateBean;
import com.work.guaishouxingqiu.aboutball.my.contract.OrderEvaluateContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.OrderEvaluatePresenter;
import com.work.guaishouxingqiu.aboutball.other.OSSRequestHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 13:11
 * 更新时间: 2019/5/15 13:11
 * 描述:订单评价Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ORDER_EVALUATE)
public class OrderEvaluateActivity extends CameraActivity<OrderEvaluatePresenter> implements OrderEvaluateContract.View {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_reserve_content)
    TextView mTvReserveContent;
    @BindView(R.id.rb_grade)
    RatingBar mRbGrade;
    @BindView(R.id.acet_content)
    AppCompatEditText mAcetContent;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    private AddImageAdapter mImageAdapter;
    private List<AddImageBean> mImageData;
    private MediaSelector.MediaOptions mMediaOptions;
    private List<String> mSelectorImageData;
    private RequestOrderEvaluateBean mRequestBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_evaluate;
    }

    @Override
    protected void initView() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        String venueName = bundle.getString(ARouterConfig.Key.VENUE_NAME);
        UIUtils.setText(mTvName, venueName);
        String orderTime = bundle.getString(ARouterConfig.Key.TARGET_DATE);
        UIUtils.setOrderDetailsItemSpan(mTvTime, UIUtils.getString(R.string.order_item_time_host), orderTime);
        String siteContent = bundle.getString(ARouterConfig.Key.TARGET_SITE);
        UIUtils.setText(mTvReserveContent, siteContent);
        long orderId = bundle.getLong(ARouterConfig.Key.ORDER_ID, -1);
        if (orderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        mRequestBean = new RequestOrderEvaluateBean();
        mRequestBean.orderId = orderId;
    }

    @Override
    protected void initData() {
        mSelectorImageData = new ArrayList<>();
        mMediaOptions = new MediaSelector.MediaOptions();
        mMediaOptions.maxChooseMedia = AddImageAdapter.MAX_IMAGE_COUNT;
        mMediaOptions.isCrop = false;
        mMediaOptions.isCompress = true;
        mMediaOptions.isShowCamera = false;

        mRvData.setLayoutManager(new GridLayoutManager(this, 4));
        mImageData = new ArrayList<>();
        mImageData.add(new AddImageBean(true));
        mImageAdapter = new AddImageAdapter(this, mImageData);
        mRvData.setAdapter(mImageAdapter);
    }

    @Override
    protected void initEvent() {
        mImageAdapter.setOnItemClickListener((view, position) -> {
            if (mImageData.get(position).isAdd) {
                clickMediaSelector();
            }
        });
        mImageAdapter.setOnDeleteClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                mSelectorImageData.remove(position);
            }
        });
    }

    private void clickMediaSelector() {
        mMediaOptions.maxChooseMedia = AddImageAdapter.MAX_IMAGE_COUNT - (mImageData.size() - 1);
        openPhotoDialog(mMediaOptions);
    }

    @Override
    protected OrderEvaluatePresenter createPresenter() {
        return new OrderEvaluatePresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        clickCommit();
    }

    private void clickCommit() {
        if (  isGrade()&&isChooseContent()) {
            mRequestBean.grade = mRbGrade.getRating();
            mRequestBean.content = DataUtils.getEditDetails(mAcetContent);
            if (mSelectorImageData.size() > 0) {
                updateImage2OSS();
            } else {
                mPresenter.evaluateOrder(mRequestBean);
            }
        }
    }

    private void updateImage2OSS() {
        OSSRequestHelp.get().uploadingFiles(mSelectorImageData, new OSSRequestHelp.OnOSSDataResultListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSucceed(List<String> pathData) {
                mRequestBean.commentPic = DataUtils.getStringFormat(pathData);
                mPresenter.evaluateOrder(mRequestBean);
            }

            @Override
            public void onFailure(String errorCode) {

            }
        },this);
    }

    @Override
    protected void resultAlbumResult(List<MediaSelectorFile> data) {
        for (int i = 0; i < data.size(); i++) {
            MediaSelectorFile media = data.get(i);
            mSelectorImageData.add(media.filePath);
            AddImageBean addBean = new AddImageBean();
            addBean.path = media.filePath;
            mImageData.add(mImageData.size() - 1, addBean);
        }
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void resultCameraResult(File cameraFile) {
        String cameraPath = cameraFile.getAbsolutePath();
        mSelectorImageData.add(cameraPath);
        AddImageBean addBean = new AddImageBean();
        addBean.path = cameraPath;
        int position = mImageData.size() - 1;
        mImageData.add(position, addBean);
        mImageAdapter.notifyDataSetChanged();
    }

    public boolean isGrade() {
        if (mRbGrade.getRating() <= 0) {
            UIUtils.showToast(R.string.please_selector_order_grade_rating);
            return false;
        }
        return true;
    }

    public boolean isChooseContent() {
        if (mSelectorImageData.size() > 0 || !DataUtils.isEmpty(DataUtils.getEditDetails(mAcetContent))) {
            return true;
        }
        UIUtils.showToast(R.string.your_not_sed_what);
        return false;
    }

    @Override
    public void resultEvaluateOrder() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
