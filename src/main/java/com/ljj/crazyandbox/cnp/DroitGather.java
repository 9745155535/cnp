package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;

import java.util.List;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 权力集
 */
public interface DroitGather {

    List<? extends GrantedAuthority> getGrantedAuthority();

    void addGrantedAuthority(GrantedAuthority grantedAuthority);

    boolean isDroitExist();

}
