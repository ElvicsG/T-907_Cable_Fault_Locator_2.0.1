package net.kehui.www.t_907_origin_V2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.view.ModeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author IF
 * @date 2018/3/26
 */
public class SettingFragment extends Fragment {
    @BindView(R.id.btn_zero)
    public ImageView  btnZero;
    @BindView(R.id.btn_lang)
    Button  btnLang;
    @BindView(R.id.bt_pulse_width)
   public ImageView btnPulse;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.setting_layout, container, false);
        unbinder = ButterKnife.bind(this, settingLayout);
        return settingLayout;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnLang.setVisibility(View.GONE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({ R.id.btn_zero, R.id.btn_lang,R.id.bt_pulse_width})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_zero:
                //GC20190712    //G?
                int simZero = ((ModeActivity) Objects.requireNonNull(getActivity())).zero;
                if (((ModeActivity) getActivity()).mode == 0x33 && ((ModeActivity) getActivity()).range ==  0x99) {
                    simZero = simZero / 2;
                }
                ((ModeActivity) getActivity()).setSimZero(simZero);
                break;
            case R.id.btn_lang:
                break;
            case R.id.bt_pulse_width:
                ((ModeActivity) getActivity()).showPulseWidth();
                  break;
                default:
                    break;
        }
    }
}
