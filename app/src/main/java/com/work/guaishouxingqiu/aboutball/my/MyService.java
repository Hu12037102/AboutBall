package com.work.guaishouxingqiu.aboutball.my;

import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateNameBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateSexBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
