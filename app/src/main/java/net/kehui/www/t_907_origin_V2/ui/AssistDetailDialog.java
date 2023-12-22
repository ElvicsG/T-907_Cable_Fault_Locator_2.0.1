package net.kehui.www.t_907_origin_V2.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.application.AppConfig;
import net.kehui.www.t_907_origin_V2.application.Constant;
import net.kehui.www.t_907_origin_V2.entity.ParamInfo;
import net.kehui.www.t_907_origin_V2.util.ScreenUtils;
import net.kehui.www.t_907_origin_V2.util.StateUtils;
import net.kehui.www.t_907_origin_V2.util.UnitUtils;
import net.kehui.www.t_907_origin_V2.entity.DataAssist;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static net.kehui.www.t_907_origin_V2.application.Constant.CurrentUnit;
import static net.kehui.www.t_907_origin_V2.application.Constant.FT_UNIT;
import static net.kehui.www.t_907_origin_V2.application.Constant.MI_UNIT;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.DECAY;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.ICM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.ICM_DECAY;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_16_KM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_1_KM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_250;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_2_KM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_32_KM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_4_KM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_500;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_64_KM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.RANGE_8_KM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.SIM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.TDR;

/**
 * @author Gong
 * @date 2023/06/29 //协助详情对话框  //GC20230629
 */
public class AssistDetailDialog extends BaseAssistDialog implements View.OnClickListener {

    String date;
    String time;
    Date date1 = new Date(System.currentTimeMillis());
    ImageView ivClose;
    EditText tvDate;
    EditText tvMode;
    EditText tvRange;
    EditText tvPhase;
    TextView tvFalutLocationUnit;
    TextView tvCableLengthUnit;
    TextView tvSaveAssist;
    TextView tvGet;    //GC20231212
    TextView tvPost;    //GC20231212
    TextView tvPost2;    //GC20231216
    public TextView tvContent;    //GC20231212
    Spinner spPhase;
    /**
     * 协助详情：保存功能改为ModeActivity实现后，有些数据需要传递   //GC20230630
     */
    public EditText tvCableId;
    public EditText tvCableLength;  //GC20231211
    public EditText tvFaultLocation;
    public EditText tvOperator;
    public EditText tvTestSite;
    /**
     * 底部添加“上传”和“返回”按键   //GC20231226
     */
    TextView btnUploadDetail;
    TextView btnBackDetail;

    private List<String> phaseList = new ArrayList<>();
    private View view;
    private ParamInfo paramInfo;
    private int positionVirtual;
    private int positionReal;

    public void setPositionReal(int positionReal) {
        this.positionReal = positionReal;
    }

    public void setPositionVirtual(int positionVirtual) {
        this.positionVirtual = positionVirtual;
    }

