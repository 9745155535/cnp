package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;
import com.ljj.crazyandbox.cnp.processor.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 抽象认证模型
 */
public abstract class AbstractDroitAuthentication implements DroitRequest, DroitGather, ProcessorGather {
    private final Client client;

    private final String topic;

    private final Map<String, String> param;

    private final List<GrantedAuthority> grantedAuthority;

    private final List<Processor> processors;


    protected AbstractDroitAuthentication(Client client, String topic, Map<String, String> param) {
        this.client = client;
        this.topic = topic;
        this.param = param;
        grantedAuthority = new ArrayList<>();
        processors = new ArrayList<>();
    }

    @Override
    public void addGrantedAuthority(GrantedAuthority grantedAuthority) {
        this.grantedAuthority.add(grantedAuthority);
    }

    @Override
    public List<GrantedAuthority> getGrantedAuthority() {
        return this.grantedAuthority;
    }

    @Override
    public List<Processor> getProcessors() {
        return this.processors;
    }

    @Override
    public void addProcessors(Processor processor) {
        this.processors.add(processor);
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }

    @Override
    public Map<String, String> getParam() {
        return this.param;
    }

    @Override
    public boolean isDroitExist() {
        return grantedAuthority.size() < 0 ? false : true;
    }

    @Override
    public boolean isProcessorExist() {
        return processors.size() < 0 ? false : true;
    }


}
