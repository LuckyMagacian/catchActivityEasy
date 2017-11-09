package net.imwork.yangyuanjian.catchactivity.impl.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.catchactivity.impl.assist.ConfigManager;
import net.imwork.yangyuanjian.catchactivity.impl.assist.LogFactory;
import net.imwork.yangyuanjian.catchactivity.impl.assist.PhoneCheck;
import net.imwork.yangyuanjian.catchactivity.impl.assist.RetMessage;
import net.imwork.yangyuanjian.catchactivity.impl.dao.ActivityRecordDao;
import net.imwork.yangyuanjian.catchactivity.impl.dao.ExchangeRecordDao;
import net.imwork.yangyuanjian.catchactivity.impl.dao.ShareRecordDao;
import net.imwork.yangyuanjian.catchactivity.impl.entity.*;
import net.imwork.yangyuanjian.catchactivity.spi.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by thunderobot on 2017/11/6.
 */
@Service
public class ActivityServiceImpl implements ActivityService{

    private Supplier<String> timeSuppile=()-> new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    @Resource
    private ActivityRecordDao activityRecordDao;
    @Resource
    private ExchangeRecordDao exchangeRecordDao;
    @Resource
    private ShareRecordDao shareRecordDao;
    @Override
    public String addActivityRecord(HttpServletRequest req, HttpServletResponse res) {
        //获取参数
        String mac=req.getParameter("mac");
        String phone=req.getParameter("phone");
        String flow=req.getParameter("flow");
        String locker="mac["+mac+"],phone["+phone+"],flow["+flow+"]";

        LogFactory.info(this,"尝试添加活动记录,"+locker);

        //获取手机号码对应的运营商,1移动,2联通,3电信,null虚拟或号码不正常
        Integer phoneType= PhoneCheck.getServiceProvider(phone);
        if(phoneType==null){
            LogFactory.info(this,"活动手机号["+phone+"]格式不符或手机运营商为虚拟运营商"+locker);
            return new RetMessage("1009","手机号格式不符或手机运营商为虚拟运营商！",null).toJson();
        }
        LogFactory.info(this,"活动手机号["+phone+"]运营商为["+(phoneType==1?"移动":phoneType==2?"联通":phoneType==3?"电信":"未知")+"]!"+locker);
        //判断数据库中是否已经存在3条记录
        List<ActivityRecord> records=null;
        EntityWrapper<ActivityRecord> wrapper=new EntityWrapper<>();
        wrapper.eq("phone",phone);
        //添加对应手机号的记录
        records=activityRecordDao.selectList(wrapper);
        LogFactory.info(this,"活动手机号["+phone+"]活动次数["+records.size()+"]!"+locker);
        int limit=ConfigManager.get("limit")==null?3:Integer.parseInt(ConfigManager.get("limit"));
//        wrapper=new EntityWrapper<>();
//        //添加对应mac的记录
//        wrapper.eq("mac",mac);
//        records.addAll(activityRecordDao.selectList(wrapper));
        if(records.size()>=limit){
            LogFactory.info(this,"活动手机号["+phone+"]活动次数["+records.size()+"]已达到上限!无法领取奖励!"+locker);
            return new RetMessage("3009","活动次数已达到上限!无法领取奖励!",null).toJson();
        }

        Gift gift=null;
        //根据运营商判断结果查找对应的流量奖励
        switch (flow){
            case "50":{
                switch (phoneType){
                    case 1:gift=Gift.MOBILE_50;break;
                    case 2:gift=Gift.UNICOM_50;break;
                    case 3:gift=Gift.UNION_50;break;
                    default:return new RetMessage("2019","流量值参数异常!",null).toJson();
                }
            };break;
            case "100":{
                switch (phoneType){
                    case 1:gift=Gift.MOBILE_100;break;
                    case 2:gift=Gift.UNICOM_100;break;
                    case 3:gift=Gift.UNION_100;break;
                    default:return new RetMessage("2019","流量值参数异常!",null).toJson();
                }
            };break;
            default:{
                LogFactory.info(this,"活动手机号["+phone+"]流量值参数["+flow+"]异常!"+locker);
                return new RetMessage("2009","流量值参数异常!",null).toJson();
            }
        }
        LogFactory.info(this,"活动手机号["+phone+"]奖励["+gift+"]!"+locker);
        //添加活动记录,等到分享后才真正发放奖励
        ActivityRecord record=new ActivityRecord();
        record.setRecordId(IdWorker.getId());
        record.setMac(mac);
        record.setPhone(phone);
        record.setTime(timeSuppile.get());
        record.setFlows(gift.getGiftId());
        record.setRemark(gift.getName());
        record.setExchanged(ActivityRecord.NOT_EXCHANGED);
        boolean flag=record.insert();
        LogFactory.info(this,"活动手机号["+phone+"]本次活动记录["+record+"]!"+locker);
        //兑换奖励
        if(flag){
            LogFactory.info(this,"活动手机号["+phone+"]添加活动记录成功,前往兑换!"+locker);
            return exchangeGift(record);
        }
        else{
            LogFactory.info(this,"活动手机号["+phone+"]添加活动记录失败,稍后在兑换!"+locker);
            return new RetMessage("0000","参与活动成功",null).toJson();
        }
    }

