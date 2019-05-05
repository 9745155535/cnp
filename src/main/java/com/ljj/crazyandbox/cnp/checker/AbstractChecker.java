package com.ljj.crazyandbox.cnp.checker;

import com.ljj.crazyandbox.cnp.DroitRequest;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;

/**
 * @author ljj
 * create time by 2019.4.2
 * 抽象检验者
 */
public abstract class AbstractChecker implements Checker {

    private final GrantedAuthority grantedAuthority;

    protected AbstractChecker(GrantedAuthority grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    @Override
    public boolean request(DroitRequest request) {
        return check(request);
    }

    @Override
    public GrantedAuthority getGrantedAuthority() {
        return this.grantedAuthority;
    }

    protected abstract boolean check(DroitRequest request);

}
