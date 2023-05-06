package net.kehui.www.t_907_origin_V2.base;

import net.kehui.www.t_907_origin_V2.adpter.DataAdapter;
import net.kehui.www.t_907_origin_V2.adpter.MyChartAdapterBase;
import net.kehui.www.t_907_origin_V2.dao.DataDao;
import net.kehui.www.t_907_origin_V2.global.BaseAppData;
import net.kehui.www.t_907_origin_V2.util.MultiLanguageUtil;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

/**
 * @author Gong
 * @date 2019/07/15
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * sparkView图形绘制部分
     */
    public MyChartAdapterBase myChartAdapterMainWave;

    public boolean isCom;
    public boolean isMemory;
    public boolean isDatabase;
    public boolean alreadyDisplayWave;  //GC20220822
    public boolean rangeChanged;    //GC20221017
    public boolean modeChanged;    //GC20221017
    public boolean allowSetRange;   //GC20221019
    public boolean allowSetMode;
    public boolean allowSetOperation;

    /**
     * 设备编号全局变量 //GC20220520
     */
    public String currentDevice;

    /**
     * 波形参数
     */
    public int mode;
    public int modeBefore;
    public int range;
    public int rangeBefore;
    public int rangeMemory; //GC20220709
    public int rangeState;
    public int gain;
    public double velocity;
    public int density;
    public int densityMax;
    public int balance;
    public int delay;
    public int inductor;
    public int pulseWidth;
    /**
     * 0xF7,0xF7,0xED,0xBF,0xA5,0x4D,0x00,0x00
     *  247, 247, 237, 191, 165,  77,   0,   0
     *  320, 320, 720,2560,3600,7120,   10200      //GC20200527    255-X/40;X为输入值  二次脉冲脉宽命令发送值 （按照这个比例选择pulseRemove）
     */
    public int pulseWidthSim;
    public int selectSim;

    /**
     * 波形原始数据数组
     */
    public int dataMax;
    public int dataLength;
    public int[] waveArray;
    public int[] simArray1;
    public int[] simArray2;
    public int[] simArray3;
    public int[] simArray4;
    public int[] simArray5;
    public int[] simArray6;
    public int[] simArray7;
    public int[] simArray8;
    public int[] simArray;

    /**
     * 波形绘制数据数组（抽点510个）
     */
    public int[] waveDraw;
    public int[] waveCompare;
    public int[] waveCompareDraw;
    public int[] simDraw1;
    public int[] simDraw2;
    public int[] simDraw3;
    public int[] simDraw4;
    public int[] simDraw5;
    public int[] simDraw6;
    public int[] simDraw7;
    public int[] simDraw8;

    /**
     * 不同范围和方式下，波形数据的点数、需要去掉的冗余点数、比例值
     */
    public final static int[] READ_TDR_SIM = {540, 540, 1052, 2076, 4124, 8220, 16412, 32796, 65556};
    public final static int[] READ_ICM_DECAY = {2068, 2068, 4116, 8212, 16404, 32788, 65556, 32788, 65556};
    public int[] removeTdrSim = {30, 30, 32, 36, 44, 60, 92, 156, 276};
    public int[] removeIcmDecay = {28, 28, 36, 52, 84, 148, 276, 148, 276};
    public int[] densityMaxTdrSim = {1, 1, 2, 4, 8, 16, 32, 64, 128};
    public int[] densityMaxIcmDecay = {4, 4, 8, 16, 32, 64, 128, 64, 128};

    /**
     * 光标参数
     */
    public int simOriginalZero;
    public int zero;
    public int pointDistance;
    public int simStandardZero;
    public int positionReal;
    public int positionVirtual;
    public int positionVirtualChange;
    public int positionSim;

    /**
     * 波形滑动相关
     */
    public int currentStart = 0;
    public int currentMoverPosition510 = 0;
    public int moverMoveValue = 0;

    /**
     * TDR、SIM自动测距相关参数
     */
    public int[] pulseRemovePoint = {4, 4, 8, 16, 32, 64, 128, 256, 512};    //TDR去脉宽点数   //jk20220711tdr
    public int[] pulseRemoveTdrWave = {52, 52, 52, 92, 276, 380, 732, 1040, 1040};      //TDR去波头    //jk20220711tdr
    public int[] pulseRemoveSimWave = {64, 64, 64, 144, 512, 720, 1424, 2040, 2040};    //SIM去波头    //jk20220711Sim
    public int tdrExtreme;      //TDR极值
    public int tdrTurning;      //TDR转折处
    public int tdrAutoLocation; //TDR自动定位位置
    public int step = 8;
    public int count = 6;
    public int gainState;
    public int balanceState;
    public boolean isLongClick;
    public boolean longTestInit;
    public boolean balanceIsReady;
    public boolean rangeIsReady;
    public int rangeAdjust = 0;
    public int medianValue = 128;  //基准数
    /**
     * SIM自动筛选  //GC20200529
     */
    public int[] overlapNum = new int[8];
    public int[] simSum = new int[9];
    public double[] simArray0Filter = new double[65560];
    public double[] simArray1Filter = new double[65560];
    public double[] simArray2Filter = new double[65560];
    public double[] simArray3Filter = new double[65560];
    public double[] simArray4Filter = new double[65560];
    public double[] simArray5Filter = new double[65560];
    public double[] simArray6Filter = new double[65560];
    public double[] simArray7Filter = new double[65560];
    public double[] simArray8Filter = new double[65560];
    public double[] simArrayF = new double[65560];  //jk20220711Sim
    public boolean receiveSimOver;
    public double[] tdrFilter = new double[65560]; //jk20220711tdr
    /**
     * ICM自动测距相关参数
     */
    public int breakdownPosition;   //ICM故障点击穿位置（向下转折处）
    public int faultResult;         //ICM自动计算得到的差值（向下转折到向上转折之间点数）
    public double[] waveArrayFilter = new double[65560];
    public double[] waveArrayIntegral = new double[65560];
    public double[] s1 = new double[65560];
    public double[] s2 = new double[65560];
    public boolean breakDown;   //是否击穿  //GC20191231

    /**
     * 测试缆信息添加    //GC20200103
     */
    public double leadLength;
    public double leadVop;

    public int[] wifiStream;
    /**
     * APP下发的命令协议(16进制)（8个字节）
     * 数据头     数据长度  指令  传输数据  校验和
     * eb90aa55     03      01      11       15
     * 指令0x01测试命令
     * eb90aa55 03 01 11 15	    测试0x11
     * eb90aa55 03 01 22 26	    取消测试0x22
     * 0x02方式
     * eb90aa55 03 02 11 16		TDR低压脉冲方式
     * eb90aa55 03 02 22 27		ICM脉冲电流方式
     * eb90aa55 03 02 33 38		SIM二次脉冲方式
     * 0x03范围
     * eb90aa55 03 03 11 17		范围500m
     * eb90aa55 03 03 22 28
     * eb90aa55 03 03 33 39
     * eb90aa55 03 03 44 4a
     * eb90aa55 03 03 55 5b
     * eb90aa55 03 03 66 6c
     * eb90aa55 03 03 77 7d
     * eb90aa55 03 03 88 8e		范围64km
     * 0x04增益
     * eb90aa55 03 04 11 18		增益+
     * eb90aa55 03 04 22 29		增益-
     * 0x05延时
     * eb90aa55 03 05 11 19		延时+
     * eb90aa55 03 05 22 2a		延时-
     * 0x06获取电池电量
     * eb90aa55 03 06 13 1c
     * 0x07平衡
     * eb90aa55 03 07 11 1b  	平衡+
     * eb90aa55 03 07 22 2c		平衡-
     * 0x09需要数据
     * eb90aa55 03 09 11 1d
     * 0x0a波宽度
     * eb90aa55 03 0a xx xx
     */
    public int command;
    public final static int COMMAND_TEST = 0x01;
    public final static int COMMAND_MODE = 0x02;
    public final static int COMMAND_RANGE = 0x03;
    public final static int COMMAND_GAIN = 0x04;
    public final static int COMMAND_DELAY = 0x05;
    public final static int COMMAND_BALANCE = 0x07;
    public final static int COMMAND_RECEIVE_WAVE = 0x09;
    public final static int COMMAND_PULSE_WIDTH = 0x0a;

    public int dataTransfer;
    public final static int TESTING = 0x11;
    public final static int CANCEL_TEST = 0x22;
    public final static int TDR = 0x11;
    public final static int ICM = 0x22;
    public final static int SIM = 0x33;
    public final static int DECAY     = 0x44;
    public final static int ICM_DECAY = 0x55;
    public final static int RANGE_250   = 0x99;
    public final static int RANGE_500   = 0x11;
    public final static int RANGE_1_KM  = 0x22;
    public final static int RANGE_2_KM  = 0x33;
    public final static int RANGE_4_KM  = 0x44;
    public final static int RANGE_8_KM  = 0x55;
    public final static int RANGE_16_KM = 0x66;
    public final static int RANGE_32_KM = 0x77;
    public final static int RANGE_64_KM = 0x88;

    /**
     * APP接收的命令（8个字节）
     * 数据头     数据长度  指令  传输数据  校验和
     * eb90aa55     03      01      33       ..  （0x33正确 0x44错误）
     * eb90aa55 03 08 11 1c		//接收到触发信号
     */
    public final static int COMMAND = 0x55;
    public final static int COMMAND_TRIGGER = 0x08;
    public final static int TRIGGERED = 0x11;
    /**
     * 获取电池电量命令 传输数据2个字节（9个字节）
     * eb90aa55 04 06 0c 53 69		//0x0c53=3155
     */
    public final static int POWER_DISPLAY = 0x06;

    /**
     * APP接收到的波形数据头
     * 数据头      数据长度    传输数据    校验和
     * eb90aaXX    4个字节     X……X       xx
     * 以XX区分——
     * 0x66：为低压脉冲或脉冲电流波形数据
     * 0x77：二次脉冲故障点未施加高压时的波形数据
     * 0x88-0xff：分别为二次脉冲故障点施加高压时的第1到第8条波形数据
     */
    public final static int WAVE_TDR_ICM_DECAY = 0x66;
    public final static int WAVE_SIM = 0x77;

    /**
     * 数据库存储波形部分
     */
    public DataAdapter adapter;
    public DataDao dao;
    public int selectedId;

    public static BaseActivity baseActivity;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //传递给非activity的类使用（记录显示中英文切换资源获取）  //GC20200525
        baseActivity = this;
        mContext = this.getBaseContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initParameter();
    }

    /**
     * 参数初始化
     */
    private void initParameter() {
        mode = TDR;
        range = RANGE_500;
        rangeMemory = RANGE_500;    //GC20220709
        rangeState = 1;
        gain = 13;
        velocity = 172;
        density = 1;
        densityMax = 1;
        balance = 5;
        inductor = 3;
        //初始化SIM序号为0，控制波形上翻、下翻按钮状态  //GC20220820
        selectSim = 0;

        dataMax = 540;
        dataLength = 510;
        waveArray = new int[540];
        waveDraw = new int[510];
        waveCompare = new int[510];
        waveCompareDraw = new int[510];
        simDraw1 = new int[510];
        simDraw2 = new int[510];
        simDraw3 = new int[510];
        simDraw4 = new int[510];
        simDraw5 = new int[510];
        simDraw6 = new int[510];
        simDraw7 = new int[510];
        simDraw8 = new int[510];

        //光标原始位置
        zero = 0;
        pointDistance = 255;
        //光标画布位置（变化范围0-509）
        positionReal = 0;
        positionVirtual = 255;
        //虚光标画布中变化的数值
        positionVirtualChange = 0;

        //增益大小状态
        gainState = 0;
        //平衡状态
        balanceState = 0;
        breakdownPosition = 0;
        //数据库相关 //20200520 //G?
        BaseAppData db = Room.databaseBuilder(getApplicationContext(), BaseAppData.class, "database-wave").allowMainThreadQueries().build();
        dao = db.dataDao();
    }

    /*
     ** 尝试关闭activity
     */
    public void tryclose(){
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }

}

