package com.example.m_evolution.interfaces;

public interface ILoginObserver extends IBaseObserver{
    void sendCodeUpdate(ILoginObservable observable);
}
