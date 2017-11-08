package net.imwork.yangyuanjian.catchactivity.impl.entity;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.catchactivity.impl.assist.EncryUtil;
import net.imwork.yangyuanjian.catchactivity.impl.assist.HttpUtil;
import org.omg.CORBA.OBJ_ADAPTER;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 流量接口参数
 * Created by thunderobot on 2017/11/6.
 */

public class ApiRequest {

    /**流量充值url*/
    private static final String URL="https://card.haotunet.com/card_api/api/?";
    /**商户编号*/
    private static final String KEY="meiyangtest";
    /**商户密钥*/
    private static final String APPSECRET="meiyangtest";
    /**数量*/
    private static final Integer NUMBER=1;

    private static final String NOTIFY_URL=null;

    /**商户编号*/
    private String key;
    /**商户密钥*/
    String appsecret;
    /**产品编码*/
    private String commodity;
    /**时间戳*/
    private Long timestamp;
    /**客户手机号码*/
    private String phone;
    /**数量*/
    private Integer number;
    /**回调地址*/
    private String notify_url;
    /**订单号（商户系统 订单流水号）*/
    private String trade_id;
    /**32 位小写签名*/
    private String sign;

    public ApiRequest(String commodity,String phone){
        this(null,null,commodity,null,phone,null,null,null,null);
    }

    public ApiRequest(String key, String appsecret, String commodity, Long timestamp, String phone, Integer number, String notify_url, String trade_id, String sign) {
        if(KEY==null)
            this.key = key;
        else
            this.key=KEY;
        if(APPSECRET==null)
            this.appsecret = appsecret;
        else
            this.appsecret =APPSECRET;
        if(NUMBER==null)
            this.number = number;
        else
            this.number = NUMBER;
        if(NOTIFY_URL==null)
            notify_url=notify_url;
        else
            this.notify_url = NOTIFY_URL;
        this.trade_id = IdWorker.getId()+"";

        this.timestamp = System.currentTimeMillis()/1000;

        this.commodity = commodity;
        this.phone = phone;
    }

    public ApiRequest(){

    }

    public String getRequest(){
        return HttpUtil.get(toGetUrl(),"utf-8");
    }

    public String toGetUrl(){
        return URL+toGetParam();
    }

    private String toGetParam(){
        if(this.sign==null)
            sign();
        Field[] fields=ApiRequest.class.getDeclaredFields();
        List<Field> list= Arrays.asList(fields);
        Map<String,Object> map=new HashMap<>();
        list.parallelStream().filter(e-> !Modifier.isStatic(e.getModifiers())).forEach(e -> {
            try {
                e.setAccessible(true);
                Object value=e.get(this);
                if(value!=null)
                    map.put(e.getName(), e.get(this));
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        });
//        System.out.println(map);
        List<String> keys=new ArrayList<>(map.keySet());
        Collections.sort(keys);
        StringBuilder builder=new StringBuilder();
        keys.stream().forEach(e->{
            if(!e.equals("appsecret"))
                builder.append(e+"="+map.get(e)+"&");
        });
        builder.replace(builder.length()-1,builder.length(),"");
        return builder.toString();
    }

    public String sign(){
//        ApiRequest request=new ApiRequest(111+"",222+"",333,1509937086,"1503547614",1,"http://notyfy_url.com","20171101253365",null);
        Field[] fields=ApiRequest.class.getDeclaredFields();
        List<Field> list= Arrays.asList(fields);
        Map<String,Object> map=new HashMap<>();
        list.parallelStream().filter(e-> !Modifier.isStatic(e.getModifiers())).forEach(e -> {
            try {
                if(!e.getName().equals("sign")){
                    e.setAccessible(true);
                    Object value=e.get(this);
                    if(value!=null)
                        map.put(e.getName(), e.get(this));
                }
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        });
//        System.out.println(map);
        List<String> keys=new ArrayList<>(map.keySet());
        Collections.sort(keys);
        StringBuilder builder=new StringBuilder();
        keys.stream().forEach(e->builder.append(e+"="+map.get(e)+"&"));
        builder.replace(builder.length()-1,builder.length(),"");
        String sign=EncryUtil.md5LowerCase(builder.toString(),"utf-8");
        this.sign=sign;
        return sign;
    }


    @Override
    public String toString() {
        return "ApiRequest{" +
                "key='" + key + '\'' +
                ", appsecret='" + appsecret + '\'' +
                ", commodity=" + commodity +
                ", timestamp=" + timestamp +
                ", phone='" + phone + '\'' +
                ", number=" + number +
                ", notify_url='" + notify_url + '\'' +
                ", trade_id='" + trade_id + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
