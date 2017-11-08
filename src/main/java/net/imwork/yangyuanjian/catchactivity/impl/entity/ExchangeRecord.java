package net.imwork.yangyuanjian.catchactivity.impl.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 兑换记录
 * Created by thunderobot on 2017/11/6.
 */
@TableName("exchange_record")
public class ExchangeRecord extends Model<ExchangeRecord>{
    /**兑换状态-成功*/
    public static final String STATUS_SUCCESS="0000";
    /**兑换状态-等待*/
    public static final String STATUS_WAIT="0001";
    /**兑换状态-失败*/
    public static final String STATUS_FAIL="0009";
    /**兑换状态-出错*/
    public static final String STATUS_ERROR="9999";

    /**兑换记录id*/
    @TableId("record_id")
    private Long recordId;
    /**礼品编号*/
    @TableField("gift_id")
    private String giftId;
    /**礼品名称*/
    @TableField("gift_name")
    private String giftName;
    /**兑换手机号*/
    @TableField("phone")
    private String phone;
    /**兑换者mac地址*/
    @TableField("mac")
    private String mac;
    /**兑换记录*/
    @TableField("record_time")
    private String recordTime;
    /**当前兑换次数*/
    @TableField("times")
    private Integer times;
    /**兑换状态*/
    @TableField("status")
    private String status;
    /**兑换失败重试次数*/
    @TableField("retryTimes")
    private Integer retryTimes;
    /**成功时订单编号*/
    @TableField("orderNum")
    private String orderNum;
    /**受理结果*/
    @TableField("code")
    private String code;
    /**错误码信息（code不为0时才有）*/
    @TableField("errmsg")
    private String errmsg;
    /**对应的活动记录的id*/
    @TableField("activity_record_id")
    private Long activityRecordId;

    @Override
    public String toString() {
        return "ExchangeRecord{" +
                "recordId=" + recordId +
                ", giftId='" + giftId + '\'' +
                ", giftName='" + giftName + '\'' +
                ", phone='" + phone + '\'' +
                ", mac='" + mac + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", times=" + times +
                ", status='" + status + '\'' +
                ", retryTimes=" + retryTimes +
                ", orderNum='" + orderNum + '\'' +
                ", code='" + code + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", activityRecordId=" + activityRecordId +
                '}';
    }


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public static String getStatusSuccess() {
        return STATUS_SUCCESS;
    }

    public static String getStatusWait() {
        return STATUS_WAIT;
    }

    public static String getStatusFail() {
        return STATUS_FAIL;
    }

    public static String getStatusError() {
        return STATUS_ERROR;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
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

    public Long getActivityRecordId() {
        return activityRecordId;
    }

    public void setActivityRecordId(Long activityRecordId) {
        this.activityRecordId = activityRecordId;
    }

    @Override
    protected Serializable pkVal() {
        return this.recordId;
    }
}
