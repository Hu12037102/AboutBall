package com.work.guaishouxingqiu.aboutball.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.permission.imp.OnPermissionsResult;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 11:39
 * 更新时间: 2019/4/9 11:39
 * 描述:
 */
public abstract class CameraFragment<P extends BasePresenter> extends BaseFragment<P> {
    protected void openScanCode() {
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
                    }
                    LogUtils.w("onActivityResult--", result);
                    //Toasts.with().showToast(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toasts.with().showToast("解析失败！");
                }
                break;
        }
    }


}
