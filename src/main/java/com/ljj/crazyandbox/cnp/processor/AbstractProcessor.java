package com.ljj.crazyandbox.cnp.processor;


import com.ljj.crazyandbox.cnp.DroitAuthentication;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 抽象处理者
 */
public abstract class AbstractProcessor implements Processor {

    private final String name;

    private final String ruleString;

    protected AbstractProcessor(String name, String ruleString) {
        this.name = name;
        this.ruleString = ruleString;
     }

    @Override
    public String handle(DroitAuthentication droitAuthentication) {
        process(droitAuthentication);
        return getName();
    }

    public abstract DroitAuthentication process(DroitAuthentication droitAuthentication);

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getRuleString(){
        return this.ruleString;
    }



}
