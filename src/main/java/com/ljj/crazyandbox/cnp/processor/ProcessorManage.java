package com.ljj.crazyandbox.cnp.processor;


import com.ljj.crazyandbox.cnp.DroitAuthentication;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;

import java.util.List;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 抽象处理管理者
 */
public interface ProcessorManage {

    DroitAuthentication execute(DroitAuthentication droitAuthentication);


    String[] checkEffectiveProcessor(GrantedAuthority[] effectiveDroit);
}
