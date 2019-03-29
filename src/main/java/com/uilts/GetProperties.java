package com.uilts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

    /**
     * 获取key对应的value
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        GetProperties readPps = new GetProperties();
        String strCfgPath = "";
        String osType = System.getProperties().getProperty("os.name").toLowerCase();

        if (osType.startsWith("windows")) {
            strCfgPath = "/config_win.properties";
        } else {

            strCfgPath = "/config_linux.properties";

        }

        return readPps.getPropertiesValue(key, strCfgPath);
    }

    public static String getDateBase(String key) {
        String path = GetProperties.class.getResource("/").getPath();
        String websiteURL = (path.replace("/build/classes", "").replace("%20", " ").replace("classes/", "") + "spring/jdbc.properties").replaceFirst("/", "");
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(websiteURL);
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    /**
     * @param @param  key
     * @param @return
     * @return String
     * @throws
     * @Title: getDateBaseFile
     * @Description: 数据库连接配置文件读取
     */
    public static String getDateBaseFile(String key) {
        GetProperties readPps = new GetProperties();
        String strCfgPath = "/jdbc.properties";
        return readPps.getPropertiesValue(key, strCfgPath);
    }

    /**
     * 根据文件路径和key获取value
     *
     * @param key
     * @param filePath
     * @return
     */
    public String getPropertiesValue(String key, String filePath) {
        String value = null;
        Properties properties = new Properties();
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            properties.load(is);
            value = properties.getProperty(key);
            is.close();
        } catch (IOException e) {
            value = null;
        }
        return value;
    }

    public static void main(String[] args) {

    }

}
