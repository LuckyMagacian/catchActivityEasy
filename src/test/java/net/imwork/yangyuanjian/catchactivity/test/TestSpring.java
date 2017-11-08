package net.imwork.yangyuanjian.catchactivity.test;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.catchactivity.impl.assist.ConfigManager;
import net.imwork.yangyuanjian.catchactivity.impl.assist.LogFactory;
import net.imwork.yangyuanjian.catchactivity.impl.dao.ActivityRecordDao;
import net.imwork.yangyuanjian.catchactivity.impl.entity.ActivityRecord;
import net.imwork.yangyuanjian.catchactivity.impl.entity.Gift;
import net.imwork.yangyuanjian.catchactivity.impl.service.ActivityServiceImpl;
import net.imwork.yangyuanjian.catchactivity.spi.ActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thunderobot on 2017/11/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:xml/spring.xml")
public class TestSpring {
    @Resource
    private ActivityRecordDao dao;
    @Resource
    private ActivityService service;
    @Test
    public void test(){
        ActivityRecord record=new ActivityRecord();
        record.setRecordId(IdWorker.getId());
        record.setPhone("15068610940");
        record.setFlows(Gift.MOBILE_100.getGiftId());
        record.setRemark(Gift.MOBILE_100.getName());
        record.setMac("66666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666");
        record.setTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        record.setExchanged(ActivityRecord.NOT_EXCHANGED);
        System.out.println(record);
        record.insert();
    }
    @Test
    public void test2() throws IOException {
        LogFactory.init();
        ConfigManager.init();
        MockHttpServletRequest req=new MockHttpServletRequest();
        req.addParameter("phone","15068610940");
        req.addParameter("mac","10086");
        req.addParameter("flow","50");
        MockHttpServletResponse res=new MockHttpServletResponse();
        System.out.println(service.addActivityRecord(req,res));
    }
    @Test
    public void test3() throws IOException {
        LogFactory.init();
        ConfigManager.init();
        System.out.println(((ActivityServiceImpl)service).notifyTest());
    }
}
