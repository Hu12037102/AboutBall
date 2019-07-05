package com.work.guaishouxingqiu.aboutball.base;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.media.adapter.PreviewAdapter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.List;

import butterknife.BindView;
import utils.bean.ImageConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/17 16:11
 * 更新时间: 2019/6/17 16:11
 * 描述:图片预览页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_IMAGE_PREVIEW)
public class PreviewImageActivity extends BaseActivity {
    @BindView(R.id.bvp_image)
    BaseViewPager mBvpImage;
    @BindView(R.id.rg_count)
    RadioGroup mRgCount;
    @BindView(R.id.iv_download)
    ImageView mIvDownload;
    private int mPosition;
    private List<String> mPathData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_image;
    }

    @Override
    public void initPermission() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.there_are_no_previews);
            finish();
            return;
        }
        mPosition = bundle.getInt(ARouterConfig.Key.POSITION);
        mPathData = bundle.getStringArrayList(ARouterConfig.Key.ARRAY_LIST_STRING);
        if (mPosition == -1 || mPathData == null || mPathData.size() == 0) {
            UIUtils.showToast(R.string.there_are_no_previews);
            finish();
            return;
        }
        if (mPosition >= mPathData.size()) {
            mPosition = 1;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        initRadioButton();
        PreviewPagerAdapter pagerAdapter = new PreviewPagerAdapter(this, mPathData);
        mBvpImage.setAdapter(pagerAdapter);
        mBvpImage.setPageTransformer(true, new PreviewAdapter.PreviewPageTransformer());
        pagerAdapter.setOnPreviewClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                finish();
            }
        });
        mBvpImage.setCurrentItem(mPosition, true);
    }

    private void initRadioButton() {
        if (mPathData.size() <= 1) {
            mRgCount.setVisibility(View.GONE);
        } else {
            mRgCount.setVisibility(View.VISIBLE);
            for (int i = 0; i < mPathData.size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                mRgCount.addView(radioButton);
                radioButton.setButtonDrawable(null);
                radioButton.setBackgroundResource(R.drawable.selector_image_radio_button);
                RadioGroup.LayoutParams layoutParams = (RadioGroup.LayoutParams) radioButton.getLayoutParams();
                layoutParams.width = ScreenUtils.dp2px(this, 8);
                layoutParams.height = ScreenUtils.dp2px(this, 8);
                layoutParams.rightMargin = ScreenUtils.dp2px(this, 20);
                radioButton.setChecked(i == mPosition);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mBvpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                RadioButton radioButton = (RadioButton) mRgCount.getChildAt(i);
                radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mIvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideManger.get().downloadImage(mPathData.get(mBvpImage.getCurrentItem()));
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {

        return null;
    }

    static class PreviewPagerAdapter extends PagerAdapter {
        private List<String> mData;
        private Context mContext;

        public void setOnPreviewClickListener(OnItemClickListener onPreviewClickListener) {
            this.onPreviewClickListener = onPreviewClickListener;
        }

        private OnItemClickListener onPreviewClickListener;

        public PreviewPagerAdapter(Context context, List<String> data) {
            this.mContext = context;
            this.mData = data;
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(mContext);
            container.addView(photoView);
            ViewGroup.LayoutParams layoutParams = photoView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            photoView.setLayoutParams(layoutParams);
            GlideManger.get().loadMediaImage(container.getContext(), mData.get(position), photoView, false);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPreviewClickListener != null) {
                        onPreviewClickListener.onClickItem(photoView, position);
                    }
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }


}
