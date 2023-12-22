package net.kehui.www.t_907_origin_V2.global;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import net.kehui.www.t_907_origin_V2.dao.DataAssistDao;
import net.kehui.www.t_907_origin_V2.entity.DataAssist;

/**
 * @author Gong
 * @date 2023/6/29  //协助记录数据库添加   //GC20230629
 */
@Database(entities = DataAssist.class, version = 1)
public abstract class BaseAppDataAssist extends RoomDatabase {
    public abstract DataAssistDao dataAssistDao();
}
