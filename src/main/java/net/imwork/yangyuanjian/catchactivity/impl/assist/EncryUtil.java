package net.imwork.yangyuanjian.catchactivity.impl.assist;

import java.security.MessageDigest;

/**
 * Created by thunderobot on 2017/11/6.
 */
public class EncryUtil {

    /**
     * md5摘要算法
     *
     * @param src
     *            明文字节码
     * @return   md5摘要的字节
     */
    public static byte[] md5En(byte[] src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(src);
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException("md5摘要算法异常", e);
        }
    }
    /**
     * md5摘要算法 补0
     *
     * @param src  要md5摘要的字符串
     * @param  charset  字符串转字节的 字符集
     * @return 	 md5摘要的结果
     */
    public static String md5LowerCase(String src, String charset) {
        try {
            if (src == null || src.length() < 1)
                throw new RuntimeException("md5摘要时,明文信息为null或为空");
            byte b[] = md5En(src.getBytes(charset));
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();// 32位的加密
        } catch (Exception e) {
            throw new RuntimeException("md5摘要算法异常", e);
        }
    }
}