    @Override
    public String exchangeGift(ActivityRecord record) {
        //若活动记录标记为已经完成兑换了,则将不再兑换
        if(ActivityRecord.HAS_EXCHANGED.equals(record.getExchanged())){
            LogFactory.info(this,"活动记录["+record.getRecordId()+"]已经完成了兑换!不能再次兑换"+record);
            return new RetMessage("5009","记录["+record+"]已经完成了奖励发放,无法再次兑换!", null).toJson();
        }
        //根据活动记录查找对应的兑换记录
        EntityWrapper<ExchangeRecord> wrapper=new EntityWrapper<>();
        wrapper.eq("activity_record_id",record.getRecordId());
        List<ExchangeRecord> records=exchangeRecordDao.selectList(wrapper);
        //若兑换记录为空,则新建兑换
        if(records.isEmpty()){
            LogFactory.info(this,"活动记录["+record.getRecordId()+"]尝试进行兑换!"+record);
            ApiRequest request=new ApiRequest(record.getPhone(),record.getFlows());
            request.setKey(ConfigManager.get("key"));
            request.setAppsecret(ConfigManager.get("appsecret"));
            request.setNotify_url(ConfigManager.get("notifyUrl"));
            request.sign();
            String responseStr=request.getRequest();
            LogFactory.info(this,"活动记录["+record.getRecordId()+"]美洋响应["+responseStr+"]"+record);
            if(responseStr==null){
                LogFactory.info(this,"活动记录["+record.getRecordId()+"]美阳响应为空!"+record);
                return new RetMessage("4009","美阳响应空",null).toJson();
            }
            ApiResponse response= JSON.parseObject(responseStr,ApiResponse.class);
            //装配兑换记录
            ExchangeRecord exchangeRecord=new ExchangeRecord();
            exchangeRecord.setGiftId(record.getFlows());
            exchangeRecord.setGiftName(record.getRemark());
            exchangeRecord.setMac(record.getMac());
            exchangeRecord.setPhone(record.getPhone());
            exchangeRecord.setRecordId(IdWorker.getId());
            exchangeRecord.setRecordTime(timeSuppile.get());
            exchangeRecord.setActivityRecordId(record.getRecordId());
            exchangeRecord.setRetryTimes(0);
            //若兑换成功,新增兑换记录,并更新对应的活动记录
            if(response.getCode().equals(ApiResponse.SUCCESS)){
                exchangeRecord.setOrderNum(response.getOrder_num());
                exchangeRecord.setStatus(ExchangeRecord.getStatusSuccess());
                exchangeRecord.setCode(response.getCode());
                exchangeRecord.setErrmsg(response.getErrmsg());
                exchangeRecord.insert();
                record.setExchanged(ActivityRecord.HAS_EXCHANGED);
                record.updateById();
                LogFactory.info(this,"活动记录["+record.getRecordId()+"]更新兑换标记,新增兑换记录["+exchangeRecord+"]"+record);
                return new RetMessage("0000","兑换奖励成功!",null).toJson();
            }else{
                LogFactory.info(this,"活动记录["+record.getRecordId()+"]兑换失败!"+record);
                return new RetMessage("6009","兑换奖励失败!",null).toJson();
            }
        }else{
            //若兑换记录不为空,而活动记录标记为未兑换,按已经兑换处理,更新活动记录为已经兑换
            if(records.size()==1){
                ExchangeRecord exchangeRecord=records.get(0);
                if(exchangeRecord.getStatus().equals(ExchangeRecord.STATUS_SUCCESS)||exchangeRecord.getStatus().equals(ExchangeRecord.STATUS_ARRIVE)){
                    record.setExchanged(ActivityRecord.HAS_EXCHANGED);
                    record.updateById();
                    LogFactory.info(this,"活动记录["+record.getRecordId()+"]已经完成了兑换但未更新兑换标记!更新兑换标记"+record);
                    return new RetMessage("0000","兑换奖励成功!",null).toJson();
                }
            }
            LogFactory.info(this,"活动记录["+record.getRecordId()+"]兑换失败!但返回成功!"+record);
            return new RetMessage("0000","兑换奖励成功!",null).toJson();
        }
    }

