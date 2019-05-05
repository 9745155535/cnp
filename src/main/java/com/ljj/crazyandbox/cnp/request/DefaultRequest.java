package com.ljj.crazyandbox.cnp.request;



import com.ljj.crazyandbox.cnp.Client;
import com.ljj.crazyandbox.cnp.DroitRequest;

import java.util.Map;

/**
 * @author ljj
 * create time by 2019.3.29
 */
public class DefaultRequest implements DroitRequest {

    private final Client client;
    private final String topic;
    private final Map<String, String> param;

    public DefaultRequest(Client client, String topic, Map<String, String> param) {
        this.client = client;
        this.topic = topic;
        this.param = param;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public Map<String, String> getParam() {
        return param;
    }
}
