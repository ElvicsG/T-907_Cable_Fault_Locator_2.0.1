package net.kehui.www.t_907_origin_V2;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PatternMatcher;
import android.util.Log;
import android.widget.Toast;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import net.kehui.www.t_907_origin_V2.application.AppConfig;
import net.kehui.www.t_907_origin_V2.base.BaseActivity;
import net.kehui.www.t_907_origin_V2.application.Constant;
import net.kehui.www.t_907_origin_V2.demo1.WifiAutoConnectManager;
import net.kehui.www.t_907_origin_V2.thread.ConnectThread;
import net.kehui.www.t_907_origin_V2.thread.ProcessThread;
import net.kehui.www.t_907_origin_V2.tookit.IWifiDisConnectListener;
import net.kehui.www.t_907_origin_V2.util.StateUtils;
import net.kehui.www.t_907_origin_V2.util.WifiUtil;
import net.kehui.www.t_907_origin_V2.view.ModeActivity;
import net.kehui.www.t_907_origin_V2.tookit.IWifiConnectListener;
import net.kehui.www.t_907_origin_V2.tookit.WifiManagerProxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static net.kehui.www.t_907_origin_V2.view.ModeActivity.BUNDLE_MODE_KEY;
import static net.kehui.www.t_907_origin_V2.view.ModeActivity.BUNDLE_COMMAND_KEY;
import static net.kehui.www.t_907_origin_V2.view.ModeActivity.BUNDLE_DATA_TRANSFER_KEY;
import static net.kehui.www.t_907_origin_V2.view.ModeActivity.BUNDLE_PARAM_KEY;

/**
 * @author 34238
 */
public class ConnectService extends Service {

    public int mode;
    public int command;
    public int dataTransfer;
    public int[] wifiStream;
    public static final int PORT = 9000;
    public static int[] mExtra;
    /**
     *是否已经与T-907建立连接
     */
    public static boolean isConnected;
    /**
     * 用于判断是否需要重新连接/是否执行connectDevice的方法   //GC20230112
     */
    public static boolean needReconnect = true;
    public static boolean doConnectDevice;
    /**
     *是否正在连接中    //20200523
     */
    private boolean isConnecting;
    /**
     * 发送命令
     */
    public static boolean canAskPower = true;

    private BufferedReader br;
    private ConnectThread connectThread;
    private ProcessThread processThread;

    /**
     * 阻塞列队 //数据生产者队列，生产的数据放入队列
     */
    public static ArrayBlockingQueue bytesDataQueue;

    static Socket socket;

    /**
     * 全局的handler对象用来执行UI更新
     */
    public static final int DEVICE_CONNECTED = 1;
    public static final int DEVICE_DISCONNECTED = 2;
    public static final int DEVICE_DO_CONNECT = 3;
    public static final int GET_COMMAND = 4;
    public static final int GET_WAVE = 5;

    public final static String BROADCAST_ACTION_DEVICE_CONNECTED = "device_connected";
    public final static String BROADCAST_ACTION_DEVICE_CONNECT_FAILURE = "device_failure";
    public final static String BROADCAST_ACTION_DOWIFI_COMMAND = "broadcast_action_dowifi_command";
    public final static String BROADCAST_ACTION_DOWIFI_WAVE = "broadcast_action_dowifi_wave";
    public final static String INTENT_KEY_COMMAND = "CMD";
    public final static String INTENT_KEY_WAVE = "WAVE";

