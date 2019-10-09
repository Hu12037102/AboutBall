package com.ifeell.app.aboutball.base;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.BaseService;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.bean.OSSToken;
import com.ifeell.app.aboutball.base.bean.RequestSureOrderBean;
import com.ifeell.app.aboutball.base.bean.ResultSureOrderDialogBean;
import com.ifeell.app.aboutball.community.bean.RequestDynamicCommentsBean;
import com.ifeell.app.aboutball.game.GameService;
import com.ifeell.app.aboutball.game.bean.ResultGameDataResultBean;
import com.ifeell.app.aboutball.home.bean.ResultRedPointInfoBean;
import com.ifeell.app.aboutball.http.RetrofitManger;
import com.ifeell.app.aboutball.login.bean.LoginResultBean;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestSettingPasswordBean;
import com.ifeell.app.aboutball.my.bean.ResultBallDetailsBean;
import com.ifeell.app.aboutball.my.bean.ResultDynamicNotificationBean;
import com.ifeell.app.aboutball.my.bean.ResultFansFocusBean;
import com.ifeell.app.aboutball.my.bean.ResultMyMessageBean;
import com.ifeell.app.aboutball.my.bean.ResultRefereeLevelBean;
import com.ifeell.app.aboutball.my.bean.ResultRefundCauseBean;
import com.ifeell.app.aboutball.my.bean.ResultSystemNotificationBean;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;
import com.ifeell.app.aboutball.my.bean.ResultWeiChatSingBean;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.RequestCreateBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestVenueListBean;
import com.ifeell.app.aboutball.venue.bean.ResultNotBookBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;

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
    public void payTicketsWeiChatSing(long orderId, BaseObserver<BaseDataBean<ResultWeiChatSingBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .payTicketsWeiChatSing(orderId, "APP")
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

    public void createPostBall(@NonNull RequestCreateBallBean ballBean, BaseObserver<BaseDataBean<String>> observer) {
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

    public void settingPassword(RequestSettingPasswordBean bean, BaseObserver<LoginResultBean> observer) {
        mRetrofitManger.create(BaseService.class)
                .settingPassword(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void loadCanUserVenueList(int pageNum, int pageSize, RequestVenueListBean bean, BaseObserver<List<ResultVenueData>> observer) {
        mRetrofitManger.create(BaseService.class)
                .getCanUserVenueList(pageNum, pageSize, bean.typeId, bean.longitude, bean.latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void newsSellingPoints(long newsId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(BaseService.class)
                .newsSellingPoints(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getSureOrderDialog(RequestSureOrderBean bean, BaseObserver<ResultSureOrderDialogBean> observer) {
        mRetrofitManger.create(BaseService.class)
                .getSureOrderDialog(bean.spuId,bean.params,bean.num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void getCheckOutOrderStatus(long orderId,BaseObserver<Integer> observer){
        mRetrofitManger.create(BaseService.class)
                .getCheckOutOrderStatus(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
