/**
 * Copyright 2023 bejson.com
 */
package net.kehui.www.t_907_origin_V2.bean907;
import java.util.List;

/**
 * Auto-generated: 2023-12-19 16:48:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/    //一、协助记录获取，接口返回基本实体类  //GC20231223
 */
public class BaseResponseAssistListDOBean {

    private int code;
    private String msg;
    private List<AssistListDOBean> data;
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

    public void setData(List<AssistListDOBean> data) {
        this.data = data;
    }
    public List<AssistListDOBean> getData() {
        return data;
    }

    @Override
    public String toString() {  //添加了转换器自动输出 //GC20231223
        return "BaseResponseAssistListDOBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}