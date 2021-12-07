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

import static net.kehui.www.t_907_origin_V2.application.Constant.hasSavedPulseWidth;

/**
 * @author Gong
 * @date 2019/07/04
 */
public class RangeFragment extends Fragment {
    @BindView(R.id.btn_250m)
    public ImageView btn250m;
    @BindView(R.id.btn_500m)
    public ImageView       btn500m;
    @BindView(R.id.btn_1km)
    public ImageView       btn1km;
    @BindView(R.id.btn_2km)
    public ImageView       btn2km;
    @BindView(R.id.btn_4km)
    public ImageView       btn4km;
    @BindView(R.id.btn_8km)
    public ImageView       btn8km;
    @BindView(R.id.btn_16km)
    public ImageView       btn16km;
    @BindView(R.id.btn_32km)
    public ImageView       btn32km;
    @BindView(R.id.btn_64km)
    public ImageView      btn64km;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rangeLayout = inflater.inflate(R.layout.range_layout, container, false);
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
                ((ModeActivity)getActivity()).setRange(0x99);
                ((ModeActivity)getActivity()).setGain(13);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 40;
                        ((ModeActivity)getActivity()).setPulseWidth(40);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(40));
                }
                //GC20200527
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 320;
                        ((ModeActivity)getActivity()).setPulseWidth(320);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(false);
                btn500m.setEnabled(true);
                btn1km.setEnabled(true);
                btn2km.setEnabled(true);
                btn4km.setEnabled(true);
                btn8km.setEnabled(true);
                btn16km.setEnabled(true);
                btn32km.setEnabled(true);
                btn64km.setEnabled(true);
                btn250m.setImageResource(R.drawable.bg_250m1); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case R.id.btn_500m:
                ((ModeActivity)getActivity()).setRange(0x11);
                ((ModeActivity)getActivity()).setGain(13);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 40;
                        ((ModeActivity)getActivity()).setPulseWidth(40);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(40));
                }
                //GC20200527
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 320;
                        ((ModeActivity)getActivity()).setPulseWidth(320);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(true);
                btn500m.setEnabled(false);
                btn1km.setEnabled(true);
                btn2km.setEnabled(true);
                btn4km.setEnabled(true);
                btn8km.setEnabled(true);
                btn16km.setEnabled(true);
                btn32km.setEnabled(true);
                btn64km.setEnabled(true);
                btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m1); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case R.id.btn_1km:
              ((ModeActivity)getActivity()).setRange(0x22);
                ((ModeActivity)getActivity()).setGain(13);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 80;
                        ((ModeActivity)getActivity()).setPulseWidth(80);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(80));
                }
                //GC20200527
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 320;
                        ((ModeActivity)getActivity()).setPulseWidth(320);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(true);
                btn500m.setEnabled(true);
                btn1km.setEnabled(false);
                btn2km.setEnabled(true);
                btn4km.setEnabled(true);
                btn8km.setEnabled(true);
                btn16km.setEnabled(true);
                btn32km.setEnabled(true);
                btn64km.setEnabled(true);
                btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1km1); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case R.id.btn_2km:
               ((ModeActivity)getActivity()).setRange(0x33);
                ((ModeActivity)getActivity()).setGain(10);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 160;
                        ((ModeActivity)getActivity()).setPulseWidth(160);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(160));
                }
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 720;
                        ((ModeActivity)getActivity()).setPulseWidth(720);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(true);
                btn500m.setEnabled(true);
                btn1km.setEnabled(true);
                btn2km.setEnabled(false);
                btn4km.setEnabled(true);
                btn8km.setEnabled(true);
                btn16km.setEnabled(true);
                btn32km.setEnabled(true);
                btn64km.setEnabled(true);
                btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2km); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case R.id.btn_4km:
               ((ModeActivity)getActivity()).setRange(0x44);
                ((ModeActivity)getActivity()).setGain(10);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 320;
                        ((ModeActivity)getActivity()).setPulseWidth(320);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(320));
                }
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 2560;
                        ((ModeActivity)getActivity()).setPulseWidth(2560);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(true);
                btn500m.setEnabled(true);
                btn1km.setEnabled(true);
                btn2km.setEnabled(true);
                btn4km.setEnabled(false);
                btn8km.setEnabled(true);
                btn16km.setEnabled(true);
                btn32km.setEnabled(true);
                btn64km.setEnabled(true);
                btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4km); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case R.id.btn_8km:
                ((ModeActivity)getActivity()).setRange(0x55);
                ((ModeActivity)getActivity()).setGain(10);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 640;
                        ((ModeActivity)getActivity()).setPulseWidth(640);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(640));
                }
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 3600;
                        ((ModeActivity)getActivity()).setPulseWidth(3600);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(true);
                btn500m.setEnabled(true);
                btn1km.setEnabled(true);
                btn2km.setEnabled(true);
                btn4km.setEnabled(true);
                btn8km.setEnabled(false);
                btn16km.setEnabled(true);
                btn32km.setEnabled(true);
                btn64km.setEnabled(true);
                btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8km); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case R.id.btn_16km:
                ((ModeActivity)getActivity()).setRange(0x66);
                ((ModeActivity)getActivity()).setGain(9);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 1280;
                        ((ModeActivity)getActivity()).setPulseWidth(1280);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(1280));
                }
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 7120;
                        ((ModeActivity)getActivity()).setPulseWidth(7120);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(true);
                btn500m.setEnabled(true);
                btn1km.setEnabled(true);
                btn2km.setEnabled(true);
                btn4km.setEnabled(true);
                btn8km.setEnabled(true);
                btn16km.setEnabled(false);
                btn32km.setEnabled(true);
                btn64km.setEnabled(true);
                btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16km); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case R.id.btn_32km:
                ((ModeActivity)getActivity()).setRange(0x77);
                ((ModeActivity)getActivity()).setGain(9);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 2560;
                        ((ModeActivity)getActivity()).setPulseWidth(2560);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(2560));
                }
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 10200;
                        ((ModeActivity)getActivity()).setPulseWidth(10200);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(true);
                btn500m.setEnabled(true);
                btn1km.setEnabled(true);
                btn2km.setEnabled(true);
                btn4km.setEnabled(true);
                btn8km.setEnabled(true);
                btn16km.setEnabled(true);
                btn32km.setEnabled(false);
                btn64km.setEnabled(true);
                btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32km); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case R.id.btn_64km:
               ((ModeActivity)getActivity()).setRange(0x88);
                ((ModeActivity)getActivity()).setGain(9);
                if (!hasSavedPulseWidth && ((ModeActivity)getActivity()).mode == 0x11) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidth = 5120;
                        ((ModeActivity)getActivity()).setPulseWidth(5120);
                    }, 20);
                    ((ModeActivity)getActivity()).etPulseWidth.setText(String.valueOf(5120));
                }
                if (((ModeActivity)getActivity()).mode == 0x33) {
                    ((ModeActivity)getActivity()).handler.postDelayed(() -> {
                        ((ModeActivity)getActivity()).pulseWidthSim = 10200;
                        ((ModeActivity)getActivity()).setPulseWidth(10200);
                    }, 20);
                }
                ((ModeActivity)getActivity()).test1();  //jk20210126
                btn250m.setEnabled(true);
                btn500m.setEnabled(true);
                btn1km.setEnabled(true);
                btn2km.setEnabled(true);
                btn4km.setEnabled(true);
                btn8km.setEnabled(true);
                btn16km.setEnabled(true);
                btn32km.setEnabled(true);
                btn64km.setEnabled(false);
                btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                btn64km.setImageResource(R.drawable.bg_64km); //jk20210129
                break;
            default:
                break;
        }
    }

}
