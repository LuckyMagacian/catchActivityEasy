package net.imwork.yangyuanjian.catchactivity.impl.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 活动记录
 * Created by thunderobot on 2017/11/5.
 */
@TableName("activity_record")
public class ActivityRecord extends Model<ActivityRecord>{
    public static final String HAS_EXCHANGED="0";
    public static final String NOT_EXCHANGED="1";

    /*活动记录编号*/
    @TableId("record_id")
    private Long recordId;
    /**用户手机号*/
    @TableField("phone")
    private String phone;
    /**流量编号*/
    @TableField("flows")
    private String flows;
    /**设备指纹*/
    @TableField("mac")
    private String mac;
    /**参与时间*/
    @TableField("time")
    private String time;
    /**备注-流量名称*/
    @TableField("remark")
    private String remark;
    /**该记录是否已经成功兑换*/
    @TableField("exchanged")
    private String exchanged;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFlows() {
        return flows;
    }

    public void setFlows(String flows) {
        this.flows = flows;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Override
    protected Serializable pkVal() {
        return this.recordId;
    }

    public String getExchanged() {
        return exchanged;
    }

    public void setExchanged(String exchanged) {
        this.exchanged = exchanged;
    }

    @Override
    public String toString() {
        return "ActivityRecord{" +
                "recordId=" + recordId +
                ", phone='" + phone + '\'' +
                ", flows='" + flows + '\'' +
                ", mac='" + mac + '\'' +
                ", time='" + time + '\'' +
                ", remark='" + remark + '\'' +
                ", exchanged='" + exchanged + '\'' +
                '}';
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

