package net.kehui.www.t_907_origin_V2.retrofit;

/**
 * 自定义异步请求接口    //GC20231220
 */
public interface T907CallBackCustom {
    void onSuccess(String result);
    void onError(Exception e);
    void onThrowable(Throwable t);  //GC20231230
}
