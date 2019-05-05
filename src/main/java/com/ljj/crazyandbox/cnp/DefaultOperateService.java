package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.checker.Checker;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;
import com.ljj.crazyandbox.cnp.processor.Processor;

import java.util.Map;

/**
 * @author ljj
 * create time by 2019.4.4
 * des 默认操作服务
 */
public class DefaultOperateService extends AbstractOperateService {
    protected DefaultOperateService(RuleResolver ruleResolver, Map<String, Checker> checkerMap, Map<String, Processor> processorMap) {
        super(checkerMap, processorMap, ruleResolver);
    }

    @Override
    public boolean enableChecker(GrantedAuthority grantedAuthority) {
        return false;
    }

    @Override
    public boolean disableChecker(GrantedAuthority grantedAuthority) {
        return false;
    }

    @Override
    public State checkerState(GrantedAuthority grantedAuthority) {
        return State.Available;
    }

    @Override
    public boolean enableProcessor(String name) {
        return false;
    }

    @Override
    public boolean disableProcessor(String name) {
        return false;
    }

    @Override
    public State processorState(String name) {
        return null;
    }

    @Override
    public Checker[] effectiveCheckers() {
        return this.getCheckers();
    }

    @Override
    public Processor[] effectiveProcessors() {
        return this.getProcessors();
    }
}
