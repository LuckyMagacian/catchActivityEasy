package net.imwork.yangyuanjian.catchactivity.xiadong;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by summer on 2017/11/20.
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController {
    @Autowired
    private WeixinUtilService weixinUtilService;

    @RequestMapping("get-signature")
    public void getSignature(HttpServletRequest request, HttpServletResponse response, String url) throws Exception{

        Map<String, Object> map = weixinUtilService.getSigunature(url);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            JSONObject responseObject = JSONObject.fromObject(map);
            writer.print(responseObject);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            writer.flush();
            writer.close();
        }

    }






}
