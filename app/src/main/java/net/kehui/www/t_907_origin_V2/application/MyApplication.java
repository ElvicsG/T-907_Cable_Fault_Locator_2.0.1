package net.kehui.www.t_907_origin_V2.application;

import android.app.Application;

import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;

import net.kehui.www.t_907_origin_V2.util.MultiLanguageUtil;
import net.kehui.www.t_907_origin_V2.util.StateUtils;

/**
 * @author ELVICS-WORK
 */
public class MyApplication extends Application {

    public static MyApplication instances;


    @Override
    public void onCreate() {
        super.onCreate();

        instances = this;
        MultiLanguageUtil.init(getApplicationContext());
        String languageType = StateUtils.getString(MyApplication.getInstances(), AppConfig.CURRENT_LANGUAGE, "follow_sys"); //默认跟随系统语言  //GC20230912
        MultiLanguageUtil.getInstance().updateLanguage(languageType);
        Constant.currentLanguage = languageType;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        String languageType = StateUtils.getString(MyApplication.getInstances(), AppConfig.CURRENT_LANGUAGE, "follow_sys"); //默认跟随系统语言  //GC20230912
        MultiLanguageUtil.getInstance().updateLanguage(languageType);
        Constant.currentLanguage = languageType;

    }

    public static MyApplication getInstances() {
        return instances;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

}
