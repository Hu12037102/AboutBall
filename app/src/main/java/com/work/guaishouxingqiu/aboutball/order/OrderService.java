package com.work.guaishouxingqiu.aboutball.order;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.order.bean.ResultOrderDetailsBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 13:58
 * 更新时间: 2019/9/12 13:58
 * 描述:
 */
public interface OrderService {
    @GET(IApiService.POST_ORDER_DETAILS)
    Observable<BaseBean<ResultOrderDetailsBean>> loadOrderDetails(@Query(Contast.SPU_ID) long souId,
                                                                  @Query(Contast.PARAMS) String params,
                                                                  @Query(Contast.NUM) int num);
}
