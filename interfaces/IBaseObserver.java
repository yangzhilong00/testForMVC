package com.example.m_evolution.interfaces;

public interface IBaseObserver {
    void onFail(IBaseObservable observable, String str);  //显示错误消息
}
