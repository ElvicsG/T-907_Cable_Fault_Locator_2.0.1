/**
 * Copyright 2023 bejson.com
 */
package net.kehui.www.t_907_origin_V2.bean907;
import java.util.Date;

/**
 * Auto-generated: 2023-12-19 16:48:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/    //一、协助记录获取，接口返回实体类    //GC20231223
 */
public class AssistListDOBean {

    private String assistId;
    private String testUser;
    private String testLoc;
    private String volLevel;
//    private Date testTime;    //系统显示方式    //GC20231223
    private String testTime;
    private String cableLen;
    private int replyStatus;
    public void setAssistId(String assistId) {
        this.assistId = assistId;
    }
    public String getAssistId() {
        return assistId;
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

    public void setVolLevel(String volLevel) {
        this.volLevel = volLevel;
    }
    public String getVolLevel() {
        return volLevel;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }
    public String getTestTime() {
        return testTime;
    }

    public void setCableLen(String cableLen) {
        this.cableLen = cableLen;
    }
    public String getCableLen() {
        return cableLen;
    }

    public void setReplyStatus(int replyStatus) {
        this.replyStatus = replyStatus;
    }
    public int getReplyStatus() {
        return replyStatus;
    }

    @Override
    public String toString() {  //添加了转换器自动输出 //GC20231223
        return "AssistListDOBean{" +
                "assistId='" + assistId + '\'' +
                ", testUser='" + testUser + '\'' +
                ", testLoc='" + testLoc + '\'' +
                ", volLevel='" + volLevel + '\'' +
                ", testTime='" + testTime + '\'' +
                ", cableLen='" + cableLen + '\'' +
                ", replyStatus=" + replyStatus +
                '}';
    }
}