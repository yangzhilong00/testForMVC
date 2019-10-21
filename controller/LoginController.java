package com.example.m_evolution.controller;

import com.example.m_evolution.activities.LoginActivity;
import com.example.m_evolution.model.LoginModel;


public class LoginController {
    private String TAG = "LoginController";

    private LoginModel mModel;
    private LoginActivity mActivity;


    public LoginController(LoginActivity activity){
        mModel = new LoginModel();
        mActivity = activity;
        mModel.addObserver(activity);
    }

    public void sendCode(String userPhone){
        //让model进行网络请求
        mModel.sendCode(userPhone);
    }
}
