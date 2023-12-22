package net.kehui.www.t_907_origin_V2.retrofit;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ELVICS-WORK
 * @date //GC20231220
 */
public class T907RetrofitUtils {
    private static T907RetrofitUtils instance = new T907RetrofitUtils();
    private Retrofit t907Retrofit;  //GC20231220
    private T907Service t907Service;  //GC20231220
    private Handler handlerRetrofit = new Handler(Looper.getMainLooper());

    private T907RetrofitUtils(){
        // 创建Retrofit对象,并生成实现接口类对象  //GC20231220
        t907Retrofit = new Retrofit.Builder().baseUrl("http://192.168.3.226:8080/T907Server/assist/")
                .addConverterFactory(GsonConverterFactory.create())         //添加转换器 //GC20231220
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())  //添加适配器
                .build();  //创建Retrofit对象
        t907Service = t907Retrofit.create(T907Service.class); //生成实现接口类对象t907Service

    }

    public static T907RetrofitUtils getInstance() {
        return instance;
    }

    public void doRetrofitGet(T907CallBackCustom t907CallBackCustom) {
        // 三、接口类对象调用对应方法获得响应  //GC20231216
        // t907Service.getAssist得到一个Call对象    //利用注解的接口，书写方法相对OkHttp更加简单，
        retrofit2.Call<ResponseBody> call = t907Service.get("11", "20231101", 0);   //协助记录获取1
        //预留换个接口方法，//协助记录获取2    //GC20231220
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {  //添加一重判断
                    String string = null;
                    try {
                        string = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //IO有同步操作，需要提取出来
                    String finalString = string;
                    handlerRetrofit.post(new Runnable() {
                        @Override
                        public void run() {
                            t907CallBackCustom.onSuccess(finalString);
                        }
                    });
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                //回调：请求失败
                handlerRetrofit.post(new Runnable() {
                    @Override
                    public void run() {
                        t907CallBackCustom.onThrowable(t);
                    }
                });
            }
        });

    }

}
