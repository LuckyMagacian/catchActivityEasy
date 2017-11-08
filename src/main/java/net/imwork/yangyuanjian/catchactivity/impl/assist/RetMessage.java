package net.imwork.yangyuanjian.catchactivity.impl.assist;

import com.alibaba.fastjson.JSON;

/**
 * Created by thunderobot on 2017/11/6.
 */
public class RetMessage {
    private String errCode;
    private String errMessge;
    private Object detail;

    public RetMessage(String errCode, String errMessge, Object detail) {
        this.errCode = errCode;
        this.errMessge = errMessge;
        this.detail = detail;
    }

    public String toJson(){
        return JSON.toJSONString(this);
    }

    @Override
    public String toString() {
        return "RetMessage{" +
                "errCode='" + errCode + '\'' +
                ", errMessge='" + errMessge + '\'' +
                ", detail=" + detail +
                '}';
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessge() {
        return errMessge;
    }

    public void setErrMessge(String errMessge) {
        this.errMessge = errMessge;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }
}
