/**
 * Copyright 2023 bejson.com
 */
package net.kehui.www.t_907_origin_V2.bean907;

/**
 * Auto-generated: 2023-12-20 19:21:35
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/    //协助详情获取，接口返回实体类   //GC20231221
 */
public class AssistDetailBean {

    private String id;
    private String assistId;
    private String faultType;
    private int range;
    private int dotNum;     //虚光标位置？
    private int solidPos;   //实光标位置？
    private String gain;
    private int speed;
    private String content; //回复内容
    private String faultData;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setAssistId(String assistId) {
        this.assistId = assistId;
    }
    public String getAssistId() {
        return assistId;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }
    public String getFaultType() {
        return faultType;
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

    public void setGain(String gain) {
        this.gain = gain;
    }
    public String getGain() {
        return gain;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getSpeed() {
        return speed;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setFaultData(String faultData) {
        this.faultData = faultData;
    }
    public String getFaultData() {
        return faultData;
    }

    @Override
    public String toString() {  //添加了转换器自动输出 //GC20231223
        return "AssistDetailBean{" +
                "id='" + id + '\'' +
                ", assistId='" + assistId + '\'' +
                ", faultType='" + faultType + '\'' +
                ", range=" + range +
                ", dotNum=" + dotNum +
                ", solidPos=" + solidPos +
                ", gain='" + gain + '\'' +
                ", speed=" + speed +
                ", content='" + content + '\'' +
//                ", faultData='" + faultData + '\'' +  //数据太长了，不想显示    //GC202312230
                '}';
    }
}