/*————enNuo————*/
//EN20200324    发送命令和获取电量修改，增加条件限制，避免极端条件下会多次尝试连接
//20200407  电量获取修改  //TODO 20200407 增加测试中控制
//20200521  界面相关
//20200522  单位转化逻辑修正
//20200523  其它优化

/*——————————其它——————————*/
//GC?
//GT 调试
//GT20200619    每个点高度显示
/*——————————其它——————————*/

/*更改记录*/
//GC20190629 光标使用优化
//GC20190703 记忆比较功能
//GC20190704 增益、平衡、延时命令调节
//GC20190708 ICM自动测距                ***************
//GC20190709 距离计算，比例选择
//GC20190712 光标零点设置
//GC20190713 数据库波形显示

/*——————————自动测距调整——————————*/
//GC20191223    250m范围取点和距离计算
//GC20191231    自动测距修改
//GC20200103    测试缆信息添加
//GC20200106    光标定位修改
//GC20200109    DC方式下自动测距单独实现
//GC20200110    击穿点判断起始位置更改
//GC20200529    SIM自动筛选最优显示
//GC20200601    筛选显示优化
//GC20200606    ICM滤波、增益直流分量参数修改
//GC20200609    SIM自动筛选判断条件增加
/*——————————自动测距调整——————————*/

