package com.work.guaishouxingqiu.aboutball.other;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.ImagePersistRequest;
import com.alibaba.sdk.android.oss.model.ListObjectsRequest;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.PutSymlinkRequest;
import com.work.guaishouxingqiu.aboutball.BuildConfig;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 15:56
 * 更新时间: 2019/4/24 15:56
 * 描述: OSS上传帮助类
 */
public class OSSRequestHelp {
    private static final String BUCKET = "babymonster";
    //public static final String BUCKET = "huxiaobai-erhu";
    private static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
    private static final String ENDPOINT_BODY = "oss-cn-shenzhen.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIjasr8hxJSy1n";
    private static final String ACCESS_KEY_SECRET = "IrtZLvnvTPKSaBJ1hLcYQuHxpEbbOb";


    private static OSSRequestHelp mOSSRequestHelp;
    private ClientConfiguration mRequestConfig;
    private ObjectMetadata mMetadata;
    private OSSCustomSignerCredentialProvider mCredentialProvider;
    private Handler mMainHandler;
    private OSSClient mOssClient;
    private List<File> mFileData;
    private List<String> mResultPathData;

    private OSSRequestHelp() {
        init();
    }

    public static OSSRequestHelp get() {
 /*       synchronized (OSSRequestHelp.class) {
            if (mOSSRequestHelp == null) {
                synchronized (OSSRequestHelp.class) {
                    mOSSRequestHelp = new OSSRequestHelp();
                }
            }
        }
        return mOSSRequestHelp;*/
        mOSSRequestHelp = new OSSRequestHelp();
        return mOSSRequestHelp;
    }

    private void init() {
        initConfig();
        initProvider();
        initHandler();
        initClient();


    }

    private void initClient() {
        mOssClient = new OSSClient(UIUtils.getContext(), OSSRequestHelp.ENDPOINT, mCredentialProvider, mRequestConfig);

    }

    private void initHandler() {
        mMainHandler = new Handler(Looper.getMainLooper());

    }

    private void initConfig() {
        mRequestConfig = new ClientConfiguration();
        mRequestConfig.setConnectionTimeout(15 * 1000); // connction time out default 15s
        mRequestConfig.setSocketTimeout(15 * 1000); // socket timeout，default 15s
        mRequestConfig.setMaxConcurrentRequest(5); // synchronous request number，default 5
        mRequestConfig.setMaxErrorRetry(2); // retry，default 2
        if (BuildConfig.IS_DEBUG) {
            OSSLog.enableLog();
        }

        mMetadata = new ObjectMetadata();
        mMetadata.setContentType("application/octet-stream");
    }

