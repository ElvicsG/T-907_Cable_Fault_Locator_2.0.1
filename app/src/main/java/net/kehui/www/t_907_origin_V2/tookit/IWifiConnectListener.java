package net.kehui.www.t_907_origin_V2.tookit;

public interface IWifiConnectListener {
    //开始连接
    void onConnectStart();

    // 连接成功
    void onConnectSuccess();

    //连接失败
    void onConnectFail(String errorMsg);

}
