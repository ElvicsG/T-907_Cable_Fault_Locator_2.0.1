package net.kehui.www.t_907_origin_V2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.view.ModeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Gong
 * @date 2019/07/03
 */
public class AdjustFragment extends Fragment {
    @BindView(R.id.btn_gain_plus)
    public ImageView btnGainPlus;
    @BindView(R.id.btn_gain_minus)
    public ImageView btnGainMinus;
    @BindView(R.id.btn_balance_plus)
    public ImageView btnBalancePlus;
    @BindView(R.id.btn_balance_minus)
    public ImageView btnBalanceMinus;
    @BindView(R.id.btn_delay_plus)
    public ImageView btnDelayPlus;
    @BindView(R.id.btn_delay_minus)
    public ImageView btnDelayMinus;
    @BindView(R.id.btn_vel_plus)
    public ImageView btnVelPlus;
    @BindView(R.id.btn_vel_minus)
    public ImageView btnVelMinus;
    @BindView(R.id.btn_vel_adjust)
    public ImageView btnVelAdjust;
   /* @BindView(R.id.btn_inductor_plus)
    public Button btnInductorPlus;
    @BindView(R.id.btn_inductor_minus)
    public Button btnInductorMinus;*/
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View adjustLayout = inflater.inflate(R.layout.adj_layout, container, false);
        unbinder = ButterKnife.bind(this, adjustLayout);
        return adjustLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //GC20190705 调节栏fragment初始化——无延时、电感按钮
        btnDelayPlus.setVisibility(View.GONE);
        btnDelayMinus.setVisibility(View.GONE);
        //btnInductorPlus.setVisibility(View.GONE);
       // btnInductorMinus.setVisibility(View.GONE);
        //初始化按键无效显示效果
        btnDelayMinus.setEnabled(false);
       // btnInductorMinus.setEnabled(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_gain_plus, R.id.btn_gain_minus, R.id.btn_balance_plus, R.id.btn_delay_plus, R.id.btn_delay_minus,
            R.id.btn_balance_minus, R.id.btn_vel_plus, R.id.btn_vel_minus,R.id.btn_vel_adjust})
    public void onViewClicked(View view) {

      switch (view.getId()) {
            case R.id.btn_gain_plus:
                int gain = ((ModeActivity) getActivity()).getGain();
                if (gain < 31) {
                    //GC20190704 增益发送命令修改   (命令范围0-31阶)
                    if(((ModeActivity)getActivity()).mode == 0x11){
                        gain++;
                    ((ModeActivity) getActivity()).setGain1(gain);
                    btnGainMinus.setEnabled(true);
                    }else{
                        gain++;
                        ((ModeActivity) getActivity()).setGain(gain);
                        btnGainMinus.setEnabled(true);
                    }
                }
                //增益命令到最大，按钮点击无效
                if (gain == 31) {
                    btnGainPlus.setEnabled(false);
                }
                break;
            case R.id.btn_gain_minus:
                gain = ((ModeActivity) getActivity()).getGain();
                if (gain > 0) {
                    if(((ModeActivity)getActivity()).mode == 0x11){
                        gain--;
                    ((ModeActivity) getActivity()).setGain1(gain);  //jk20210129
                    btnGainPlus.setEnabled(true);
                    }else {
                        gain--;
                        ((ModeActivity) getActivity()).setGain(gain);
                        btnGainPlus.setEnabled(true);
                    }
                }
                if (gain == 0) {
                    btnGainMinus.setEnabled(false);
                }
                break;
            case R.id.btn_balance_plus:
                int balance = ((ModeActivity) getActivity()).getBalance();
                if (balance < 15) {
                    balance++;
                    //GC20190704 平衡发送命令修改   (命令范围0-15阶)
                    ((ModeActivity) getActivity()).setBalance(balance);
                    btnBalanceMinus.setEnabled(true);
                }
                if (balance == 15) {
                    btnBalancePlus.setEnabled(false);
                }
                break;
            case R.id.btn_balance_minus:
                balance = ((ModeActivity) getActivity()).getBalance();
                if (balance > 0) {
                    balance--;
                    ((ModeActivity) getActivity()).setBalance(balance);
                    btnBalancePlus.setEnabled(true);
                }
                if (balance == 0) {
                    btnBalanceMinus.setEnabled(false);
                }
                break;
            case R.id.btn_delay_plus:
                int delay = ((ModeActivity) getActivity()).getDelay();
                if (delay < 1250) {
                    delay = delay + 5;
                    //GC20190704 延时发送命令修改   (延时从0到1250，点击一次增加5，共250阶)
                    ((ModeActivity) getActivity()).setDelay(delay);
                    btnDelayMinus.setEnabled(true);
                }
                if (delay == 1250) {
                    btnDelayPlus.setEnabled(false);
                }
                break;
            case R.id.btn_delay_minus:
                delay = ((ModeActivity) getActivity()).getDelay();
                if (delay > 0) {
                    delay = delay - 5;
                    ((ModeActivity) getActivity()).setDelay(delay);
                    btnDelayPlus.setEnabled(true);
                }
                if (delay == 0) {
                    btnDelayMinus.setEnabled(false);
                }
                break;
            case R.id.btn_vel_plus:
                double velocity = ((ModeActivity) getActivity()).getVelocity();
                if (velocity < 300) {
                    velocity++;
                    ((ModeActivity) getActivity()).setVelocity(velocity);
                    btnVelPlus.setEnabled(true);
                }
                if(velocity == 300){
                    btnVelPlus.setEnabled(false);
                }
                break;
            case R.id.btn_vel_minus:
                velocity = ((ModeActivity) getActivity()).getVelocity();
                if (velocity > 90) {
                    velocity--;
                    ((ModeActivity) getActivity()).setVelocity(velocity);
                    btnVelMinus.setEnabled(true);
                }
                if(velocity == 90){
                    btnVelMinus.setEnabled(false);
                }
                break;
          case R.id.btn_vel_adjust:
              ((ModeActivity) getActivity()).showCalView1();
              break;

            default:
                break;
        }
    }

}