    @Override
    public boolean checkPhone(String phone, String mac) {
        return false;
    }

    @Override
    public String notify(HttpServletRequest req, HttpServletResponse res) {
        String trade_id=req.getParameter("trade_in");
        String order_num=req.getParameter("order_num");
        String status=req.getParameter("status");
        String card_key=req.getParameter("card_key");
        String card_password=req.getParameter("card_password");
        String errmsg=req.getParameter("errmsg");
        String price=req.getParameter("price");

        NotifyResponse response=new NotifyResponse();
        response.setNotifyId(IdWorker.getId());
        response.setTrade_id(trade_id);
        response.setOrder_num(order_num);
        response.setStatus(status);
        response.setCard_key(card_key);
        response.setCard_password(card_password);
        response.setPrice(price);
        response.setErrmsg(errmsg);
        response.insert();
        response.setNofity_time(timeSuppile.get());

        List<ExchangeRecord> records=exchangeRecordDao.selectList(new EntityWrapper<ExchangeRecord>().eq("order_num",response.getOrder_num()));
        for(ExchangeRecord each:records){
            each.setStatus(ExchangeRecord.STATUS_ARRIVE);
            each.updateById();
        }
        LogFactory.info(this,"收到回调通知"+response);



        return "success";
    }

    @Override
    public String shareIn(HttpServletRequest req, HttpServletResponse res) {
        //获取参数
        String phone=req.getParameter("phone");
        String mac=req.getParameter("mac");
        //查找分享手机号与mac 对应的分享记录
        EntityWrapper<ShareRecord> wrapper=new EntityWrapper<>();
//        wrapper.eq("share_phone",phone);
        wrapper.eq("accept_mac",mac);
        List<ShareRecord> records=shareRecordDao.selectList(wrapper);
        //若接收分享的mac没有对应的分享记录
        if(records.isEmpty()){
            //根据分享的手机号查找对应的活动记录
            EntityWrapper<ActivityRecord> arWrapper=new EntityWrapper<>();
            arWrapper.eq("phone",phone);
            List<ActivityRecord> activityRecords=activityRecordDao.selectList(arWrapper);
            if(activityRecords.isEmpty()){
                //分享者并没有参与活动
                return new RetMessage("7009","分享手机号["+phone+"]没有对应活动记录!",null).toJson();
            }
            //添加分享记录
            ActivityRecord activityRecord=activityRecords.get(0);
            ShareRecord record=new ShareRecord();
            record.setAcceptMac(mac);
            record.setAcceptTime(timeSuppile.get());
            record.setRecordId(IdWorker.getId());
            record.setShareMac(activityRecord.getMac());
            record.insert();
            return new RetMessage("8009","mac["+mac+"]接受["+phone+"]邀请成功!",null).toJson();
        }else{
            //接受分享的mac已经接受了其他邀请
            return new RetMessage("8009","该mac["+mac+"]已经接受了其他邀请了!",null).toJson();
        }

    }

    @Override
    public String giftQuery(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }

    public String notifyTest(){
        //测试商品移动10m
        ApiRequest request=new ApiRequest("15068610940","cc00176002");
        request.setKey(ConfigManager.get("key"));
        request.setAppsecret(ConfigManager.get("appsecret"));
        request.setNotify_url(ConfigManager.get("notifyUrl"));
        request.setNumber(1);
        String responseStr=request.getRequest();
        LogFactory.info(this, "测试生产响应"+responseStr);
        return responseStr;
    }
}
