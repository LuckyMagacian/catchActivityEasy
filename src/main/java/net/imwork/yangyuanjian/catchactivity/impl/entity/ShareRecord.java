package net.imwork.yangyuanjian.catchactivity.impl.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import sun.security.provider.SHA;

import java.io.Serializable;

/**
 * 分享记录表
 * Created by thunderobot on 2017/11/7.
 */
@Deprecated
@TableName("share_record")
public class ShareRecord extends Model<ShareRecord>{
    /**分享记录编号*/
    @TableId("record_id")
    private Long recordId;
    /**分享者手机号*/
    @TableField("share_phone")
    private String sharePhone;
    /**接受者手机号*/
    @TableField("accept_phone")
    private String acceptPhone;
    /**分享者mac*/
    @TableField("share_mac")
    private String shareMac;
    /**接受者mac*/
    @TableField("accept_mac")
    private String acceptMac;
    /**接受时间*/
    @TableField("accept_time")
    private String acceptTime;
    /**接受者是否参与了活动*/
    @TableField("enjoy_activity")
    private String enjoyActivity;


    @Override
    public String toString() {
        return "ShareRecord{" +
                "recordId=" + recordId +
                ", sharePhone='" + sharePhone + '\'' +
                ", acceptPhone='" + acceptPhone + '\'' +
                ", shareMac='" + shareMac + '\'' +
                ", acceptMac='" + acceptMac + '\'' +
                ", acceptTime='" + acceptTime + '\'' +
                ", enjoyActivity='" + enjoyActivity + '\'' +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.recordId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getSharePhone() {
        return sharePhone;
    }

    public void setSharePhone(String sharePhone) {
        this.sharePhone = sharePhone;
    }

    public String getAcceptPhone() {
        return acceptPhone;
    }

    public void setAcceptPhone(String acceptPhone) {
        this.acceptPhone = acceptPhone;
    }

    public String getShareMac() {
        return shareMac;
    }

    public void setShareMac(String shareMac) {
        this.shareMac = shareMac;
    }

    public String getAcceptMac() {
        return acceptMac;
    }

    public void setAcceptMac(String acceptMac) {
        this.acceptMac = acceptMac;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getEnjoyActivity() {
        return enjoyActivity;
    }

    public void setEnjoyActivity(String enjoyActivity) {
        this.enjoyActivity = enjoyActivity;
    }
}