    public AssistDetailDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(getContext()).inflate(R.layout.layout_assist_detail_dialog, null, false);
        setContentView(view);
        initView();
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(getContext()) * 0.9);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.8);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initUnit();
        initData();

    }

    private void initView() {
        ivClose = view.findViewById(R.id.iv_close);
        tvCableId = view.findViewById(R.id.tv_cable_id);
        tvDate = view.findViewById(R.id.tv_date);
        tvMode = view.findViewById(R.id.tv_mode);
        tvRange = view.findViewById(R.id.tv_range);
        tvCableLength = view.findViewById(R.id.tv_cable_length);
        tvFaultLocation = view.findViewById(R.id.tv_fault_location);
        tvPhase = view.findViewById(R.id.tv_phase);
        tvOperator = view.findViewById(R.id.tv_operator);
        tvTestSite = view.findViewById(R.id.tv_test_site);
        tvSaveAssist = view.findViewById(R.id.tv_save_assist);
        spPhase = view.findViewById(R.id.sp_phase);
        tvFalutLocationUnit = view.findViewById(R.id.tv_fault_location_unit);
        tvCableLengthUnit = view.findViewById(R.id.tv_cable_length_unit);
//        tvSaveAssist.setOnClickListener(this);  //协助详情：保存事件转到ModeActivity实现 //GC20230630
        ivClose.setOnClickListener(this);

        tvGet = view.findViewById(R.id.tv_Get); //GC20231212
        tvPost = view.findViewById(R.id.tv_Post); //GC20231212
        tvPost2 = view.findViewById(R.id.tv_Post2); //GC20231216
        tvContent = view.findViewById(R.id.tv_Content); //GC20231212

        btnUploadDetail  = view.findViewById(R.id.btn_upload_detail);
        btnBackDetail = view.findViewById(R.id.btn_back_detail);    //绑定id    //GC20231226
        btnUploadDetail.setOnClickListener(this);
        btnBackDetail.setOnClickListener(this);   //定义可点击   //GC20231226

    }

    /**
     * 协助详情：点击“上传”按钮事件  //GC20231226
     */
    public void clickUploadDetail(View.OnClickListener clickListener) {
        btnUploadDetail.setOnClickListener(clickListener);
    }

    /**
     * 协助详情：点击“返回”按钮事件，再弹出协助对话框  //GC20231226
     */
    public void clickBackDetail(View.OnClickListener clickListener) {
        btnBackDetail.setOnClickListener(clickListener);
    }

    /**
     * 协助详情：点击“保存”按钮事件，用于保存数据后再弹出协助对话框 //GC20230630
     */
    public void saveAssistance(View.OnClickListener clickListener) {
        tvSaveAssist.setOnClickListener(clickListener);
    }

    /**
     * 点击“发起Get请求”  //GC20231212
     */
    public void clickGet(View.OnClickListener clickListener) {
        tvGet.setOnClickListener(clickListener);
    }

    /**
     * 点击“Post”  //GC20231212
     */
    public void clickPost(View.OnClickListener clickListener) {
        tvPost.setOnClickListener(clickListener);
    }

    /**
     * 点击“Post2”  //GC20231216
     */
    public void clickPost2(View.OnClickListener clickListener) {
        tvPost2.setOnClickListener(clickListener);
    }

    private void initUnit() {
        //单位转化逻辑修正  //20200522
        CurrentUnit = StateUtils.getInt(getContext(), AppConfig.CURRENT_SAVE_UNIT, MI_UNIT);
        changeUnitView(CurrentUnit);

    }

    private void initData() {
        getMainParamInfo();
        setCableId();
        setEtDate();
        setEtMode();
        setEtRange();
        setCableLength();
        setEtLocation();
        setSpPhase();

    }

    private void setCableId() {
        if (paramInfo != null) {
            tvCableId.setText(paramInfo.getCableId());
        }

    }

    private void setEtDate() {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);   //GC20230609    年份格式修改
