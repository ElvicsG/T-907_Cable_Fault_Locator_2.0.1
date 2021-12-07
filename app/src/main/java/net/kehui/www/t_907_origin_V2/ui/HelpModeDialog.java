package net.kehui.www.t_907_origin_V2.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.percentlayout.widget.PercentRelativeLayout;

import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.application.Constant;
import net.kehui.www.t_907_origin_V2.util.ScreenUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.kehui.www.t_907_origin_V2.base.BaseActivity.DECAY;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.ICM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.ICM_DECAY;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.SIM;
import static net.kehui.www.t_907_origin_V2.base.BaseActivity.TDR;

/**
 * @author 34238
 */
public class HelpModeDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.rl_mode)
    PercentRelativeLayout rlMode;
    @BindView(R.id.iv_circle)
    ImageView ivCircle;

    private boolean isClicked;

    public HelpModeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_help_mode_dialog);
        ButterKnife.bind(this);

        ivCircle.setVisibility(View.GONE);

        final WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = ScreenUtils.getScreenWidth(getContext());
        params.height = ScreenUtils.getScreenHeight(getContext());
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setEtMode();

    }

    private void setEtMode() {
        int mode = Constant.ModeValue;
        switch (mode) {
            case TDR:
                rlMode.setBackgroundResource(R.drawable.bg_tdr_excel);
                break;
            case ICM:
                rlMode.setBackgroundResource(R.drawable.bg_icm_decay_excel);
                break;
            case ICM_DECAY:
                rlMode.setBackgroundResource(R.drawable.bg_icm_decay_excel);
                break;
            case SIM:
                rlMode.setBackgroundResource(R.drawable.bg_sim_excel);
                break;
            case DECAY:
                rlMode.setBackgroundResource(R.drawable.bg_decay_excel);
                break;
            default:
                break;
        }
    }

    /**
     * 暂时不用
     * @param view
     */
    @Override
    @OnClick({R.id.iv_close,R.id.iv_circle})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_close) {
            dismiss();
        } else if (view.getId() == R.id.iv_circle){
            if(isClicked){
                rlMode.setBackgroundResource(R.drawable.bg_tdr_excel);
            } else {
                rlMode.setBackgroundResource(R.drawable.bg_tdr_excel);
            }
            isClicked = !isClicked;
        }
    }

}
