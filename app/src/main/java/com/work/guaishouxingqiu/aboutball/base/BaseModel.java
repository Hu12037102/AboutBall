package com.work.guaishouxingqiu.aboutball.base;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.BaseService;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.community.bean.RequestDynamicCommentsBean;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRedPointInfoBean;
import com.work.guaishouxingqiu.aboutball.http.RetrofitManger;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestSettingPasswordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultDynamicNotificationBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultFansFocusBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyMessageBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeLevelBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundCauseBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultSystemNotificationBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultWeiChatSingBean;
import com.work.guaishouxingqiu.aboutball.venue.VenueService;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestCreateBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultNotBookBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 10:53
 * 更新时间: 2019/3/6 10:53
 * 描述:
 */
public class BaseModel {

    protected RetrofitManger mRetrofitManger;


    public BaseModel() {

        if (mRetrofitManger == null) {
            mRetrofitManger = RetrofitManger.getDefault();
        }
    }

    public void loadOSSToken(BaseObserver<OSSToken> observer) {
        mRetrofitManger.create(MyService.class)
                .loadOSSToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadRefereeLevelData(BaseObserver<List<ResultRefereeLevelBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadRefereeLevelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void updateApkInfo(String version, BaseObserver<BaseDataBean<ResultUpdateApkBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .updateApkInfo(Contast.ANDROID, version)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void judgeRefereeStatus(BaseObserver<Integer> observer) {
        mRetrofitManger.create(MyService.class)
                .judgeRefereeStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    public void payWeiChatSing(long orderId, BaseObserver<BaseDataBean<ResultWeiChatSingBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .payWeiChatSing(orderId, "APP")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void loadRefundCauseList(BaseObserver<List<ResultRefundCauseBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .getRefundCause()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadTeamDetails(long teamId, BaseObserver<ResultBallDetailsBean> observer) {
        mRetrofitManger.create(MyService.class)
                .loadBallTeamDetails(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void joinTeam(long teamId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .joinTeam(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void postDynamicComments(RequestDynamicCommentsBean bean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .postDynamicComments(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getAttentionTweet(long concernId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .getAttentionTweet(concernId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getCancelAttentionTweet(long concernId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .getCancelAttentionTweet(concernId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void dynamicsDianZan(long tweetId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .dynamicsDianZan(tweetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void dynamicsCancelDianZan(long tweetId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .dynamicsCancelDianZan(tweetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void deleteDynamics(long tweetId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .deleteDynamics(tweetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadFansAndFocus(BaseObserver<ResultFansFocusBean> observer) {
        mRetrofitManger.create(BaseService.class)
                .loadFanFocus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void shareCommunityDynamic(long tweetId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .shareCommunityDynamic(tweetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadGameResultDetails(long gameId, BaseObserver<List<ResultGameDataResultBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadGameDataResult(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void sureUserOrder(long orderId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .sureUserOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void obtainRedPoint(BaseObserver<List<ResultRedPointInfoBean>> observer) {
        mRetrofitManger.create(BaseService.class)
                .getReadPoint()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getMyMessageList(BaseObserver<List<ResultMyMessageBean>> observer) {
        mRetrofitManger.create(BaseService.class)
                .getMyMessageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void getDynamicNotificationList(int noticeType, int pageNum, int pageSize, BaseObserver<List<ResultDynamicNotificationBean>> observer) {
        mRetrofitManger.create(BaseService.class)
                .getDynamicNotificationList(noticeType, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void getSystemNotificationList(int noticeType, int pageNum, int pageSize, BaseObserver<List<ResultSystemNotificationBean>> observer) {
        mRetrofitManger.create(BaseService.class)
                .getSystemNotificationList(noticeType, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void clearRedPoint(int noticeType, BaseObserver<String> observer) {
        mRetrofitManger.create(BaseService.class)
                .clearRedPoint(noticeType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void createPostBall(@NonNull RequestCreateBallBean ballBean,BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .createPostBall(ballBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void gainNotBooking(BaseObserver<List<ResultNotBookBean>> observer) {
        mRetrofitManger.create(BaseService.class)
                .gainNotBooking()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void editAboutBall(RequestCreateBallBean ballBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(VenueService.class)
                .editAboutBall(ballBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void settingPassword(RequestSettingPasswordBean bean,BaseObserver<LoginResultBean> observer) {
        mRetrofitManger.create(BaseService.class)
                .settingPassword(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