//GC20200313    增益显示转为百分比
//GC20200314    模式界面电量图标同步主页界面
//GC20200319    “等待触发”对话框重连时不消掉BUG修改
//GC20200327    帮助功能添加
//GC20200330    SIM标记光标添加
//GC20200331    不同范围发射不同脉宽功能添加
//GC20200424    不同模式下初始化发射的不同命令
//GC20200525    界面布局优化
//GC20200527    SIM方式下脉宽命令发送
//GC20200528    波形滑动区域控制
//GC20200611    缩放后移动滑块时画光标bug修正
//GC20200612    SIM标记光标（可以自定义）
//GC20200613    延时修改、按钮状态
//GC20200630    离线状态按钮可操作调整
//GC20200716    添加版本号显示
//GC20200817    断线二次脉冲处理
//GC20200916    低压脉冲长按自动测试逻辑改进

//jk20200714    低压脉冲光标、距离修改测试、自动增益
//jk20200715    低压脉冲测试按键长按响应添加
//jk20200716    平衡自动调整
//jk20200804    二次脉冲光标定位
//jk20200904    更改起始判断
//jk20201022    低压脉冲自动定位以133为中心点
//jk20201130    多次脉冲增益判断数值更改
//jk20201130    多次脉冲延时间隔增加
//jk20201130    脉冲电流延长线不选就不计算
//jk20210202    保存延长线参数
//jk20210206    解决电源按键问题
//jk20210420    脉冲电流容错处理  添加标志false_flag
//jk20210527    求出曲线拟合后求解纵坐标值为0时横坐标的结果  一元三次方程求解

