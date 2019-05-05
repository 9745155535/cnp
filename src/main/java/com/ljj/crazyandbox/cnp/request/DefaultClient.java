package com.ljj.crazyandbox.cnp.request;


import com.ljj.crazyandbox.cnp.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 服务模块
 */
@Data
@AllArgsConstructor
public class DefaultClient implements Client {

    private String name;
    /**
     * 可多服务请求
     */
    private String[] serve;

    @Override
    public String[] gather() {
        return this.serve;
    }
    @Override
    public String getName(){
        return this.name;
    }
}
