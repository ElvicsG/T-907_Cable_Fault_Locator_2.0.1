package net.kehui.www.t_907_origin_V2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * @author Gong
 * @date 2022/05/19
 * 这是范围栏
 */
public class RangeFragment extends Fragment {
    @BindView(R.id.btn_250m)
    public ImageView btn250m;
    @BindView(R.id.btn_500m)
    public ImageView btn500m;
    @BindView(R.id.btn_1km)
    public ImageView btn1km;
    @BindView(R.id.btn_2km)
    public ImageView btn2km;
    @BindView(R.id.btn_4km)
    public ImageView btn4km;
    @BindView(R.id.btn_8km)
    public ImageView btn8km;
    @BindView(R.id.btn_16km)
    public ImageView btn16km;
    @BindView(R.id.btn_32km)
    public ImageView btn32km;
    @BindView(R.id.btn_64km)
    public ImageView btn64km;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rangeLayout = inflater.inflate(R.layout.fragment_range, container, false);
        unbinder = ButterKnife.bind(this, rangeLayout);
        return rangeLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn250m.setEnabled(true);
        btn500m.setEnabled(false);
        btn1km.setEnabled(true);
        btn2km.setEnabled(true);
        btn4km.setEnabled(true);
        btn8km.setEnabled(true);
        btn16km.setEnabled(true);
        btn32km.setEnabled(true);
        btn64km.setEnabled(true);
        btn500m.setImageResource(R.drawable.bg_500m1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_250m,R.id.btn_500m, R.id.btn_1km, R.id.btn_2km, R.id.btn_4km, R.id.btn_8km, R.id
            .btn_16km, R.id.btn_32km, R.id.btn_64km})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_250m:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;     //范围切换后状态记录 //GC20220801
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x99;
                //切换范围后直接发送测试命令    //GC20220706
                ((ModeActivity) getActivity()).modeRangeTest();
                break;
            case R.id.btn_500m:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x11;
                ((ModeActivity) getActivity()).modeRangeTest();  //GC20220706
                break;
            case R.id.btn_1km:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x22;
                ((ModeActivity) getActivity()).modeRangeTest();  //GC20220706
                break;
            case R.id.btn_2km:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x33;
                ((ModeActivity)getActivity()).modeRangeTest();  //GC20220706
                break;
            case R.id.btn_4km:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x44;
                ((ModeActivity)getActivity()).modeRangeTest();  //GC20220706
                break;
            case R.id.btn_8km:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x55;
                ((ModeActivity)getActivity()).modeRangeTest();  //GC20220706
                break;
            case R.id.btn_16km:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x66;
                ((ModeActivity)getActivity()).modeRangeTest();  //GC20220706
                break;
            case R.id.btn_32km:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x77;
                ((ModeActivity)getActivity()).modeRangeTest();  //GC20220706
                break;
            case R.id.btn_64km:
                ((ModeActivity) Objects.requireNonNull(getActivity())).rangeChanged = true;
                ((ModeActivity) Objects.requireNonNull(getActivity())).range = 0x88;
                ((ModeActivity)getActivity()).modeRangeTest();  //GC20220706
                break;
            default:
                break;
        }
    }
}