/*——————————2.0.1版本整理——————————*/
//jk20210123    直接进入测试方式界面1
//GC20211214    服务中toast只可以跟随系统语言
/**
 * //                       _ooOoo_
 * //                      o8888888o
 * //                      88" . "88
 * //                      (| -_- |)
 * //                       O\ = /O
 * //                   ____/`---'\____
 * //                 .   ' \\| |// `.
 * //                  / \\||| : |||// \
 * //                / _||||| -:- |||||- \
 * //                  | | \\\ - /// | |
 * //                | \_| ''\---/'' | |
 * //                 \ .-\__ `-` ___/-. /
 * //              ______`. .' /--.--\ `. . __
 * //           ."" '< `.___\_<|>_/___.' >'"".
 * //          | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //            \ \ `-. \_ __\ /__ _/ .-` / /
 * //    ======`-.____`-.___\_____/___.-`____.-'======
 * //                       `=---='
 * //
 * //    .............................................
 * //             佛祖保佑             永无BUG
 * =====================================================
 */
/*——————————2.0.2版本整理——————————*/
//20200520  数据库相关
//GC20210125    波形数据以文件形式保存

//GC20220517    添加设备编号输入弹窗
//GC20220520    实现设备编号本地保存读取
//GC20220621    如果是安卓10.0，需要后台获取连接的wifi名称则添加进程获取位置信息权限

