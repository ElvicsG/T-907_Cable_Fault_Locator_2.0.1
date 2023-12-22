package net.kehui.www.t_907_origin_V2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import net.kehui.www.t_907_origin_V2.entity.DataAssist;

/**
 * @author Gong
 * @date 2023/6/29  //协助数据库添加：接口 //GC20230629
 */

@Dao
public interface DataAssistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(DataAssist... data);

    @Delete
    int deleteData(DataAssist... data);

    @Update
    int updateData(DataAssist... data);

    @Query("SELECT * FROM dataAssist")
    DataAssist[] query();

    /**
     * //数据库相关 //20200520
     * @param index
     * @return
     */
    @Query("SELECT  dataid,unit,para,cableId,date,time,mode,range,line,phase,positionVirtual,positionReal,tester,location,testsite,'' as waveData,'' as waveDataSim  FROM dataAssist limit (:index*10),10")
    DataAssist[] queryByIndex(int index);

    @Query("SELECT * FROM dataAssist WHERE dataId = :dataId")
    DataAssist[] queryDataId(int dataId);

    @Query("SELECT * FROM dataAssist WHERE date LIKE :date")

    /**
     * //数据库相关 //20200520
     */
    DataAssist[] queryDate(int date);
    @Query("SELECT   dataid,unit,para,cableId,date,time,mode,range,line,phase,positionVirtual,positionReal,tester,location,testsite,'' as waveData,'' as waveDataSim  FROM dataAssist WHERE date LIKE :date and mode LIKE :mode limit (:index),10")
    DataAssist[] queryDateByIndex(String date, String mode, int index);

    @Query("SELECT   *  FROM dataAssist WHERE dataId=:id")
    DataAssist[] queryWaveById(int id);   //添加

    @Query("SELECT * FROM dataAssist WHERE mode LIKE :mode")
    DataAssist[] queryMode(String mode);

    /**
     * //数据库相关 //20200520
     * @param mode
     * @param index
     * @return
     */
    @Query("SELECT  dataid,unit,para,cableId,date,time,mode,range,line,phase,positionVirtual,positionReal,tester,location,testsite,'' as waveData,'' as waveDataSim   FROM dataAssist WHERE mode LIKE :mode limit (:index),10")
    DataAssist[] queryModeByIndex(String mode, int index);

    @Query("SELECT * FROM dataAssist WHERE range LIKE :range")
    DataAssist[] queryRange(String range);

    @Query("SELECT * FROM dataAssist WHERE line LIKE :line")
    DataAssist[] queryLine(String line);

    @Query("SELECT * FROM dataAssist WHERE phase LIKE :phase")
    DataAssist[] queryPhase(String phase);

    @Query("SELECT * FROM dataAssist WHERE tester LIKE :tester")
    DataAssist[] queryTester(String tester);

    @Query("SELECT * FROM dataAssist WHERE location LIKE :location")
    DataAssist[] queryLocation(String location);
}
