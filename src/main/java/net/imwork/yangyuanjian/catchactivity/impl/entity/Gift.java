package net.imwork.yangyuanjian.catchactivity.impl.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;

/**
 * Created by thunderobot on 2017/11/5.
 */
@TableName("gift")
public class Gift extends Model<Gift>{
    /**最大兑换次数*/
    public static final Integer MAX_EXCAHNEG_TIMES=3;

    /**流量充值70M（全国-移动）*/
    public static final Gift MOBILE_50=new Gift("cc00176003","流量充值70M（全国-移动）");
    /**流量充值150M（全国-移动）*/
    public static final Gift MOBILE_100=new Gift("cc00176004","流量充值150M（全国-移动）");
    /**流量充值50M（全国-联通）*/
    public static final Gift UNICOM_50=new Gift("cc00174002","流量充值50M（全国-联通）");
    /**流量充值100M（全国-联通）*/
    public static final Gift UNICOM_100=new Gift("cc00174004","流量充值100M（全国-联通）");
    /**流量充值50M（全国-电信）*/
    public static final Gift UNION_50=new Gift("cc00175002","流量充值50M（全国-电信）");
    /**流量充值100M（全国-电信）*/
    public static final Gift UNION_100=new Gift("cc00175005","流量充值100M（全国-电信）");


    @TableId("gift_id")
    private String giftId;
    @TableField("name")
    private String name;
    public Gift(String giftId,String name){
        this.giftId=giftId;
        this.name=name;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "giftId='" + giftId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.giftId;
    }


    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
