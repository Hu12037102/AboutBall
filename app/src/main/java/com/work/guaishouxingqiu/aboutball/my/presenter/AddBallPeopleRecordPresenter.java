package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.AddBallPeopleRecordContract;
import com.work.guaishouxingqiu.aboutball.my.model.AddBallPeopleRecordModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 17:25
 * 更新时间: 2019/6/4 17:25
 * 描述:添加球员记录P
 */
public class AddBallPeopleRecordPresenter extends BasePresenter<AddBallPeopleRecordContract.View,
        AddBallPeopleRecordModel> implements AddBallPeopleRecordContract.Presenter {
    public AddBallPeopleRecordPresenter(@NonNull AddBallPeopleRecordContract.View view) {
        super(view);
    }

    @Override
    protected AddBallPeopleRecordModel createModel() {
        return new AddBallPeopleRecordModel();
    }

    @Override
    public void start() {

    }
}
