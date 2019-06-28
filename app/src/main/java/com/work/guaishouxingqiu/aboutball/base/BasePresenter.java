package com.work.guaishouxingqiu.aboutball.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseModelCallback;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.community.bean.RequestDynamicCommentsBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultFansFocusBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeLevelBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundCauseBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultWeiChatSingBean;
import com.work.guaishouxingqiu.aboutball.other.SharedPreferencesHelp;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:28
 * 更新时间: 2019/3/4 13:28
 * 描述:
 */
public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter, IBaseModelCallback {
    public int mPageNum = Contast.DEFAULT_PAGE_NUM;
    public int mPageSize = Contast.DEFAULT_PAGE_SIZE;
    public boolean isRefresh = true;
    protected CompositeDisposable mCompositeDisposable;
    public V mView;
    protected M mModel;
    private HintDialog mUpdateDialog;


    protected abstract M createModel();

    public BasePresenter(@NonNull V view) {
        mView = DataUtils.checkData(view);
        mCompositeDisposable = new CompositeDisposable();
        mModel = createModel();


    }

    public void loadOssToken() {
        mModel.loadOSSToken(new BaseObserver<>(true, this, new BaseObserver.Observer<OSSToken>() {
            @Override
            public void onNext(BaseBean<OSSToken> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultOSSToken(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    private void defaultShowResult(BaseBean baseBean) {
        if (mView != null) {
            mView.showToast(baseBean.message);
        }
    }

    @Override
    public void deathPresenter() {


        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        if (mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear();
        }

        if (mView != null) {
            mView = null;
        }
        if (mModel != null) {
            mModel = null;
        }
    }


    public void loadRefereeLevelData() {
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        String refereeJson = sp.getString(SharedPreferencesHelp.KEY_REFEREE_CACHE_LIST);
        if (DataUtils.isEmpty(refereeJson)) {
            mModel.loadRefereeLevelData(new BaseObserver<>(this, new BaseObserver.Observer<List<ResultRefereeLevelBean>>() {
                @Override
                public void onNext(BaseBean<List<ResultRefereeLevelBean>> t) {
                    sp.putObject(SharedPreferencesHelp.KEY_REFEREE_CACHE_LIST, new Gson().toJson(t.result));
                    LogUtils.w("SharedPreferencesHelp--", new Gson().toJson(t.result));
                    mView.resultLevelData(t.result);
                }

                @Override
                public void onError(Throwable e) {

                }
            }));
        } else {
            LogUtils.w("SharedPreferencesHelp---", refereeJson);
            Gson gson = new Gson();
            List<ResultRefereeLevelBean> data = gson.fromJson(refereeJson, new TypeToken<List<ResultRefereeLevelBean>>() {
            }.getType());
            mView.resultLevelData(data);
        }
    }

    public void updateApkInfo(String version) {

        mModel.updateApkInfo(version, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<ResultUpdateApkBean>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<ResultUpdateApkBean>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result.code == IApi.Code.SUCCEED && t.result.result != null) {
                    mView.resultApkInfo(t.result.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, false));
    }

    public void judgeRefereeStatus() {
        mModel.judgeRefereeStatus(new BaseObserver<>(true, this, new BaseObserver.Observer<Integer>() {
            @Override
            public void onNext(BaseBean<Integer> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultRefereeStatus(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, false));
    }

    public void payWeiChatSing(long orderId) {
        mModel.payWeiChatSing(orderId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<ResultWeiChatSingBean>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<ResultWeiChatSingBean>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t) && t.result.result != null) {
                    mView.resultWeiChatSing(t.result.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, false));
    }

    public void loadRefundCauseList() {
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        String refundJson = sp.getString(SharedPreferencesHelp.KEY_REFUND_CAUSE_LIST);
        if (DataUtils.isEmpty(refundJson)) {
            mModel.loadRefundCauseList(new BaseObserver<>(this, new BaseObserver.Observer<List<ResultRefundCauseBean>>() {
                @Override
                public void onNext(BaseBean<List<ResultRefundCauseBean>> t) {
                    if (DataUtils.isResultSure(t) && t.result.size() > 0) {
                        sp.putObject(SharedPreferencesHelp.KEY_REFUND_CAUSE_LIST, new Gson().toJson(t.result));
                        mView.resultRefundCauseData(t.result);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            }));
        } else {
            Gson gson = new Gson();
            List<ResultRefundCauseBean> data = gson.fromJson(refundJson, new TypeToken<List<ResultRefundCauseBean>>() {
            }.getType());
            mView.resultRefundCauseData(data);
        }
    }

    public void loadTeamDetails(long mTeamId) {
        mModel.loadTeamDetails(mTeamId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultBallDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultBallDetailsBean> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultTeamDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    public void joinTeam(long teamId) {
        mModel.joinTeam(teamId, new BaseObserver<>(this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultJoinTeamSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    /**
     * 动态评论
     *
     * @param bean 请求动态评论bean
     */
    public void postDynamicComments(RequestDynamicCommentsBean bean) {
        mModel.postDynamicComments(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultDynamicCommentsSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    public void getAttentionTweet(int position, long concernId) {
        mModel.getAttentionTweet(concernId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultAttentionTweetStatus(position);
                    UserManger.get().putFollowCount(UserManger.get().getFollowCount() + 1);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    public void getCancelAttentionTweet(int position, long concernId) {
        mModel.getCancelAttentionTweet(concernId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultAttentionTweetStatus(position);
                    int followCount = UserManger.get().getFollowCount();
                    if (followCount > 0) {
                        followCount -= 1;
                        UserManger.get().putFollowCount(followCount);
                    } else {
                        UserManger.get().putFollowCount(0);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    public void dynamicsDianZan(long tweetId, int position) {
        mModel.dynamicsDianZan(tweetId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultDianZanStatus(position);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    public void dynamicsCancelDianZan(long tweetId, int position) {
        mModel.dynamicsCancelDianZan(tweetId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultDianZanStatus(position);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    public void deleteDynamics(long tweetId, int position) {
        mModel.deleteDynamics(tweetId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultDeleteDynamicSucceed(position);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    public void loadFansAndFocus() {
        mModel.loadFansAndFocus(new BaseObserver<>(true, this, new BaseObserver.Observer<ResultFansFocusBean>() {
            @Override
            public void onNext(BaseBean<ResultFansFocusBean> t) {
                if (DataUtils.isResultSure(t)) {
                    UserManger.get().putFollowCount(t.result.followCount);
                    UserManger.get().putFansCount(t.result.fansCount);
                    mView.resultFansFocus(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    public void shareCommunityDynamic(long tweetId) {
        mModel.shareCommunityDynamic(tweetId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultShareCommunityDynamic();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
