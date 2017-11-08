package net.imwork.yangyuanjian.catchactivity.impl.entity;

/**
 * 流量接口响应
 * Created by thunderobot on 2017/11/6.
 */
public class ApiResponse {
    public static final String SUCCESS="0";


    /**受理结果*/
    private String code;
    /**错误码信息（code不为0时才有）*/
    private String errmsg;
    /**订单号（卡券平台订单号）（code为0时才有）*/
    private String order_num;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code='" + code + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", order_num='" + order_num + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }
}
