package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.checker.Checker;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;

/**
 * @author ljj
 * create time by 2019.4.1
 * des 检验者服务
 */
public interface CheckerOperateService extends Partake {

    boolean enableChecker(GrantedAuthority grantedAuthority);

    boolean disableChecker(GrantedAuthority grantedAuthority);

    RuleResolver getRuleResolver();

    Checker getChecker(GrantedAuthority grantedAuthority);

    /**
     * 消费权限的处理者
     * @param grantedAuthority
     * @return
     */
    String[] consumer(GrantedAuthority grantedAuthority);

    Partake.State checkerState(GrantedAuthority grantedAuthority);


}
