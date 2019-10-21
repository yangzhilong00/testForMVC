package com.example.m_evolution.model;

import com.example.m_evolution.interfaces.IBaseModelAction;
import com.example.m_evolution.interfaces.IBaseObservable;
import com.example.m_evolution.interfaces.IBaseObserver;
import com.example.m_evolution.interfaces.ILoginObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseModels implements IBaseObservable, IBaseModelAction {
    private List<IBaseObserver> mObservers;

    public BaseModels() {
        mObservers = new ArrayList<>();
    }

    @Override
    public void addObserver(IBaseObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void deleteObserver(IBaseObserver observer) {
        if (mObservers == null || mObservers.size() == 0) {
            return;
        }
        mObservers.remove(observer);
    }

    //常用的函数：显示错误信息
    @Override
    public void onFail(String str) {
        for(IBaseObserver observer:mObservers){
            observer.onFail(this, str);
        }
    }

    public List<IBaseObserver> getObservers() {
        return mObservers;
    }
}
