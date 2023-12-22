package net.kehui.www.t_907_origin_V2.application;

/**
 * @author common
 * @date 2019/7/8
 */
public class Constant {
    public static String SSID = "T-";   //要连接的WiFi名字    //gc调试  //网络SSID更改   //GC20220520
    public static final String DEVICE_IP = "192.168.5.143";
    public static final String BASE_API = "http://cfl.kehui.cn/";
    public static final String PARAM_INFO_KEY = "param_info_key";
    public static final String PULSE_WIDTH_INFO_KEY = "pulse_width_info_key";
    /**
     * 定义数据库显示的广播  //GC20190713
     */
    public static final String DISPLAY_ACTION = "display_action";

    public static final int MI_UNIT = 0;
    public static final int FT_UNIT = 1;
    public static int CurrentUnit = MI_UNIT;
    public static int CurrentSaveUnit = MI_UNIT;

    public static int Mode;//16进制指令
    public static int Range;//16进制指令
    public static int Gain;
    public static double Velocity;
    public static int DensityMax;
    public static String Date;
    public static String Time;
    public static int Phase;//代表相位编码
    public static int PositionV;//虚光标
    public static int PositionR;//实光标

    public static double CurrentLocation;
    public static double SaveLocation;
    public static int[] Para;   //Para包含ModeValue、RangeValue、SaveToDBGain、Velocity  //GC20231226
    public static int ModeValue;    //16进制指令    //重复了好像 //GC20230630
    public static int RangeValue;   //16进制指令
    public static int SaveToDBGain;
    /**
     * 非SIM波形和SIM第一条波形
     */
    public static int[] WaveData;
    /**
     * SIM第二条波形  SIM共接收9条波形（1+8的组合共8组）
     */
    public static int[] SimData;
    public static int[] TempData1;
    public static int[] TempData2;
    public static int[] TempData3;
    public static int[] TempData4;
    public static int[] TempData5;
    public static int[] TempData6;
    public static int[] TempData7;
    public static int[] TempData8;

    public static String currentLanguage = "";

    /**
     * 脉宽是否设置    //GC20200331
     */
    public static boolean hasSavedPulseWidth;

    /**
     * 电量状态记录   //GC20200314
     */
    public static int batteryValue = -1;

    //TODO 20200407 增加测试中控制，测试中未绘制波形，不要请求电量。
    public static boolean isTesting = false;

    /**
     * 接收的非SIM波形数据长度、单条SIM波形数据长度
     */
    public static int waveLen = 549;
    public static int waveSimLen = 549;

    /**
     * 是否需要补齐波形数据
     */
    public static boolean needAddData = false;

    public static Boolean allowSave;   //GC20231208

    /**
     * 远程协助相关参数   //GC20231224
     */
    public static String deviceId = "";

}