/*——————————2.0.3版本整理——————————*/
//GC20220622    低压脉冲方式下调整增益发送测试命令功能代码优化
//去掉activity_mode无用布局文件
//GC20220701    本地存储范围记录
//GC20220709    范围切换按钮状态变化代码简化，SIM范围要跟随TDR变化   //GC20220731 TDR自动测试范围记录
//GC20220801    TDR增益调整后不发测试命令
//GC20220808    打开数据库BUG修复
//GC20220810    弹窗外部按钮点击控制

/*——————————2.0.4版本整理——————————*/
//GC20220812    ICM增益调整后切换至TDR增益未响应 / 延长线界面修改 / SIM波形序号显示后前面没有空格
//GC20220814    针对开路测试情况，SIM虚光标定位到零点
//jk20220711Sim         SIM自动测距算法修改，多次脉冲找起始点修改，利用两条波形滤波后的差值进行精确查找  考虑故障点近端的情况，需要往前找
//jk20220711tdrRange    低压脉冲范围切换规整，先调增益再切范围
//jk20220711tdr         低压脉冲波形判断条件更改
//GC20220819    方式、范围、调节、操作fragment显示状态设置、按照TDR方式初始化
//GC20220820    操作栏SIM波形上翻、下翻按钮状态控制
//GC20220821    数据库多个方式可打开设置
//GC20220822    放大、缩小、还原按钮状态控制；横向滑块控制
//GC20220824    调节栏增益、平衡、延时、波速按钮状态控制
//GC20220825    离线状态控制
//GC20220914    ICM方式下的“延长线”按钮屏蔽/版本号添加
//GC20221017    SIM方式按照记忆的TDR范围变化BUG（907方式、范围一个方法写的，所以需要分开记录）
//GC20221019    方式、范围按钮快速点击限制处理
/*——————————2.0.5版本整理——————————*/
//GC20221025    数据库打开波形后记忆比较操作BUG
/*——————————2.0.6版本整理——————————*/
//GC20221203    波形数据混入其它信息后抛掉多余部分——有可能有，未验证XXXXXXXX
//GC20230112    添加只有在连接到指定SSID时才会成功建立设备连接的限制功能/建立网络连接的条件限制逻辑梳理
//GC20230113    添加断开已连接的其它WiFi的功能，避免设备切换时无法自主切换网络/设备编号更改完毕后点击确认后重启APP




//GT20220801    数据接收改动
//GT屏蔽算法
//GT20221019    getBundleExtra报错尝试修改（作用不大，数据库离线命令发送）
//以下未改
//GC20220806    点击SIM范围自动寻找
 /*——————————算法调整——————————*/
//jk20220922    TDR算法开路波形容错调整
//jk20221019    SIM算法数组下标越界异常处理
//jk20221020    数组容错处理
//GC20220926    判断逻辑优化
/*——————————算法调整——————————*/
