package net.imwork.yangyuanjian.catchactivity.spi;

import net.imwork.yangyuanjian.catchactivity.impl.entity.ActivityRecord;
import net.imwork.yangyuanjian.catchactivity.impl.entity.Gift;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by thunderobot on 2017/11/5.
 */
public interface ActivityService {
    /**
     * 添加活动记录接口
     * @param req
     * @param res
     * @return
     */
    String addActivityRecord(HttpServletRequest req,HttpServletResponse res);

    /**
     * 去第三方接口兑换礼品
     * @param record
     * @return
     */
    public String exchangeGift(ActivityRecord record);
    /**
     * 校验手机号是否可以参与活动
     * @return
     */
    boolean checkPhone(String phone,String mac);
    /**
     * 流量充值完成回调接口
     * @param req
     * @param res
     * @return
     */
    String notify(HttpServletRequest req,HttpServletResponse res);
    /**
     * 分享链接进入接口
     * @param req
     * @param res
     * @return
     */
     String shareIn(HttpServletRequest req,HttpServletResponse res);
    /**
     * 手机号码查询奖品到账情况接口
     * @param req
     * @param res
     * @return
     */
     String giftQuery(HttpServletRequest req,HttpServletResponse res);
}
