package com.ljj.crazyandbox.cnp;


import java.util.Map;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 权力请求
 */
public interface DroitRequest{


    /**
     * 请求模块
     * @return
     */
    Client getClient();

    /**
     * 请求的主题，
     * @return
     */
    String getTopic();

    /**
     * 相关参数
     * @return
     */
    Map<String,String> getParam();
}
