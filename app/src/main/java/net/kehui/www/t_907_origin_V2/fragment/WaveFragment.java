package net.kehui.www.t_907_origin_V2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.application.Constant;
import net.kehui.www.t_907_origin_V2.view.ModeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Gong
 * @date 2019/07/04
 */
public class WaveFragment extends Fragment {
    @BindView(R.id.btn_zoom_in)
    public ImageView btnZoomIn;
    @BindView(R.id.btn_zoom_out)
    public ImageView btnZoomOut;
    @BindView(R.id.btn_res)
    public ImageView btnRes;
    @BindView(R.id.btn_memory)
    public ImageView btnMemory;
    @BindView(R.id.btn_compare)
    public ImageView btnCompare;
    @BindView(R.id.wavePrevious)
    public ImageView btnWavePrevious;
    @BindView(R.id.waveNext)
    public ImageView btnWaveNext;
    @BindView(R.id.bt_pulse_width)
    public ImageView btnPulse;
    @BindView(R.id.btn_zero)
    public ImageView  btnZero;
    @BindView(R.id.btn_file1)
    public ImageView  btnFile1;
    @BindView(R.id.btn_update1)
    public ImageView  btnUP;
    @BindView(R.id.btn_lead1)
    public ImageView  btnLead1;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View optionLayout = inflater.inflate(R.layout.wave_layout, container, false);
        unbinder = ButterKnife.bind(this, optionLayout);
        return optionLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //GC20190705 操作栏fragment初始化——无波形选择按钮
        btnWavePrevious.setVisibility(View.GONE);
        btnWaveNext.setVisibility(View.GONE);
        btnZero.setVisibility(View.GONE);

        //初始化按键无效显示效果
        btnZoomOut.setEnabled(false);
        btnZoomIn.setEnabled(false);   //jk20210125
        btnRes.setEnabled(false);
        btnWaveNext.setEnabled(false);
        btnWavePrevious.setEnabled(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_zoom_in, R.id.btn_zoom_out, R.id.btn_res, R.id.btn_memory, R.id.btn_compare, R.id.wavePrevious, R.id.waveNext,R.id.bt_pulse_width,R.id.btn_zero,R.id.btn_file1,R.id.btn_update1,R.id.btn_lead1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_zoom_in:
                //GC20190711
                int density = ((ModeActivity) Objects.requireNonNull(getActivity())).getDensity();

                if (density > 1) {
                    density = density / 2;
                    ((ModeActivity) getActivity()).setDensity(density);
                    btnZoomOut.setEnabled(true);
                    btnRes.setEnabled(true);
                    btnZoomOut.setImageResource(R.drawable.bg_zoom2_selector); //jk20210130cs
                    btnRes.setImageResource(R.drawable.bg_res_selector);  //jk20210130cs
                }
                //无法放大
                if (density == 1) {
                    btnZoomIn.setEnabled(false);
                    btnZoomIn.setImageResource(R.drawable.bg_fangda); //jk20210130cs
                }
                break;
            case R.id.btn_zoom_out:
                density = ((ModeActivity) Objects.requireNonNull(getActivity())).getDensity();
                if (density < Constant.DensityMax) {
                    density = density * 2;
                    ((ModeActivity) getActivity()).setDensity(density);
                    btnZoomIn.setEnabled(true);
                    btnRes.setEnabled(true);
                    btnZoomIn.setImageResource(R.drawable.bg_zoom1_selector); //jk20210130cs
                    btnRes.setImageResource(R.drawable.bg_res_selector);  //jk20210130cs
                }
                //缩小到最初显示，只显示放大按钮
                if (density == Constant.DensityMax) {
                    btnZoomOut.setEnabled(false);
                    btnRes.setEnabled(false);
                    btnZoomOut.setImageResource(R.drawable.bg_suoxiao);//jk20210130cs
                    btnRes.setImageResource(R.drawable.bg_huanyuan);//jk20210130cs
                }
                break;
            case R.id.btn_res:
                ((ModeActivity) Objects.requireNonNull(getActivity())).setDensity(Constant.DensityMax);
                btnZoomIn.setEnabled(true);
                btnZoomOut.setEnabled(false);
                btnRes.setEnabled(false);
                btnZoomIn.setImageResource(R.drawable.bg_zoom1_selector); //jk20210130cs
                btnZoomOut.setImageResource(R.drawable.bg_suoxiao);//jk20210130cs
                btnRes.setImageResource(R.drawable.bg_huanyuan);//jk20210130cs
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
                    ((ModeActivity) getActivity()).setSelectSim(((ModeActivity) getActivity()).selectSim);
                    btnWaveNext.setEnabled(true);
                }
                //到第1组波形，下翻按钮点击无效
                if (((ModeActivity) getActivity()).selectSim == 1) {
                    btnWavePrevious.setEnabled(false);
                }
                break;
            case R.id.waveNext:
                ((ModeActivity) getActivity()).selectSim = ((ModeActivity) Objects.requireNonNull(getActivity())).getSelectSim();
                if (((ModeActivity) getActivity()).selectSim < 8) {
                    ((ModeActivity) getActivity()).selectSim++;
                    ((ModeActivity) getActivity()).setSelectSim(((ModeActivity) getActivity()).selectSim);
                    btnWavePrevious.setEnabled(true);
                }
                //到第8组波形，上翻按钮点击无效
                if (((ModeActivity) getActivity()).selectSim == 8) {
                    btnWaveNext.setEnabled(false);
                }
                break;
            case R.id.bt_pulse_width:
                ((ModeActivity) getActivity()).showPulseWidth();
                break;
            case R.id.btn_zero:
                //GC20190712    //G?
                int simZero = ((ModeActivity) Objects.requireNonNull(getActivity())).zero;
                if (((ModeActivity) getActivity()).mode == 0x33 && ((ModeActivity) getActivity()).range ==  0x99) {
                    simZero = simZero / 2;
                }
                ((ModeActivity) getActivity()).setSimZero(simZero);
                break;
            case  R.id.btn_file1:
                ((ModeActivity) getActivity()). showFileView();
               break;
            case R.id.btn_update1:  //jk20210201
                ((ModeActivity) getActivity()).showup();
                ((ModeActivity) getActivity()).downloadFile1();
                break;
            case R.id.btn_lead1:
                ((ModeActivity) getActivity()).showLeadView1();
                btnFile1.setEnabled(false);  //jk20210427
                break;
            default:
                break;

        }
    }
}
