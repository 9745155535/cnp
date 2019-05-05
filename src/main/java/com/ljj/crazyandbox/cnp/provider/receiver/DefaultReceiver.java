package com.ljj.crazyandbox.cnp.provider.receiver;


import com.ljj.crazyandbox.cnp.DroitAuthentication;
import com.ljj.crazyandbox.cnp.DroitRequest;
import com.ljj.crazyandbox.cnp.checker.CheckerManage;
import com.ljj.crazyandbox.cnp.receiver.AbstractReceiver;

/**
 * @author ljj
 * create time by 2019.4.3
 * des 默认接收者
 */
public class DefaultReceiver extends AbstractReceiver {


    public DefaultReceiver(CheckerManage checkerManage) {
        super(checkerManage);
    }

    @Override
    protected DroitRequest setRequest(Object request) {
        DroitAuthentication droitAuthentication = null;
        if (request instanceof DroitAuthentication) {
            droitAuthentication = (DroitAuthentication) request;
        }
        return droitAuthentication;
    }
}
