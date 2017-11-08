package net.imwork.yangyuanjian.catchactivity.impl.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 美阳回调通知响应
 * Created by yangyuanjian on 2017/11/8.
 */
@TableName("notify_response")
public class NotifyResponse extends Model<NotifyResponse>{
    /**通知编号*/
    @TableId("notify_id")
    private Long notifyId;
    /**订单编号*/
    @TableField
    private String trade_id;
    /**平台订单编号*/
    @TableField
    private String order_num;
    /**交易状态*/
    @TableField
    private String status;
    /**卡券的券编码*/
    @TableField
    private String card_key;
    /**卡券密码*/
    @TableField
    private String card_password;
    /**扣款金额*/
    @TableField
    private String price;
    /**错误信息*/
    @TableField
    private String errmsg;
    /**通知时间*/
    @TableField
    private String nofity_time;
    @Override
    protected Serializable pkVal() {
        return notifyId;
    }

    @Override
    public String toString() {
        return "NotifyResponse{" +
                "notifyId=" + notifyId +
                ", trade_id='" + trade_id + '\'' +
                ", order_num='" + order_num + '\'' +
                ", status='" + status + '\'' +
                ", card_key='" + card_key + '\'' +
                ", card_password='" + card_password + '\'' +
                ", price='" + price + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", nofity_time='" + nofity_time + '\'' +
                '}';
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCard_key() {
        return card_key;
    }

    public void setCard_key(String card_key) {
        this.card_key = card_key;
    }

    public String getCard_password() {
        return card_password;
    }

    public void setCard_password(String card_password) {
        this.card_password = card_password;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getNofity_time() {
        return nofity_time;
    }

    public void setNofity_time(String nofity_time) {
        this.nofity_time = nofity_time;
    }
}
