package com.ljj.crazyandbox.cnp.droit;

import java.io.Serializable;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 权力模型
 */
public abstract class GrantedAuthority implements Serializable {

    public abstract String getValue();

    @Override
    public boolean equals(Object obj) {
        return obj.equals(getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
