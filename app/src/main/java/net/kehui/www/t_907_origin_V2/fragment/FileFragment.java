package net.kehui.www.t_907_origin_V2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import net.kehui.www.t_907_origin_V2.R;
import net.kehui.www.t_907_origin_V2.view.ModeActivity;
//import net.kehui.www.t_907_origin.view.ListActivity;
//import net.kehui.www.t_907_origin.view.SaveActivity;
//import net.kehui.www.t_907_origin.view.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author IF
 * @date 2018/3/26
 */
public class FileFragment extends Fragment {
    @BindView(R.id.btn_save)
    ImageView btnSave;
    @BindView(R.id.btn_browse)
    Button       btnBrowse;
    @BindView(R.id.btn_search)
    ImageView      btnDisplay;
    @BindView(R.id.file_sidebar)
    LinearLayout fileSidebar;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fileLayout = inflater.inflate(R.layout.file_layout, container, false);
        unbinder = ButterKnife.bind(this, fileLayout);
        return fileLayout;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnBrowse.setVisibility(View.GONE);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_save, R.id.btn_browse, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                ((ModeActivity) getActivity()).showSaveDialog();
                break;
            case R.id.btn_browse:

                break;
            case R.id.btn_search:
               ((ModeActivity) getActivity()).showRecordsDialog();
                break;
            default:
                break;
        }
    }
}
