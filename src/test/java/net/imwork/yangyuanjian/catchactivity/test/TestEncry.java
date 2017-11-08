package net.imwork.yangyuanjian.catchactivity.test;

import net.imwork.yangyuanjian.catchactivity.impl.assist.EncryUtil;
import net.imwork.yangyuanjian.catchactivity.impl.entity.ApiRequest;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by thunderobot on 2017/11/6.
 */
public class TestEncry {

//    @Test
//    public void test1(){
//        ApiRequest request=new ApiRequest(111+"",222+"",333,1509937086,"1503547614",1,"http://notyfy_url.com","20171101253365",null);
////        Field[] fields=ApiRequest.class.getDeclaredFields();
////        List<Field> list=Arrays.asList(fields);
////        Map<String,Object>map=new HashMap<>();
////        list.parallelStream().forEach(e -> {
////            try {
////                if(!e.getName().equals("sign")){
////                    e.setAccessible(true);
////                    map.put(e.getName(), e.get(request));
////                }
////            } catch (IllegalAccessException e1) {
////                e1.printStackTrace();
////            }
////        });
////        System.out.println(map);
////        List<String> keys=new ArrayList<>(map.keySet());
////        Collections.sort(keys);
////        StringBuilder builder=new StringBuilder("'");
////        keys.stream().forEach(e->builder.append(e+"="+map.get(e)+"&"));
////        builder.replace(builder.length()-1,builder.length(),"'");
////        System.out.println(builder.toString());
////        System.out.println(EncryUtil.md5LowerCase(builder.toString(),"utf-8"));
////        System.out.println(EncryUtil.md5LowerCase(builder.toString(),"gbk"));
////        System.out.println(EncryUtil.md5LowerCase(builder.toString(),"iso8859-1"));
//        System.out.println(request.sign());
//        System.out.println(request.toGetUrl());
//        System.out.println(request.getRequest());
//    }

    @Test
    public void test2(){
        System.out.println((System.currentTimeMillis()+"").length());
        System.out.println("1509937086".length());
    }
}
