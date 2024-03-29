package net.kehui.www.t_907_origin_V2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import net.kehui.www.t_907_origin_V2.ConnectService;
import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.view.ModeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Gong
 * @date 2022/05/19
 * 这是方式栏
 */
public class ModeFragment extends Fragment {
    @BindView(R.id.btn_tdr)
    public ImageView btnTdr;
    @BindView(R.id.btn_icm)
    public ImageView btnIcm;
    @BindView(R.id.btn_sim)
    public ImageView btnSim;
    @BindView(R.id.btn_decay)
    public ImageView btnDecay;
    @BindView(R.id.btn_icmc)
    public ImageView btnIcmc;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View modeLayout = inflater.inflate(R.layout.fragment_mode, container, false);
        unbinder = ButterKnife.bind(this, modeLayout);
        return modeLayout;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //按照TDR方式初始化    //GC20220819
        btnTdr.setEnabled(false);
        btnTdr.setImageResource(R.drawable.bg_tdr_mode_pressed);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_tdr, R.id.btn_icm, R.id.btn_sim, R.id.btn_decay, R.id.btn_icmc})
    public void onViewClicked(View view) {
        if (!ConnectService.isConnected) {
            ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetMode = true;    //未连接,操作可以点击 //GC20221019
        }
        //快速点击限制    //GC20221019
        if ( ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetMode == false) {
            return;
        }
        ((ModeActivity) Objects.requireNonNull(getActivity())).allowSetMode = false;
        ((ModeActivity) Objects.requireNonNull(getActivity())).modeChanged = true;     //范围切换后状态记录 //GC20221017
        switch (view.getId()) {
            case R.id.btn_tdr:
               ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x11);
               ((ModeActivity)getActivity()).modeRangeTest();
                break;
            case R.id.btn_icm:
                ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x22);
                ((ModeActivity)getActivity()).modeRangeTest();
                break;
            case R.id.btn_sim:
                ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x33);
                ((ModeActivity)getActivity()).modeRangeTest();
                break;
            case R.id.btn_icmc:
                ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x55);
                ((ModeActivity)getActivity()).modeRangeTest();
                break;
            case R.id.btn_decay:
                ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x44);
                ((ModeActivity)getActivity()).modeRangeTest();
                break;
            default:
                break;
        }
    }
}
