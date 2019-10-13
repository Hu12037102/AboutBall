package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.contract.MessageNotificationContract;
import com.ifeell.app.aboutball.my.model.MessageNotificationModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/1 9:42
 * 更新时间: 2019/8/1 9:42
 * 描述:消息通知详情通知P
 */
public class MessageNotificationPresenter extends BasePresenter<MessageNotificationContract.View,
        MessageNotificationModel> implements MessageNotificationContract.Presenter {
    public MessageNotificationPresenter(@NonNull MessageNotificationContract.View view) {
        super(view);
    }

    @Override
    protected MessageNotificationModel createModel() {
        return new MessageNotificationModel();
    }

    @Override
    public void start() {

    }
}
