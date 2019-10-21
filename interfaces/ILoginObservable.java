package com.example.m_evolution.interfaces;

public interface ILoginObservable extends IBaseObservable{
    //通知View进行更新
    void sendCodeUpdate();
}
