package net.kehui.www.t_907_origin_V2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import net.kehui.www.t_907_origin_V2.ConnectService;
import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.application.Constant;
import net.kehui.www.t_907_origin_V2.view.ModeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

/**
 * @author Gong
 * @date 2022/05/19
 * 这是操作栏
 */
public class OperationFragment extends Fragment {
    @BindView(R.id.btn_zoom_in)
    public ImageView btnZoomIn;
    @BindView(R.id.btn_zoom_out)
    public ImageView btnZoomOut;
    @BindView(R.id.btn_memory)
    public ImageView btnMemory;
    @BindView(R.id.btn_compare)
    public ImageView btnCompare;
    @BindView(R.id.btn_res)
    public ImageView btnRes;
    @BindView(R.id.bt_pulse_width)
    public ImageView btnPulse;
    @BindView(R.id.btn_lead1)
    public ImageView  btnLead1;
    @BindView(R.id.btn_file1)
    public ImageView  btnFile1;
    @BindView(R.id.btn_update1)
    public ImageView  btnUP;
    @BindView(R.id.wavePrevious)
    public ImageView btnWavePrevious;
    @BindView(R.id.waveNext)
    public ImageView btnWaveNext;
    @BindView(R.id.btn_zero)
    public ImageView  btnZero;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View optionLayout = inflater.inflate(R.layout.fragment_operation, container, false);
        unbinder = ButterKnife.bind(this, optionLayout);
        return optionLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //按照TDR方式初始化    //GC20220819
        btnZoomIn.setEnabled(false);
        btnZoomIn.setImageResource(R.drawable.bg_fangda);
        btnZoomOut.setEnabled(false);
        btnZoomOut.setImageResource(R.drawable.bg_suoxiao);
        btnRes.setEnabled(false);
        btnRes.setImageResource(R.drawable.bg_huanyuan);
        btnWavePrevious.setVisibility(View.GONE);
        btnWaveNext.setVisibility(View.GONE);
        btnZero.setVisibility(View.GONE);
        btnLead1.setVisibility(View.GONE);  //GC20220914    “延长线”按钮不显示
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_zoom_in, R.id.btn_zoom_out, R.id.btn_res, R.id.btn_memory, R.id.btn_compare, R.id.wavePrevious, R.id.waveNext,R.id.bt_pulse_width,R.id.btn_zero,R.id.btn_file1,R.id.btn_update1,R.id.btn_lead1})
    public void onViewClicked(View view) {
        if (!ConnectService.isConnected) {
            ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetOperation = true;    //未连接,操作可以点击 //GC20221019
        }
        //快速点击限制    //GC20221019
        if ( ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetOperation == false) {
            return;
        }
        ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetOperation = false;
        switch (view.getId()) {
            case R.id.btn_zoom_in:
                //GC20190711
                int density = ((ModeActivity) Objects.requireNonNull(getActivity())).getDensity();
                if (density > 1) {
                    density = density / 2;
                    ((ModeActivity) getActivity()).setDensity(density); //GC20220822
                }
                break;
            case R.id.btn_zoom_out:
                density = ((ModeActivity) Objects.requireNonNull(getActivity())).getDensity();
                if (density < Constant.DensityMax) {
                    density = density * 2;
                    ((ModeActivity) getActivity()).setDensity(density); //GC20220822
                }
                break;
            case R.id.btn_res:
                ((ModeActivity) Objects.requireNonNull(getActivity())).setDensity(Constant.DensityMax); //GC20220822
                break;
            case R.id.btn_memory:
                ((ModeActivity) Objects.requireNonNull(getActivity())).clickMemory();
                break;
            case R.id.btn_compare:
                ((ModeActivity) Objects.requireNonNull(getActivity())).clickCompare();
                break;
            case R.id.wavePrevious:
                //GC20190702 SIM共8组，从1-8
                ((ModeActivity) getActivity()).selectSim = ((ModeActivity) Objects.requireNonNull(getActivity())).getSelectSim();
                if (((ModeActivity) getActivity()).selectSim > 1) {
                    ((ModeActivity) getActivity()).selectSim--;
                }
                ((ModeActivity) getActivity()).setSelectSim(((ModeActivity) getActivity()).selectSim);  //GC20220820
                break;
            case R.id.waveNext:
                ((ModeActivity) getActivity()).selectSim = ((ModeActivity) Objects.requireNonNull(getActivity())).getSelectSim();
                if (((ModeActivity) getActivity()).selectSim < 8) {
                    ((ModeActivity) getActivity()).selectSim++;
                }
                ((ModeActivity) getActivity()).setSelectSim(((ModeActivity) getActivity()).selectSim);  //GC20220820
                break;
            case R.id.bt_pulse_width:   //脉宽去掉
                ((ModeActivity) getActivity()).showPulseWidth();
                break;
            case R.id.btn_zero:
                //GC20190712    //G?
                int simZero = ((ModeActivity) Objects.requireNonNull(getActivity())).zero;
                if (((ModeActivity) getActivity()).mode == 0x33 && ((ModeActivity) getActivity()).range ==  0x99) {
                    simZero = simZero / 2;
                }
                ((ModeActivity) getActivity()).setSimZero(simZero);
                ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetOperation = true;    //GC20221019
                break;
            case  R.id.btn_file1:
                ((ModeActivity) getActivity()). showFileView();
                ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetOperation = true;    //GC20221019
               break;
            case R.id.btn_update1:
                ((ModeActivity) getActivity()).showUp();
                ((ModeActivity) getActivity()).downloadFile1();
                ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetOperation = true;    //GC20221019
                break;
            case R.id.btn_lead1:    //延长线去掉
                ((ModeActivity) getActivity()).showLeadView1();
                break;
            default:
                break;
        }
    }

    /**
     * 长按更新按键弹出设备编号弹窗 //GC20220517
     */
    @OnLongClick({R.id.btn_update1})
    public boolean onLongClick2(View view){
        ((ModeActivity) getActivity()).showCurrentDevice();
        //弹窗弹出时操作栏按钮需无效，否则会被点击    //GC20220810
        ((ModeActivity) getActivity()).setOperationFalse();
        return true;
    }
}
