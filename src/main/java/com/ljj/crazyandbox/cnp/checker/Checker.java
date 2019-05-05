package com.ljj.crazyandbox.cnp.checker;


import com.ljj.crazyandbox.cnp.DroitRequest;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;

/**
 * @author ljj
 * create time by 2019.4.2
 * 检验者
 */
public interface Checker {

    boolean request(DroitRequest request);


    GrantedAuthority getGrantedAuthority();
}
