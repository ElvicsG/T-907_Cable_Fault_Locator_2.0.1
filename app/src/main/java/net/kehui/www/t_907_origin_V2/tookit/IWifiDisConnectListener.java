package net.kehui.www.t_907_origin_V2.tookit;

public interface IWifiDisConnectListener {

    //断开成功
    void onDisConnectSuccess();

    //断开失败
    void onDisConnectFail(String errorMsg);


}
