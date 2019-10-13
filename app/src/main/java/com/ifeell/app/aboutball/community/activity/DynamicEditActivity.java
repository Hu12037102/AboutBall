package com.ifeell.app.aboutball.community.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.DelayedClickListener;
import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.CameraActivity;
import com.ifeell.app.aboutball.community.bean.RequestPublishDynamicBean;
import com.ifeell.app.aboutball.community.bean.ResultTopicBean;
import com.ifeell.app.aboutball.community.contract.DynamicEditContract;
import com.ifeell.app.aboutball.community.presenter.DynamicEditPresenter;
import com.ifeell.app.aboutball.media.MediaSelector;
import com.ifeell.app.aboutball.media.bean.MediaSelectorFile;
import com.ifeell.app.aboutball.my.adapter.AddImageAdapter;
import com.ifeell.app.aboutball.my.bean.AddImageBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.other.OSSRequestHelp;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.util.SpanUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 16:37
 * 更新时间: 2019/6/14 16:37
 * 描述:动态编辑P
 */
@Route(path = ARouterConfig.Path.ACTIVITY_DYNAMIC_EDIT)
public class DynamicEditActivity extends CameraActivity<DynamicEditPresenter> implements DynamicEditContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.acet_content)
    AppCompatEditText mAcetContent;
    @BindView(R.id.tv_content_count)
    TextView mTvContentCount;
    @BindView(R.id.rv_image)
    RecyclerView mRvImage;
    @BindView(R.id.item_topic)
    ItemView mItemTopic;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    private List<AddImageBean> mImageData;
    private MediaSelector.MediaOptions mMediaOptions;
    private AddImageAdapter mImageAdapter;
    private List<String> mRequestOSSPathData;
    private RequestPublishDynamicBean mRequestBean;
    private ResultTopicBean mResultTopicBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic_edit;
    }

    @Override
    protected void initView() {
        mRvImage.setLayoutManager(new GridLayoutManager(this, 4));
        mTvCommit.setText(R.string.publish);
        mItemTopic.mTvLeft.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTopic.mTvLeft.setHint(R.string.selector_topic);
    }

    @Override
    protected void initSellingPoint() {
        super.initSellingPoint();
        DataUtils.addSellingPoint(this, SellingPointsEvent.Key.A0410);
    }

    @Override
    protected void initData() {
        mRequestBean = new RequestPublishDynamicBean();
        mRequestOSSPathData = new ArrayList<>();

        mMediaOptions = new MediaSelector.MediaOptions();
        mMediaOptions.maxChooseMedia = AddImageAdapter.MAX_IMAGE_COUNT;
        mMediaOptions.isCrop = false;
        mMediaOptions.isCompress = true;
        mMediaOptions.isShowCamera = false;
        mMediaOptions.isShowVideo = true;

        mImageData = new ArrayList<>();
        mImageData.add(new AddImageBean(true));
        mImageAdapter = new AddImageAdapter(this, mImageData);
        mRvImage.setAdapter(mImageAdapter);
    }

    private void clickMediaSelector() {
        DataUtils.addSellingPoint(DynamicEditActivity.this, SellingPointsEvent.Key.A041002);
        mMediaOptions.maxChooseMedia = AddImageAdapter.MAX_IMAGE_COUNT - (mImageData.size() - 1);
        openCameraVideoDialog(mMediaOptions, mRequestOSSPathData.size() > 0);
    }

    @Override
    protected void initEvent() {
        mItemTopic.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                DataUtils.addSellingPoint(DynamicEditActivity.this, SellingPointsEvent.Key.A041003);
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_SELECTOR_TOPIC, DynamicEditActivity.this);
            }
        });
        mImageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                AddImageBean bean = mImageData.get(position);
                if (bean.isAdd) {
                    clickMediaSelector();
                } else {
                    clickPreviewMedia(position);
                }
            }
        });
        mImageAdapter.setOnDeleteClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                if (position < mRequestOSSPathData.size()) {
                    mRequestOSSPathData.remove(position);
                }
            }
        });
        mAcetContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.addSellingPoint(DynamicEditActivity.this, SellingPointsEvent.Key.A041001);
            }
        });
        mAcetContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvContentCount.setText(mAcetContent.getText().length() + "/450");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvCommit.setOnClickListener(new DelayedClickListener() {
            @Override
            public void onDelayedClick(View view) {
                DataUtils.addSellingPoint(DynamicEditActivity.this, SellingPointsEvent.Key.A041004);
                if (isCanPublish()) {
                    String content = DataUtils.getEditDetails(mAcetContent);
                    if (mResultTopicBean != null) {
                        if (content.contains(mResultTopicBean.topicTitle)) {
                            content = content.replace(mResultTopicBean.topicTitle, "");
                        }
                    }
                    mRequestBean.tweetContent = content;
                    if (mRequestOSSPathData.size() > 0) {
                        if (mRequestOSSPathData.size() == 1 && DataUtils.isVideo(mRequestOSSPathData.get(0))) {
                            GlideManger.get().loadImageBitmap(mRequestOSSPathData.get(0), new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    if (resource.getWidth() > resource.getHeight()) {
                                        mRequestBean.tweetType = Contast.DynamicType.VIDEO_LANDSCAPE;
                                    } else {
                                        mRequestBean.tweetType = Contast.DynamicType.VIDEO_PORTRAIT;
                                    }
                                    ossUpdateFile();
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                        } else {
                            mRequestBean.tweetType = Contast.DynamicType.IMAGE;
                            ossUpdateFile();
                        }

                    } else {
                        mPresenter.publishDynamic(mRequestBean);
                    }
                }
            }
        });
    }

    private void ossUpdateFile() {
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
                mRequestBean.imageUrl = sb.toString();
                mPresenter.publishDynamic(mRequestBean);
            }

            @Override
            public void onFailure(String errorCode) {

            }
        }, true, DynamicEditActivity.this);
    }

    private void clickPreviewMedia(int position) {
        mViewModel.startActivityToPreview(position, (ArrayList<String>) mRequestOSSPathData);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                //话题选择返回
                case ARouterIntent.REQUEST_CODE:
                    if (data == null) {
                        return;
                    }
                    if (mResultTopicBean != null) {
                        mAcetContent.setText(DataUtils.getEditDetails(mAcetContent).replace(mResultTopicBean.topicTitle, ""));
                    }
                    mResultTopicBean = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    if (mResultTopicBean == null) {
                        return;
                    }
                    UIUtils.setText(mItemTopic.mTvLeft, mResultTopicBean.topicTitle);
                    mAcetContent.setText(SpanUtils.getTextColor(R.color.color_2, 0, mResultTopicBean.topicTitle.length(), mResultTopicBean.topicTitle.concat(DataUtils.getEditDetails(mAcetContent))));
                    mAcetContent.setSelection(mAcetContent.getText() == null ? 0 : DataUtils.getTextTrimLength(DataUtils.getEditDetails(mAcetContent)));
                    mRequestBean.topicId = mResultTopicBean.topicId;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected DynamicEditPresenter createPresenter() {
        return new DynamicEditPresenter(this);
    }


    /*@OnClick(R.id.tv_commit)
    public void onViewClicked() {

    }*/

    private boolean isCanPublish() {
        if (mRequestBean.imageUrl == null && DataUtils.isEmpty(DataUtils.getEditDetails(mAcetContent))) {
            UIUtils.showToast(R.string.you_are_not_going_to_say_anything);
            return false;
        }
        return true;
    }

    @Override
    protected void resultAlbumResult(List<MediaSelectorFile> data) {
        LogUtils.w("resultAlbumResult--", data.get(0).filePath);
        if (MediaSelectorFile.hasVideo(data)) {
            mRequestOSSPathData.clear();
            mImageAdapter.removeData();
        }
        for (int i = 0; i < data.size(); i++) {
            MediaSelectorFile media = data.get(i);
            mRequestOSSPathData.add(media.filePath);
            AddImageBean addBean = new AddImageBean();
            addBean.path = media.filePath;
            addBean.isVideo = DataUtils.isVideo(addBean.path);
            mImageData.add(mImageData.size() - 1, addBean);
        }
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void resultCameraResult(File cameraFile) {
        String cameraPath = cameraFile.getAbsolutePath();
        mRequestOSSPathData.add(cameraPath);
        AddImageBean addBean = new AddImageBean();
        addBean.path = cameraPath;
        int position = mImageData.size() - 1;
        mImageData.add(position, addBean);
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void resultCameraVideoResult(String filePath, boolean isVideo) {

        mRequestOSSPathData.add(filePath);
        AddImageBean addBean = new AddImageBean();
        addBean.isVideo = isVideo;
        addBean.path = filePath;
        int position = mImageData.size() - 1;
        mImageData.add(position, addBean);
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultPublishSucceed() {
        mViewModel.clickBackForResult();
    }
}
