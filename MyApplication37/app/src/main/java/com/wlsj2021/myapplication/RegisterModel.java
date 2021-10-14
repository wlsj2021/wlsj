package com.wlsj2021.myapplication;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterModel {
    @Provides
     Presenter mPresenter(){
        return new Presenter();
    };
}
