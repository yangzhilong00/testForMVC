package com.example.m_evolution.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.m_evolution.bean.HTTPBean.EasyHTTPBean;
import com.example.m_evolution.bean.HTTPBean.LoginHTTPResult;
import com.example.m_evolution.MyApp;
import com.example.m_evolution.R;
import com.example.m_evolution.controller.LoginController;
import com.example.m_evolution.interfaces.IBaseObservable;
import com.example.m_evolution.interfaces.ILoginObservable;
import com.example.m_evolution.interfaces.ILoginObserver;
import com.example.m_evolution.model.LoginModel;
import com.example.m_evolution.utils.SystemUtils;
import com.example.m_evolution.view.CloseFloatingRelativeLayout;

import org.greenrobot.eventbus.EventBus;

//import cn.smssdk.EventHandler;
//import cn.smssdk.SMSSDK;
import nsu.edu.com.library.SwipeBackActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends SwipeBackActivity implements View.OnClickListener, ILoginObserver {
    private MyApp myApp;

    //View
    private EditText mEtUserPhone;
    private TextView mTvSendCode;

    //Controller
    LoginController mController;

    //验证码等待时间
    private int mCodeWaitTime = 0;

    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        initData();
        setListener();
    }

    private void findView(){
        mTvSendCode = findViewById(R.id.button_send_code);
    }

    private void initData() {
        mController = new LoginController(this);
        myApp = (MyApp) getApplication();
    }

    private void setListener(){
        mTvSendCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(id == R.id.button_send_code){
            if(mCodeWaitTime>0) return ;
            String userPhone = mEtUserPhone.getText().toString();
            mController.sendCode(userPhone);
        }
    }

    @Override
    public void sendCodeUpdate(ILoginObservable observable) {
        if(observable instanceof LoginModel){
            myApp.makeToast("已发送验证码");
            //设置灰色，并提示倒计时
            mCodeWaitTime = 60;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if(mCodeWaitTime > 0){
                        mTvSendCode.setText("重新发送("+mCodeWaitTime+")");
                        mCodeWaitTime--;
                        mHandler.postDelayed(this, 1000);
                    }
                    else{
                        mTvSendCode.setText("发送验证码");
                    }
                }
            };
            mHandler.post(runnable);
        }
    }


    @Override
    public void onFail(IBaseObservable observable, String str) {
        if(observable instanceof LoginModel){
            myApp.makeToast(str);
        }
    }
}



