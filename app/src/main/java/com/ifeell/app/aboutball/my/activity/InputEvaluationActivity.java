package com.ifeell.app.aboutball.my.activity;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.CameraActivity;
import com.ifeell.app.aboutball.media.MediaSelector;
import com.ifeell.app.aboutball.media.bean.MediaSelectorFile;
import com.ifeell.app.aboutball.my.adapter.AddImageAdapter;
import com.ifeell.app.aboutball.my.bean.AddImageBean;
import com.ifeell.app.aboutball.my.bean.RequestInputEvaluationBean;
import com.ifeell.app.aboutball.my.contract.InputEvaluationContract;
import com.ifeell.app.aboutball.my.presenter.InputEvaluationPresenter;
import com.ifeell.app.aboutball.other.OSSRequestHelp;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 10:44
 * 更新时间: 2019/5/27 10:44
 * 描述:填写评价Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_INPUT_EVALUATION)
public class InputEvaluationActivity extends CameraActivity<InputEvaluationPresenter> implements InputEvaluationContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.acet_content)
    AppCompatEditText mAcetContent;
    @BindView(R.id.tv_length)
    TextView mTvLength;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    private long mRefereeId;
    private long mTeamId;
    private int mFlag;
    private AddImageAdapter mImageAdapter;
    private List<AddImageBean> mImageData;
    private MediaSelector.MediaOptions mMediaOptions;
    private List<String> mRequestOSSPathData;
    private long mAgreeId;
    private RequestInputEvaluationBean mRequestBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_evaluation;
    }

    @Override
    public void initPermission() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        mFlag = bundle.getInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, -1);
        mRefereeId = bundle.getLong(ARouterConfig.Key.REFEREE_ID, -1);
        mTeamId = bundle.getLong(ARouterConfig.Key.TEAM_ID, -1);
        mAgreeId = bundle.getLong(ARouterConfig.Key.AGREE_ID, -1);
        if (mFlag == -1 || mAgreeId == -1) {
            UIUtils.showToast(R.string.not_find_evaluation);
            finish();
            return;
        }


        super.initPermission();
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private boolean isInputContent() {
        if (DataUtils.isEmpty(DataUtils.getEditDetails(mAcetContent))) {
            UIUtils.showToast(R.string.please_tell_me_what_you_think);
            return false;
        }
        return true;
    }


    @Override
    protected void initData() {
        mMediaOptions = new MediaSelector.MediaOptions();
        mMediaOptions.maxChooseMedia = AddImageAdapter.MAX_IMAGE_COUNT;
        mMediaOptions.isCrop = false;
        mMediaOptions.isCompress = true;
        mMediaOptions.isShowCamera = false;

        mRequestOSSPathData = new ArrayList<>();
        mRequestBean = new RequestInputEvaluationBean();
        mRequestBean.agreeId = mAgreeId;
        mRequestBean.teamId = mTeamId;
        mRequestBean.refereeId = mRefereeId;

        mImageData = new ArrayList<>();
        mImageData.add(new AddImageBean(true));
        mImageAdapter = new AddImageAdapter(this, mImageData);
        mRvData.setAdapter(mImageAdapter);
    }

    @Override
    protected void initEvent() {
        mImageAdapter.setOnItemClickListener((view, position) -> {
            AddImageBean bean = mImageData.get(position);
            if (bean.isAdd) {
                clickMediaSelector();
            }
        });
        mImageAdapter.setOnDeleteClickListener((view, position) -> mRequestOSSPathData.remove(position));
        mAcetContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvLength.setText(DataUtils.getTextTrimLength(DataUtils.getEditDetails(mAcetContent)) + "/140");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected InputEvaluationPresenter createPresenter() {
        return new InputEvaluationPresenter(this);
    }

    private void clickMediaSelector() {
        mMediaOptions.maxChooseMedia = AddImageAdapter.MAX_IMAGE_COUNT - (mImageData.size() - 1);
        openPhotoDialog(mMediaOptions);
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (isInputContent()) {
            mRequestBean.commentContent = DataUtils.getEditDetails(mAcetContent);
            if (mRequestOSSPathData.size() > 0) {
                OSSRequestHelp.get().uploadingFiles(mRequestOSSPathData, new OSSRequestHelp.OnOSSDataResultListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSucceed(List<String> pathData) {
                        mRequestBean.commentPic = DataUtils.appendImagesPath(pathData);
                        postEvaluation();
                    }

                    @Override
                    public void onFailure(String errorCode) {

                    }
                },this);
            } else {
                postEvaluation();

            }
        }
    }

    private void postEvaluation() {
        if (mFlag == Contast.InputEvaluationType.OPPONENT) {
            mPresenter.postEvaluationOpponent(mRequestBean);
        } else if (mFlag == Contast.InputEvaluationType.REFEREE) {
            mPresenter.postEvaluationReferee(mRequestBean);
        }
    }

    @Override
    protected void resultAlbumResult(List<MediaSelectorFile> data) {
        for (int i = 0; i < data.size(); i++) {
            MediaSelectorFile media = data.get(i);
            mRequestOSSPathData.add(media.filePath);
            AddImageBean addBean = new AddImageBean();
            addBean.path = media.filePath;
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
    public void resultEvaluationSucceed() {
        mViewModel.clickBackForResult();
    }
}
