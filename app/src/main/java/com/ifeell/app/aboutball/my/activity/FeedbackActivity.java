package com.ifeell.app.aboutball.my.activity;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.CameraActivity;
import com.ifeell.app.aboutball.media.MediaSelector;
import com.ifeell.app.aboutball.media.bean.MediaSelectorFile;
import com.ifeell.app.aboutball.my.adapter.AddImageAdapter;
import com.ifeell.app.aboutball.my.bean.AddImageBean;
import com.ifeell.app.aboutball.my.bean.RequestFeedbackBean;
import com.ifeell.app.aboutball.my.contract.FeedbackContract;
import com.ifeell.app.aboutball.my.presenter.FeedbackPresenter;
import com.ifeell.app.aboutball.other.OSSRequestHelp;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseDialog;
import com.ifeell.app.aboutball.weight.HintDialog;
import com.ifeell.app.aboutball.weight.SingWheelDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/13 9:25
 * 更新时间: 2019/5/13 9:25
 * 描述:意见反馈Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_FEEDBACK)
public class FeedbackActivity extends CameraActivity<FeedbackPresenter> implements FeedbackContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.item_type)
    ItemView mItemType;
    @BindView(R.id.acet_content)
    AppCompatEditText mAcetContent;
    @BindView(R.id.rv_image)
    RecyclerView mRvImage;
    private List<String> mTypeList;
    private SingWheelDialog mTypeDialog;
    private RequestFeedbackBean mRequestBean;
    private AddImageAdapter mAdapter;
    private List<AddImageBean> mData;
    private MediaSelector.MediaOptions mMediaOptions;
    private List<String> mRequestOSSPathData;
    private HintDialog mBackDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        mRvImage.setLayoutManager(new GridLayoutManager(this, 4));
        mItemType.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemType.mTvRight.setHint(R.string.please_selector_feedback_type);
    }

    @Override
    protected void initData() {
        mMediaOptions = new MediaSelector.MediaOptions();
        mMediaOptions.maxChooseMedia = AddImageAdapter.MAX_IMAGE_COUNT;
        mMediaOptions.isCrop = false;
        mMediaOptions.isCompress = true;
        mMediaOptions.isShowCamera = false;

        mRequestOSSPathData = new ArrayList<>();
        String[] typeArray = getResources().getStringArray(R.array.feedback_type_array);
        mTypeList = Arrays.asList(typeArray);

        mRequestBean = new RequestFeedbackBean();

        mData = new ArrayList<>();
        mData.add(new AddImageBean(true));
        mAdapter = new AddImageAdapter(this, mData);
        mRvImage.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                clickBack();
            }
        });
        mItemType.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                if (mTypeDialog == null) {
                    mTypeDialog = new SingWheelDialog(FeedbackActivity.this, mTypeList);
                    mTypeDialog.setTitle(R.string.selector_feedback_type);
                }
                if (!mTypeDialog.isShowing()) {
                    mTypeDialog.show();
                }

                mTypeDialog.setOnItemClickListener((view1, position) -> {
                    mRequestBean.adviseType = mTypeList.get(position);
                    mItemType.setContentText(mRequestBean.adviseType);
                });
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                AddImageBean bean = mData.get(position);
                if (bean.isAdd) {
                    clickMediaSelector();
                }
            }
        });
        mAdapter.setOnDeleteClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                LogUtils.w("setOnDeleteClickListener--", position + "--" + mRequestOSSPathData.size());
                mRequestOSSPathData.remove(position);
            }
        });
    }

    private void clickMediaSelector() {
        mMediaOptions.maxChooseMedia = AddImageAdapter.MAX_IMAGE_COUNT - (mData.size() - 1);
        openPhotoDialog(mMediaOptions);
    }

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter(this);
    }


    @Override
    protected void resultAlbumResult(List<MediaSelectorFile> data) {
        for (int i = 0; i < data.size(); i++) {
            MediaSelectorFile media = data.get(i);
            mRequestOSSPathData.add(media.filePath);
            AddImageBean addBean = new AddImageBean();
            addBean.path = media.filePath;
            mData.add(mData.size() - 1, addBean);
        }
        mAdapter.notifyDataSetChanged();


    }

    @Override
    protected void resultCameraResult(File cameraFile) {
        String cameraPath = cameraFile.getAbsolutePath();
        mRequestOSSPathData.add(cameraPath);
        AddImageBean addBean = new AddImageBean();
        addBean.path = cameraPath;
        int position = mData.size() - 1;
        mData.add(position, addBean);
        mAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (isSelectorType() && isInputContentOrImage()) {
            mRequestBean.advise = DataUtils.getEditDetails(mAcetContent);
            if (mRequestOSSPathData.size() == 0) {
                mPresenter.feedback(mRequestBean);
            } else {
                OSSRequestHelp.get().uploadingFiles(mRequestOSSPathData, new OSSRequestHelp.OnOSSDataResultListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSucceed(List<String> pathData) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < pathData.size(); i++) {
                            sb = sb.append(pathData.get(i)).append(i == pathData.size() - 1 ? "" : ",");
                        }
                        LogUtils.w("isInputContentOrImage--", sb.toString());
                        mRequestBean.pictureUrl = sb.toString();
                        mPresenter.feedback(mRequestBean);
                    }

                    @Override
                    public void onFailure(String errorCode) {

                    }
                },this);
            }
        }
    }

    private boolean isSelectorType() {
        if (mRequestBean.adviseType == null) {
            UIUtils.showToast(R.string.please_selector_feedback_type);
            return false;
        }
        return true;
    }

    private boolean isInputContentOrImage() {
        if (DataUtils.isEmpty(DataUtils.getEditDetails(mAcetContent)) && mRequestOSSPathData.size() == 0) {
            UIUtils.showToast(R.string.please_inout_feedback_content_or_image);
            return false;
        }
        return true;
    }


    @Override
    public void resultFeedbackSucceed() {
        finish();
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    private void clickBack() {
        if (mRequestBean.adviseType != null || !DataUtils.isEmpty(DataUtils.getEditDetails(mAcetContent))
                || mRequestOSSPathData.size() > 0) {
            mBackDialog = new HintDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setBody(R.string.you_sure_give_up_to_submit)
                    .setShowSingButton(false)
                    .builder();
            if (!mBackDialog.isShowing()) {
                mBackDialog.show();
            }
            mBackDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
                @Override
                public void onClickSure(@NonNull View view) {
                    mBackDialog.dismiss();
                    finish();
                }

                @Override
                public void onClickCancel(@NonNull View view) {
                    mBackDialog.dismiss();
                }
            });
        } else {
            finish();
        }
    }
}
