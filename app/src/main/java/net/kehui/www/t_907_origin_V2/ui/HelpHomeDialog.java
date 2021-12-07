package net.kehui.www.t_907_origin_V2.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.util.ScreenUtils;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 34238
 */
public class HelpHomeDialog extends BaseDialog implements View.OnClickListener {

    public HelpHomeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_help_home_dialog);
        ButterKnife.bind(this);

        final WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = ScreenUtils.getScreenWidth(getContext());
        params.height = ScreenUtils.getScreenHeight(getContext());
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    @Override
    @OnClick({R.id.iv_close})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_close) {
            dismiss();
        }
    }

}
