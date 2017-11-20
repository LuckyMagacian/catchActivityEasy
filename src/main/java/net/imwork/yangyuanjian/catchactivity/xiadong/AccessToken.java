package net.imwork.yangyuanjian.catchactivity.xiadong;

public class AccessToken {
    //私有的默认构造子
    private AccessToken() {
    }

    //已经自行实例化
    private static final AccessToken single = new AccessToken();

    //静态工厂方法
    public static AccessToken getInstance() {
        return single;
    }


    private String token;
    private long expiresTime;//失效时间，不再是微信服务器返回的7200秒有效时间

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

}

