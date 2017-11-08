package net.imwork.yangyuanjian.catchactivity.impl.assist;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by thunderobot on 2017/11/3.
 */
public class HttpUtil {
    /**utf-8字符集*/
    private static final String UTF8="utf-8";
    /**默认的超时时间*/
    private static final int defTimeout=1000;

    /**
     * 发送请求
     *
     * @param content
     *            请求内容
     * @param outStream
     *            输出流
     * @param charset
     *            编码字符集 默认utf-8
     */
    private static void post(String content, OutputStream outStream, String charset) {
        try {
            charset = charset == null ? UTF8 : charset;
            OutputStreamWriter writer;
            writer = new OutputStreamWriter(outStream, charset);
            PrintWriter printer = new PrintWriter(writer);
            printer.println(content);
            printer.close();
        } catch (Exception e) {
            throw new RuntimeException("发送post请求异常", e);
        }
    }

    /**
     * 接受请求
     *
     * @param inStream
     *            输入流
     * @param charset
     *            解码字符集 默认utf-8
     * @return 请求内容
     */
    private static String receive(InputStream inStream, String charset) {
        try {
            charset = charset == null ? UTF8 : charset;
            InputStreamReader reader = new InputStreamReader(inStream, charset);
            BufferedReader buffReader = new BufferedReader(reader);
            String temp;
            StringBuffer reply = new StringBuffer();
            while ((temp = buffReader.readLine()) != null)
                reply.append(temp);
            buffReader.close();
            return reply.toString();
        } catch (Exception e) {
            throw new RuntimeException("接收数据异常", e);
        }
    }


    /**
     * 通过给定的url发送内容,并返回接收方返回的内容,实现了https
     *
     * @param content
     *            发送的内容
     * @param Url
     *            接收方地址
     * @param charset
     *            编码解码字符集 默认utf-8
     * @param type
     *            发送内容格式
     * @param timeout
     *            超时时间
     * @return 接收方返回的内容
     */
    public static String post(String content, String Url, String charset, String type, Integer timeout) {
        try {
            System.out.println("url:"+Url);
            charset = charset == null ? UTF8 : charset;
            timeout = timeout == null ? defTimeout : timeout;
            URL url = new URL(Url);
            HttpsURLConnection conns=null;
            HttpURLConnection conn 	= (HttpURLConnection) url.openConnection();
            if(Url.toLowerCase().startsWith("https"))
                conns=(HttpsURLConnection)conn;
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            if (type != null)
                conn.setRequestProperty("Content-Type", type + ";Charset=" + charset);

            if(conns==null){
                conn.connect();
                post(content, conn.getOutputStream(), charset);
                if (conn.getResponseCode() == 200)
                    return receive(conn.getInputStream(), charset);
            }else{
                conns.connect();
                post(content, conns.getOutputStream(), charset);
                if (conns.getResponseCode() == 200)
                    return receive(conns.getInputStream(), charset);
            }
        } catch (Exception e) {
            throw new RuntimeException("发送post请求异常", e);
        }
        return null;
    }


    /**
     * 发送字符串信息
     *
     * @param str
     *            字符串内容
     * @param url
     *            接收方地址
     * @param charset
     *            编码字符集 默认 utf-8
     * @param timeout
     *            超时时间
     * @return 接收方返回的内容
     */
    public static String postStr(String str, String url, String charset, Integer timeout) {
        return post(str, url, charset, null, timeout);
    }

    /**
     * 发送键值对
     *
     * @param params
     *            键值对
     * @param url
     *            目标url
     * @param charset
     *            编解码字符集
     * @return 发送键值对的响应
     */
    public static String postKeyValue(Map<String, String> params, String url, String charset) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> keyValue = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> each : params.entrySet())
                keyValue.add(new BasicNameValuePair(each.getKey(), each.getValue()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(keyValue);
            entity.setContentEncoding(charset);
            post.setEntity(entity);
            HttpResponse res = client.execute(post);

            BufferedReader buffReader = new BufferedReader(
                    new InputStreamReader(res.getEntity().getContent(), charset));
            StringBuffer strBuff = new StringBuffer();
            String temp = null;
            while ((temp = buffReader.readLine()) != null)
                strBuff.append(temp);
            return strBuff.toString();

        } catch (Exception e) {
            throw new RuntimeException("发送键值对异常", e);
        }
    }
    /**
     * 发送get 请求
     *
     * @param url
     *            地址+直接参数跟随
     * @param charset
     *            字符集
     * @return get到的字符串
     */
    public static String get(String url, String charset) {
        try {
            charset=charset==null?UTF8:charset;
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet();
            get.setURI(new URI(url));
            HttpResponse res = client.execute(get);
            BufferedReader buffReader = new BufferedReader(
                    new InputStreamReader(res.getEntity().getContent(), charset));
            StringBuffer strBuff = new StringBuffer();
            String temp = null;
            while ((temp = buffReader.readLine()) != null)
                strBuff.append(temp);
            return strBuff.toString();
        } catch (Exception e) {
            throw new RuntimeException("发送get请求异常", e);
        }
    }


    /**
     * 发送get请求
     *
     * @param url
     *            目标url
     * @param param
     *            参数map
     * @param charset
     *            字符集
     * @return http get方法的返回字符串
     */
    public static String get(String url, Map<String, Object> param, String charset) {
        StringBuffer params = new StringBuffer("?");
        for (Map.Entry<String, Object> each : param.entrySet())
            params.append(each.getKey() + "=" + each.getValue() + "&");
        return get(url + params.substring(0, params.length()), charset);
    }
}
