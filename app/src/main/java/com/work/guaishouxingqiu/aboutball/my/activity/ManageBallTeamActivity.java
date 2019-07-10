package com.work.guaishouxingqiu.aboutball.my.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.CameraActivity;
import com.work.guaishouxingqiu.aboutball.media.MediaSelector;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestManageBallTeamBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.ManageBallTeamContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.ManageBallTeamPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.other.OSSRequestHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;
import com.work.guaishouxingqiu.aboutball.weight.SelectorColorDialog;
import com.work.guaishouxingqiu.aboutball.weight.SingWheelDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 11:48
 * 更新时间: 2019/4/24 11:48
 * 描述:管理球队页面（创建和编辑）
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MANAGE_BALL_TEAM)
public class ManageBallTeamActivity extends CameraActivity<ManageBallTeamPresenter> implements ManageBallTeamContract.View {
    //创建
    public static final int CREATE = 0;
    //编辑
    public static final int EDIT = 1;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.civ_logo)
    CircleImageView mCivLogo;
    @BindView(R.id.cl_logo)
    ConstraintLayout mClLogo;
    @BindView(R.id.acet_team_name)
    AppCompatEditText mAcetTeamName;
    @BindView(R.id.item_type)
    ItemView mItemType;
    @BindView(R.id.item_color)
    ItemView mItemColor;
    @BindView(R.id.tv_create)
    TextView mTvCreate;
    private SingWheelDialog mItemTypeDialog;
    private SelectorColorDialog mItemColorDialog;
    private RequestManageBallTeamBean mRequestBean;
    private List<ResultTypeBean> mTypeData;
    private ResultBallDetailsBean mBallBean;

    @Override
    protected void resultAlbumResult(List<MediaSelectorFile> data) {
        mRequestBean.logoUrl = data.get(0).filePath;
        GlideManger.get().loadLogoImage(this, mRequestBean.logoUrl, mCivLogo);
    }

    @Override
    protected void resultCameraResult(File cameraFile) {
        mRequestBean.logoUrl = cameraFile.getAbsolutePath();
        GlideManger.get().loadLogoImage(this, mRequestBean.logoUrl, mCivLogo);
    }

    private void uploadingFile(String filePath) {
        if (FileUtils.existsFile(filePath)) {
            OSSRequestHelp.get().uploadingFile(filePath, new OSSRequestHelp.OnOSSResultListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onUpdate(long progressSize) {

                }

                @Override
                public void onSucceed(String path) {
                    mRequestBean.logoUrl = path;
                    mRequestBean.teamName = DataUtils.getEditDetails(mAcetTeamName);
                    if (mBallBean != null) {
                        mPresenter.editTeam(mRequestBean);
                    } else {
                        mPresenter.createTeam(mRequestBean);
                    }
                }

                @Override
                public void onFailure(String errorCode) {

                }
            },this);
        } else {
            if (DataUtils.isEmpty(mRequestBean.logoUrl)) {
                UIUtils.showToast(R.string.please_choose_team_logo);
            } else {
                mRequestBean.teamName = DataUtils.getEditDetails(mAcetTeamName);
                mPresenter.editTeam(mRequestBean);
            }

        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_ball_team;
    }

    @Override
    protected void initView() {
        mRequestBean = new RequestManageBallTeamBean();
        mBallBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        //说明是编辑
        if (mBallBean != null) {
            mTitleView.mTvCenter.setText(R.string.edit_ball_team_details);
            mRequestBean.teamId = mBallBean.teamId;
            mRequestBean.logoUrl = mBallBean.teamLogo;
            GlideManger.get().loadLogoImage(this, mRequestBean.logoUrl, mCivLogo);
            mAcetTeamName.setText(mBallBean.teamName);
            if (mBallBean.teamName != null) {
                mAcetTeamName.setSelection(DataUtils.getEditDetails(mAcetTeamName).length());
            }
            mItemType.setContentText(mBallBean.teamType);
            LogUtils.w("initView--", mBallBean.teamTypeId + "---");
            mRequestBean.typeId = mBallBean.teamTypeId;
            mRequestBean.shirtColor = mBallBean.shirtColor;
            mItemColor.setContentText(mBallBean.shirtColor);
            mTvCreate.setText(R.string.save);
        } else {
            mTitleView.mTvCenter.setText(R.string.create_ball_team);
            mTvCreate.setText(R.string.create_ball_team);

        }
    }

    @Override
    protected void initData() {
        mItemType.mTvRight.setHint(R.string.please_choose_exercise_type);
        mItemType.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemColor.mTvRight.setHint(R.string.please_selector_ball_clothing_color);
        mItemColor.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));

    }

    @Override
    protected void initEvent() {
        mItemType.setOnItemClickListener(view -> {
            clickItemType();
        });
        mItemColor.setOnItemClickListener(view -> {
            clickItemColor();
        });
    }

    private void clickItemColor() {
        if (mItemColorDialog == null) {
            mItemColorDialog = new SelectorColorDialog(this);
            mItemColorDialog.setOnColorSelectorListener((view, color) -> {
                mItemColor.setContentText(color);
                mRequestBean.shirtColor = mItemColor.mTvRight.getText().toString();
            });
        }
        if (!mItemColorDialog.isShowing()) {
            mItemColorDialog.show();
        }

    }

    private void clickItemType() {
        mPresenter.loadBallTypeList();

    }

    @Override
    protected ManageBallTeamPresenter createPresenter() {
        return new ManageBallTeamPresenter(this);
    }


    @OnClick({R.id.cl_logo, R.id.tv_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cl_logo:
                MediaSelector.MediaOptions options = new MediaSelector.MediaOptions();
                options.isCrop = true;
                options.maxChooseMedia = 1;
                options.isShowVideo = false;
                openPhotoDialog(options);
                break;
            case R.id.tv_create:
                clickCreateTeam();
                break;
        }
    }

    private void clickCreateTeam() {
        if (isHasLogo() && isHasTeamName() && isHasType() && isHasShirtColor()) {
            uploadingFile(mRequestBean.logoUrl);
        }
    }

    private boolean isHasLogo() {
        if (!DataUtils.isEmpty(mRequestBean.logoUrl)) {
            return true;
        } else {
            UIUtils.showToast(R.string.please_choose_team_logo);
            return false;
        }
    }

    private boolean isHasTeamName() {
        if (DataUtils.isEmpty(DataUtils.getEditDetails(mAcetTeamName))) {
            UIUtils.showToast(R.string.please_inout_team_name);
            return false;
        }
        return true;
    }

    public boolean isHasType() {
        if (DataUtils.isEmpty(mItemType.mTvRight.getText())) {
            UIUtils.showToast(R.string.please_selector_type);
            return false;
        }
        return true;
    }

    public boolean isHasShirtColor() {
        if (DataUtils.isEmpty(mItemColor.mTvRight.getText())) {
            UIUtils.showToast(R.string.please_selector_shirt_color);
            return false;
        }
        return true;
    }

    @Override
    public void resultBallTypeList(List<ResultTypeBean> data) {
        if (data != null && data.size() > 0) {
            mTypeData = data;
            List<String> mBallTypeList = new ArrayList<>();
            for (ResultTypeBean bean : data) {
                mBallTypeList.add(bean.typeName);
            }
            if (mItemTypeDialog == null) {
                mItemTypeDialog = new SingWheelDialog(ManageBallTeamActivity.this, mBallTypeList);
                mItemTypeDialog.setOnItemClickListener((view1, position) -> {
                    mRequestBean.typeId = mTypeData.get(position).typeId;

                    mItemType.setContentText(mBallTypeList.get(position));
                });
            } else {
                mItemTypeDialog.notifyData(mBallTypeList);
            }
            if (!mItemTypeDialog.isShowing()) {
                mItemTypeDialog.show();
            }
            mItemTypeDialog.setTitle(R.string.choose_exercise_type);
        } else {
            UIUtils.showToast("没有约球类型列表！");
        }
    }

    @Override
    public void resultManageTeam(long id) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void resultEditTeam() {
        finish();
    }


}
