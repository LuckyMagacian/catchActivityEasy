package net.imwork.yangyuanjian.catchactivity.xiadong;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by summer on 2017/5/14.
 */
public class CommonUtil {

    public static String getCurrentDatetimeStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmss");
        String datestr = sdf.format(new Date());
        return datestr;
    }

    public static String getCurrentDateStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datestr = sdf.format(new Date());
        return datestr;
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public static String sha1(String origin) throws Exception{
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(origin.getBytes("UTF-8"));
        String signature = bytesToHex(crypt.digest());
        return signature;
    }

    /**
     * 二进制转十六进制
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if(digital < 0) {
                digital += 256;
            }
            if(digital < 16){
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }


    public static void main(String[] args){
        System.out.println(CommonUtil.getCurrentDatetimeStr());
        System.out.println(CommonUtil.getRandomString(10));
    }
}

