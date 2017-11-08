package net.imwork.yangyuanjian.catchactivity.test;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.catchactivity.impl.dao.ActivityRecordDao;
import net.imwork.yangyuanjian.catchactivity.impl.entity.ActivityRecord;
import net.imwork.yangyuanjian.catchactivity.impl.entity.Gift;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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
}
