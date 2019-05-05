package com.ljj.crazyandbox.cnp;


import java.util.Collection;
/**
 * @author ljj
 * create time by 2019.3.29
 * des 服务模块
 */
public interface Client {

    /**
     * 客户端标识
     * @return
     */
    String getName();

    String[] gather();

}
