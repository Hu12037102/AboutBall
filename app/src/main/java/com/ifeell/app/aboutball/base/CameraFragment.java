package com.ifeell.app.aboutball.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.permission.imp.OnPermissionsResult;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.HintDialog;
import com.ifeell.app.aboutball.weight.Toasts;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 11:39
 * 更新时间: 2019/4/9 11:39
 * 描述:
 */
public abstract class CameraFragment<P extends BasePresenter> extends BaseFragment<P> {
    protected void openScanCode() {
        DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0109);
        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> allowPermissions) {
                startActivityForResult(new Intent(mContext, CaptureActivity.class), ARouterIntent.REQUEST_CODE);
            }

            @Override
            public void onNoAllow(List<String> noAllowPermissions) {
                HintDialog hintDialog = new HintDialog.Builder(mContext)
                        .setTitle(R.string.hint)
                        .setBody(R.string.camera_is_must_permission)
                        .setSure(R.string.sure).builder();
                hintDialog.show();
                hintDialog.setOnItemClickListener(view -> {
                    hintDialog.dismiss();
                    openScanCode();
                });
            }

            @Override
            public void onForbid(List<String> noForbidPermissions) {
                showForbidPermissionDialog();
            }
        }, Manifest.permission.CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ARouterIntent.REQUEST_CODE:
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result != null) {
                        UIUtils.parseScanCode(result);
                        resultScanCode(result);
                    }

                    LogUtils.w("onActivityResult--", result);//15681673146651413
                    //Toasts.with().showToast(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toasts.with().showToast("解析失败！");
                }
                break;
        }
    }

    protected void resultScanCode(String scanCode) {
    }

}
