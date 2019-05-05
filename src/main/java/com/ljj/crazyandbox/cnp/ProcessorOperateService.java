package com.ljj.crazyandbox.cnp;

import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;
import com.ljj.crazyandbox.cnp.processor.Processor;

/**
 * @author ljj
 * create time by 2019.4.1
 * des 检验者服务
 */
public interface ProcessorOperateService extends Partake {

    boolean enableProcessor(String name);

    boolean disableProcessor(String name);

    RuleResolver getRuleResolver();

    Processor getProcessor(String name);
    /**
     * 所需权限
     * @param name
     * @return
     */
    GrantedAuthority[] provider(String name);

    Partake.State processorState(String name);


}
