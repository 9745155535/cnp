package com.ljj.crazyandbox.cnp.processor;


import com.ljj.crazyandbox.cnp.DroitAuthentication;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 抽象处理者
 */
public interface Processor {

    String handle(DroitAuthentication droitAuthentication);

    String getName();

    String getRuleString();


}
