package com.ifeell.app.aboutball.permission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Button;


import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.permission.imp.OnPermissionsResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: IntentUtils
 * @Author: 胡庆岭
 * @CreateTime: 2019/3/4 13:14
 * @UpdateTime: 2019/3/4 13:14
 * @Description:
 */

public abstract class PermissionActivity<P extends BasePresenter> extends BaseActivity<P> {
    private AlertDialog mForbidDialog;
    private static final int REQUEST_CODE = 100;
    private  List<String> mAllowList = new ArrayList<>();
    private  List<String> mNoAllowList = new ArrayList<>();
    private  List<String> mForbidList = new ArrayList<>();
    private OnPermissionsResult mOnPermissionsResult;
    private String[] mPermissions;

    protected void requestPermission(@NonNull OnPermissionsResult onPermissionsResult, @NonNull String... permissions) {
        this.mPermissions = permissions;
        this.mOnPermissionsResult = onPermissionsResult;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> requestList = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    requestList.add(permission);
                }
            }
            if (requestList.size() > 0) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
            } else {
                if (mOnPermissionsResult != null ) {
                    mOnPermissionsResult.onAllow(Arrays.asList(permissions));
                }
            }
        } else {
            mOnPermissionsResult.onAllow(Arrays.asList(permissions));
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (permissions.length == grantResults.length) {
                    clearPermission();

                    for (int i = 0; i < grantResults.length; i++) {

                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            mAllowList.add(permissions[i]);
                        } else {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                                mNoAllowList.add(permissions[i]);
                            } else {
                                mForbidList.add(permissions[i]);
                            }
                        }
                    }
                    if (mAllowList.size() == permissions.length) {
                        if (mOnPermissionsResult != null) {
                            //全部同意
                            mOnPermissionsResult.onAllow(mAllowList);
                        }

                    } else {
                        if (mForbidList.size() > 0) {
                            if (mOnPermissionsResult != null) {
                                //全部永久禁止或者部分永久禁止
                                mOnPermissionsResult.onForbid(mForbidList);
                            }
                        } else {
                            if (mOnPermissionsResult != null) {
                                //全部拒绝
                                mOnPermissionsResult.onNoAllow(mNoAllowList);
                            }
                        }
                    }

                }
                break;
            default:
                break;
        }
    }

    private void clearPermission() {
        mAllowList.clear();
        mNoAllowList.clear();
        mForbidList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearPermission();
    }

    protected void showForbidPermissionDialog() {
        if (mForbidDialog == null) {
            mForbidDialog = new AlertDialog.Builder(this).setTitle("权限被禁止")
                    .setMessage("需要获取权限，否则无法正常使用功能；设置路径：设置-应用-权限")
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .setCancelable(false).create();
            mForbidDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positionButton = mForbidDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negativeButton = mForbidDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    positionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IntentUtils.openActivityApplyCenter(PermissionActivity.this);

                        }
                    });
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mForbidDialog.dismiss();
                            finish();
                        }
                    });

                }
            });
            mForbidDialog.show();
        } else if (!mForbidDialog.isShowing()) {
            mForbidDialog.show();
        }
    }

    protected void dismissForbidPermissionDialog() {
        if (mForbidDialog != null && mForbidDialog.isShowing()) {
            mForbidDialog.dismiss();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentUtils.OPEN_APPLY_CENTER_CODE && resultCode == 0) {
            if (mPermissions != null && mPermissions.length > 0 && mOnPermissionsResult != null) {
                requestPermission(mOnPermissionsResult, mPermissions);
                dismissForbidPermissionDialog();
                // getActivity().recreate();
            }
        }
    }
}
