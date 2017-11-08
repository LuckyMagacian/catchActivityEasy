package net.imwork.yangyuanjian.catchactivity.impl.assist;

/**
 * Created by thunderobot on 2017/11/6.
 */
public class PhoneCheck {
    /**获取手机运营商*/
    public static Integer getServiceProvider(String phone){
        if(phone==null||phone.isEmpty())
            return null;
        final String  mobileRegex="(13[4-9])|(15[0-2])|(15[7-9])|(18[2-4])|(18[7-8])|(178)|(147)";
        final String  unionRegex="(133)|(153)|(189)|(18[0-1])|(173)|(177)";
        final String  unicomRegex="(13[0-2])|(15[5-6])|(18[5-6])|(145)|(176)";
        phone = phone.substring(0,3);
        if(phone.matches(mobileRegex))
            return 0;
        if(phone.matches(unionRegex))
            return 1;
        if(phone.matches(unicomRegex))
            return 2;
        return null;
    }
}
