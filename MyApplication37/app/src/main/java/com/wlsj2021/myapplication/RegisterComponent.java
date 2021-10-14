package com.wlsj2021.myapplication;

import dagger.Component;

@Component(modules = RegisterModel.class)
public interface RegisterComponent {
    void inject(MainActivity mainActivity);
}
