package net.kehui.www.t_907_origin_V2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.percentlayout.widget.PercentRelativeLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.application.AppConfig;
import net.kehui.www.t_907_origin_V2.application.Constant;
import net.kehui.www.t_907_origin_V2.base.BaseActivity;
import net.kehui.www.t_907_origin_V2.util.ScreenUtils;
import net.kehui.www.t_907_origin_V2.util.StateUtils;
import net.kehui.www.t_907_origin_V2.util.UnitUtils;
import net.kehui.www.t_907_origin_V2.view.ModeActivity;
import net.kehui.www.t_907_origin_V2.entity.DataAssist;
import net.kehui.www.t_907_origin_V2.adpter.RecordsAssistAdapter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static net.kehui.www.t_907_origin_V2.application.Constant.DISPLAY_ACTION;
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
 * @date 2023/06/29 //协助记录对话框  //GC20230629
 */
public class AssistRecordDialog extends BaseAssistDialog implements View.OnClickListener, CustomDatePickerDialogFragment.OnSelectedDateListener {

    @BindView(R.id.iv_close)
    ImageView ivClose;

    RecyclerView rvRecords;
    TextView tvCableId;
    TextView tvDate;
    TextView tvMode;
    TextView tvRange;
    TextView tvCableLength;
    TextView tvFaultLocation;
    TextView tvPhase;
    TextView tvOperator;
    TextView tvTestSite;
    TextView tvDisplay;
    TextView tvDelete;
    //20200524
    TextView tvSelectModeText;
    TextView tvSearchDate;
    TextView tvSearch;
    Spinner spMode;
    /**
     * 底部添加“发起新协助”和“返回”按键   //GC20230630
     */
    TextView btnInitiateNewAssist;
    TextView btnBackRecord;

    /**
     * 数据库存储波形部分
     */
    private RecordsAssistAdapter adapter;   //GC20230629
    private int selectedId;
    private int mode;
    private int pos;
    private int spModePos;  //GC20230913

    //加载类型
    //0是第一次 1是加载
    private int loadType = 0;
    private boolean isHas = true;
    //加载到第几页
    private int loadPage = 0;
    //一页加载几条    //G?
    private int pageSize;

    private boolean fromMain;

