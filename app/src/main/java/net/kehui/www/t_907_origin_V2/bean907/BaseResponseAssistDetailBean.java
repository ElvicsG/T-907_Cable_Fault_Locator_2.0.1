package net.kehui.www.t_907_origin_V2.bean907;

/**
 * Auto-generated: 2023-12-20 19:21:35
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/    //二、协助详情获取，接口返回基本实体类  //GC20231221
 */
public class BaseResponseAssistDetailBean {

    private int code;
    private String msg;
    private AssistDetailBean data;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(AssistDetailBean data) {
        this.data = data;
    }
    public AssistDetailBean getData() {
        return data;
    }

    @Override
    public String toString() {  //添加了转换器自动输出 //GC20231223
        return "BaseResponseAssistDetailBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}