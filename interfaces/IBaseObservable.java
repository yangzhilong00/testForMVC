package com.example.m_evolution.interfaces;

public interface IBaseObservable {
    void addObserver(IBaseObserver observer);
    void deleteObserver(IBaseObserver observer);
}
