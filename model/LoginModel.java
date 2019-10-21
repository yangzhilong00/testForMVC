package com.example.m_evolution.model;

import android.util.Log;

import com.example.m_evolution.bean.HTTPBean.EasyHTTPBean;
import com.example.m_evolution.interfaces.IBaseObserver;
import com.example.m_evolution.interfaces.ILoginObservable;
import com.example.m_evolution.interfaces.ILoginObserver;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.m_evolution.MyApp.httpAPI;

public class LoginModel extends BaseModels implements ILoginObservable {
    private String TAG = "LoginModel";

    public LoginModel(){

    }

    //进行网络请求：请求发送验证码
    public void sendCode(String userPhone){
        Log.d(TAG, "sendCode: ");
        if(userPhone.length() == 0){
            onFail("手机号未填写");
            return;
        }
        else if(userPhone.length()<11){
            onFail("手机号不正确");
            return;
        }
        //通过HTTP发送请求
        httpAPI.verify(userPhone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EasyHTTPBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.toString());
                        onFail("网络出错");
                    }

                    @Override
                    public void onNext(EasyHTTPBean easyHTTPBean) {
                        if(easyHTTPBean.getCode()==0 && easyHTTPBean.getIsOk().equals("1")){
                            sendCodeUpdate();
                        }
                        else{
                            onFail("操作失败: "+easyHTTPBean.getMsg());
                        }
                    }
                });
    }

    //通知View进行更新
    @Override
    public void sendCodeUpdate() {
        if (getObservers() == null || getObservers().size() == 0) {
            return;
        }
        for (IBaseObserver observer : getObservers()) {
            if(observer instanceof ILoginObserver){
                ((ILoginObserver)observer).sendCodeUpdate(this);
            }
        }
    }
}
