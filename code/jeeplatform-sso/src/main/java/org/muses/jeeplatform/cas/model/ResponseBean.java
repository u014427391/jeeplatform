package org.muses.jeeplatform.cas.model;

import java.util.Map;
 
public class ResponseBean {
    //返回状态 1 成功 2 失败
    public String status;
    //返回数据
    public Map data;
    //返回信息
    public String msg;
     
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Map getData() {
        return data;
    }
    public void setData(Map data) {
        this.data = data;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}