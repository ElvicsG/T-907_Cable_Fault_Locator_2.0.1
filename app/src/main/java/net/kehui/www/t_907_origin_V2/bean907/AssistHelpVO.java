package net.kehui.www.t_907_origin_V2.bean907;

/**
 * 协助信息上传，上传表单实体类   //GC20231222
 */
public class AssistHelpVO {
    private String deviceId;    //1.设备标识   //GC20231224
    private String testTime;    //2.测试时间
    private String testUser;    //3.测试用户
    private String testLoc;     //4.测试地点
    private String cableId;     //5.电缆号
    private int cableLen;       //6.电缆全长
    private String volLevel;    //7.电压等级
    private String gain;        //8.增益
    private double speed;       //9.波速度
    private int range;
    private int dotNum;
    private int solidPos;
    private String note;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getDeviceId() {
        return deviceId;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }
    public String getTestTime() {
        return testTime;
    }

    public void setTestUser(String testUser) {
        this.testUser = testUser;
    }
    public String getTestUser() {
        return testUser;
    }

    public void setTestLoc(String testLoc) {
        this.testLoc = testLoc;
    }
    public String getTestLoc() {
        return testLoc;
    }

    public void setCableId(String cableId) {
        this.cableId = cableId;
    }
    public String getCableId() {
        return cableId;
    }

    public void setCableLen(int cableLen) {
        this.cableLen = cableLen;
    }
    public int getCableLen() {
        return cableLen;
    }

    public void setVolLevel(String volLevel) {
        this.volLevel = volLevel;
    }
    public String getVolLevel() {
        return volLevel;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }
    public String getGain() {
        return gain;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getSpeed() {
        return speed;
    }

    public void setRange(int range) {
        this.range = range;
    }
    public int getRange() {
        return range;
    }

    public void setDotNum(int dotNum) {
        this.dotNum = dotNum;
    }
    public int getDotNum() {
        return dotNum;
    }

    public void setSolidPos(int solidPos) {
        this.solidPos = solidPos;
    }
    public int getSolidPos() {
        return solidPos;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return note;
    }

}