    private LinearLayoutManager layoutManager;
    private View view;
    private TextView tvNoRecords;
    private PercentRelativeLayout rlHasRecords;
    private String searchDate;
    private TextView tvCableLengthUnit;
    private TextView tvFaultLocationUnit;
    private List<String> modeList = new ArrayList<>();
    private BaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.layout_assist_record_dialog, null);    //GC20230629
        setContentView(view);
        initView();
        Constant.CurrentSaveUnit = StateUtils.getInt(getContext(), AppConfig.CURRENT_SAVE_UNIT, MI_UNIT);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(getContext()) * 0.9);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.96);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        new Thread() {
            @Override
            public void run() {
                initAdapter();  //界面卡顿十分严重，新建一个线程运行   //GC20231213
                /*if (fromMain) {
                    setSpMode();
                } else {
                    spMode.setVisibility(View.GONE);
                    tvSelectModeText.setVisibility(View.GONE);
                }*/
                //bug修正，根据方式的不同选择下拉菜单的位置，以初始化加载的记录（通过方式区分）  //GC20230913
                switch (mode) {
                    case 0x11:  //TDR
                        spModePos = 0;
                        break;
                    case 0x22:  //ICM
                        spModePos = 1;
                        break;
                    case 0x33:  //SIM
                        spModePos = 3;
                        break;
                    case 0x44:  //DECAY
                        spModePos = 4;
                        break;
                    case 0x55:  //ICM_DECAY
                        spModePos = 2;
                        break;
                    default:
                        break;
                }
                //直接进入方式界面，可选择其他方式下的记录 //GC20220821
                setSpMode();
            }
        }.start();  //不应在主线程中进行耗时操作，比如网络请求，很复杂耗时的计算，对数据库的访问以及数据修改等。应该另开辟子线程将之放入到子线程中

    }

    private void initView() {
        ivClose = view.findViewById(R.id.iv_close);
        rvRecords = view.findViewById(R.id.rv_records);
        tvCableId = view.findViewById(R.id.tv_cable_id);
        tvDate = view.findViewById(R.id.tv_date);
        tvMode = view.findViewById(R.id.tv_mode);
        tvRange = view.findViewById(R.id.tv_range);
        tvCableLength = view.findViewById(R.id.tv_cable_length);
        tvFaultLocation = view.findViewById(R.id.tv_fault_location);
        tvPhase = view.findViewById(R.id.tv_phase);
        tvOperator = view.findViewById(R.id.tv_operator);
        tvTestSite = view.findViewById(R.id.tv_test_site);
        tvDisplay = view.findViewById(R.id.tv_display);
        tvDelete = view.findViewById(R.id.tv_delete);
        tvNoRecords = view.findViewById(R.id.tv_no_records);
        rlHasRecords = view.findViewById(R.id.rl_has_records_assist);
        tvCableLengthUnit = view.findViewById(R.id.tv_cable_length_unit);
        tvFaultLocationUnit = view.findViewById(R.id.tv_fault_location_unit);
        spMode = view.findViewById(R.id.sp_mode);
        tvSelectModeText = view.findViewById(R.id.tv_selectMode);
        tvSearchDate = view.findViewById(R.id.tv_search_date);
        tvSearch = view.findViewById(R.id.tv_search);
        ivClose.setOnClickListener(this);
        tvDisplay.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvSearchDate.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        btnInitiateNewAssist = view.findViewById(R.id.btn_initiate_new_assist);
        btnBackRecord = view.findViewById(R.id.btn_back_record);    //绑定id    //GC20230630
        btnInitiateNewAssist.setOnClickListener(this);
        btnBackRecord.setOnClickListener(this);   //定义可点击   //GC20230630
    }

    private void initAdapter() {
        adapter = new RecordsAssistAdapter(); //GC20230629
        layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setReverseLayout(true);   //列表翻转  //GC20230913
//        layoutManager.setStackFromEnd(true);    //列表再底部开始展示，反转后由上面开始展示    //GC20231116列表不翻转
        rvRecords.setLayoutManager(layoutManager);
        rvRecords.setAdapter(adapter);
        rvRecords.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener((view, dataId, selectedPara[], selectedWave[], selectedSim[], position) -> {
            adapter.changeSelected(position);
            selectedId = dataId;
            //GC20190713
            Constant.Para = selectedPara;
            DataAssist waveData = GetWaveData(selectedId);    //显示时   //GC20230629
            Constant.WaveData = waveData.waveData;
            Constant.SimData = waveData.waveDataSim;
            Constant.PositionR = adapter.datas.get(position).positionReal;
            Constant.PositionV = adapter.datas.get(position).positionVirtual;
            Constant.SaveLocation = adapter.datas.get(position).location;
            pos = position;
            setDataByPositionAssist(adapter.datas.get(position));
        });
        adapter.setOnItemInitDataListener((view, dataId, para, waveData, simData, position) -> {
            DataAssist data = adapter.datas.get(position);
            setDataByPositionAssist(data);
        });
        loadData();

        rvRecords.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= layoutManager.getItemCount() - 1 && isHas) {
                        loadPage++;
                        loadType = 1;
                        loadData();
                    }
                }
            }
        });

    }

    /**
     * 测距模式下拉菜单
     */
    private void setSpMode() {
        modeList.add(getContext().getResources().getString(R.string.btn_tdr));
        modeList.add(getContext().getResources().getString(R.string.btn_icm));
        modeList.add(getContext().getResources().getString(R.string.btn_icm_decay));
        modeList.add(getContext().getResources().getString(R.string.btn_sim));
        modeList.add(getContext().getResources().getString(R.string.btn_decay));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, modeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMode.setAdapter(adapter);
        spMode.setSelection(spModePos, true);   //根据方式选择下拉菜单的位置    //GC20230913
        spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(getContext().getResources().getColor(R.color.blue_303f9f)); //设置颜色
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.sp_12)); //设置大小
                tv.setGravity(Gravity.CENTER_HORIZONTAL); //设置居中
                switch (position) {
                    case 0:
                        mode = TDR;
                        break;
                    case 1:
                        mode = ICM;
                        break;
                    case 2:
                        mode = ICM_DECAY;
                        break;
                    case 3:
                        mode = SIM;
                        break;
                    case 4:
                        mode = DECAY;
                        break;
                    default:
                        break;
                }
                loadType = 0;
                loadPage = 0;
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 点击“发起新协助”按钮事件   //GC20230630
     */
    public void initiateNewAssist(View.OnClickListener clickListener) {
        btnInitiateNewAssist.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.btn_back_record: //返回执行的事件    //GC20230630
                dismiss();
                break;
            case R.id.tv_display:   //协助数据库数据显示 //GC20230629
                Intent intent = new Intent();
                if (fromMain) {
                    intent.setClass(getContext(), ModeActivity.class);
                    intent.putExtra(ModeActivity.BUNDLE_MODE_KEY, Integer.valueOf(adapter.datas.get(pos).mode));
                    intent.putExtra("display_action", ModeActivity.DISPLAY_DATABASE);
                    intent.putExtra("isReceiveData", false);
                    getContext().startActivity(intent);
                } else {
                    intent = new Intent(DISPLAY_ACTION);
                    intent.putExtra(ModeActivity.BUNDLE_MODE_KEY, Integer.valueOf(adapter.datas.get(pos).mode));
                    intent.putExtra("display_action", ModeActivity.DISPLAY_DATABASE);
                    getContext().sendBroadcast(intent);
                }
                dismiss();
                break;
            case R.id.tv_delete:
                tvDelete.setEnabled(false);
                deletePosition();
                break;
            case R.id.tv_search_date:
                showDatePickDialog();
                break;
            case R.id.tv_search:
                loadType = 0;
                loadPage = 0;
                loadData();
                break;
            default:
                break;
        }

    }

    private void deletePosition() {
        Flowable.create((FlowableOnSubscribe<List>) e -> {
            DataAssist[] datas = null;
            datas = daoAssist.queryDataId(selectedId);
            daoAssist.deleteData(datas);
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
                adapter.datas = list;
                adapter.notifyDataSetChanged();

                if (adapter.getItemCount() <= 0) {
                    rlHasRecords.setVisibility(View.INVISIBLE);  //底部还有按钮，所以是不可见    //GC20230630    GONE
                    tvNoRecords.setVisibility(View.VISIBLE);
                } else {
                    if (pos == list.size()) {
                        pos -= 1;
                    }
                    selectedId = adapter.datas.get(pos).dataId;
                    setDataByPositionAssist(adapter.datas.get(pos));
                    adapter.changeSelected(pos);
                    Constant.Para = adapter.datas.get(pos).para;
                    DataAssist waveData = GetWaveData(selectedId);
                    Constant.WaveData = waveData.waveData;
                    Constant.SimData = waveData.waveDataSim;
                    Constant.PositionR = adapter.datas.get(pos).positionReal;
                    Constant.PositionV = adapter.datas.get(pos).positionVirtual;
                    Constant.SaveLocation = adapter.datas.get(pos).location;
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
                adapter.datas.remove(pos);
                if (adapter.getItemCount() <= 0) {
                    rlHasRecords.setVisibility(View.INVISIBLE);  //底部还有按钮，所以是不可见    //GC20230630    GONE
                    tvNoRecords.setVisibility(View.VISIBLE);
                } else {
                    if (pos == adapter.datas.size()) {
                        pos -= 1;
                    }
                    selectedId = adapter.datas.get(pos).dataId;
                    setDataByPositionAssist(adapter.datas.get(pos));
                    adapter.changeSelected(pos);
                    Constant.Para = adapter.datas.get(pos).para;
                    DataAssist waveData = GetWaveData(selectedId);
                    Constant.WaveData = waveData.waveData;
                    Constant.SimData = waveData.waveDataSim;
                    Constant.PositionR = adapter.datas.get(pos).positionReal;
                    Constant.PositionV = adapter.datas.get(pos).positionVirtual;
                    Constant.SaveLocation = adapter.datas.get(pos).location;
                }
                adapter.notifyDataSetChanged();
                tvDelete.setEnabled(true);
            }
        });
        adapter.deleteItem(pos);

    }

    private DataAssist GetWaveData(int selectedId) {
        DataAssist[] data;
        data = daoAssist.queryWaveById(selectedId);   //显示时   //GC20230629
        return data[0];

    }

    private void loadData() {
        Flowable.create((FlowableOnSubscribe<List<DataAssist>>) e -> {
            DataAssist[] data;
            int index;
            //todo 查询逻辑如何处理 日期模式...
            if (mode == 0) {
                index = loadPage * 10;
                data = daoAssist.queryByIndex(index);
            } else if (searchDate != null) {
                index = loadPage * 10;
                data = daoAssist.queryDateByIndex(searchDate, mode + "", index);
            } else {
                index = loadPage * 10;
                data = daoAssist.queryModeByIndex(mode + "", index);
            }
            e.onNext(Arrays.asList(data));
            e.onComplete();
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<DataAssist>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(List<DataAssist> list) {
                if (loadType == 0) {
                    loadPage = 0;
                    if (list.size() > 0) {
//                        int listLength = list.size() - 1;  //记录列表长度，定位到最后位置    //GC20230913
                        int listLength = 0;  //定位位置为0    //GC20231116
                        isHas = true;
                        rlHasRecords.setVisibility(View.VISIBLE);
                        tvNoRecords.setVisibility(View.GONE);
                        if (adapter.datas != null) {
                            adapter.datas.clear();
                        }
                        adapter.datas.addAll(list);
                        adapter.notifyDataSetChanged();
                        adapter.changeSelected(listLength);  //方式改变后，刷新点击位置  //GC20220821  //刷新定位到最列表后位置    //GC20230913
                        setDataByPositionAssist(list.get(listLength));
                        selectedId = list.get(listLength).dataId;
                        //GC20190713
                        Constant.Para = list.get(listLength).para;
                        DataAssist waveData = GetWaveData(selectedId);
                        Constant.WaveData = waveData.waveData;
                        Constant.SimData = waveData.waveDataSim;
                        Constant.PositionV = list.get(listLength).positionVirtual;
                        Constant.PositionR = list.get(listLength).positionReal;
                        Constant.SaveLocation = list.get(listLength).location;
                    } else {
                        rlHasRecords.setVisibility(View.INVISIBLE);  //底部还有按钮，所以是不可见    //GC20230630    GONE
                        tvNoRecords.setVisibility(View.VISIBLE);
                    }
                } else {
                    adapter.datas.addAll(list);
                    adapter.notifyDataSetChanged();
                }
                if (list.size() <= pageSize) {
                    isHas = false;
                } else {
                    isHas = true;
                }
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void setDataByPositionAssist(DataAssist data) {
        tvCableId.setText(String.valueOf(data.cableId));
        if (Constant.CurrentSaveUnit == FT_UNIT) {
            if (!TextUtils.isEmpty(data.line)) {
                if (data.line.equals("0") || data.line.equals("0.0")) {
                    tvCableLength.setText(" ");
                } else {
                    tvCableLength.setText(UnitUtils.miToFt(Double.valueOf(data.line)));
                }
            } else {
                tvCableLength.setText(" ");
            }
            tvCableLengthUnit.setText(R.string.ft);
            tvFaultLocationUnit.setText(R.string.ft);
        } else {
            if (data.line.equals("0") || data.line.equals("0.0")) {
                tvCableLength.setText(" ");
            } else {
                tvCableLength.setText(data.line);
            }
            tvCableLengthUnit.setText(R.string.mi);
            tvFaultLocationUnit.setText(R.string.mi);
        }

        tvDate.setText(data.date + " " + data.time);
        tvMode.setText(initMode(Integer.valueOf(data.mode)));
        tvRange.setText(initRange(data.range));
        if (Constant.CurrentSaveUnit == MI_UNIT) {
            tvFaultLocation.setText(new DecimalFormat("0.00").format(data.location));
        } else {
            tvFaultLocation.setText(UnitUtils.miToFt(data.location));
        }
        tvPhase.setText(initPhase(Integer.valueOf(data.phase)));
        tvOperator.setText(data.tester);
        tvTestSite.setText(data.testsite);    //协助记录：测试地点加载错误，误添加tester  //GC20231211

    }

    private String initRange(int range) {
        switch (range) {
            case RANGE_250:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_250m);
                } else if (Constant.CurrentSaveUnit == FT_UNIT) {
                    return getContext().getResources().getString(R.string.btn_250m_to_ft);
                }
            case RANGE_500:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_500m);
                } else {
                    return getContext().getResources().getString(R.string.btn_500m_to_ft);
                }
            case RANGE_1_KM:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_1km);
                } else {
                    return getContext().getResources().getString(R.string.btn_1km_to_yingli);
                }
            case RANGE_2_KM:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_2km);
                } else {
                    return getContext().getResources().getString(R.string.btn_2km_to_yingli);
                }
            case RANGE_4_KM:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_4km);
                } else {
                    return getContext().getResources().getString(R.string.btn_4km_to_yingli);
                }
            case RANGE_8_KM:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_8km);
                } else {
                    return getContext().getResources().getString(R.string.btn_8km_to_yingli);
                }
            case RANGE_16_KM:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_16km);
                } else {
                    return getContext().getResources().getString(R.string.btn_16km_to_yingli);
                }
            case RANGE_32_KM:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_32km);
                } else {
                    return getContext().getResources().getString(R.string.btn_32km_to_yingli);
                }
            case RANGE_64_KM:
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    return getContext().getResources().getString(R.string.btn_64km);
                } else {
                    return getContext().getResources().getString(R.string.btn_64km_to_yingli);
                }
            default:
                return "";
        }
    }

    private String initMode(int mode) {
        switch (mode) {
            case TDR:
                return getContext().getResources().getString(R.string.btn_tdr);
            case ICM:
                return getContext().getResources().getString(R.string.btn_icm);
            case ICM_DECAY:
                return getContext().getResources().getString(R.string.btn_icm_decay);
            case SIM:
                return getContext().getResources().getString(R.string.btn_sim);
            case DECAY:
                return getContext().getResources().getString(R.string.btn_decay);
            default:
                return "";
        }

    }

    private String initPhase(int phase) {
        switch (phase) {
            case 0:
                return getContext().getResources().getString(R.string.phaseA);
            case 1:
                return getContext().getResources().getString(R.string.phaseB);
            case 2:
                return getContext().getResources().getString(R.string.phaseC);
            default:
                return "";
        }

    }

    private void showDatePickDialog() {
        CustomDatePickerDialogFragment fragment = new CustomDatePickerDialogFragment();
        fragment.setOnSelectedDateListener(this);
        Bundle bundle = new Bundle();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        bundle.putSerializable(CustomDatePickerDialogFragment.CURRENT_DATE, currentDate);

        long start = 0;
        long day = 24 * 60 * 60 * 1000;
        long end = currentDate.getTimeInMillis() - day;
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(start);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(end);
        bundle.putSerializable(CustomDatePickerDialogFragment.START_DATE, startDate);
        bundle.putSerializable(CustomDatePickerDialogFragment.END_DATE, currentDate);

        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), CustomDatePickerDialogFragment.class.getSimpleName());

    }

    @Override
    public void onSelectedDate(int year, int monthOfYear, int dayOfMonth) {
        if (year == 0 && monthOfYear == 0 && dayOfMonth == 0) {
            tvSearchDate.setText("");
            searchDate = null;
        } else {
            searchDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date curDate = new Date(searchDate);
            //获取当前时间
            searchDate = formatter.format(curDate);
            tvSearchDate.setText(year + getContext().getResources().getString(R.string.date_year) + (monthOfYear + 1) + getContext().getResources().getString(R.string.date_month) + dayOfMonth + getContext().getResources().getString(R.string.date_day));
        }
    }

    public void setFromMain(boolean fromMain) {
        this.fromMain = fromMain;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public AssistRecordDialog(@NonNull BaseActivity context) {
        super(context);
        activity = context;

    }

}
