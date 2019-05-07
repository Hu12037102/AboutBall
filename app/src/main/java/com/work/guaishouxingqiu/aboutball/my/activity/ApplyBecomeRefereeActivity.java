package com.work.guaishouxingqiu.aboutball.my.activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.CameraActivity;
import com.work.guaishouxingqiu.aboutball.media.MediaSelector;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestApplyRefereeBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeLevelBean;
import com.work.guaishouxingqiu.aboutball.my.contract.ApplyBecomeRefereeContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.ApplyBecomeRefereePresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.other.OSSRequestHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.SingWheelDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/6 11:42
 * 更新时间: 2019/5/6 11:42
 * 描述:申请成为裁判Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_APPLY_BECOME_REFEREE)
public class ApplyBecomeRefereeActivity extends CameraActivity<ApplyBecomeRefereePresenter> implements
        ApplyBecomeRefereeContract.View {
    private static final int MAX_PAGER_COUNT = 3;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_schedule_1)
    TextView mTvSchedule1;
    @BindView(R.id.tv_schedule_2)
    TextView mTvSchedule2;
    @BindView(R.id.tv_schedule_3)
    TextView mTvSchedule3;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    @BindView(R.id.line_schedule_1)
    View mSchedule1;
    @BindView(R.id.line_schedule_2)
    View mSchedule2;
    private RefereeAdapter mRefereeAdapter;
    private RequestApplyRefereeBean mRequestBean;
    private SingWheelDialog mSingWheelDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_become_referee;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mRequestBean = new RequestApplyRefereeBean();
        mRefereeAdapter = new RefereeAdapter(this);
        mBvpContent.setOffscreenPageLimit(MAX_PAGER_COUNT);
        mBvpContent.setAdapter(mRefereeAdapter);

    }

    @Override
    protected void initEvent() {
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mSchedule1.setBackgroundColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.colorFFEFEFEF));
                        mSchedule2.setBackgroundColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.colorFFEFEFEF));
                        mTvSchedule1.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        mTvSchedule2.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.colorFFA6A6A6));
                        mTvSchedule3.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.colorFFA6A6A6));
                        mTvNext.setText(R.string.next);
                        break;
                    case 1:
                        mSchedule1.setBackgroundColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        mSchedule2.setBackgroundColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.colorFFEFEFEF));
                        mTvSchedule1.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        mTvSchedule2.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        mTvSchedule3.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.colorFFA6A6A6));
                        mTvNext.setText(R.string.commit);
                        break;
                    case 2:
                        mSchedule1.setBackgroundColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        mSchedule2.setBackgroundColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        mTvSchedule1.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        mTvSchedule2.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        mTvSchedule3.setTextColor(ContextCompat.getColor(ApplyBecomeRefereeActivity.this, R.color.color_4));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                clickBack();
            }
        });
    }

    private void clickOpenPhoto() {
        MediaSelector.MediaOptions mediaOptions = MediaSelector.getDefaultOptions();
        mediaOptions.isShowVideo = false;
        mediaOptions.maxChooseMedia = 1;
        if (mBvpContent.getCurrentItem() == 0) {
            mediaOptions.scaleX = 3;
            mediaOptions.scaleY = 4;
        } else {
            mediaOptions.scaleX = 4;
            mediaOptions.scaleY = 3;
        }
        mediaOptions.cropHeight = ScreenUtils.dp2px(this, 300);
        mediaOptions.cropWidth = ScreenUtils.dp2px(this, 225);
        mediaOptions.isCrop = true;
        mediaOptions.isShowCamera = false;
        openPhotoDialog(mediaOptions);
    }

    @Override
    protected ApplyBecomeRefereePresenter createPresenter() {
        return new ApplyBecomeRefereePresenter(this);
    }


    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        switch (mBvpContent.getCurrentItem()) {
            case 0:
                if (isInputName() && isSetPhoto()) {
                    mBvpContent.setCurrentItem(1, true);
                }
                break;
            case 1:
                if (mRequestBean.certificateLevel <= 0){
                    UIUtils.showToast(R.string.please_selector_referee_class);
                }else if (mRequestBean.certificateLevel==1||!DataUtils.isEmpty(mRequestBean.certificatePhoto)){
                    clickCommit();
                }else {
                    UIUtils.showToast(R.string.please_update_your_referee_grading_certificate);
                }
                break;
            default:
                break;
        }
    }

    private void clickCommit() {
        if (mRefereeAdapter.mAcetName != null) {
            mRequestBean.name = DataUtils.getEditDetails(mRefereeAdapter.mAcetName);
        }
        OSSRequestHelp.get().uploadingFile(mRequestBean.photo, new OSSRequestHelp.OnOSSResultListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onUpdate(long progressSize) {

            }

            @Override
            public void onSucceed(String path) {
                mRequestBean.photo = path;
                if (mRequestBean.certificateLevel > 1) {
                    OSSRequestHelp.get().uploadingFile(mRequestBean.certificatePhoto, new OSSRequestHelp.OnOSSResultListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onUpdate(long progressSize) {

                        }

                        @Override
                        public void onSucceed(String path) {
                            mRequestBean.certificatePhoto = path;
                            mPresenter.commitRefereeCredential(mRequestBean);
                        }

                        @Override
                        public void onFailure(String errorCode) {

                        }
                    });
                } else {
                    mPresenter.commitRefereeCredential(mRequestBean);
                }
            }

            @Override
            public void onFailure(String errorCode) {

            }
        });
    }

    public boolean isInputName() {
        if (mRefereeAdapter.mAcetName != null && !DataUtils.isEmpty(DataUtils.getEditDetails(mRefereeAdapter.mAcetName))) {
            return true;
        }
        UIUtils.showToast(R.string.please_input_your_name);
        return false;
    }

    public boolean isSetPhoto() {
        if (mRequestBean.photo != null) {
            return true;
        }
        UIUtils.showToast(R.string.please_update_your_referee_photo);
        return false;
    }

    @Override
    protected void resultAlbumResult(List<MediaSelectorFile> data) {
        setCurrentImageContent(data.get(0).filePath);
    }

    @Override
    protected void resultCameraResult(File cameraFile) {
        setCurrentImageContent(cameraFile.getAbsolutePath());
    }

    private void showRefereeDialog() {
        mPresenter.loadRefereeLevelData();
    }

    @Override
    public void resultLevelData(List<ResultRefereeLevelBean> data) {
        super.resultLevelData(data);
        if (data != null && data.size() > 0) {
            List<String> wheelData = new ArrayList<>();
            for (ResultRefereeLevelBean bean : data) {
                wheelData.add(bean.level);
            }
            if (mSingWheelDialog == null) {
                mSingWheelDialog = new SingWheelDialog(this, wheelData);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mSingWheelDialog.create();
                }
                mSingWheelDialog.setTitle(R.string.selector_referee_grade_book);
            }
            mSingWheelDialog.setOnItemClickListener((view, position) -> {
                if (mRefereeAdapter.mLlBody != null) {
                    if (position > 0) {
                        mRefereeAdapter.mLlBody.setVisibility(View.VISIBLE);
                    } else {
                        mRefereeAdapter.mLlBody.setVisibility(View.GONE);
                    }
                }
                if (mRefereeAdapter.mItemGrade != null) {
                    mRefereeAdapter.mItemGrade.mTvRight.setText(wheelData.get(position));
                }
                mRequestBean.certificateLevel = data.get(position).levelId;
            });
            if (!mSingWheelDialog.isShowing()) {
                mSingWheelDialog.show();
            }

        }
    }

    private void setCurrentImageContent(String filePath) {
        switch (mBvpContent.getCurrentItem()) {
            case 0:
                if (mRefereeAdapter.mIvContent != null) {
                    GlideManger.get().loadImage(this, filePath, mRefereeAdapter.mIvContent);
                }
                if (mRefereeAdapter.mTvUpdatePhoto != null) {
                    mRefereeAdapter.mTvUpdatePhoto.setVisibility(View.GONE);
                }
                if (mRefereeAdapter.mTvPhotoAgain != null) {
                    mRefereeAdapter.mTvPhotoAgain.setVisibility(View.VISIBLE);
                }
                mRequestBean.photo = filePath;
                break;
            case 1:
                if (mRefereeAdapter.mIvCertificateContent != null) {
                    GlideManger.get().loadImage(this, filePath, mRefereeAdapter.mIvCertificateContent);
                }
                if (mRefereeAdapter.mTvUpdateCertificatePhoto != null) {
                    mRefereeAdapter.mTvUpdateCertificatePhoto.setVisibility(View.GONE);
                }
                if (mRefereeAdapter.mTvCertificateAgain != null) {
                    mRefereeAdapter.mTvCertificateAgain.setVisibility(View.VISIBLE);
                }
                mRequestBean.certificatePhoto = filePath;
                break;
            default:
                break;
        }
    }

    @Override
    public void resultCommitRefereeCredential() {
        mBvpContent.setCurrentItem(MAX_PAGER_COUNT - 1, true);
        mTvNext.setVisibility(View.GONE);
    }

    class RefereeAdapter extends PagerAdapter {
        private Context mContext;
        AppCompatEditText mAcetName;
        ImageView mIvContent;
        TextView mTvUpdatePhoto, mTvPhotoAgain;
        private ItemView mItemGrade;
        private LinearLayout mLlBody;
        private TextView mTvUpdateCertificatePhoto;
        private TextView mTvCertificateAgain;
        private ImageView mIvCertificateContent;


        RefereeAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return ApplyBecomeRefereeActivity.MAX_PAGER_COUNT;
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            View inflateView;
            switch (position) {
                case 0:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_referee_message_view, container, false);
                    mAcetName = inflateView.findViewById(R.id.aet_name);
                    mIvContent = inflateView.findViewById(R.id.iv_content);
                    mTvUpdatePhoto = inflateView.findViewById(R.id.tv_update_photo);
                    mTvPhotoAgain = inflateView.findViewById(R.id.tv_photo_again);
                    mTvUpdatePhoto.setOnClickListener(v -> clickOpenPhoto());
                    mTvPhotoAgain.setOnClickListener(v -> {
                        mRequestBean.photo = null;
                        mIvContent.setImageResource(0);
                        mTvPhotoAgain.setVisibility(View.GONE);
                        mTvUpdatePhoto.setVisibility(View.VISIBLE);
                    });
                    break;
                case 1:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_referee_grade_view, container, false);
                    mItemGrade = inflateView.findViewById(R.id.item_grade);
                    mItemGrade.mTvRight.setTextColor(ContextCompat.getColor(mContext, R.color.color_4));
                    mItemGrade.mTvRight.setHintTextColor(ContextCompat.getColor(mContext, R.color.colorFFA6A6A6));
                    mItemGrade.mTvRight.setHint(R.string.selector_referee_grade_book);
                    mIvCertificateContent = inflateView.findViewById(R.id.iv_content);
                    mTvCertificateAgain = inflateView.findViewById(R.id.tv_certificate_again);
                    mTvUpdateCertificatePhoto = inflateView.findViewById(R.id.tv_update_photo);
                    mLlBody = inflateView.findViewById(R.id.ll_body);
                    mItemGrade.setOnItemClickListener(view -> showRefereeDialog());
                    mTvUpdateCertificatePhoto.setOnClickListener(v -> clickOpenPhoto());
                    mTvCertificateAgain.setOnClickListener(v -> {
                        mRequestBean.certificatePhoto = null;
                        mIvCertificateContent.setImageResource(0);
                        mTvCertificateAgain.setVisibility(View.GONE);
                        mTvUpdateCertificatePhoto.setVisibility(View.VISIBLE);
                    });
                    break;
                case 2:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_referee_audit_view, container, false);
                    break;
                default:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_referee_message_view, container, false);
                    break;
            }
            container.addView(inflateView);
            return inflateView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    private void clickBack() {
        if (mBvpContent.getCurrentItem() == MAX_PAGER_COUNT - 1) {
            finish();
        } else if (mRequestBean.isHasData()) {
            HintDialog hintDialog = new HintDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setBody(R.string.you_sure_of_submission_of_accreditation)
                    .setShowSingButton(false)
                    .builder();
            hintDialog.show();
            hintDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
                @Override
                public void onClickSure(@NonNull View view) {
                    hintDialog.dismiss();
                    finish();
                }

                @Override
                public void onClickCancel(@NonNull View view) {
                    hintDialog.dismiss();
                }
            });
        } else {
            finish();
        }

    }
}
