package net.kehui.www.t_907_origin_V2.bean907;

/**
 * 一、协助记录获取，查询参数实体类   //GC20231223
 */
public class AssistListVOBean {
    private String deviceId;
    private String testTime;
    private int replyStatus;
    private String beginDate;
    private String endDate;
    private int pageSize;
    private int pageNum;

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

    public void setReplyStatus(int replyStatus) {
        this.replyStatus = replyStatus;
    }
    public int getReplyStatus() {
        return replyStatus;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }
    public String getBeginDate() {
        return beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getEndDate() {
        return endDate;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageSize() {
        return pageSize;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageNum() {
        return pageNum;
    }
}
