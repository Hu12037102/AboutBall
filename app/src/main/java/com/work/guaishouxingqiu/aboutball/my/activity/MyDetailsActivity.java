package com.work.guaishouxingqiu.aboutball.my.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.CameraActivity;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;
import com.work.guaishouxingqiu.aboutball.media.MediaSelector;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateBirthdayBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateHeadPhotoBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateHeightBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateWeightBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.other.OSSRequestHelp;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weight.BirthdayDialog;
import com.work.guaishouxingqiu.aboutball.weight.HeightDialog;
import com.work.guaishouxingqiu.aboutball.weight.SexDialog;
import com.work.guaishouxingqiu.aboutball.weight.WeightDialog;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 9:19
 * 更新时间: 2019/3/20 9:19
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_DETAILS)
public class MyDetailsActivity extends CameraActivity<MyDetailsPresenter> implements MyDetailsContract.View {
    @BindView(R.id.civ_head)
    CircleImageView mCivHead;
    @BindView(R.id.item_name)
    ItemView mItemName;
    @BindView(R.id.item_sex)
    ItemView mItemSex;
    @BindView(R.id.item_birthday)
    ItemView mItemBirthday;
    @BindView(R.id.item_stature)
    ItemView mItemStature;
    @BindView(R.id.item_weight)
    ItemView mItemWeight;
    @BindView(R.id.item_phone)
    ItemView mItemPhone;
    @BindView(R.id.tv_wechat_right)
    TextView mTvWechatRight;
    @BindView(R.id.item_alter_password)
    ItemView mItemAlterPassword;
    @BindView(R.id.item_address)
    ItemView mItemAddress;
    private UserBean mUserBean;
    public static final String NO_SETTING = "未设置";
    private int mSexType;
    private RequestUpdateWeightBean mUpdateWeightBean;
    private RequestUpdateHeightBean mUpdateHeightBean;
    private RequestUpdateBirthdayBean mBirthdayBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_details;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void onStart() {
        super.onStart();
        initUserData();
    }

    private void initUserData() {
        mUserBean = UserManger.get().getUser();
        GlideManger.get().loadImage(this, mUserBean.headerImg, R.mipmap.icon_my_default_head, R.mipmap.icon_my_default_head, mCivHead);
        mItemName.mTvRight.setText(DataUtils.isEmpty(mUserBean.nickName) ? NO_SETTING : mUserBean.nickName);
        setGender(mUserBean.gender);
        mItemBirthday.mTvRight.setText(DataUtils.isEmpty(mUserBean.birthday) ? NO_SETTING : mUserBean.birthday);
        mItemStature.mTvRight.setText(mUserBean.height == 0 ? NO_SETTING : String.valueOf(mUserBean.height).concat(" ").concat("cm"));
        mItemWeight.mTvRight.setText(mUserBean.weight == 0 ? NO_SETTING : String.valueOf(mUserBean.weight).concat(" ").concat("kg"));
        mItemPhone.mTvRight.setText(DataUtils.isEmpty(mUserBean.phone) ? NO_SETTING : mUserBean.phone);

    }

    private void setGender(int sexType) {
        switch (sexType) {
            case Contast.SEX_TYPE_MAN:
                mItemSex.mTvRight.setText(R.string.man);
                break;
            case Contast.SEX_TYPE_WOMAN:
                mItemSex.mTvRight.setText(R.string.women);
                break;
            default:
                mItemSex.mTvRight.setText(R.string.not_setting);
                break;
        }
    }

    @Override
    protected void initEvent() {
        mItemSex.setOnItemClickListener(view -> clickUpdateSex());
        mItemName.setOnItemClickListener(view -> clickUpdateName());
        mItemWeight.setOnItemClickListener(view -> clickUpdateWeight());
        mItemStature.setOnItemClickListener(view -> clickUpdateHeight());
        mItemBirthday.setOnItemClickListener(view -> clickUpdateBirthday());
        mItemAddress.setOnItemClickListener(view -> clickMyAddress());
        mItemPhone.setOnItemClickListener(view -> clickUpdatePhone());
        mItemAlterPassword.setOnItemClickListener(view -> clickUpdatePassword());
    }

