package net.imwork.yangyuanjian.catchactivity.xiadong;


import net.imwork.yangyuanjian.catchactivity.impl.assist.LogFactory;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信工具类
 * @author Stephen
 *
 */
@Service
public class WeixinUtilService {
	//private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	//关注公众号后获取用户信息
	private static final String GET_MEMBER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	//网页授权获取用户信息
	private static final String GET_MEMBER_INFO_FROM_AOUTH_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	//引导用户进入授权页面
	private static final String AUTHOR_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	//获取网页授权access_token
	private static final String WEB_AOUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	private static AccessToken ACCESS_TOKEN = AccessToken.getInstance();


	private static final String APP_ID = "wx131ee64199604dfb";
	private static final String ACCESS_TOKEN_URL = "http://jsbank-myoungad.haotunet.com/get_wx_access_token/";



	//Logger logger = Logger.getLogger(WeixinUtilService.class);

	private static String postJson(JSONObject json) {


		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(ACCESS_TOKEN_URL);

		post.setHeader("Content-Type", "application/json");
		//post.addHeader("Authorization", "Basic YWRtaW46");
		String result = "";

		try {

			StringEntity s = new StringEntity(json.toString(), "utf-8");
			s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(s);

			// 发送请求
			HttpResponse httpResponse = client.execute(post);

			// 获取响应输入流
			InputStream inStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
			StringBuilder strber = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				strber.append(line + "\n");
			inStream.close();

			result = strber.toString();
			System.out.println(result);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				System.out.println("请求服务器成功，做相应处理");

			} else {

				System.out.println("请求服务端失败");

			}


		} catch (Exception e) {
			System.out.println("请求异常");
			throw new RuntimeException(e);
		}

		return result;
	}

	private String getAccessToken2(){

        Long now = System.currentTimeMillis() / 1000;
        System.out.println("now = "+now);

        long expiresTime = ACCESS_TOKEN.getExpiresTime();



        // 若token不存在，或 已超时
        if (null == ACCESS_TOKEN.getToken() || now > expiresTime) {

            LogFactory.info(this,"token 不存在或已过期...");


            JSONObject obj = new JSONObject();
            obj.put("APP_ID", APP_ID);
            JSONObject tokenObj = JSONObject.fromObject(postJson(obj));

            String token = tokenObj.getString("access_token");
            String deadline = tokenObj.getString("deadline");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
               date  = sdf.parse(deadline);
               expiresTime = date.getTime()/1000;

            }catch(java.text.ParseException e){
                e.printStackTrace();
            }

            ACCESS_TOKEN.setToken(token);
            ACCESS_TOKEN.setExpiresTime(expiresTime-200);
            LogFactory.info(this,"ExpiresTime = "+ACCESS_TOKEN.getExpiresTime());

        }

        LogFactory.info(this,"Token = "+ACCESS_TOKEN.getToken());
        return ACCESS_TOKEN.getToken();

	}


	public Map<String,Object> getSigunature(String weburl) throws Exception{
		Long timestamp = System.currentTimeMillis()/1000;
		int noncestr = new Random().nextInt();


		String accessToken = getAccessToken2();
		System.out.println("accessToken:" + accessToken);
		//获取jsapi_ticket
		String jsapi_ticket = null;
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
			String responseText = doGetStr(url).toString();
			jsapi_ticket = null;
			JSONObject object = JSONObject.fromObject(responseText);
			if (object.containsKey("ticket")) {
				jsapi_ticket = object.getString("ticket");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//生成signature
		List<String> nameList = new ArrayList<String>();
		nameList.add("noncestr");
		nameList.add("timestamp");
		nameList.add("url");
		nameList.add("jsapi_ticket");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("noncestr", noncestr);
		valueMap.put("timestamp", timestamp);
		valueMap.put("url", weburl);
		valueMap.put("jsapi_ticket", jsapi_ticket);
		Collections.sort(nameList);
		String origin = "";
		for (int i = 0; i < nameList.size(); i++) {
			origin += nameList.get(i) + "=" + valueMap.get(nameList.get(i)).toString() + "&";
		}
		origin = origin.substring(0, origin.length() - 1);

		String signature = CommonUtil.sha1(origin);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsapi_ticket", jsapi_ticket);
		map.put("appId", APP_ID);
		map.put("signature", signature.toLowerCase());
		map.put("timestamp", timestamp.toString());
		map.put("noncestr", String.valueOf(noncestr));

		return map;
	}

	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}



	public static void main(String args[]) throws ParseException, IOException, Exception{
        WeixinUtilService wus = new WeixinUtilService();
//		String token = wus.getAccessToken2();
//        System.out.println(token);
        wus.getSigunature("http://gmc.tunnel.qydev.com/yinliangame/index.html");
    }





}
