package com.work.guaishouxingqiu.aboutball.other;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.aliyun.clientinforeport.AlivcEventConfig;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.io.File;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.PUT;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 15:56
 * 更新时间: 2019/4/24 15:56
 * 描述: OSS上传帮助类
 */
public class OSSRequestHelp {
    public static final String BUCKET = "babymonster";
    public static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";

    private OSSToken mOSSToken;
    private Context mContext;

    public OSSRequestHelp(@NonNull Context context, @NonNull OSSToken ossToken) {
        this.mContext = context;
        //ossToken.accessKeyId = "LTAI2xtSmi1FyFDH";
       ossToken.accessKeyId = "LTAIjasr8hxJSy1n";
       // ossToken.accessKeySecret = "ZO1qaEbxctlJHz27p99xdXZTPhAtMI";
        ossToken.accessKeySecret = "IrtZLvnvTPKSaBJ1hLcYQuHxpEbbOb";
        this.mOSSToken = ossToken;

    }

    public String getObjectKey(String fileName) {
        return System.currentTimeMillis() + fileName;
    }

    /**
     * Oss上传文件
     *
     * @param filePath
     */
    public void uploadingFile(String filePath) {
        new Thread(){
            @Override
            public void run() {
                //super.run();

                File file = new File(filePath);
                if (!file.exists() || !file.isFile()) {
                    UIUtils.showToast("上传文件不能为空！");
                    return;
                }
                String ossObjectKey = getObjectKey(file.getName());
                PutObjectRequest request = new PutObjectRequest(OSSRequestHelp.BUCKET, ossObjectKey, file.getAbsolutePath());

       /* ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        request.setMetadata(metadata);*/
                //上传监听回调
                request.setProgressCallback((request1, currentSize, totalSize) -> {

                });
                OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("IrtZLvnvTPKSaBJ1hLcYQuHxpEbbOb",
                        "LTAIjasr8hxJSy1n", mOSSToken.securityToken);
                OSSClient ossClient = new OSSClient(mContext, OSSRequestHelp.ENDPOINT, credentialProvider);
                ossClient.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        LogUtils.w("onSuccess--", "上传成功啦！" + request.getObjectKey());
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                        LogUtils.w("onSuccess--", "上传失败啦！" + request.getObjectKey() + "--" + serviceException.getErrorCode() + "--"
                        + serviceException.getStatusCode());
                    }
                });
            }
        }.start();

    }

    public void asyncUploadingFile(String filePath) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                uploadingFile(filePath);
                emitter.onNext(filePath);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