    private void clickUpdatePassword() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_UPDATE_PASSWORD);
    }

    private void clickUpdatePhone() {
        if (UserManger.get().isLogin()) {
            mViewModel.startActivityToUpdatePhoneForResult(null, Contast.LoginStatus.UPDATE_PHONE);
        } else {
            mViewModel.showLoginDialog();
        }
    }

    private void clickMyAddress() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_EDIT_MY_ADDRESS);
    }

    private void clickUpdateBirthday() {
        BirthdayDialog birthdayDialog = new BirthdayDialog(this);
        birthdayDialog.setBirthdayDate(mUserBean.birthday);
        birthdayDialog.show();
        birthdayDialog.setOnBirthdayResultListener(date -> {
                    mBirthdayBean = new RequestUpdateBirthdayBean();
                    mBirthdayBean.birthday = date;
                    mPresenter.updateBirthday(mBirthdayBean);
                }

        );

    }

    private void clickUpdateHeight() {
        HeightDialog heightDialog = new HeightDialog(this);
        heightDialog.setHeight(UserManger.get().getUser().height);
        heightDialog.show();
        heightDialog.setOnHeightResultListener(height -> {
            mUpdateHeightBean = new RequestUpdateHeightBean();
            mUpdateHeightBean.height = height;
            mPresenter.updateHeight(mUpdateHeightBean);
        });
    }

    private void clickUpdateWeight() {
        UserBean userBean = UserManger.get().getUser();
        WeightDialog weightDialog = new WeightDialog(this);
        weightDialog.setWeight(userBean.weight);
        weightDialog.show();
        weightDialog.setOnWeightResultListener(weight -> {
            mUpdateWeightBean = new RequestUpdateWeightBean();
            mUpdateWeightBean.weight = weight;
            mPresenter.updateWeight(mUpdateWeightBean);
        });
    }

    private void clickUpdateName() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ALTER_NAME);
    }

    @Override
    protected MyDetailsPresenter createPresenter() {
        return new MyDetailsPresenter(this);
    }


    @OnClick({R.id.rl_head_photo, R.id.item_name, R.id.item_birthday,
            R.id.item_stature, R.id.item_weight, R.id.item_phone, R.id.cl_wechat, R.id.item_alter_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_head_photo:
                clickUpdateHeadPhoto();
                break;
            case R.id.item_name:
                break;
            case R.id.item_birthday:
                break;
            case R.id.item_stature:
                break;
            case R.id.item_weight:
                break;
            case R.id.item_phone:
                break;
            case R.id.cl_wechat:
                break;
            case R.id.item_alter_password:
                break;
        }
    }

    private void clickUpdateHeadPhoto() {
        MediaSelector.MediaOptions mediaOptions = new MediaSelector.MediaOptions();
        mediaOptions.maxChooseMedia = 1;
        mediaOptions.isCrop = true;
        mediaOptions.isShowCamera = true;
        super.openPhotoDialog(mediaOptions);

    }

    private void clickUpdateSex() {
        SexDialog sexDialog = new SexDialog(this);
        sexDialog.setSexIndex(mUserBean.gender);
        sexDialog.show();
        sexDialog.setOnSelectorSexResult(sexType -> {
            mSexType = sexType;
            mPresenter.updateSex(sexType);
        });
    }


    @Override
    public void resultUpdateSex() {
        mUserBean.gender = mSexType;
        UserManger.get().putUser(mUserBean);
        setGender(mUserBean.gender);
    }

    @Override
    public void resultUpdateWeight() {
        mUserBean.weight = Integer.valueOf(mUpdateWeightBean.weight);
        UserManger.get().putUser(mUserBean);
        mItemWeight.mTvRight.setText(mUserBean.weight == 0 ? NO_SETTING : String.valueOf(mUserBean.weight).concat(" ").concat("kg"));
    }

    @Override
    public void resultUpdateHeight() {
        mUserBean.height = Integer.valueOf(mUpdateHeightBean.height);
        UserManger.get().putUser(mUserBean);
        mItemStature.mTvRight.setText(mUserBean.height == 0 ? NO_SETTING : String.valueOf(mUserBean.height).concat(" ").concat("cm"));
    }

    @Override
    public void resultUpdateBirthday() {
        mUserBean.birthday = mBirthdayBean.birthday;
        UserManger.get().putUser(mUserBean);
        mItemBirthday.mTvRight.setText(mUserBean.birthday);
    }

    @Override
    public void resultUpdateHeadPhoto(String headPhotoUrl) {
        mUserBean.headerImg = headPhotoUrl;
        UserManger.get().putUser(mUserBean);
    }


    @Override
    protected void resultAlbumResult(List<MediaSelectorFile> data) {
        uploadingImage2OSS(data.get(0).filePath);
        LogUtils.w("resultAlbumResult--", data.size() + "--");
    }

    @Override
    protected void resultCameraResult(File cameraFile) {
        uploadingImage2OSS(cameraFile.getAbsolutePath());
        LogUtils.w("resultAlbumResult---", cameraFile.getAbsolutePath() + "--");
    }

    private void uploadingImage2OSS(String filePath) {
        OSSRequestHelp.get().uploadingFile(filePath, new OSSRequestHelp.OnOSSResultListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onUpdate(long progressSize) {

            }

            @Override
            public void onSucceed(String path) {
                RequestUpdateHeadPhotoBean bean = new RequestUpdateHeadPhotoBean();
                bean.headerImg = path;
                mPresenter.updateHeadPhoto(bean);
                GlideManger.get().loadHeadImage(MyDetailsActivity.this, path, mCivHead);
            }

            @Override
            public void onFailure(String errorCode) {
                GlideManger.get().loadHeadImage(MyDetailsActivity.this, mUserBean.headerImg, mCivHead);
            }
        });
    }
}
