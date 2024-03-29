package net.kehui.www.t_907_origin_V2.ui;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.kehui.www.t_907_origin_V2.R;

import java.util.Calendar;

public class CustomDatePickerDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String CURRENT_DATE = "datepicker_current_date";
    public static final String START_DATE = "datepicker_start_date";
    public static final String END_DATE = "datepicker_end_date";
    Calendar currentDate;
    Calendar startDate;
    Calendar endDate;

    DatePicker datePicker;
    TextView backButton;
    TextView ensureButton;
    TextView clearButton;
    View splitLineV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        Bundle bundle = getArguments();
        currentDate = (Calendar) bundle.getSerializable(CURRENT_DATE);
        startDate = (Calendar) bundle.getSerializable(START_DATE);
        endDate = (Calendar) bundle.getSerializable(END_DATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setDimAmount(0.8f);
        View view = inflater.inflate(R.layout.dialog_date_picker_layout, container, false);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int style;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            style = R.style.ZZBDatePickerDialogLStyle;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            style = R.style.ZZBDatePickerDialogLStyle;
        } else {
            style = getTheme();
        }
        return new Dialog(getActivity(), style);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            datePicker = view.findViewById(R.id.datePickerView);
            backButton = view.findViewById(R.id.back);
            backButton.setOnClickListener(this);
            ensureButton = view.findViewById(R.id.ensure);
            ensureButton.setOnClickListener(this);

            clearButton = view.findViewById(R.id.dateclear);
            clearButton.setOnClickListener(this);


            splitLineV = view.findViewById(R.id.splitLineV);

            datePicker.setCalendarViewShown(false);
            datePicker.setSpinnersShown(true);
            //滚轮模式必须使用确定菜单
            ensureButton.setVisibility(View.VISIBLE);
            clearButton.setVisibility(View.VISIBLE);
            splitLineV.setVisibility(View.VISIBLE);
            initDatePicker();
        }
    }

    private void initDatePicker() {
        if (datePicker == null) {
            return;
        }
        if (currentDate == null) {
            currentDate = Calendar.getInstance();
            currentDate.setTimeInMillis(System.currentTimeMillis());
        }
        datePicker.init(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH), null);
        if (startDate != null) {
            datePicker.setMinDate(startDate.getTimeInMillis());
        }
        if (endDate != null) {
            endDate.set(Calendar.HOUR_OF_DAY, 23);
            endDate.set(Calendar.MINUTE, 59);
            endDate.set(Calendar.SECOND, 59);
            datePicker.setMaxDate(endDate.getTimeInMillis());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                dismiss();
                break;
            case R.id.ensure:
                returnSelectedDateUnderLOLLIPOP();
                break;
            case R.id.dateclear:
                returnCleareDate();
                break;
            default:
                break;
        }
    }

    private void returnCleareDate() {

        if (onSelectedDateListener != null) {
            onSelectedDateListener.onSelectedDate(0, 0, 0);
        }
        dismiss();
    }

    private void returnSelectedDateUnderLOLLIPOP() {
        //bug3:5.0上超过可选区间的日期依然能选中,所以要手动校验.5.1上已解决，但是为了与5.0保持一致，也采用确定菜单返回日期
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 0, 0, 0);
            selectedDate.set(Calendar.MILLISECOND, 0);
            if (selectedDate.before(startDate) || selectedDate.after(endDate)) {
                Toast.makeText(getActivity(), "日期超出有效范围", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (onSelectedDateListener != null) {
            onSelectedDateListener.onSelectedDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onSelectedDateListener = null;
    }

    public interface OnSelectedDateListener {
        void onSelectedDate(int year, int monthOfYear, int dayOfMonth);
    }

    OnSelectedDateListener onSelectedDateListener;

    public void setOnSelectedDateListener(OnSelectedDateListener onSelectedDateListener) {
        this.onSelectedDateListener = onSelectedDateListener;
    }

//    @Override
//    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
//                && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { //LOLLIPOP上，这个回调无效，排除将来可能的干扰
//            return;
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //5.0以下，必须采用滚轮模式，所以需借助确定菜单回传选定值
//            return;
//        }
//        if (onSelectedDateListener != null) {
//            onSelectedDateListener.onSelectedDate(year, monthOfYear, dayOfMonth);
//        }
//        dismiss();
//    }
}