//        SimpleDateFormat dateSdf = new SimpleDateFormat("yy/MM/dd", Locale.US);
        SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        date = dateSdf.format(date1);
        time = timeSdf.format(date1);
        tvDate.setText(this.date + " " + this.time);
        Constant.Date = this.date;
        Constant.Time = time;
        //日期——2.对应AssistHelpVO.testTime  //协助详情可修改内容 //GC20231226
        tvDate.setEnabled(false);

    }

    private void setEtMode() {
        int mode = Constant.ModeValue;
        switch (mode) {
            case TDR:
                tvMode.setText(getContext().getResources().getString(R.string.btn_tdr));
                Constant.Mode = TDR;
                //测试方式记录    //GC20210125
                modeName = "TDR";
                modeSave = 0;
                break;
            case ICM:
                tvMode.setText(getContext().getResources().getString(R.string.btn_icm));
                Constant.Mode = ICM;
                //测试方式记录    //GC20210125
                modeName = "ICM";
                modeSave = 1;
                break;
            case ICM_DECAY:
                tvMode.setText(getContext().getResources().getString(R.string.btn_icm_decay));
                Constant.Mode = ICM_DECAY;
                modeName = "ICMDECAY";
                modeSave = 1;   //modeSave = 4; //GC20230609
                break;
            case SIM:
                tvMode.setText(getContext().getResources().getString(R.string.btn_sim));
                Constant.Mode = SIM;
                modeName = "SIM";
                modeSave = 2;
                break;
            case DECAY:
                tvMode.setText(getContext().getResources().getString(R.string.btn_decay));
                Constant.Mode = DECAY;
                modeName = "DECAY";
                modeSave = 1;   //modeSave = 3; //GC20230609
                break;
            default:
                break;
        }
        tvMode.setEnabled(false);
    }

    private void setEtRange() {
        int range = Constant.RangeValue;
        switch (range) {
            case RANGE_250:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_250m_to_ft));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_250m));
                }
                Constant.Range = RANGE_250;
                rangeSave = 0;  //本地存储范围记录  //GC20220701    //rangeSave = 8;    //GC20230609
                break;
            case RANGE_500:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_500m_to_ft));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_500m));
                }
                Constant.Range = RANGE_500;
                rangeSave = 0;  //GC20220701
                break;
            case RANGE_1_KM:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_1km_to_yingli));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_1km));
                }
                Constant.Range = RANGE_1_KM;
                rangeSave = 1;  //GC20220701
                break;
            case RANGE_2_KM:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_2km_to_yingli));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_2km));
                }
                Constant.Range = RANGE_2_KM;
                rangeSave = 2;
                break;
            case RANGE_4_KM:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_4km_to_yingli));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_4km));
                }
                Constant.Range = RANGE_4_KM;
                rangeSave = 3;
                break;
            case RANGE_8_KM:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_8km_to_yingli));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_8km));
                }
                Constant.Range = RANGE_8_KM;
                rangeSave = 4;
                break;
            case RANGE_16_KM:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_16km_to_yingli));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_16km));
                }
                Constant.Range = RANGE_16_KM;
                rangeSave = 5;
                break;
            case RANGE_32_KM:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_32km_to_yingli));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_32km));
                }
                Constant.Range = RANGE_32_KM;
                rangeSave = 6;
                break;
            case RANGE_64_KM:
                if (CurrentUnit == FT_UNIT) {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_64km_to_yingli));
                } else {
                    tvRange.setText(getContext().getResources().getString(R.string.btn_64km));
                }
                Constant.Range = RANGE_64_KM;
                rangeSave = 7;
                break;
            default:
                break;
        }
        tvRange.setEnabled(false);
    }

    private void setCableLength() {
        if (paramInfo != null) {
            //单位转化逻辑修正  //20200522
            if (CurrentUnit == MI_UNIT) {
                if (paramInfo.getCableLength().equals("0") || paramInfo.getCableLength().equals("0.0")) {
                    tvCableLength.setText("");
                } else {
                    tvCableLength.setText(paramInfo.getCableLength());
                }
            } else {
                if (paramInfo.getCableLength().equals("0") || paramInfo.getCableLength().equals("0.0")) {
                    tvCableLength.setText("");
                } else {
                    tvCableLength.setText(UnitUtils.miToFt(Double.valueOf(paramInfo.getCableLength())));
                }
            }
        }
    }

    private void setEtLocation() {
        Constant.SaveLocation = Constant.CurrentLocation;
        if (Constant.CurrentUnit == MI_UNIT) {
            tvFaultLocation.setText(new DecimalFormat("0.00").format(Constant.SaveLocation));
        } else {
            tvFaultLocation.setText(UnitUtils.miToFt(Constant.SaveLocation));
        }
    }

    private void setSpPhase() {
        phaseList.add(getContext().getResources().getString(R.string.phaseA));
        phaseList.add(getContext().getResources().getString(R.string.phaseB));
        phaseList.add(getContext().getResources().getString(R.string.phaseC));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_item, phaseList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhase.setAdapter(adapter);
        spPhase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constant.Phase = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getMainParamInfo() {
        paramInfo = (ParamInfo) StateUtils.getObject(getContext(), Constant.PARAM_INFO_KEY);
    }

    private void changeUnitView(int currentUnit) {
        if (currentUnit == MI_UNIT) {
            tvFalutLocationUnit.setText(R.string.mi);
            tvCableLengthUnit.setText(R.string.mi);

        } else {
            tvFalutLocationUnit.setText(R.string.ft);
            tvCableLengthUnit.setText(R.string.ft);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            /*case R.id.tv_save_assist: //协助详情：保存事件转到ModeActivity实现 //GC20230630
                final DataAssist data = formatData(new DataAssist());
                Flowable.create((FlowableOnSubscribe<List>) e -> {
                    daoAssist.insertData(data);   //保存时   //GC20230629
                    List list = Arrays.asList(daoAssist.query());
                    e.onNext(list);
                    e.onComplete();
                }, BackpressureStrategy.BUFFER)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List list) {
                        //数据库保存提示 //20200520
//                        Toast.makeText(getContext(), list.size() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        //数据库保存修改   //20200520
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                    }
                });
                //点击“保存”按钮后存储文件 //GC20210125
                saveClick();
                dismiss();
                break;*/
            default:
                break;
        }
    }

    /**
     * 数据库数据赋值
     * @param data
     * @return
     */
    private DataAssist formatData(DataAssist data) {
        data.date = Constant.Date.trim();
        data.cableId = tvCableId.getText().toString();
        data.time = Constant.Time.trim();
        data.mode = Constant.Mode + "";
        data.range = Constant.Range;
        data.location = Constant.SaveLocation;

        if (data.location == 0) {
            if (!TextUtils.isEmpty(tvFaultLocation.getText().toString())) {
                data.location = Double.parseDouble(tvFaultLocation.getText().toString());
            }
        }

        if (paramInfo != null) {
            data.line = paramInfo.getCableLength();
        } else {
            data.line = "";
        }

        data.phase = Constant.Phase + "";
        data.tester = tvOperator.getText().toString().trim();
        data.testsite = tvTestSite.getText().toString().trim();
        data.waveData = Constant.WaveData;
        data.waveDataSim = Constant.SimData;

        //TODO 20191226 存储zero 和pointDistance
        data.positionReal = positionReal;
        data.positionVirtual = positionVirtual;
        //参数数据 方式  范围 增益 波速度
        data.para = new int[]{Constant.ModeValue, Constant.RangeValue, Constant.SaveToDBGain, (int) Constant.Velocity};
        return data;
    }

    /**
     * 波形数据以文件形式保存     //GC20210125
     */
    String waveData = "";
    byte modeSave;
    byte rangeSave;
    private void saveClick() {
        initDataName();
        //直接将int数组转变为byte数组（int数据大小未超过byte显示）
        int length = Constant.WaveData.length;
        byte[] bytes;
        if (modeSave == 2) {    //GC20230609
            bytes = new byte[length * 2 + 20];
        } else {
            bytes = new byte[length + 20];
        }
        bytes[0] = modeSave;
        bytes[1] = rangeSave;
        bytes[2] = (byte) Constant.SaveToDBGain;
        //GC20230609
        byte[] bytes1 = shortToByte((short) Constant.Velocity); //波速度
        bytes[3] = bytes1[1];   //高8位
        bytes[4] = bytes1[0];   //低8位
        /*bytes[3] = (byte) (velocityTemp/256);   //高8位
        bytes[4] = (byte) (velocityTemp%256);   //低8位*/
        byte[] bytes2 = shortToByte((short) Math.abs(Constant.PositionV - Constant.PositionR));    //虚实光标距离
        bytes[5] = bytes2[1];   //高8位
        bytes[6] = bytes2[0];   //低8位
        byte[] bytes3 = shortToByte((short) Constant.PositionR); //实光标
        bytes[7] = bytes3[1];   //高8位
        bytes[8] = bytes3[0];   //低8位
//        for (int i = 3; i < 20; i++) {    //GC20230609
        for (int i = 9; i < 20; i++) {
            bytes[i] = 0;
        }
        for (int i = 20, j = 0; j < length; i++, j++) {
            bytes[i] = (byte) Constant.WaveData[j];
        }
        if (modeSave == 2) {    //GC20230609    多次脉冲第二条波形添加
            for (int i = 20 + length, j = 0; j < length; i++, j++) {
                bytes[i] = (byte) Constant.SimData[j];
            }
        }
        //根据byte数组生成文件，保存到手机上
        createFileWithByte(bytes);
        waveData = "";
        //弹出信息提示
//        Toast.makeText(ModeActivity.this, "生成文件成功！", Toast.LENGTH_LONG).show();
    }

    /**
     * short转byte
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.valueOf(temp & 0xff).byteValue();
            // 向右移8位
            temp = temp >> 8;
        }
        return b;
    }

    private String fileName;
    private String modeName;
    private void initDataName() {
//        fileName = "byte_to_file";
        fileName = modeName + Constant.Date.trim() + Constant.Time.trim();  //方式在前，907单独  //GC20231208
        fileName = fileName.replaceAll(":","");
        fileName = fileName.replaceAll("/","");
    }

    /**
     * 根据byte数组生成文件
     * @param bytes 生成文件用到的byte数组
     */
    private void createFileWithByte(byte[] bytes) {
        //在sd卡中设置新目录存放文件
        String path  = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path + "/907ASSIST");  //协助存储文件夹   //GC20230922    //dialog里面方法废弃
        //创建FileOutputStream对象
        FileOutputStream outputStream = null;
        //创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果目录不存在则创建
            if (!file.exists()) {
                file.mkdir();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file + "/" + fileName);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

}