    private void initProvider() {
        mCredentialProvider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                return OSSUtils.sign(ACCESS_KEY_ID, ACCESS_KEY_SECRET, content);
            }
        };
    }


    /**
     * Oss上传文件
     *
     * @param filePath
     */
    public void uploadingFile(String filePath, OnOSSResultListener listener, BaseActivity activity) {

        //super.run();
        if (filePath == null) {
            UIUtils.showToast("上传文件不能为空！");
            return;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            UIUtils.showToast("上传文件不能为空！");
            return;
        }
        if (listener != null) {
            listener.onStart();
        }

        //  String ossObjectKey = getObjectKey(file.getName());
        PutObjectRequest request = new PutObjectRequest(OSSRequestHelp.BUCKET, file.getName(), file.getAbsolutePath());
        request.setCRC64(OSSRequest.CRC64Config.YES);
        request.setMetadata(mMetadata);
        //上传监听回调
        request.setProgressCallback((request1, currentSize, totalSize) -> {
            if (listener != null) {
                listener.onUpdate((long) ((double) currentSize / (double) totalSize * 100L));
            }
        });

        //provide签名
        /* OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("LTAIjasr8hxJSy1n","IrtZLvnvTPKSaBJ1hLcYQuHxpEbbOb", "");*/
        // provide签名
        /*  OSSAuthCredentialsProvider credentialProvider = new OSSAuthCredentialsProvider("https://oss-cn-shenzhen.aliyuncs.com")*/
        // provide签名阿里自带的签名
        mOssClient.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (activity != null && !activity.isFinishing()) {
                    //拼接阿里路径
                    String ossFilePath = "https://" + OSSRequestHelp.BUCKET.concat(".").concat(OSSRequestHelp.ENDPOINT_BODY).concat("/")
                            .concat(request.getObjectKey());
                    LogUtils.w("onSuccess--", "上传成功啦！" + ossFilePath + "--" + Thread.currentThread().getName());
                    FileUtils.removeFile(file);
                    mMainHandler.post(() -> {
                        if (listener != null) {
                            listener.onSucceed(ossFilePath);
                        }
                    });
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
               /* LogUtils.w("onSuccess--", "上传失败啦！" + request.getObjectKey() + "--" + serviceException.getErrorCode() + "--"
                        + serviceException.getStatusCode());*/
                if (activity != null && !activity.isFinishing()) {
                    mMainHandler.post(() -> {
                        UIUtils.showToast(R.string.uploading_fail_again);
                        if (listener != null) {
                            String errCode = "上传失败";
                            if (serviceException != null) {
                                errCode = serviceException.getErrorCode();
                            } else {
                                if (clientException != null) {
                                    errCode = clientException.getMessage();
                                }
                            }
                            listener.onFailure(errCode);
                        }
                    });
                }

            }
        });
    }

    public List<File> checkFiles(List<String> filePathData) {
        List<File> files = new ArrayList<>();
        if (filePathData == null || filePathData.size() == 0) {
            UIUtils.showToast("上传文件不能为空！");
            return files;
        }
        for (String path : filePathData) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                files.add(file);
            } else {
                UIUtils.showToast("上传文件不能为空！");
            }
        }
        return files;
    }

    public void uploadingFiles(List<String> filePathData, OnOSSDataResultListener listener, BaseActivity activity) {
        if (mFileData == null) {
            mFileData = checkFiles(filePathData);
            if (mFileData.size() == 0) {
                return;
            }
            mResultPathData = new ArrayList<>();
            if (listener != null) {
                listener.onStart();
            }
        }
        if (mFileData.size() == 0) {
            UIUtils.showToast("上传文件不能为空！");
            return;
        }
        File file = mFileData.get(0);
        if (!file.exists() || !file.isFile()) {
            UIUtils.showToast("没有获取到文件！");
            return;
        }
        PutObjectRequest request = new PutObjectRequest(OSSRequestHelp.BUCKET, file.getName(), file.getAbsolutePath());
        request.setCRC64(OSSRequest.CRC64Config.YES);
        request.setMetadata(mMetadata);
        //上传监听回调
        request.setProgressCallback((request1, currentSize, totalSize) -> {

        });
        mOssClient.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (activity != null && !activity.isFinishing()) {
                    mMainHandler.post(() -> {
                        String ossFilePath = "https://" + OSSRequestHelp.BUCKET.concat(".").concat(OSSRequestHelp.ENDPOINT_BODY).concat("/")
                                .concat(request.getObjectKey());
                        mResultPathData.add(ossFilePath);
                        filePathData.remove(0);
                        mFileData.remove(0);
                        FileUtils.removeFile(file);
                        if (filePathData.size() == 0) {

                            if (listener != null) {
                                listener.onSucceed(mResultPathData);
                            }
                        } else {
                            uploadingFiles(filePathData, listener, activity);
                        }
                    });
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (activity != null && !activity.isFinishing()) {
                    mMainHandler.post(() -> {
                        UIUtils.showToast(R.string.uploading_fail_again);
                        if (listener != null) {
                            String errCode = "上传失败";
                            if (serviceException != null) {
                                errCode = serviceException.getErrorCode();
                            } else {
                                if (clientException != null) {
                                    errCode = clientException.getMessage();
                                }
                            }
                            listener.onFailure(errCode);
                        }
                    });
                }
            }
        });

    }


    public void uploadingFiles(List<String> filePathData, OnOSSDataResultListener listener, boolean showLoadingView, BaseActivity activity) {
        if (mFileData == null) {
            mFileData = checkFiles(filePathData);
            if (mFileData.size() == 0) {
                return;
            }
            mResultPathData = new ArrayList<>();
            if (listener != null) {
                listener.onStart();
            }
        }
        if (mFileData.size() == 0) {
            UIUtils.showToast("上传文件不能为空！");
            return;
        }
        File file = mFileData.get(0);
        if (!file.exists() || !file.isFile()) {
            UIUtils.showToast("没有获取到文件！");
            return;
        }
        PutObjectRequest request = new PutObjectRequest(OSSRequestHelp.BUCKET, file.getName(), file.getAbsolutePath());
        request.setCRC64(OSSRequest.CRC64Config.YES);
        request.setMetadata(mMetadata);
        //上传监听回调
        request.setProgressCallback((request1, currentSize, totalSize) -> {

        });
        if (activity != null && !activity.isFinishing() && showLoadingView) {
            activity.showLoadingView();
        }
        mOssClient.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (activity != null && !activity.isFinishing()) {
                    mMainHandler.post(() -> {

                        String ossFilePath = "https://" + OSSRequestHelp.BUCKET.concat(".").concat(OSSRequestHelp.ENDPOINT_BODY).concat("/")
                                .concat(request.getObjectKey());
                        mResultPathData.add(ossFilePath);
                        filePathData.remove(0);
                        mFileData.remove(0);
                        FileUtils.removeFile(file);
                        if (filePathData.size() == 0) {
                            if (showLoadingView) {
                                activity.dismissLoadingView();
                            }
                            if (listener != null) {
                                listener.onSucceed(mResultPathData);
                            }
                        } else {
                            uploadingFiles(filePathData, listener, showLoadingView, activity);
                        }
                    });
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (activity != null && !activity.isFinishing()) {
                    mMainHandler.post(() -> {
                        if (showLoadingView) {
                            activity.dismissLoadingView();
                        }
                        UIUtils.showToast(R.string.uploading_fail_again);
                        if (listener != null) {
                            String errCode = "上传失败";
                            if (serviceException != null) {
                                errCode = serviceException.getErrorCode();
                            } else {
                                if (clientException != null) {
                                    errCode = clientException.getMessage();
                                }
                            }
                            listener.onFailure(errCode);
                        }
                    });
                }
            }
        });

    }


    public interface OnOSSResultListener {
        void onStart();

        void onUpdate(long progressSize);

        void onSucceed(String path);

        void onFailure(String errorCode);
    }

    public interface OnOSSDataResultListener {
        void onStart();

        // void onUpdate(long progressSize);

        void onSucceed(List<String> pathData);

        void onFailure(String errorCode);
    }
}
