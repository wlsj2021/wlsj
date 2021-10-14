package com.wlsj2021.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.RxActivity;
import com.wlsj2021.myapplication.net.HomeModel;
import com.wlsj2021.myapplication.net.NetServiceApi;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends RxActivity {

    @BindView(R.id.main_tv)
    TextView mainTv;
    @BindView(R.id.button)
    Button button;
    @Inject
    Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DaggerRegisterComponent.builder().build().inject(this);

        mPresenter.see();

        final RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity or Fragment instance
        rxPermissions
                .request(
                        Manifest.permission.INTERNET)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                    }
                });
    }

    private void getNet() {

        //okHttp的客户端
        OkHttpClient okHttpClient =
                new OkHttpClient()
                        .newBuilder()
//                        //自定义拦截器
//                        .addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//
//                return null;
//            }
//        })
            .build();
        //retrofit客户端
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                //rxjava2
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        NetServiceApi netServiceApi = retrofit.create(NetServiceApi.class);

        netServiceApi.getHomePage(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<HomeModel>bindToLifecycle())
                .subscribe(new Subscriber<HomeModel>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG", "onCompleted: ", null);

                    }

                    @Override
                    public void onError(Throwable e) {
                    e.printStackTrace();
                    }

                    @Override
                    public void onNext(HomeModel homeModel) {
                        Log.e("TAG", "onNext: "+homeModel.getData().getTotal(), null);

                    }
                });

//        Call<HomeModel> homePage = service.getHomePage(0);
//        homePage.enqueue(new Callback<HomeModel>() {
//            @Override
//            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
//                Log.e("TAG", "onResponse: "+response,null );
//            }
//
//            @Override
//            public void onFailure(Call<HomeModel> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }

    public void btn(View view) {
        ARouter.getInstance().build("/application/login")
                .withString("key", "username")
                .withBoolean("key1", false)
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {

                    }

                    @Override
                    public void onArrival(Postcard postcard) {

                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });

    }

    @OnClick(R.id.main_tv)
    public void onClick() {
        mainTv.setText("好用");
        getNet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}