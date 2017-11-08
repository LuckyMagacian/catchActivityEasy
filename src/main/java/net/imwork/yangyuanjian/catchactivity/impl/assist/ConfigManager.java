package net.imwork.yangyuanjian.catchactivity.impl.assist;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Created by thunderobot on 2017/11/5.
 */
public class ConfigManager {



    private static Properties config=new Properties();


    public void init() throws IOException {
        ClassLoader loader = ConfigManager.class.getClassLoader();
        if (loader.getClass().equals(java.net.URLClassLoader.class))
            loader = sun.misc.Launcher.getLauncher().getClassLoader();
        URL url = loader.getResource("");
        String path = null;
        try {
            path = url.toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        config.load(new FileInputStream(path+"properties/param.properties"));
    }

    public static String get(String key){
        return config.getProperty(key);
    }
}