    @Override
    public void onCreate() {
        //初始化网络SSID，默认为T-907，可长按操作栏“更新”按钮更改     //GC20220520
        Constant.SSID = StateUtils.getString(ConnectService.this, AppConfig.CURRENT_DEVICE, "T-907-0");
        if ((Constant.SSID).length() == 7) {    //如果本地存储的SSID是默认的“T-907-0”，默认去连接“T-907”
            if ( "0".equals( (Constant.SSID).substring(6) ) ) {
                Constant.SSID = "T-907";
            }
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
        this.bytesDataQueue = new ArrayBlockingQueue(100);
        Log.e("【SOCKET连接】", "服务启动");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //如果连接正常并且允许收取电量。
                if (isConnected && canAskPower) {
                    //EN20200324
                    canAskPower = false;
                    command = 0x06;
                    dataTransfer = 0x13;
                    sendCommand();
                }
                handler.postDelayed(this, 30000);
            }
        };
        handler.postDelayed(runnable, 30000);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //服务启动相关
        return null;
    }

    public Handler handler = new Handler(msg -> {
        switch (msg.what) {
            case DEVICE_CONNECTED:
                //服务中toast只可以跟随系统语言     //GC20211214
//                Toast.makeText(this, getResources().getString(R.string.connect_success), Toast.LENGTH_SHORT).show();
                sendBroadcast(BROADCAST_ACTION_DEVICE_CONNECTED, null, null);   //通过广播将服务中的状态变化传递出去
                break;
            case DEVICE_DISCONNECTED:
//                Toast.makeText(ConnectService.this, getResources().getString(R.string.disconnect), Toast.LENGTH_LONG).show();
                sendBroadcast(BROADCAST_ACTION_DEVICE_CONNECT_FAILURE, null, null);
                break;
            case DEVICE_DO_CONNECT:
//                Toast.makeText(ConnectService.this, getResources().getString(R.string.communication_failed), Toast.LENGTH_LONG).show();
                if (!isConnecting) {
                    connectWifi();
                    if (doConnectDevice) {  //添加条件判断，只有在连接到制定SSID时才会建立设备连接  //GC20230112
                        connectDevice();
                    }
                }
                break;
            case GET_COMMAND:
                wifiStream = msg.getData().getIntArray("CMD");
                assert wifiStream != null;
                sendBroadcast(BROADCAST_ACTION_DOWIFI_COMMAND, INTENT_KEY_COMMAND, wifiStream);
                break;
            case GET_WAVE:
                wifiStream = msg.getData().getIntArray("WAVE");
                assert wifiStream != null;
                mExtra = wifiStream;
                sendBroadcast(BROADCAST_ACTION_DOWIFI_WAVE, INTENT_KEY_WAVE, wifiStream);
                break;
            default:
                break;
        }
        return false;
    });

    /**
     * 监听网络广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            assert action != null;
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                assert connectivityManager != null;
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null) {
                    if (info.isConnected()) {
                        handler.sendEmptyMessage(DEVICE_DO_CONNECT);
                        Log.e("【SOCKET连接】", "有网络，尝试去建立连接");
                    }
                } else {
                    try {
                        connectThread.getOutputStream().flush();
                        connectThread.getOutputStream().close();
                        connectThread.getSocket().close();
                        socket = null;
                        connectThread = null;
                        processThread = null;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    if (needReconnect) {    //先判断，避免无可用网络和socket异常重复执行建立连接的操作   //GC20230112
                        needReconnect = false;
                        handler.sendEmptyMessage(DEVICE_DISCONNECTED);
                        handler.sendEmptyMessage(DEVICE_DO_CONNECT);
                        Log.e("【SOCKET连接】", "无可用网络，发送广播给modeActivity设置界面状态，然后尝试去建立连接");
                    }
                }
            }
        }
    };

    private void connectWifi() {
        WifiUtil wifiUtil = new WifiUtil(this);
        if (wifiUtil.checkState() != 3) {
            //WIFI网卡不可用
            wifiUtil.openWifi();
        }
        try {
            //获取可用网络的SSID   //GC20230113
            String currentSSID = wifiUtil.getSSID();
            if (currentSSID != null) {
                currentSSID = currentSSID.substring(1, currentSSID.length() - 1);
            }
//            if (!wifiUtil.getSSID().contains(Constant.SSID)) {
            if (!Objects.equals(currentSSID, Constant.SSID)) {  //不能用contains，设备编号都包括T-907，会造成连接到其它非设定编号的T-907  //GC20230112
                doConnectDevice = false;    //GC20230112
                Log.e("【SOCKET连接】", "平板WIFI未连接到设定的SSID ");
                WifiManagerProxy.get().init(getApplication());
                //断开已连接的其它WiFi  //GC20230113
                if (currentSSID != null) {
                    WifiManagerProxy.get().disConnect(currentSSID, new IWifiDisConnectListener() {
                        @Override
                        public void onDisConnectSuccess() {
                            Log.e("【SOCKET连接】", "onDisConnectSuccess://取消已连接的SSID成功 ");
                            needReconnect = false;  //取消已连接网络后，避免监听提示无可用网络，再次执行建立连接的操作
                        }

                        @Override
                        public void onDisConnectFail(String errorMsg) {
                            Log.e("【SOCKET连接】", "onDisConnectFail: " + errorMsg + "//取消已连接的SSID失败 ");
                        }
                    });
                }
//                wifiUtil.addNetwork(wifiUtil.createWifiInfo(Constant.SSID, "123456789", 3));    //旧的连接写法
                //尝试建立需要的连接并监听   //GC20220621
                WifiManagerProxy.get().connect(Constant.SSID, "123456789", new IWifiConnectListener() {
                    @Override
                    public void onConnectStart() {
                        Log.e("【SOCKET连接】", "onConnectStart://IWifiConnectListener，开始监听网络变化 ");
                    }

                    @Override
                    public void onConnectSuccess() {
                        Log.e("【SOCKET连接】", "onConnectSuccess://连接设定的SSID成功 ");
                    }

                    @Override
                    public void onConnectFail(String errorMsg) {
                        Log.e("【SOCKET连接】", "onConnectFail: " + errorMsg + "//连接设定的SSID失败 ");
                        handler.sendEmptyMessageDelayed(DEVICE_DO_CONNECT, 20000);
                    }
                });

            } else {
                doConnectDevice = true; //GC20230112
                Log.e("【SOCKET连接】", "平板WIFI已连接到设定的SSID ");
                connectDevice();
            }
        } catch (Exception l_Ex) {
        }
    }

    private void connectDevice() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("connect-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), threadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(() -> {
            try {
                if (!isConnected) {
                    isConnecting = true;
                    Log.e("【SOCKET连接】", "开始执行设备连接操作");
                    if (socket == null) {
                        socket = new Socket(Constant.DEVICE_IP, PORT);
                        socket.setKeepAlive(true);
                        Log.e("【SOCKET连接】", "建立socket连接");
                    }
                    if (connectThread == null) {
                        connectThread = new ConnectThread(socket, handler, Constant.DEVICE_IP);
                        connectThread.start();
                        Log.e("【SOCKET连接】", "启动接收数据线程connectThread");
                    }
                    if (processThread == null) {
                        processThread = new ProcessThread(handler);
                        processThread.start();
                        Log.e("【SOCKET连接】", "启动处理数据线程processThread");
                    }
                    isConnecting = false;
                    needReconnect = true;   //设备连接成功后可以再次进行建立连接的操作   //GC20230112
                    Log.e("【SOCKET连接】", "设备连接成功，整个连接过程结束");
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    connectThread.getOutputStream().flush();
                    connectThread.getOutputStream().close();
                    connectThread.getSocket().close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                handler.sendEmptyMessage(DEVICE_DISCONNECTED);
                Log.e("【SOCKET连接】", "设备连接失败，需要重连");
                isConnecting = false;
                handler.sendEmptyMessageDelayed(DEVICE_DO_CONNECT, 2000);
            }
        });
        singleThreadPool.shutdown();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        assert intent != null;
        Bundle bundle = intent.getBundleExtra(BUNDLE_PARAM_KEY);
        //接收到发送指令的信息
        if (bundle != null && bundle.getInt(BUNDLE_COMMAND_KEY) != 0) {
            mode = bundle.getInt(BUNDLE_MODE_KEY);
            command = bundle.getInt(BUNDLE_COMMAND_KEY);
            dataTransfer = bundle.getInt(BUNDLE_DATA_TRANSFER_KEY);
            sendCommand();
        }
        return super.onStartCommand(intent, flags, startId);    //GT20221019

    }

    /**
     * APP下发命令
     */
    public void sendCommand() {
        //发命令时禁止请求电量    //EN20200324
        canAskPower = false;

        byte[] request = new byte[8];
        //数据头部分
        request[0] = (byte) 0xeb;
        request[1] = (byte) 0x90;
        request[2] = (byte) 0xaa;
        request[3] = (byte) 0x55;
        //数据长度
        request[4] = (byte) 0x03;
        request[5] = (byte) command;
        if (command == BaseActivity.COMMAND_RANGE && dataTransfer == BaseActivity.RANGE_250) {
            //需要发送250m范围命令时改为500m范围命令
            dataTransfer = BaseActivity.RANGE_500;
        }
        if (command == BaseActivity.COMMAND_MODE && dataTransfer == BaseActivity.ICM_DECAY) {
            //需要发送ICM-DECAY方式命令时改为ICM命令
            dataTransfer = BaseActivity.ICM;
        }
        request[6] = (byte) dataTransfer;
        int sum = request[4] + request[5] + request[6];
        request[7] = (byte) sum;
        //TODO 20200407 发送数据是判断连接是否正常，否则不发送
        if (connectThread != null && ConnectService.isConnected) {
            connectThread.sendCommand(request);
            Log.e("#【APP-->设备】", "指令：" + command + " 传输数据：" + sendDataTransfer(command, dataTransfer) + " ——发命令时禁止请求电量");
        }
    }

    /**
     * @param cmdStr    指令
     * @param dataStr   传输数据
     * @return  发送内容
     */
    private String sendDataTransfer(int cmdStr, int dataStr) {
        String returnStr = "";
        if (cmdStr == 1) {
            switch (dataStr) {
                case 17:
                    returnStr = "0x11 / 测试";
                    break;
                case 34:
                    returnStr = "0x22 / 取消测试";
                    break;
                default:
                    break;
            }
        }
        if (cmdStr == 2) {
            switch (dataStr) {
                case 17:
                    returnStr = "0x11 / TDR";
                    break;
                case 34:
                    returnStr = "0x22 / ICM";
                    break;
                case 85:
                    returnStr = "0x55 / ICM_DECAY";
                    break;
                case 51:
                    returnStr = "0x33 / SIM";
                    break;
                case 68:
                    returnStr = "0x44 / DECAY";
                    break;
                default:
                    break;
            }
        }
        if (cmdStr == 3) {
            switch (dataStr) {
                case 153:
                    returnStr = "0x99 / 250m";
                    break;
                case 17:
                    returnStr = "0x11 / 500m";
                    break;
                case 34:
                    returnStr = "0x22 / 1km";
                    break;
                case 51:
                    returnStr = "0x33 / 2km";
                    break;
                case 68:
                    returnStr = "0x44 / 4km";
                    break;
                case 85:
                    returnStr = "0x55 / 8km";
                    break;
                case 102:
                    returnStr = "0x66 / 16km";
                    break;
                case 119:
                    returnStr = "0x77 / 32km";
                    break;
                case 136:
                    returnStr = "0x88 / 64km";
                    break;
                default:
                    break;
            }
        }
        if (cmdStr == 4) {
            returnStr = dataStr + "   / 增益";
        }
        if (cmdStr == 5) {
            returnStr = dataStr + "   / 延迟";
        }
        if (cmdStr == 6) {
            returnStr = " / 电量";
        }
        if (cmdStr == 7) {
            returnStr = dataStr + "   / 平衡";
        }
        if (cmdStr == 9) {
            returnStr = "开始接收数据 " + String.valueOf(dataStr);
        }
        if (cmdStr == 10) {
            returnStr = dataStr + " / 脉宽";
        }
        return returnStr;
    }

    /**
     *发送广播
     */
    public void sendBroadcast(String action, String extraKey, int[] extra) {
        try {
            Intent intent = new Intent();
            intent.setAction(action);
            if (extraKey != null) {
                intent.putExtra(extraKey, extra);
            }
            sendBroadcast(intent);
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setAction(action);
            mExtra = extra;
            sendBroadcast(intent);
            e.printStackTrace();
        }

    }

    /**
     * @return 获取ip
     */
    private ArrayList<String> getConnectedIP() {
        ArrayList<String> connectedIP = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ip = splitted[0];
                    connectedIP.add(ip);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectedIP;
    }

    @Override
    public void onDestroy() {
        isConnected = false;
        handler.removeCallbacksAndMessages(null);
        unregisterReceiver(receiver);

        try {
            connectThread.getOutputStream().flush();
            connectThread.getOutputStream().close();
            connectThread.getSocket().close();

/*            WifiUtil wifiUtil = new WifiUtil(this);
            wifiUtil.closeWifi();*/
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * @param context * @return
     */
    private String getMacDefault(Context context) {
        String mac = "0";
        if (context == null) {
            return mac;
        }
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info;
        info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();

        return mac;
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    private String getMacAddress() {
        String macSerial = null;
        String str = "0";

        try {
            Process pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            while (null != str) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim(); //去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }

        return macSerial;
    }

    /**
     * Android 7.0之后获取Mac地址
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     * @return
     */
    private String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String getMac() {
        String mac = "0";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        if (mac == null || mac.equals("")) {
            mac = "0";
        }
        return mac;
    }

}
