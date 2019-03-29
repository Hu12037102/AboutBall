package com.work.guaishouxingqiu.aboutball.login;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RegisterResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestLoginBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestRegisterBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateSexBean;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 14:26
 * 更新时间: 2019/3/8 14:26
 * 描述:
 */
public interface LoginService {
    @POST(IApiService.LOGIN)
    Observable<BaseBean<LoginResultBean>> login(@Body RequestLoginBean requestLoginBean);

    @GET(IApiService.VERIFICATION_CODE)
    Observable<BaseBean> sendMessageCode(@Query(Contast.PHONE) String phone, @Query(Contast.TYPE) int type);

    @GET(IApiService.USER_ACCOUNT)
    Observable<BaseBean<UserBean>> userAccount();
    @GET(IApiService.USER_ACCOUNT_INFO)
    Observable<BaseBean<UserBean>> userAccountInfo();

    @POST(IApiService.REGISTER)
    Observable<BaseBean<RegisterResultBean>> register(@Body RequestRegisterBean bean);


}
