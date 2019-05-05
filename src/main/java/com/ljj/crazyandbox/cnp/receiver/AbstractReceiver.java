package com.ljj.crazyandbox.cnp.receiver;


import com.ljj.crazyandbox.cnp.Client;
import com.ljj.crazyandbox.cnp.DroitGather;
import com.ljj.crazyandbox.cnp.DroitRequest;
import com.ljj.crazyandbox.cnp.StringUtility;
import com.ljj.crazyandbox.cnp.checker.CheckerManage;

/**
 * @author ljj
 * create time by 2019.4.1
 * 触发接收器
 */
public abstract class AbstractReceiver implements Receiver {
    private final CheckerManage checkerManage;
    private DroitRequest request;
    public AbstractReceiver(CheckerManage checkerManage) {
        this.checkerManage = checkerManage;
    }

    @Override
    public DroitGather receive(Object request) {
        this.request = setRequest(request);
        CheckerManage checkManage = this.getCheckManage();
        return checkManage.grant(checkDroitRequest());
    }

    public CheckerManage getCheckManage() {
        return this.checkerManage;
    }
    protected abstract DroitRequest setRequest(Object request);


    protected DroitRequest getRequest() {
        return this.request;
    }

    private DroitRequest checkDroitRequest() {
        String topic = request.getTopic();
        Client client = request.getClient();
        if (!StringUtility.allNotBlank(topic) || client == null){
            throw new RuntimeException(this.getClass().getSimpleName() +": droit request error");
        }
        return request;
    }


}
