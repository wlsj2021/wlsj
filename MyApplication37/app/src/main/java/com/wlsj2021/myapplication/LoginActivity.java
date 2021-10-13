package com.wlsj2021.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/application/login")
public class LoginActivity extends AppCompatActivity {
   @Autowired
    public String key;
   @Autowired
   public boolean key1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ARouter.getInstance().inject(this);

        Log.e("TAG", "onCreate: "+key+key1, null);
    }
}