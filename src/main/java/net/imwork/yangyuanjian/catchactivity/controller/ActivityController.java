package net.imwork.yangyuanjian.catchactivity.controller;

import net.imwork.yangyuanjian.catchactivity.impl.assist.ConfigManager;
import net.imwork.yangyuanjian.catchactivity.impl.assist.LogFactory;
import net.imwork.yangyuanjian.catchactivity.impl.entity.ApiRequest;
import net.imwork.yangyuanjian.catchactivity.impl.service.ActivityServiceImpl;
import net.imwork.yangyuanjian.catchactivity.spi.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by thunderobot on 2017/11/5.
 */
@Controller
public class ActivityController {
    private String testGift="cc00176002";
    @Resource
    private ActivityService service;
    @RequestMapping(value = "/enjoy",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String enjoyActivity(HttpServletRequest req, HttpServletResponse res){
        res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/json;charset=utf-8");
        return service.addActivityRecord(req,res);
    }
    @RequestMapping(value = "/notify",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String notify(HttpServletRequest req,HttpServletResponse res){
        res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/json;charset=utf-8");
        return service.notify(req,res);
    }
    @RequestMapping(value = "/notifyTest",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testOnly(){
       return ((ActivityServiceImpl) service).notifyTest();
    }
}
