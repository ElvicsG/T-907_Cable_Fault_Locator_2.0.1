package net.kehui.www.t_907_origin_V2.ui;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import net.kehui.www.t_907_origin_V2.dao.DataAssistDao;
import net.kehui.www.t_907_origin_V2.global.BaseAppDataAssist;

/**
 * @author Gong
 * @date 2023/6/29  //GC20230629
 */
public class BaseAssistDialog extends Dialog {

    public DataAssistDao daoAssist;

    public BaseAssistDialog(@NonNull Context context) {
        super(context);
        //数据库相关 //20200520
        BaseAppDataAssist dbAssist = Room.databaseBuilder(getContext(), BaseAppDataAssist.class, "database-waveAssist").allowMainThreadQueries().build();
        daoAssist = dbAssist.dataAssistDao();
    }

    public BaseAssistDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        BaseAppDataAssist dbAssist = Room.databaseBuilder(getContext(), BaseAppDataAssist.class, "database-waveAssist").allowMainThreadQueries().build();
        daoAssist = dbAssist.dataAssistDao();
    }

    protected BaseAssistDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        BaseAppDataAssist dbAssist = Room.databaseBuilder(getContext(), BaseAppDataAssist.class, "database-waveAssist").allowMainThreadQueries().build();
        daoAssist = dbAssist.dataAssistDao();
    }
}
