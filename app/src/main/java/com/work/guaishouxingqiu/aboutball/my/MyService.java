package com.work.guaishouxingqiu.aboutball.my;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestEditAddressBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestNewAddressBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateBirthdayBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateHeightBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateNameBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateSexBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateWeightBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyAddress;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultPrizeBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.my
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/3/28
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public interface MyService {
    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountSex(@Body RequestUpdateNameBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountWeight(@Body RequestUpdateWeightBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountSex(@Body RequestUpdateSexBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountHeight(@Body RequestUpdateHeightBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountBirthday(@Body RequestUpdateBirthdayBean bean);

    @GET(IApiService.UPDATE_APK_INFO)
    Observable<BaseBean<BaseDataBean<ResultUpdateApkBean>>> updateApkInfo(@Query(Contast.PHONE_MODEL) String phoneModel,
                                                                          @Query(Contast.VERSION) String version);

    @GET(IApiService.GET_MY_PRIZE)
    Observable<BaseBean<ResultPrizeBean>> getMyPrize(@Query(Contast.STATS) int state);

    @POST(IApiService.POST_EDIT_ADDRESS)
    Observable<BaseBean<BaseDataBean>> postNewAddress(@Body RequestNewAddressBean bean);

    @POST(IApiService.POST_EDIT_ADDRESS)
    Observable<BaseBean<BaseDataBean>> postEditAddress(@Body RequestEditAddressBean bean);

    @GET(IApiService.GET_MY_ADDRESS)
    Observable<BaseBean<ResultMyAddress>> getMyAddress();

    @GET(IApiService.GET_HAS_ADDRESS)
    Observable<BaseBean<String>> hasAddress();
}
